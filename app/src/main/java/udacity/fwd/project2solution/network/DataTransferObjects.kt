package udacity.fwd.project2solution.network

import com.squareup.moshi.Json
import udacity.fwd.project2solution.database.entities.AsteroidEntity
import udacity.fwd.project2solution.database.entities.PictureOfDayEntity
import udacity.fwd.project2solution.domain.model.Asteroid


data class NetworkAsteroidContainer(val asteroids: ArrayList<NetworkAsteroid>)

data class NetworkAsteroid(
    val id: Long,
    val codename: String,
    val closeApproachDate: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
)

data class NetworkPictureOfDay(
    @Json(name = "media_type")
    val mediaType: String,
    val title: String,
    val url: String
)

fun NetworkPictureOfDay.asDatabaseModel(): PictureOfDayEntity {
    return PictureOfDayEntity(
        url = url,
        title = title,
        mediaType = mediaType
    )
}

fun NetworkPictureOfDay.asDatabaseModel(date: String): PictureOfDayEntity {
    return PictureOfDayEntity(
        url = url,
        title = title,
        mediaType = mediaType,
        date = date
    )
}

fun NetworkAsteroidContainer.asDomainModel(): List<Asteroid> {
    return asteroids.map {
        Asteroid(
            id = it.id,
            codename = it.codename,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }
}

fun NetworkAsteroidContainer.asDatabaseModel(): List<AsteroidEntity> {
    return asteroids.map {
        AsteroidEntity(
            id = it.id,
            codename = it.codename,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }
}