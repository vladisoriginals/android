package android.example.movies.search

import android.app.Application
import android.example.movies.database.MoviesDatabase
import android.example.movies.domain.Movie
import android.example.movies.repository.MoviesRepository
import androidx.lifecycle.*
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject

class SearchViewModel(application: Application) : AndroidViewModel(application) {

    private val disposable = CompositeDisposable()
    private val repository = MoviesRepository(MoviesDatabase.getInstance(application))
    private val _movies = MutableLiveData<List<Movie>>()
    private val _error = PublishSubject.create<Unit>()

    val movies: LiveData<List<Movie>> = _movies
    val error: Observable<Unit> = _error

    init {
        repository.movies
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { _movies.value = it }
            .addTo(disposable)
        repository.refreshMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onError = {
                    println(it.localizedMessage)
                    _error.onNext(Unit)
                }
            )
            .addTo(disposable)
    }

    override fun onCleared() {
        disposable.clear()
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