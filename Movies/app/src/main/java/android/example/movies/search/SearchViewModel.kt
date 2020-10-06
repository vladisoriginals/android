package android.example.movies.search

import android.app.Application
import android.example.movies.database.MoviesDatabase
import android.example.movies.domain.Movie
import android.example.movies.repository.MoviesRepository
import androidx.lifecycle.*
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.internal.util.HalfSerializer.onNext
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.io.IOException

class SearchViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = MoviesRepository(MoviesDatabase.getInstance(application))

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies

    private val _error = PublishSubject.create<Unit>()
    val error: Observable<Unit> = _error
    private val disposeBag = CompositeDisposable()


    init {
        val disposeMovie = repository.movies?.subscribeOn(Schedulers.io())
             ?.observeOn(AndroidSchedulers.mainThread())
             ?.subscribe({
                 _movies.value = it
             },{
                 println(it.localizedMessage)
                 _error.onNext(Unit)
             })
        val disposeRefreshMovie = repository.refreshMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onError = {_error.onNext(Unit)})

        disposeBag.add(disposeMovie!!)
        disposeBag.add(disposeRefreshMovie)
    }

    private fun refreshDataFromRepository() {
        viewModelScope.launch {
            try {
                repository.refreshMovies()

            } catch (network: IOException) {
                println(network.localizedMessage)


            }
        }
    }


    override fun onCleared() {
        disposeBag.clear()
        super.onCleared()
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