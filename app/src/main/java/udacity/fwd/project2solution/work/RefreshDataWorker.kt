package udacity.fwd.project2solution.work

import android.content.Context
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
        val database = AsteroidDatabase.getInstance(applicationContext)
        val repository = AsteroidRepository(database)
        return try {
            repository.refreshImageOfTheDay()
            repository.refreshAsteroids()

            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}