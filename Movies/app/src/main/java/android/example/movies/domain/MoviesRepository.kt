package android.example.movies.domain

import android.example.movies.domain.Movie
import android.example.movies.domain.Video
import io.reactivex.Completable
import io.reactivex.Observable

interface MoviesRepository {

    fun getMovies(): Observable<List<Movie>>
    fun getTrailer(movie: Movie): Observable<Video>
    fun refreshMovies(): Completable
    fun processingRequestNetworkAndGetTrailerIntoDb(movie: Movie): Completable

}