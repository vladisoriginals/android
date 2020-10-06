package android.example.movies.repository

import android.example.movies.database.*
import android.example.movies.domain.Movie
import android.example.movies.domain.Video
import android.example.movies.network.*
import io.reactivex.Completable
import io.reactivex.Observable


class MoviesRepository(private val databaseMovies: MoviesDatabase) {

    val movies: Observable<List<Movie>>? = databaseMovies.moviesDao.getMovies().map{
         it.asDomainModel()
    }

    fun getTrailer(movie: Movie): Observable<Video> {
        return databaseMovies.trailerDao.getTrailerByMovieId(movie.id).map {
            it.asDomainModel()
        }
    }


    fun refreshMovies(): Completable {
        return MoviesNetwork.retrofitService.getPopularMoviesAsync().flatMapCompletable {
            fromCompletableMovies(it)

        }

    }

    fun fromCompletableMovies(networkContainerMovies: NetworkContainerMovies): Completable{
        return Completable.fromAction{
            databaseMovies.moviesDao.insertAll(networkContainerMovies.asDatabaseMovies())
        }
    }

    fun getTrailerFromNetwork(movie: Movie): Completable {

        return MoviesNetwork.retrofitService.getMovieVideosAsync(movie.id).flatMapCompletable {
                   fromCompletableTrailer(it)
        }
    }

    fun fromCompletableTrailer(networkContainerVideos: NetworkContainerVideos): Completable{
        return Completable.fromAction {
            databaseMovies.trailerDao.insertURL(networkContainerVideos.toMovieTrailerEntity())
        }
    }


}