package udacity.fwd.project2solution

//import udacity.fwd.project2solution.work.RefreshDataWorker
import android.app.Application
import androidx.work.*
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import udacity.fwd.project2solution.work.RefreshDataWorker
import java.util.concurrent.TimeUnit

class AsteroidRadarApplication : Application() {
    private val applicationScope = CoroutineScope(Dispatchers.Default)

    private fun delayedInit() = applicationScope.launch {
        setupRecurringWork()
    }

    private fun setupRecurringWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(true)
            .build()

        val repeatingRequest = PeriodicWorkRequestBuilder<RefreshDataWorker>(1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()
        // launch call enqueuPeriodicWork() to schedule the work.
        WorkManager.getInstance().enqueueUniquePeriodicWork(
            RefreshDataWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,

            repeatingRequest
        )

    }


    override fun onCreate() {
        super.onCreate()

        delayedInit()
        val builder = Picasso.Builder(this)
        val built = builder.build()
        built.setIndicatorsEnabled(true)
        built.isLoggingEnabled = true
        Picasso.setSingletonInstance(built)
    }
}