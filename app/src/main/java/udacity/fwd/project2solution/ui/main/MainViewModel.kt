package udacity.fwd.project2solution.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import udacity.fwd.project2solution.R
import udacity.fwd.project2solution.database.AsteroidDatabase
import udacity.fwd.project2solution.domain.model.Asteroid
import udacity.fwd.project2solution.domain.model.PictureOfDay
import udacity.fwd.project2solution.repository.AsteroidApiStatus
import udacity.fwd.project2solution.repository.AsteroidRepository

enum class DataChoices {
    CURRENT_WEEK, TODAY, LOCALLY
}

class MainViewModel(application: Application) :
    AndroidViewModel(application) {


    private val repo = AsteroidRepository.getInstance(AsteroidDatabase.getInstance(application))

    private var _asteroids = MutableLiveData<LiveData<List<Asteroid>>>(MutableLiveData(listOf()))

    val asteroids = Transformations.map(_asteroids) {
        it
    }

    private var _picOfDay = repo.loadPicOfTheDay()

    val imageOfDay: LiveData<PictureOfDay>
        get() = _picOfDay

    private val _title = MutableLiveData<String>()
    val title: LiveData<String>
        get() = _title


    val imgOfTheDayStatus: LiveData<AsteroidApiStatus>
        get() = repo.imgOfTheDayStatus

    private val _apiStatus = repo.apiStatus

    val apiStatus: LiveData<AsteroidApiStatus>
        get() = repo.apiStatus


    init {
//        _imgOfTheDayStatus.value = AsteroidApiStatus.LOADING
//        _apiStatus.value = AsteroidApiStatus.LOADING

        updateData(DataChoices.CURRENT_WEEK)
//        viewModelScope.launch {
//            async {
//                try {
//                    repo.refreshImageOfTheDay()
//                } catch (ex: Exception) {
//                    Log.d("MainViewModel", "Failed to refresh pic of the day from network")
//                }
//            }

//            async {
//                try {
        Log.d("MainViewModel", "loading pic of the day")

        //_picOfDay.value =
        Log.d("MainViewModel", repo.loadPicOfTheDay().toString())

        _asteroids.value = repo.loadWeekAsteroids()
//                    _apiStatus.value = AsteroidApiStatus.DONE
//                } catch (ex: Exception) {
//                    _apiStatus.value = AsteroidApiStatus.ERROR
//                    Log.d("MainViewModel", "Failed to refresh asteroids from network")
//
//                }
//            }


//        }

    }

    fun updateData(choices: DataChoices) {
        val app = getApplication<Application>()
        when (choices) {
            DataChoices.CURRENT_WEEK -> {
                _title.value = app.getString(R.string.title_week_list)
                _asteroids.value = repo.loadWeekAsteroids()
            }
            DataChoices.TODAY -> {
                _title.value = app.getString(R.string.title_today_list)
                _asteroids.value = repo.loadTodaysAsteroids()
            }
            DataChoices.LOCALLY -> {
                _title.value = app.getString(R.string.title_all_list)
                _asteroids.value = repo.loadAllAsteroids()
            }
        }
    }


}