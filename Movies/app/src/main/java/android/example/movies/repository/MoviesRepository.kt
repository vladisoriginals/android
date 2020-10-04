package android.example.movies.repository

import android.example.movies.database.DatabaseMovies
import android.example.movies.database.DatabaseTrailer
import android.example.movies.database.MoviesDatabase
import android.example.movies.domain.Movie
import android.example.movies.domain.Video
import android.example.movies.network.MoviesNetwork
import android.example.movies.network.asDatabaseMovies
import android.example.movies.network.toMovieTrailerEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class MoviesRepository(private val databaseMovies: MoviesDatabase) {

    val movies: Flow<List<Movie>> = databaseMovies.moviesDao.getMovies().map { it.asDomainModel() }

    fun fetchTrailer(movieId: Int): Flow<Video> {
        return databaseMovies.trailerDao.getTrailerByMovieId(movieId)
            .filterNotNull()
            .map { it.asDomainModel() }
    }

    suspend fun refreshMovies() {
        withContext(Dispatchers.IO) {
            val moviesContainer = MoviesNetwork.retrofitService.getPopularMoviesAsync().await()
            databaseMovies.moviesDao.insertAll(moviesContainer.asDatabaseMovies())
        }

    }

    suspend fun getTrailerFromNetwork(movie: Movie) {
        withContext(Dispatchers.IO) {
            val trailer = MoviesNetwork.retrofitService.getMovieVideosAsync(movie.id).await()
            databaseMovies.trailerDao.insertURL(trailer.toMovieTrailerEntity())
        }
    }

    private fun List<DatabaseMovies>.asDomainModel(): List<Movie>{
        return map {
            Movie(
                id = it.id,
                title = it.title,
                releaseDate = it.releaseDate,
                overview = it.overview,
                posterPath = it.posterPath,
                backdropPatch = it.posterPath
            )
        }
    }

    private fun DatabaseTrailer.asDomainModel(): Video = Video(id, url)

}