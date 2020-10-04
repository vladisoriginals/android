package android.example.movies.details

import android.app.Application
import android.example.movies.database.MoviesDatabase
import android.example.movies.domain.Movie
import android.example.movies.domain.Video
import android.example.movies.repository.MoviesRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val movie: Movie,
    private val app: Application
) : ViewModel() {

    private val repository = MoviesRepository(MoviesDatabase.getInstance(app))
    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _trailer = MutableLiveData<Video>()
    val trailer: LiveData<Video> = _trailer


    private fun getUrl() {
        viewModelScope.launch {
            repository.getTrailerFromNetwork(movie)

        }
    }

    init {
        viewModelScope.launch {
            repository.fetchTrailer(movie.id).collect { _trailer.value = it }
        }
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
