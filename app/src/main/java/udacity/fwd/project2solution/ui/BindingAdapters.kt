package udacity.fwd.project2solution

import android.os.Build
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import udacity.fwd.project2solution.model.Asteroid
import udacity.fwd.project2solution.model.PictureOfDay
import udacity.fwd.project2solution.ui.main.AsteroidApiStatus
import udacity.fwd.project2solution.ui.main.AsteroidListAdapter

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
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
        AsteroidApiStatus.LOADING -> {
            imageView.visibility = View.VISIBLE
            imageView.setImageResource(R.drawable.loading_animation)
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
            imageView.visibility = View.VISIBLE
            imageView.setImageResource(R.drawable.loading_animation)
            imageView.contentDescription = imageView.context.getString(R.string.loading_asteroids)
        }
    }
}

@BindingAdapter("asteroidApiStatus")
fun bindAsteroidStatusTextView(imageView: TextView, status: AsteroidApiStatus?) {
    when (status) {
        AsteroidApiStatus.LOADING -> {
            imageView.visibility = View.VISIBLE
            imageView.setText(R.string.fetching_data)

        }
        AsteroidApiStatus.ERROR -> {
            imageView.visibility = View.VISIBLE
            imageView.setText(R.string.failed_fetch_data)
            imageView.contentDescription =
                imageView.context.getString(R.string.failed_loading_asteroids)

        }
        AsteroidApiStatus.DONE -> {
            imageView.visibility = View.GONE
        }
        else -> { // if status is null
            imageView.visibility = View.VISIBLE
            imageView.setText(R.string.fetching_data)
        }
    }
}


@BindingAdapter("imageOfDayStatus")
fun bindimageOfDayStatus(imageView: ImageView, status: AsteroidApiStatus) {
    when (status) {
        AsteroidApiStatus.LOADING -> {
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

@RequiresApi(Build.VERSION_CODES.M)
@BindingAdapter("imageUrl")
fun bindImageOfDayStatus(imageView: ImageView, imgUrl: PictureOfDay?) {
    Log.d("PictureOfDay", imgUrl?.url ?: "no picture of the day")
    imgUrl?.let {
        val imgUri = it.url //imgUrl.toUri().buildUpon().scheme("https").build()
        Picasso.with(imageView.context)
            .load(imgUri)
            .placeholder(R.drawable.loading_animation)
            .error(R.drawable.ic_broken_image)
            .into(imageView);

    }

}
