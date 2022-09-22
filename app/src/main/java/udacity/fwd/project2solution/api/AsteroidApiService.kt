package udacity.fwd.project2solution.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import udacity.fwd.project2solution.api.Constants.API_KEY_STRING
import udacity.fwd.project2solution.api.Constants.API_KEY_VALUE
import udacity.fwd.project2solution.api.Constants.HOST
import udacity.fwd.project2solution.api.Constants.SCHEME
import udacity.fwd.project2solution.model.PictureOfDay

/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val url = HttpUrl.Builder()
    .scheme(SCHEME)
    .host(HOST)
    .addQueryParameter(API_KEY_STRING, API_KEY_VALUE)
    .build(); // HttpUrl.Builder(). BASE_URL.toUri().buildUpon().query(API_QUERY).build()


private val logging = HttpLoggingInterceptor()
// set your desired log level
logging.setLevel(HttpLoggingInterceptor.Level.BODY)
private val httpClient = OkHttpClient.Builder()
// add your other interceptors …
// add logging as last interceptor
httpClient.addInterceptor(logging);  // <-- this is the important line!

/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object.
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(url)

    .build()

interface AsteroidApiService {
    @GET("planetary/apod")
    suspend fun getImageOfTheDay(): PictureOfDay
}

object AsteroidApi {
    val retrofitService: AsteroidApiService by lazy { retrofit.create(AsteroidApiService::class.java) }
}