package com.example.harrypotterapp.model.repository

import com.example.harrypotterapp.model.api.ApiService
import com.example.harrypotterapp.model.database.HarryPotterDao
import com.example.harrypotterapp.model.database.HarryPotterEntity
import com.example.harrypotterapp.model.entity.HarryPotterCharacter
import com.example.utilities.ResultState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.kotlin.mock
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class HarryPotterRepositoryTest {

    @Mock
    private lateinit var apiService: ApiService

    @Mock
    private lateinit var harryPotterDao: HarryPotterDao

    private lateinit var repository: HarryPotterRepository

    @Before
    fun setup() {
        repository = HarryPotterRepository(apiService, harryPotterDao)
    }

    @Test
    fun `test getAllCharacters with successful API response`() = runTest {
        // Given: a successful response from the API
        val apiResponseCharacters = listOf(
            HarryPotterCharacter(
                id = "1",
                name = "Harry Potter",
                actor = "Daniel Radcliffe",
                species = "Human",
                house = "Gryffindor",
                dateOfBirth = "1980-07-31",
                alive = true,
                image = "image_url"

            )
        )
        val apiResponse = Response.success(apiResponseCharacters)

        // Expected entities to be inserted into the database
        val expectedEntities = apiResponseCharacters.map {
            HarryPotterEntity(
                id = it.id,
                name = it.name,
                actor = it.actor,
                species = it.species,
                house = it.house,
                dateOfBirth = it.dateOfBirth,
                alive = it.alive,
                image = it.image
            )
        }

        // When: the API service returns the successful response
        `when`(apiService.getAllCharacters()).thenReturn(apiResponse)

        doAnswer {
            // this is to simulate a suspending call, do nothing special
            // Just return Unit
        }.`when`(harryPotterDao).insertAll(expectedEntities)

        // Act: Fetch all characters from the repository
        val results = repository.getAllCharacters().toList()

        // Assert: Verify that the result first emits a Loading state, then Success state
        assertTrue(results[0] is ResultState.Loading)
        assertTrue(results[1] is ResultState.Success)
        assertEquals(apiResponseCharacters, (results[1] as ResultState.Success).data)

        // Verify that the DAO's insertAll method was called
        verify(harryPotterDao, times(1)).insertAll(expectedEntities)
    }

    @Test
    fun `test getAllCharacters with failed API response and fallback to local database`() = runTest {
        // Given: a failed response from the API
        val failedResponse: Response<List<HarryPotterCharacter>> = Response.error(500, mock())

        // Mocking the API response
        `when`(apiService.getAllCharacters()).thenReturn(failedResponse)

        // Mocking the local database return value
        val dbResponseCharacters = listOf(
            HarryPotterEntity(
                id = "2",
                name = "Hermione Granger",
                actor = "Emma Watson",
                species = "Human",
                house = "Gryffindor",
                dateOfBirth = "1979-09-19",
                alive = true,
                image = "image_url_hermione"
            )
        )

        val expectedEntities = dbResponseCharacters.map {
            HarryPotterCharacter(
                id = it.id,
                name = it.name,
                actor = it.actor,
                species = it.species,
                house = it.house,
                dateOfBirth = it.dateOfBirth,
                alive = it.alive,
                image = it.image
            )
        }
        `when`(harryPotterDao.getAllCharacters()).thenReturn(dbResponseCharacters)

        // Act: Fetch all characters from the repository
        val results = repository.getAllCharacters().toList()

        // Assert: Verify that the result first emits a Loading state, then Success state with local data
        assertTrue(results[0] is ResultState.Loading)
        assertTrue(results[1] is ResultState.Success)

        //val expectedEntities = dbResponseCharacters.map { it.toDomain() }
        assertEquals(expectedEntities, (results[1] as ResultState.Success).data)
    }

    @Test
    fun `test searchCharacters with valid query`() = runTest {
        // Given: a query and search results from the database
        val query = "Harry"
        val searchResults = listOf(
            HarryPotterEntity(
                id = "1",
                name = "Harry Potter",
                actor = "Daniel Radcliffe",
                species = "Human",
                house = "Gryffindor",
                dateOfBirth = "1980-07-31",
                alive = true,
                image = "image_url"
            )
        )

        // Mocking the DAO search method
        `when`(harryPotterDao.search("%$query%")).thenReturn(searchResults)

        // Act: Perform the search
        val results = repository.searchCharacters(query).toList()

        // Convert searchResults to domain models for comparison
        val expectedCharacters = searchResults.map {
            HarryPotterCharacter(
                id = it.id,
                name = it.name,
                actor = it.actor,
                species = it.species,
                house = it.house,
                dateOfBirth = it.dateOfBirth,
                alive = it.alive,
                image = it.image
            )
        }

        // Assert: Verify that the result is Success with the expected data
        assertTrue(results[0] is ResultState.Success)
        assertEquals(expectedCharacters, (results[0] as ResultState.Success).data)
    }

    @Test
    fun `test searchCharacters with empty query`() = runTest {
        // Given: an empty query
        val query = ""
        val searchResults = emptyList<HarryPotterEntity>()

        // Mocking the DAO search method
        `when`(harryPotterDao.search("%$query%")).thenReturn(searchResults)

        // Act: Perform the search
        val results = repository.searchCharacters(query).toList()

        // Convert searchResults to domain models for comparison
        val expectedCharacters = searchResults.map {
            HarryPotterCharacter(
                id = it.id,
                name = it.name,
                actor = it.actor,
                species = it.species,
                house = it.house,
                dateOfBirth = it.dateOfBirth,
                alive = it.alive,
                image = it.image
            )
        }

        // Assert: Verify that the result is Success with empty data
        assertTrue(results[0] is ResultState.Success)
        assertEquals(expectedCharacters, (results[0] as ResultState.Success).data)
    }

    @Test
    fun `test getAllCharacters with API error`() = runTest {
        // Given: an exception from the API
        val exception = RuntimeException("API Error")

        // Mocking the API response
        `when`(apiService.getAllCharacters()).thenThrow(exception)

        // Act: Fetch all characters from the repository
        val results = repository.getAllCharacters().toList()

        // Assert: Verify that the result first emits a Loading state, then Error state with the exception message
        assertTrue(results[0] is ResultState.Loading)
        assertTrue(results[1] is ResultState.Error)
        assertEquals("API Error", (results[1] as ResultState.Error).error)
    }
}