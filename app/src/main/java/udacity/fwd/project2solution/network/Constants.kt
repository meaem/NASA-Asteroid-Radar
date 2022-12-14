package udacity.fwd.project2solution.network

import okhttp3.HttpUrl
import udacity.fwd.project2solution.BuildConfig

object Constants {
    const val SCHEME = "https"
    const val HOST = "api.nasa.gov"
    const val API_KEY_STRING = "api_key"
    const val START_DATE_STRING = "start_date"
    const val END_DATE_STRING = "end_date"
    const val API_QUERY_DATE_FORMAT = "yyyy-MM-dd"
    const val DEFAULT_END_DATE_DAYS = 7

    const val API_KEY_VALUE = BuildConfig.API_KEY_VALUE


    val url = HttpUrl.Builder()
        .scheme(SCHEME)
        .host(HOST)
        .build()
}

