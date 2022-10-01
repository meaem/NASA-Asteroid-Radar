package udacity.fwd.project2solution.repository

import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import udacity.fwd.project2solution.api.*
import udacity.fwd.project2solution.database.AsteroidDatabase
import udacity.fwd.project2solution.database.entities.asDomainModel
import java.text.SimpleDateFormat
import java.util.*

class AsteroidRepository(db: AsteroidDatabase) {


    private val today = SimpleDateFormat(
        Constants.API_QUERY_DATE_FORMAT,
        Locale.getDefault()
    ).format(Calendar.getInstance().time)

    private val asteroidSource = db.asteroidDao
    private val picOfDaySource = db.picOdDayDao


    val asteroids = Transformations.map(asteroidSource.getAsteroids(today)) {
        it.asDomainModel()
    }


    //    private val _imgaeOfDay = MutableLiveData<PictureOfDay?>()
    val imageOfDay = Transformations.map(picOfDaySource.getPicOfTheDay(today)) {
        it?.asDomainModel()
    }

    //    private val _remoteAsteroids = MutableLiveData<MutableList<Asteroid>>()
    suspend fun refreshImageOfTheDay() {
        withContext(Dispatchers.IO) {

//        try {

            val _imgaeOfDay = AsteroidApi.retrofitService.getImageOfTheDay(Constants.API_KEY_VALUE)
                .asDatabaseModel(today)


            picOfDaySource.addPic(_imgaeOfDay)
//            _imgOfTheDayStatus.value = AsteroidApiStatus.DONE

//        } catch (e: Exception) {

//            _imgOfTheDayStatus.value = AsteroidApiStatus.ERROR
//            _imgaeOfDay.value = null
//            Log.d("MainViewModel", e.stackTraceToString())
//        }
        }
    }

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {

//            try {
            val dates = getNextSevenDaysFormattedDates()
            val resultObject = AsteroidApi.retrofitService.getAsteroids(
                Constants.API_KEY_VALUE,
                dates.first(),
                dates.get(2)
            )
            val _remoteAsteroids =
                parseAsteroidsJsonResult(JSONObject(resultObject)).asDatabaseModel()

            asteroidSource.addAllAsteroids(_remoteAsteroids)
//                _apiStatus.value = AsteroidApiStatus.DONE

//            } catch (e: Exception) {

//                _apiStatus.value = AsteroidApiStatus.ERROR
//                _remoteAsteroids.value = listOf()
//                Log.d("MainViewModel", e.stackTraceToString())
//            }
        }
    }


}


