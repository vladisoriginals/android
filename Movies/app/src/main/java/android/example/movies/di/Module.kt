package android.example.movies.di

import android.example.movies.database.getInstance
import android.example.movies.details.DetailsViewModel
import android.example.movies.domain.Movie
import android.example.movies.network.moviesNetwork
import android.example.movies.repository.MoviesRepository
import android.example.movies.repository.MoviesRepositoryImpl
import android.example.movies.search.SearchViewModel
import android.example.movies.usecase.GetMovies
import android.example.movies.usecase.GetTrailer
import android.example.movies.usecase.GetTrailerFromNetwork
import android.example.movies.usecase.RefreshMovies
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val moduleInfo = module {

    viewModel { SearchViewModel(get<RefreshMovies>(), get<GetMovies>()) }
    viewModel { (movie: Movie) -> DetailsViewModel(movie, get<GetTrailerFromNetwork>(), get<GetTrailer>()) }

    single { moviesNetwork() }
    single { getInstance(androidContext()) }
    single<MoviesRepository> { MoviesRepositoryImpl(get(), get()) }

    factory { GetMovies(get<MoviesRepository>()) }
    factory { RefreshMovies(get<MoviesRepository>()) }
    factory { GetTrailerFromNetwork(get<MoviesRepository>()) }
    factory { GetTrailer(get<MoviesRepository>()) }

}