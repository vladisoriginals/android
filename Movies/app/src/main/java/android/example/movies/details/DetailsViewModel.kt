package android.example.movies.details

import android.app.Application
import android.example.movies.database.MoviesDatabase
import android.example.movies.domain.Video
import android.example.movies.repository.MoviesRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.withLatestFrom
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject

class DetailsViewModel(
    private val movieId: Int,
    app: Application
) : ViewModel() {

    private val disposable = CompositeDisposable()
    private val repository = MoviesRepository(MoviesDatabase.getInstance(app))
    private val viewState = BehaviorSubject.create<Video>()
    private val launchTrailerEvent = PublishSubject.create<Unit>()

    val trailerUrl: Observable<String> = trailerFlow()

    init {
        repository.fetchTrailer(movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { viewState.onNext(it) }
            .addTo(disposable)
        repository.getTrailerFromNetwork(movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
            .addTo(disposable)
    }

    fun launchTrailer() {
        launchTrailerEvent.onNext(Unit)
    }

    private fun trailerFlow(): Observable<String> {
        return launchTrailerEvent.withLatestFrom(viewState) { _, state -> state }
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
