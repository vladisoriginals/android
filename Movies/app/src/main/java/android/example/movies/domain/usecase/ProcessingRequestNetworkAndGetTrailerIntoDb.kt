package android.example.movies.domain.usecase

import android.example.movies.domain.Movie
import android.example.movies.domain.MoviesRepository
import io.reactivex.Completable

interface ProcessingRequestNetworkAndGetTrailerIntoDbUseCase{
    fun execute(movie: Movie): Completable
}

class ProcessingRequestNetworkAndGetTrailerIntoDb (val repository: MoviesRepository) : ProcessingRequestNetworkAndGetTrailerIntoDbUseCase {
    override fun execute(movie: Movie): Completable {
        return repository.processingRequestNetworkAndGetTrailerIntoDb(movie)
    }
}