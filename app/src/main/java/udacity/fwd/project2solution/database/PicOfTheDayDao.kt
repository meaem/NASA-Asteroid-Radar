package udacity.fwd.project2solution.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import udacity.fwd.project2solution.database.entities.PictureOfDayEntity

@Dao
interface PicOfTheDayDao {
    @Insert(onConflict = IGNORE)
    suspend fun addPic(pic: PictureOfDayEntity)


    @Query("SELECT * from pic_of_day_table pic where pic.date=:startDate")
    fun getPicOfTheDay(startDate: String): LiveData<PictureOfDayEntity>
}