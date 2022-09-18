package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.Network
import com.udacity.asteroidradar.repo.Repository
import com.udacity.asteroidradar.room.getDatabase
import kotlinx.coroutines.launch

private const val TAG = "MainViewModel"

class MainViewModel(app: Application) : ViewModel() {
    private val database = getDatabase(app)
    private val repository = Repository(database)
    private val _imageOfDay = MutableLiveData<PictureOfDay>()
    val imageOfDay: LiveData<PictureOfDay> get() = _imageOfDay
    private val _asteroidNav = MutableLiveData<Asteroid>()
    val asteroidNav: LiveData<Asteroid> get() = _asteroidNav

    init {
        viewModelScope.launch {
            try {
                repository.reloadAsteroids()
            } catch (e: Exception) {
                Log.e(TAG, ":$e ")
            }
        }

        viewModelScope.launch {
            _imageOfDay.value = Network.picOfDayService.getImageOfDay()
        }
    }

    val asteroids: LiveData<List<Asteroid>> = repository.asteroids

    fun navigateToDetailFragment(asteroid: Asteroid) {
        _asteroidNav.value = asteroid

    }

    fun onCompleteNavigation(){
        _asteroidNav.value = null
    }
}

class Factory(val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(app) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}