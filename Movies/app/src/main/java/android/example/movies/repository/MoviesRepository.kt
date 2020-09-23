package android.example.movies.repository

import android.example.movies.database.*
import android.example.movies.domain.Movie
import android.example.movies.domain.Video
import android.example.movies.network.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesRepository(private val databaseMovies: MoviesDatabase) {

    val movies: LiveData<List<Movie>> = Transformations.map(databaseMovies.moviesDao.getMovies()) {
        it.asDomainModel()
    }

    fun getTrailer(movie: Movie) : LiveData<Video> {
        return Transformations.map((databaseMovies.trailerDao.getTrailerByMovieId(movie.id))) {
               it.asDomainModel()
           }
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



}