package android.example.movies.data.repository

import android.example.movies.data.database.*
import android.example.movies.domain.Movie
import android.example.movies.domain.Video
import android.example.movies.data.network.*
import android.example.movies.domain.MoviesRepository
import io.reactivex.Completable
import io.reactivex.Observable


class MoviesRepositoryImpl(
    private val databaseMovies: MoviesDatabase,
    private val moviesApiService: MoviesApiService
) :
    MoviesRepository {

    override fun getMovies(): Observable<List<Movie>> = databaseMovies.moviesDao.getMovies().map {
        it.asDomainModel()
    }

    override fun getTrailer(movie: Movie): Observable<Video> {
        return databaseMovies.trailerDao.getTrailerByMovieId(movie.id).map {
            it.asDomainModel()
        }
    }


    override fun refreshMovies(): Completable {
        return moviesApiService.getPopularMoviesAsync().flatMapCompletable {
            fromCompletableMovies(it)

        }

    }

    override fun processingRequestNetworkAndGetTrailerIntoDb(movie: Movie): Completable {

        return moviesApiService.getMovieVideosAsync(movie.id).flatMapCompletable {
            fromCompletableTrailer(it)
        }
    }

    private fun fromCompletableMovies(networkContainerMovies: NetworkContainerMovies): Completable {
        return Completable.fromAction {
            databaseMovies.moviesDao.insertAll(networkContainerMovies.asDatabaseMovies())
        }
    }


    private fun fromCompletableTrailer(networkContainerVideos: NetworkContainerVideos): Completable {
        return Completable.fromAction {
            databaseMovies.trailerDao.insertURL(networkContainerVideos.toMovieTrailerEntity())
        }
    }


}