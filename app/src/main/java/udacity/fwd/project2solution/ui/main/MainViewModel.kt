package udacity.fwd.project2solution.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import udacity.fwd.project2solution.R
import udacity.fwd.project2solution.database.AsteroidDatabase
import udacity.fwd.project2solution.repository.AsteroidRepository

enum class DataChoices {
    CURRENT_WEEK, TODAY, LOCALLY
}

class MainViewModel(application: Application) :
    AndroidViewModel(application) {


    private val repo = AsteroidRepository(AsteroidDatabase.getInstance(application))

    val asteroids = repo.asteroids
    val imageOfDay = repo.imageOfDay


    private val _apiStatus = MutableLiveData<AsteroidApiStatus>()
    val apiStatus: LiveData<AsteroidApiStatus>
        get() = _apiStatus


    private val _imgOfTheDayStatus = MutableLiveData<AsteroidApiStatus>()
    val imgOfTheDayStatus: LiveData<AsteroidApiStatus>
        get() = _imgOfTheDayStatus

    private val _title = MutableLiveData<String>()
    val title: LiveData<String>
        get() = _title


    val dbStatus = Transformations.map(asteroids) {
        when (it?.isEmpty()) {
            true -> AsteroidApiStatus.LOADING
            else -> AsteroidApiStatus.DONE
        }
    }


    init {
        _imgOfTheDayStatus.value = AsteroidApiStatus.LOADING
        _apiStatus.value = AsteroidApiStatus.LOADING

        updateData(DataChoices.CURRENT_WEEK)
        viewModelScope.launch {
            async {
                try {
                    repo.refreshImageOfTheDay()
                } catch (ex: Exception) {
                    Log.d("MainViewModel", "Failed to refresh pic of the day from network")
                }
            }

            async {
                try {
                    repo.refreshAsteroids()
                    _apiStatus.value = AsteroidApiStatus.DONE
                } catch (ex: Exception) {
                    _apiStatus.value = AsteroidApiStatus.ERROR
                    Log.d("MainViewModel", "Failed to refresh asteroids from network")

                }
            }


        }

    }

    fun updateData(choices: DataChoices) {
        val app = getApplication<Application>()
        when (choices) {
            DataChoices.CURRENT_WEEK -> _title.value = app.getString(R.string.title_weekly)
            DataChoices.TODAY -> _title.value = app.getString(R.string.today)
            DataChoices.LOCALLY -> _title.value = app.getString(R.string.locally)
        }
    }


}