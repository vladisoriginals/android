package android.example.movies.search

import android.app.Application
import android.example.movies.database.MoviesDatabase
import android.example.movies.domain.Movie
import android.example.movies.repository.MoviesRepository
import androidx.lifecycle.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.IOException

class SearchViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = MoviesRepository(MoviesDatabase.getInstance(application))
    private val _movies = MutableLiveData<List<Movie>>()
    private val _error = BroadcastChannel<Unit>(1)

    val movies: LiveData<List<Movie>> = _movies
    val error: Flow<Unit> = _error.asFlow()

    init {
        viewModelScope.launch {
            repository.movies.collect { _movies.value = it }
        }
        viewModelScope.launch {
            try {
                repository.refreshMovies()
            } catch (network: IOException) {
                println(network.localizedMessage)
                _error.send(Unit)
            }
        }
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SearchViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}