package com.example.harrypotterapp.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface HarryPotterDao {
    @Query("SELECT * FROM characters")
    fun getAllCharacters(): List<HarryPotterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<HarryPotterEntity>)

    @Query("SELECT * FROM characters WHERE name LIKE :query OR actor LIKE :query")
    suspend fun search(query: String): List<HarryPotterEntity>

}