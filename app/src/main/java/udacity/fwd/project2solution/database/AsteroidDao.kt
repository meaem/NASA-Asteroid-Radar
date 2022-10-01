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

    @Query("SELECT * from asteroid_table ast where ast.closeApproachDate >= :startDate Order by closeApproachDate")
    fun getAsteroids(startDate: String): LiveData<List<AsteroidEntity>>
}