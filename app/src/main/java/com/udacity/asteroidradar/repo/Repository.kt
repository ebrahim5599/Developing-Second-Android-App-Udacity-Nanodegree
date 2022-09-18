package com.udacity.asteroidradar.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.api.Network
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.asRoomModel
import com.udacity.asteroidradar.room.AsteroidsDatabase
import com.udacity.asteroidradar.room.asModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class Repository(private val asteroidsDatabase: AsteroidsDatabase) {
    suspend fun reloadAsteroids() {
        withContext(Dispatchers.IO) {
            val string = Network.mainService.getALlAsteroids()
            val jsonObject = JSONObject(string)
            val asteroids = parseAsteroidsJsonResult(jsonObject)
            asteroidsDatabase.dao.insertAndDeleteInTransaction(asteroids.asRoomModel())
        }
    }

    val asteroids: LiveData<List<Asteroid>> =
        Transformations.map(asteroidsDatabase.dao.getAsteroids()) {
            it.asModel()
        }
}