package com.example.harrypotterapp.model.repository

import com.example.harrypotterapp.model.api.ApiService
import com.example.harrypotterapp.model.database.HarryPotterDao
import com.example.harrypotterapp.model.database.HarryPotterEntity
import com.example.harrypotterapp.model.entity.HarryPotterCharacter
import com.example.utilities.ResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HarryPotterRepository @Inject constructor(
    private val apiService: ApiService,
    private val harryPotterDao: HarryPotterDao
) {
    suspend fun getAllCharacters(): Flow<ResultState<List<HarryPotterCharacter>>> {
        return flow{
            emit(ResultState.Loading())
            val response = apiService.getAllCharacters()
            if(response.isSuccessful && response.body() !=null){
                emit(ResultState.Success(response.body()!!))
                harryPotterDao.insertAll(response.body()!!.map { it.toEntity() })
            }else{
                //emit(ResultState.Error("Error in fetching data"))
                emit(ResultState.Success(harryPotterDao.getAllCharacters().map { it.toDomain() }))
            }
        }.catch { e->emit(ResultState.Error(e.localizedMessage ?: "Error in fetching data")) }
    }

    private fun HarryPotterCharacter.toEntity() = HarryPotterEntity(
        id, name, actor, species, house, dateOfBirth, alive, image
    )


    private fun HarryPotterEntity.toDomain() = HarryPotterCharacter(
        id = id,
        name = name,
        actor = actor,
        species = species,
        house = house,
        dateOfBirth = dateOfBirth,
        alive = alive,
        image = image
    )

    fun searchCharacters(query: String): Flow<ResultState<List<HarryPotterCharacter>>> = flow {
        val filtered = harryPotterDao.search("%$query%")
        emit(ResultState.Success(filtered.map { it.toDomain() }))
    }

}