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
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.*

class DetailsViewModel(
    movie: Movie,
    app: Application
) : ViewModel() {

    private val repository = MoviesRepository(MoviesDatabase.getInstance(app))

    private val _trailer = MutableLiveData<Video>()
    val trailer: LiveData<Video> = _trailer

    private val _error = PublishSubject.create<Unit>()
    val error: Observable<Unit> = _error

    private val disposeBag = CompositeDisposable()

    init {
        val disposeGetTrailer = repository.getTrailerFromNetwork(movie)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onError = {_error.onNext(Unit)})

        val disposeTrailer = repository.getTrailer(movie)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _trailer.value = it
            },{
                _error.onNext(Unit)
            })
        disposeBag.add(disposeGetTrailer)
        disposeBag.add(disposeTrailer)
    }

    override fun onCleared() {
        disposeBag.clear()
        super.onCleared()
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
