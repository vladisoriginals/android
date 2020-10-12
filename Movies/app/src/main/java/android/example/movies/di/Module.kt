package android.example.movies.di

import android.example.movies.data.database.MoviesDatabase
import android.example.movies.data.database.getDatabaseInstance
import android.example.movies.presentation.details.DetailsViewModel
import android.example.movies.domain.Movie
import android.example.movies.data.network.MoviesApiService
import android.example.movies.data.network.getMoviesService
import android.example.movies.domain.MoviesRepository
import android.example.movies.data.repository.MoviesRepositoryImpl
import android.example.movies.presentation.search.SearchViewModel
import android.example.movies.domain.usecase.*
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val moduleInfo = module {

    viewModel { SearchViewModel(get<RefreshMovies>(), get<GetMovies>()) }
    viewModel { (movie: Movie) -> DetailsViewModel(movie, get<ProcessingRequestNetworkAndGetTrailerIntoDb>(), get<GetTrailer>()) }

    single<MoviesApiService> { getMoviesService() }
    single<MoviesDatabase> { getDatabaseInstance(androidContext()) }
    single<MoviesRepository> { MoviesRepositoryImpl(get<MoviesDatabase>(), get<MoviesApiService>()) }

    factory<GetMovies> { GetMovies(get<MoviesRepository>()) }
    factory<RefreshMovies> { RefreshMovies(get<MoviesRepository>()) }
    factory<ProcessingRequestNetworkAndGetTrailerIntoDb> { ProcessingRequestNetworkAndGetTrailerIntoDb(get<MoviesRepository>()) }
    factory<GetTrailer> { GetTrailer(get<MoviesRepository>()) }

}