package android.example.movies.domain.usecase

import android.example.movies.domain.MoviesRepository
import io.reactivex.Completable

interface RefreshMoviesUseCase {
    fun execute(): Completable
}

class RefreshMovies(val repository: MoviesRepository) : RefreshMoviesUseCase {

    override fun execute(): Completable {
        return repository.refreshMovies()
    }
}