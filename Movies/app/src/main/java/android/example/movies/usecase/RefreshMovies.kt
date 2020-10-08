package android.example.movies.usecase

import android.example.movies.repository.MoviesRepository
import io.reactivex.Completable

interface RefreshMoviesUseCase {
    fun execute(): Completable
}

class RefreshMovies(val repository: MoviesRepository) : RefreshMoviesUseCase {

    override fun execute(): Completable {
        return repository.refreshMovies()
    }
}