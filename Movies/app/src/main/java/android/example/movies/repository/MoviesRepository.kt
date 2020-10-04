package android.example.movies.repository

import android.example.movies.database.DatabaseMovies
import android.example.movies.database.DatabaseTrailer
import android.example.movies.database.MoviesDatabase
import android.example.movies.domain.Movie
import android.example.movies.domain.Video
import android.example.movies.network.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

class MoviesRepository(private val databaseMovies: MoviesDatabase) {

    val movies: Observable<List<Movie>> = databaseMovies.moviesDao.getMovies().map { it.asDomainModel() }

    fun fetchTrailer(movieId: Int): Observable<Video> {
        return databaseMovies.trailerDao.getTrailerByMovieId(movieId)
            .map { it.asDomainModel() }
    }

    fun refreshMovies(): Completable {
        return MoviesNetwork.retrofitService.getPopularMoviesAsync()
            .flatMapCompletable(::insertMoviesInDb)
    }

    fun getTrailerFromNetwork(movieId: Int): Completable {
        return MoviesNetwork.retrofitService.getMovieVideosAsync(movieId)
            .flatMapCompletable(::insertURLInDb)
    }

    private fun insertMoviesInDb(movies: NetworkContainerMovies): Completable {
        return Completable.fromAction {
            databaseMovies.moviesDao.insertAll(movies.asDatabaseMovies())
        }
    }

    private fun insertURLInDb(video: NetworkContainerVideos): Completable {
        return Completable.fromAction {
            databaseMovies.trailerDao.insertURL(video.toMovieTrailerEntity())
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