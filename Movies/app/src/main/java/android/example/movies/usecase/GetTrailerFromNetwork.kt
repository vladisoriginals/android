package android.example.movies.usecase

import android.example.movies.domain.Movie
import android.example.movies.repository.MoviesRepository
import io.reactivex.Completable

interface GetTrailerFromNetworkUseCase{
    fun execute(movie: Movie): Completable
}

class GetTrailerFromNetwork (val repository: MoviesRepository) : GetTrailerFromNetworkUseCase {
    override fun execute(movie: Movie): Completable {
        return repository.getTrailerFromNetwork(movie)
    }
}