package udacity.fwd.project2solution.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import udacity.fwd.project2solution.database.entities.AsteroidEntity

@Dao
interface AsteroidDao {
    @Insert(onConflict = IGNORE)
    suspend fun addAsteroid(asteroid: AsteroidEntity)

    @Insert(onConflict = IGNORE)
    suspend fun addAllAsteroids(asteroids: List<AsteroidEntity>)

    @Query("SELECT * from asteroid_table ast where ast.closeApproachDate >= :startDate and ast.closeApproachDate <= :endDate Order by closeApproachDate")
    fun getAsteroidsInPeriod(startDate: String, endDate: String): LiveData<List<AsteroidEntity>>

    @Query("SELECT * from asteroid_table ast where ast.closeApproachDate = :startDate")
    fun getTodayAsteroids(startDate: String): LiveData<List<AsteroidEntity>>

    @Query("SELECT * from asteroid_table ast Order by closeApproachDate")
    fun getAllAsteroids(): LiveData<List<AsteroidEntity>>

    @Query("DELETE from asteroid_table where closeApproachDate < :date")
    suspend fun deleteAllBefore(date: String)
}