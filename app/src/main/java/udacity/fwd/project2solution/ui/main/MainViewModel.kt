package udacity.fwd.project2solution.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
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

    val imageOfDay: LiveData<PictureOfDay?>
        get() = repo.imageOfDay

    private val _title = MutableLiveData<String>()
    val title: LiveData<String>
        get() = _title


    val imgOfTheDayStatus: LiveData<AsteroidApiStatus>
        get() = repo.imgOfTheDayStatus

    val apiStatus = repo.apiStatus


    init {
//        _imgOfTheDayStatus.value = AsteroidApiStatus.LOADING
//        _apiStatus.value = AsteroidApiStatus.LOADING

        updateData(DataChoices.CURRENT_WEEK)
        viewModelScope.launch {
            async {
                try {
                    repo.refreshImageOfTheDay()
                } catch (ex: Exception) {
                    Log.d("MainViewModel", "Failed to refresh pic of the day from network")
                }
            }

//            async {
//                try {
            _asteroids.value = repo.loadWeekAsteroids()
//                    _apiStatus.value = AsteroidApiStatus.DONE
//                } catch (ex: Exception) {
//                    _apiStatus.value = AsteroidApiStatus.ERROR
//                    Log.d("MainViewModel", "Failed to refresh asteroids from network")
//
//                }
//            }


        }

    }

    fun updateData(choices: DataChoices) {
        val app = getApplication<Application>()
        when (choices) {
            DataChoices.CURRENT_WEEK -> {
                _title.value = app.getString(R.string.title_weekly)
                _asteroids.value = repo.loadWeekAsteroids()
            }
            DataChoices.TODAY -> {
                _title.value = app.getString(R.string.today)
                _asteroids.value = repo.loadTodaysAsteroids()
            }
            DataChoices.LOCALLY -> {
                _title.value = app.getString(R.string.locally)
                _asteroids.value = repo.loadAllAsteroids()
            }
        }
    }


}