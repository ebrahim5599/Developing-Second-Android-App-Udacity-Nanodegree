package com.udacity.asteroidradar.manager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.repo.Repository
import com.udacity.asteroidradar.room.getDatabase
import java.lang.Exception

class ReloadDataWorker(appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext,
    params
) {
    override suspend fun doWork(): Result {
        val roomInstance = getDatabase(applicationContext)
        val repoInstance = Repository(roomInstance)
        return try {
            repoInstance.reloadAsteroids()
            Result.success()
        } catch (ex: Exception) {
            Result.retry()
        }
    }

    companion object{
        const val WORKER_NAME = "Asteroid worker"

    }
}