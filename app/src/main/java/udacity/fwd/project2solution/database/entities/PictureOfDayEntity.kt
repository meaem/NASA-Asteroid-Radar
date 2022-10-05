package udacity.fwd.project2solution.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import udacity.fwd.project2solution.domain.model.PictureOfDay

@Entity(tableName = "pic_of_day_table")
data class PictureOfDayEntity(
    @PrimaryKey
    val date: String = "",
    val mediaType: String,
    val title: String,
    val url: String
)

fun PictureOfDayEntity.asDomainModel(): PictureOfDay {
    return PictureOfDay(
        date = date,
        mediaType = mediaType,
        url = url,
        title = title
    )
}