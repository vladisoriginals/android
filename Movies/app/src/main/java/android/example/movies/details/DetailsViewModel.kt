package android.example.movies.details

import android.app.Application
import android.example.movies.database.MoviesDatabase
import android.example.movies.domain.Video
import android.example.movies.repository.MoviesRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val movieId: Int,
    app: Application
) : ViewModel() {

    private val repository = MoviesRepository(MoviesDatabase.getInstance(app))
    private val viewState = MutableStateFlow<Video?>(null)
    private val launchTrailerEvent = BroadcastChannel<Unit>(1)

    val trailerUrl: Flow<String> = trailerFlow()

    init {
        viewModelScope.launch {
            repository.fetchTrailer(movieId).collect { viewState.value = it }
        }
        viewModelScope.launch {
            repository.getTrailerFromNetwork(movieId)
        }
    }

    fun launchTrailer() {
        launchTrailerEvent.offer(Unit)
    }

    private fun trailerFlow(): Flow<String> {
        return launchTrailerEvent.asFlow()
            .map { viewState.value }
            .filterNotNull()
            .map { it.url }
    }

    class Factory(
        private val movieId: Int,
        private val app: Application
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DetailsViewModel(movieId, app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}
