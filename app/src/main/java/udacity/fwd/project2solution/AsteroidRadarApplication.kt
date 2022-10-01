package udacity.fwd.project2solution

import android.app.Application
import com.squareup.picasso.Picasso

class AsteroidRadarApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val builder = Picasso.Builder(this)
        val built = builder.build()
        built.setIndicatorsEnabled(true)
        built.isLoggingEnabled = true
        Picasso.setSingletonInstance(built)
    }
}