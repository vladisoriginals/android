package android.example.movies.details

import android.app.Application
import android.example.movies.database.MoviesDatabase
import android.example.movies.domain.Movie
import android.example.movies.repository.MoviesRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.*

class DetailsViewModel(
    private val movie: Movie,
    private val app: Application
) : ViewModel() {

    private val repository = MoviesRepository(MoviesDatabase.getInstance(app))

    val trailer = repository.getTrailer(movie)


    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    private fun getUrl() {
        viewModelScope.launch {
            repository.getTrailerFromNetwork(movie)

        }
    }

    init {
        getUrl()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


    class Factory(val movie: Movie, val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DetailsViewModel(movie, app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
