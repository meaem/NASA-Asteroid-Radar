package udacity.fwd.project2solution.work

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import retrofit2.HttpException
import udacity.fwd.project2solution.database.AsteroidDatabase
import udacity.fwd.project2solution.repository.AsteroidRepository

class RefreshDataWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {
    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        Log.d("RefreshDataWorker", "Worker done")
        val database = AsteroidDatabase.getInstance(applicationContext)
        val repository = AsteroidRepository.getInstance(database)

        repository.removePreviousAsteroids()
        repository.removePreviousPics()
        return try {
            repository.refreshImageOfTheDay()
            repository.refreshAsteroids()
            Log.d("RefreshDataWorker", "Worker Finish")


            Result.success()
        } catch (e: HttpException) {
            Log.d("RefreshDataWorker", "Worker ERROR")

            Result.retry()
        }
    }
}