package com.example.harrypotterapp.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class HarryPotterEntity(
    @PrimaryKey val id:String,
    val name:String,
    val actor:String,
    val species:String,
    val house:String?,
    val dateOfBirth:String?,
    val alive:Boolean,
    val image: String?
)
