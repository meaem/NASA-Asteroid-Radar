package udacity.fwd.project2solution.api


import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import udacity.fwd.project2solution.api.Constants.API_KEY_STRING
import udacity.fwd.project2solution.api.Constants.END_DATE_STRING
import udacity.fwd.project2solution.api.Constants.START_DATE_STRING
import udacity.fwd.project2solution.api.Constants.url
import udacity.fwd.project2solution.model.PictureOfDay

/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


private val httpClient = OkHttpClient.Builder()
    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build()

/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object.
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(url)

    .client(httpClient)
    .build()

interface AsteroidApiService {
    @GET("planetary/apod")
    suspend fun getImageOfTheDay(@Query("${API_KEY_STRING}") apiKey: String): PictureOfDay

    //https://api.nasa.gov/neo/rest/v1/feed?start_date=START_DATE&end_date=END_DATE&api_key=YOUR_API_KEY
    @GET("neo/rest/v1/feed")
    suspend fun getAsteroids(
        @Query(API_KEY_STRING) apiKey: String,
        @Query(START_DATE_STRING) start_date: String,
        @Query(END_DATE_STRING) end_date: String
    ): String
}

object AsteroidApi {
    val retrofitService: AsteroidApiService by lazy { retrofit.create(AsteroidApiService::class.java) }
}