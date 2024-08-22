package com.example.harrypotterapp.model.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [HarryPotterEntity::class], version = 1)
abstract class HarryPotterAppDatabase : RoomDatabase(){
    abstract fun harryPotterDao(): HarryPotterDao
}