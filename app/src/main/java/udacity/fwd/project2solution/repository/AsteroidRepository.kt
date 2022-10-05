package udacity.fwd.project2solution.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import udacity.fwd.project2solution.api.*
import udacity.fwd.project2solution.database.AsteroidDatabase
import udacity.fwd.project2solution.database.entities.asDomainModel
import udacity.fwd.project2solution.domain.model.Asteroid
import java.text.SimpleDateFormat
import java.util.*

class AsteroidRepository private constructor(private val db: AsteroidDatabase) {

    companion object {
        @Volatile
        private var INSTANCE: AsteroidRepository? = null

        fun getInstance(db: AsteroidDatabase): AsteroidRepository {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = AsteroidRepository(db)
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

    private var cal = Calendar.getInstance()

    private val today = SimpleDateFormat(
        Constants.API_QUERY_DATE_FORMAT,
        Locale.getDefault()
    ).format(cal.time)


    val imageOfDay = Transformations.map(db.picOdDayDao.getPicOfTheDay(today)) {
        it?.asDomainModel()
    }


    private val _apiStatus = MutableLiveData<AsteroidApiStatus>()
    val apiStatus: LiveData<AsteroidApiStatus>
        get() = _apiStatus


    private val _imgOfTheDayStatus = MutableLiveData<AsteroidApiStatus>()
    val imgOfTheDayStatus: LiveData<AsteroidApiStatus>
        get() = _imgOfTheDayStatus


    fun loadAllAsteroids(): LiveData<List<Asteroid>> {
        return Transformations.map(db.asteroidDao.getAllAsteroids()) { ast ->
            ast.asDomainModel()
        }
    }

    fun loadTodaysAsteroids(): LiveData<List<Asteroid>> {
        return Transformations.map(db.asteroidDao.getTodayAsteroids(today)) { ast ->
            ast.asDomainModel()
        }
    }

    fun loadWeekAsteroids(): LiveData<List<Asteroid>> {
        cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_MONTH, 7)
        val todayPlus7 = SimpleDateFormat(
            Constants.API_QUERY_DATE_FORMAT,
            Locale.getDefault()
        ).format(cal.time)

        return Transformations.map(db.asteroidDao.getAsteroidsInPeriod(today, todayPlus7)) { ast ->
            ast.asDomainModel()
        }
    }


    suspend fun refreshImageOfTheDay() {
        withContext(Dispatchers.Main) {
            _imgOfTheDayStatus.value = AsteroidApiStatus.FETCHING

        }

        withContext(Dispatchers.IO) {
            val _imgaeOfDay = AsteroidApi.retrofitService.getImageOfTheDay(Constants.API_KEY_VALUE)
                .asDatabaseModel(today)
            db.picOdDayDao.addPic(_imgaeOfDay)

        }
        withContext(Dispatchers.Main) {
            _imgOfTheDayStatus.value = AsteroidApiStatus.DONE
        }
    }

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.Main) {
            _apiStatus.value = AsteroidApiStatus.FETCHING
        }
        withContext(Dispatchers.IO) {

            val dates = getNextSevenDaysFormattedDates()
            val resultObject = AsteroidApi.retrofitService.getAsteroids(
                Constants.API_KEY_VALUE,
                dates.first(),
                dates.last()
            )
            val _remoteAsteroids =
                parseAsteroidsJsonResult(JSONObject(resultObject)).asDatabaseModel()

            db.asteroidDao.addAllAsteroids(_remoteAsteroids)


        }
        withContext(Dispatchers.Main) {

            _apiStatus.value = AsteroidApiStatus.DONE
        }
    }
    suspend fun removePreviousAsteroids() {
        db.asteroidDao.deleteAllBefore(today)
    }

    suspend fun removePreviousPics() {
        db.picOdDayDao.deleteAllBefore(today)
    }


}


