package android.example.movies.usecase

import android.example.movies.domain.Movie
import android.example.movies.domain.Video
import android.example.movies.repository.MoviesRepository
import io.reactivex.Observable

interface GetTrailerUseCase {
    fun execute(movie: Movie): Observable<Video>
}

class GetTrailer (val repository: MoviesRepository) : GetTrailerUseCase {
    override fun execute(movie: Movie): Observable<Video> {
       return repository.getTrailer(movie)
    }
}