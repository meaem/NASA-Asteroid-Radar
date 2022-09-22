package udacity.fwd.project2solution.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import udacity.fwd.project2solution.api.AsteroidApi
import udacity.fwd.project2solution.model.Asteroid
import udacity.fwd.project2solution.model.PictureOfDay

enum class AsteroidApiStatus {
    LOADING, ERROR, DONE
}

class MainViewModel : ViewModel() {

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
        _apiStatus.value = AsteroidApiStatus.LOADING
//        _imgaeOfDay.value =
//            "https://www.nasa.gov/sites/default/files/styles/full_width_feature/public/thumbnails/image/gsfc_20171208_archive_e000226_orig.jpg"
        //ToDo simulate a delay in the fetching data. This should be removed
        getImageOfTheDay()

        viewModelScope.launch {
            delay(3000)

            _apiStatus.value = AsteroidApiStatus.DONE
            _asteroids.value = mutableListOf(
                Asteroid(
                    68347, "2001 KB76", "2020-10-25", 14444.0,
                    1010.55, 333.2, 8888.33,
                    false
                ),
                Asteroid(
                    563256, "2010 KB07", "2022-10-25", 485482.0,
                    2345.55, 6543.2, 9876.33,
                    true
                )
            )
        }

    }


    private fun getImageOfTheDay() {
        // TODOo (03) Set the correct status for LOADING, ERROR, and DONE
        viewModelScope.launch {
            _imgOfTheDayStatus.value = AsteroidApiStatus.LOADING
            try {

                _imgaeOfDay.value = AsteroidApi.retrofitService.getImageOfTheDay()
                _imgOfTheDayStatus.value = AsteroidApiStatus.DONE

            } catch (e: Exception) {

                _imgOfTheDayStatus.value = AsteroidApiStatus.ERROR
                _imgaeOfDay.value = null
                Log.d("PictureOfDay", e.stackTraceToString())
            }
        }
    }
}