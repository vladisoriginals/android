package android.example.movies.data.network

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val SERVER_API_KEY = "ee91d1ada39b2d77c3f4bc78957fcee7"

private const val BASE_URL = "https://api.themoviedb.org/3/"

interface MoviesApiService {
    @GET(value = "movie/popular")
    fun getPopularMoviesAsync(@Query("api_key") apiKey: String = SERVER_API_KEY) : Single<NetworkContainerMovies>

    @GET(value = "movie/{movieId}/videos")
    fun getMovieVideosAsync(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String = SERVER_API_KEY) : Single<NetworkContainerVideos>
}




fun getMoviesService(): MoviesApiService {
 val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create())
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .build()

    return retrofit.create(MoviesApiService::class.java)
}




