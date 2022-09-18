package com.udacity.asteroidradar.room

import android.content.Context
import androidx.room.*

@Database(entities = [RoomEntity::class], version = 1)
abstract class AsteroidsDatabase : RoomDatabase() {
    abstract val dao: AsteroidsDao
}

private lateinit var INSTANCE: AsteroidsDatabase

fun getDatabase(context: Context): AsteroidsDatabase {
    synchronized(AsteroidsDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                AsteroidsDatabase::class.java,
                "Asteroids").build()
        }
    }
    return INSTANCE
}
