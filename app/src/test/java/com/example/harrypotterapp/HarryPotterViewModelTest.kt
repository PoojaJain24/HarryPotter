package com.example.harrypotterapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.harrypotterapp.model.entity.HarryPotterCharacter
import com.example.harrypotterapp.model.repository.HarryPotterRepository
import com.example.harrypotterapp.ui.viewModel.HarryPotterViewModel
import com.example.utilities.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class HarryPotterViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var harryPotterRepository: HarryPotterRepository

    private lateinit var viewModel: HarryPotterViewModel

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Before
    fun setUp() {
        // Initialize Mockito mocks
        MockitoAnnotations.openMocks(this)

        // Set the main dispatcher to the test dispatcher
        Dispatchers.setMain(testDispatcher)

        // Initialize ViewModel after ensuring that the repository is mocked
        viewModel = HarryPotterViewModel(harryPotterRepository)
    }

    @Test
    fun `test initial state is Loading`() = testScope.runTest {
        // Mock the repository to return a flow that emits a Loading state
        Mockito.`when`(harryPotterRepository.getAllCharacters()).thenReturn(flow {
            emit(ResultState.Loading())
        })

        // Act: Trigger the ViewModel method
        viewModel.getHarryPotterAllCharacters()

        // Wait until all coroutines have completed
        advanceUntilIdle()

        // Assert that the initial state is Loading
        assert(viewModel.harryPotterCharacters.value is ResultState.Loading) {
            "Expected Loading state but was ${viewModel.harryPotterCharacters.value}"
        }
    }

    @Test
    fun `test fetch all characters success`() = testScope.runTest {
        val characters = listOf(
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

        // Mock the repository to return a flow that emits a Success state
        Mockito.`when`(harryPotterRepository.getAllCharacters()).thenReturn(flow {
            emit(ResultState.Success(characters))
        })

        // Act: call the method to fetch characters
        viewModel.getHarryPotterAllCharacters()

        // Wait until all coroutines have completed
        advanceUntilIdle()

        // Assert that the state is Success
        val actualState = viewModel.harryPotterCharacters.value
        assert(actualState is ResultState.Success) {
            "Expected Success state but was $actualState"
        }

        // Assert that the fetched characters match the expected characters
        val fetchedCharacters = (actualState as ResultState.Success).data
        assert(fetchedCharacters == characters) {
            "Expected $characters but was $fetchedCharacters"
        }
    }

    @Test
    fun `test search characters`() = testScope.runTest {
        // Given: a search query and the expected characters that match this query
        val searchQuery = "Harry"
        val matchingCharacters = listOf(
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
        val resultState = ResultState.Success(matchingCharacters)

        // Mock the repository to return the expected data for the search query
        Mockito.`when`(harryPotterRepository.searchCharacters(searchQuery)).thenReturn(flow {
            emit(resultState)
        })

        // When: calling the search method
        viewModel.search(searchQuery)

        // Wait until all coroutines have completed
        advanceUntilIdle()

        // Then: verify that the ViewModel's state is updated with the search results
        val actualState = viewModel.harryPotterCharacters.value
        assert(actualState is ResultState.Success) {
            "Expected Success state but was $actualState"
        }

        // Verify that the fetched list of characters matches the expected list
        val fetchedCharacters = (actualState as ResultState.Success).data
        assert(fetchedCharacters == matchingCharacters) {
            "Expected $matchingCharacters but was $fetchedCharacters"
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}