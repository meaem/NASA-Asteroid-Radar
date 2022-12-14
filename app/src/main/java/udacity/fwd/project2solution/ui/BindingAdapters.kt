package udacity.fwd.project2solution

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import udacity.fwd.project2solution.domain.model.Asteroid
import udacity.fwd.project2solution.domain.model.PictureOfDay
import udacity.fwd.project2solution.repository.AsteroidApiStatus
import udacity.fwd.project2solution.ui.main.AsteroidListAdapter

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
        imageView.contentDescription =
            imageView.context.getString(R.string.potentially_hazardous_asteroid_description)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
        imageView.contentDescription =
            imageView.context.getString(R.string.not_hazardous_asteroid_description)

    }
}

@BindingAdapter("statusColor")
fun bindAsteroidStatusFontColor(tv: TextView, isHazardous: Boolean) {
    if (isHazardous) {
        tv.setTextColor(tv.context.getColor(R.color.potentially_hazardous))//.setImageResource(R.drawable.ic_status_potentially_hazardous)
    } else {
        tv.setTextColor(tv.context.getColor(R.color.default_text_color))
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
        imageView.contentDescription =
            imageView.context.getString(R.string.potentially_hazardous_asteroid_image)

    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
        imageView.contentDescription =
            imageView.context.getString(R.string.not_hazardous_asteroid_image)

    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}

/**
 */
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Asteroid>?) {
    val adapter = recyclerView.adapter as AsteroidListAdapter
    adapter.submitList(data)
}

@BindingAdapter("asteroidApiStatus")
fun bindAsteroidStatus(imageView: ImageView, status: AsteroidApiStatus?) {
    when (status) {
        AsteroidApiStatus.FETCHING -> {
            imageView.visibility = View.VISIBLE
            imageView.setImageResource(R.drawable.downloading_animation)
            imageView.contentDescription = imageView.context.getString(R.string.loading_asteroids)
        }
        AsteroidApiStatus.ERROR -> {
            imageView.visibility = View.VISIBLE
            imageView.setImageResource(R.drawable.ic_connection_error)
            imageView.contentDescription =
                imageView.context.getString(R.string.failed_loading_asteroids)

        }
        AsteroidApiStatus.DONE -> {
            imageView.visibility = View.GONE
        }
        else -> { // if status is null
            imageView.visibility = View.GONE
//            imageView.setImageResource(R.drawable.downloading_animation)
//            imageView.contentDescription = imageView.context.getString(R.string.loading_asteroids)
        }
    }
}

@BindingAdapter("asteroidApiStatus")
fun bindAsteroidStatusTextView(tv: TextView, status: AsteroidApiStatus?) {
    when (status) {
        AsteroidApiStatus.FETCHING -> {
            tv.visibility = View.VISIBLE
            tv.setText(R.string.fetching_data)

        }
        AsteroidApiStatus.ERROR -> {
            tv.visibility = View.VISIBLE
            tv.setText(R.string.failed_fetch_data)
            tv.contentDescription =
                tv.context.getString(R.string.failed_loading_asteroids)

        }
        AsteroidApiStatus.DONE -> {
            tv.visibility = View.GONE
        }
        else -> { // if status is null
            tv.visibility = View.GONE
//            tv.setText(R.string.fetching_data)
        }
    }
}


@BindingAdapter("imageOfDayStatus")
fun bindimageOfDayStatus(imageView: ImageView, status: AsteroidApiStatus) {
    when (status) {
        AsteroidApiStatus.FETCHING -> {
            imageView.visibility = View.VISIBLE
            imageView.setImageResource(R.drawable.loading_animation)
            imageView.contentDescription =
                imageView.context.getString(R.string.loading_image_of_the_day)
        }
        AsteroidApiStatus.ERROR -> {
            imageView.visibility = View.VISIBLE
            imageView.setImageResource(R.drawable.ic_connection_error)
            imageView.contentDescription =
                imageView.context.getString(R.string.failed_loading_image_of_the_day)

        }
        AsteroidApiStatus.DONE -> {
            imageView.visibility = View.VISIBLE
        }
    }
}

//@RequiresApi(Build.VERSION_CODES.M)
@BindingAdapter("imageOfDay")
fun bindImageOfDayStatus(imageView: ImageView, img: PictureOfDay?) {
//    Log.d("PictureOfDay", imgUrl?: "no picture of the day")
    img?.let {
        imageView.contentDescription = imageView.context.getString(
            R.string.nasa_picture_of_day_content_description_format,
            img.title
        )
        if (it.url.isNotBlank()) {
            val imgUri = it //imgUrl.toUri().buildUpon().scheme("https").build()
            Picasso.with(imageView.context)
                .load(img.url)
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image)
                .into(imageView)
        }
    }

}
