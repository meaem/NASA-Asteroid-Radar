package udacity.fwd.project2solution.ui.main

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import org.json.JSONObject
import udacity.fwd.project2solution.api.AsteroidApi
import udacity.fwd.project2solution.api.Constants
import udacity.fwd.project2solution.api.Constants.API_KEY_VALUE
import udacity.fwd.project2solution.api.Constants.url
import udacity.fwd.project2solution.api.getNextSevenDaysFormattedDates
import udacity.fwd.project2solution.api.parseAsteroidsJsonResult
import udacity.fwd.project2solution.database.AsteroidDao
import udacity.fwd.project2solution.model.Asteroid
import udacity.fwd.project2solution.model.PictureOfDay
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(private val dataSource: AsteroidDao) : ViewModel() {

    private val _apiStatus = MutableLiveData<AsteroidApiStatus>()
    val apiStatus: LiveData<AsteroidApiStatus>
        get() = _apiStatus


    private val _imgOfTheDayStatus = MutableLiveData<AsteroidApiStatus>()
    val imgOfTheDayStatus: LiveData<AsteroidApiStatus>
        get() = _imgOfTheDayStatus


    private val today = SimpleDateFormat(
        Constants.API_QUERY_DATE_FORMAT,
        Locale.getDefault()
    ).format(Calendar.getInstance().time)


    private val _localAsteroids = dataSource.getAsteroids(today)

    val asteroids: LiveData<List<Asteroid>>
        get() = _localAsteroids


    val dbStatus = Transformations.map(asteroids) {
        when (it?.isEmpty()) {
            true -> AsteroidApiStatus.LOADING
            else -> AsteroidApiStatus.DONE
        }
    }

    private val _imgaeOfDay = MutableLiveData<PictureOfDay?>()
    val imageOfDay: LiveData<PictureOfDay?>
        get() = _imgaeOfDay

    init {
        Log.d("OkHttp", url.toString())
        _apiStatus.value = AsteroidApiStatus.LOADING

        getImageOfTheDay()

        getRemoteAsteroids()
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

    private fun getRemoteAsteroids() {
        viewModelScope.launch {
            _apiStatus.value = AsteroidApiStatus.LOADING
            try {
                val dates = getNextSevenDaysFormattedDates()
                val resultObject = AsteroidApi.retrofitService.getAsteroids(
                    API_KEY_VALUE,
                    dates.first(),
                    dates.last()
                )
                val _remoteAsteroids = parseAsteroidsJsonResult(JSONObject(resultObject))
                saveAsteroids(_remoteAsteroids)
                _apiStatus.value = AsteroidApiStatus.DONE

            } catch (e: Exception) {

                _apiStatus.value = AsteroidApiStatus.ERROR
//                _remoteAsteroids.value = listOf()
                Log.d("MainViewModel", e.stackTraceToString())
            }
        }
    }

    private suspend fun saveAsteroids(asteroids: List<Asteroid>) {
        dataSource.addAllAsteroids(asteroids)
    }
}