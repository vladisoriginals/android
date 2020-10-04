package android.example.movies.details

import android.app.Application
import android.example.movies.database.MoviesDatabase
import android.example.movies.domain.Movie
import android.example.movies.domain.Video
import android.example.movies.repository.MoviesRepository
import androidx.lifecycle.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val movie: Movie,
    app: Application
) : ViewModel() {

    private val repository = MoviesRepository(MoviesDatabase.getInstance(app))
    private val _trailer = MutableLiveData<Video>()
    val trailer: LiveData<Video> = _trailer

    init {
        viewModelScope.launch {
            repository.fetchTrailer(movie.id).collect { _trailer.value = it }
        }
        viewModelScope.launch {
            repository.getTrailerFromNetwork(movie)
        }
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
