package android.example.movies.di

import android.example.movies.database.getInstance
import android.example.movies.details.DetailsViewModel
import android.example.movies.domain.Movie
import android.example.movies.network.moviesNetwork
import android.example.movies.repository.MoviesRepository
import android.example.movies.search.SearchViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val moduleInfo = module {

    viewModel { SearchViewModel( get()) }
    viewModel { (movie: Movie) -> DetailsViewModel(get(), movie) }

    single { moviesNetwork() }
    single { getInstance(androidContext()) }
    single { MoviesRepository(get(), get()) }

}