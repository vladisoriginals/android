package android.example.movies.data.network

import android.example.movies.data.database.DatabaseMovies
import android.example.movies.data.database.DatabaseTrailer
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkMovie(
    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String,
    @Json(name = "release_date") val releaseDate: String,
    @Json(name = "overview") val overview: String,
    @Json(name = "poster_path") val posterPath: String,
    @Json(name = "backdrop_path")val backdropPatch: String
)

@JsonClass(generateAdapter = true)
data class NetworkContainerMovies(val results: List<NetworkMovie>)

fun NetworkContainerMovies.asDatabaseMovies(): List<DatabaseMovies>{
    return results.map {
        DatabaseMovies(
            id = it.id,
            title = it.title,
            releaseDate = it.releaseDate,
            overview = it.overview,
            posterPath = POSTER_BASE_URL + it.posterPath,
            backdropPatch = BACKDROP_BASE_URL + it.backdropPatch
        )
    }

}

@JsonClass(generateAdapter = true)
data class NetworkVideo(
    @Json(name = "key") val url: String
)
@JsonClass(generateAdapter = true)
data class NetworkContainerVideos(
    @Json(name = "id")val id: Int,
    @Json(name = "results")val urls: List<NetworkVideo> )

fun NetworkContainerVideos.toMovieTrailerEntity(): DatabaseTrailer =
    DatabaseTrailer (id, "$TRAILER__BASE_URL${urls[0].url}")


private const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w342"
private const val BACKDROP_BASE_URL = "https://image.tmdb.org/t/p/w780"
private const val TRAILER__BASE_URL = "https://www.youtube.com/watch?v="
