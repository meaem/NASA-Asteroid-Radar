package udacity.fwd.project2solution.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import udacity.fwd.project2solution.api.AsteroidApi
import udacity.fwd.project2solution.api.Constants.API_KEY_VALUE
import udacity.fwd.project2solution.api.Constants.url
import udacity.fwd.project2solution.api.getNextSevenDaysFormattedDates
import udacity.fwd.project2solution.api.parseAsteroidsJsonResult
import udacity.fwd.project2solution.database.AsteroidDao
import udacity.fwd.project2solution.model.Asteroid
import udacity.fwd.project2solution.model.PictureOfDay

class MainViewModel(dataSource: AsteroidDao) : ViewModel() {

    private val _apiStatus = MutableLiveData<AsteroidApiStatus>()
    val apiStatus: LiveData<AsteroidApiStatus>
        get() = _apiStatus


    private val _imgOfTheDayStatus = MutableLiveData<AsteroidApiStatus>()
    val imgOfTheDayStatus: LiveData<AsteroidApiStatus>
        get() = _imgOfTheDayStatus

    private val _asteroids = MutableLiveData<List<Asteroid>>()
    val asteroids: LiveData<List<Asteroid>>
        get() = _asteroids


    private val _imgaeOfDay = MutableLiveData<PictureOfDay?>()
    val imageOfDay: LiveData<PictureOfDay?>
        get() = _imgaeOfDay

    init {
        Log.d("OkHttp", url.toString())
        _apiStatus.value = AsteroidApiStatus.LOADING

        getImageOfTheDay()

        getAsteroids()
    }

    private fun getImageOfTheDay() {
        viewModelScope.launch {
            _imgOfTheDayStatus.value = AsteroidApiStatus.LOADING
            try {

                _imgaeOfDay.value = AsteroidApi.retrofitService.getImageOfTheDay(API_KEY_VALUE)
                _imgOfTheDayStatus.value = AsteroidApiStatus.DONE

            } catch (e: Exception) {

                _imgOfTheDayStatus.value = AsteroidApiStatus.ERROR
                _imgaeOfDay.value = null
                Log.d("MainViewModel", e.stackTraceToString())
            }
        }
    }

    private fun getAsteroids() {
        viewModelScope.launch {
            _apiStatus.value = AsteroidApiStatus.LOADING
            try {
                val dates = getNextSevenDaysFormattedDates()
                val resultObject = AsteroidApi.retrofitService.getAsteroids(
                    API_KEY_VALUE,
                    dates.first(),
                    dates.last()
                )
                _asteroids.value = parseAsteroidsJsonResult(JSONObject(resultObject))
                _apiStatus.value = AsteroidApiStatus.DONE

            } catch (e: Exception) {

                _apiStatus.value = AsteroidApiStatus.ERROR
                _asteroids.value = listOf()
                Log.d("MainViewModel", e.stackTraceToString())


            }
        }
    }
}