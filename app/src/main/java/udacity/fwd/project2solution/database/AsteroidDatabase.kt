package udacity.fwd.project2solution.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import udacity.fwd.project2solution.database.entities.AsteroidEntity
import udacity.fwd.project2solution.database.entities.PictureOfDayEntity

@Database(
    entities = [AsteroidEntity::class, PictureOfDayEntity::class],
    version = 3,
    exportSchema = false
)
abstract class AsteroidDatabase : RoomDatabase() {

    abstract val asteroidDao: AsteroidDao
    abstract val picOdDayDao: PicOfTheDayDao

    companion object {
        @Volatile
        private var INSTANCE: AsteroidDatabase? = null

        fun getInstance(context: Context): AsteroidDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AsteroidDatabase::class.java,
                        "asteroid_database"
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }

        }
    }
}