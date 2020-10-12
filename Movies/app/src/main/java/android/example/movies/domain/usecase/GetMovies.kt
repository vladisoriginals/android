package android.example.movies.domain.usecase

import android.example.movies.domain.Movie
import android.example.movies.domain.MoviesRepository
import io.reactivex.Observable


interface GetMoviesUseCase{
    fun execute(): Observable<List<Movie>>
}

class GetMovies(val repository: MoviesRepository) : GetMoviesUseCase {
    override fun execute(): Observable<List<Movie>> {
        return repository.getMovies()
    }
}