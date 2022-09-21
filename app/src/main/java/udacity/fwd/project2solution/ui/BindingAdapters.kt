package udacity.fwd.project2solution

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import udacity.fwd.project2solution.model.Asteroid
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
fun bindAsteroidStatus(progressBar: ProgressBar, status: AsteroidApiStatus) {
    when (status) {
        AsteroidApiStatus.LOADING -> {
            progressBar.visibility = View.VISIBLE
            progressBar.indeterminateDrawable =
                progressBar.context.getDrawable(R.drawable.loading_animation)
//            "@drawable/my_progress_indeterminate"

//            imageView.setImageResource(R.drawable.loading_animation)
        }
        AsteroidApiStatus.ERROR -> {
            progressBar.visibility = View.VISIBLE
//            imageView.setImageResource(R.drawable.ic_connection_error)
        }
        AsteroidApiStatus.DONE -> {
            progressBar.visibility = View.GONE
        }
    }
}