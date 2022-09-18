package com.udacity.asteroidradar.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AsteroidsDao{
    @Query("SELECT * FROM RoomEntity ORDER BY closeApproachDate DESC")
    fun getAsteroids(): LiveData<List<RoomEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(asteroids: List<RoomEntity>)

    @Query("DELETE FROM RoomEntity")
    fun deleteAll()

    @Transaction
    fun insertAndDeleteInTransaction(asteroids: List<RoomEntity>) {
        // Anything inside this method runs in a single transaction.
        deleteAll()
        insertAll(asteroids)
    }

}