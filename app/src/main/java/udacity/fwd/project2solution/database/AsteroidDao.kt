package udacity.fwd.project2solution.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import udacity.fwd.project2solution.model.Asteroid

@Dao
interface AsteroidDao {
    @Insert(onConflict = IGNORE)
    suspend fun addAsteroid(asteroid: Asteroid)

    @Insert(onConflict = IGNORE)
    suspend fun addAllAsteroids(asteroids: List<Asteroid>)

    @Query("SELECT * from asteroid_table ast where ast.closeApproachDate >= :startDate")
    fun getAsteroids(startDate: String): LiveData<List<Asteroid>>
}