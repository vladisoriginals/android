package android.example.movies.presentation.search

import android.example.movies.domain.Movie
import android.example.movies.domain.usecase.GetMoviesUseCase
import android.example.movies.domain.usecase.RefreshMoviesUseCase
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class SearchViewModel(
    refreshMoviesUseCase: RefreshMoviesUseCase,
    getMoviesUseCase: GetMoviesUseCase
) : ViewModel() {

    private val _movies = BehaviorSubject.create<List<Movie>>()
    val movies: Observable<List<Movie>> = _movies

    private val _error = PublishSubject.create<Unit>()
    val error: Observable<Unit> = _error

    private val _showLoading = BehaviorSubject.create<Boolean>()
    val showLoading: Observable<Boolean> = _showLoading

    private val disposeBag = CompositeDisposable()


    init {
        _showLoading.onNext(true)
        val disposeMovie = getMoviesUseCase.execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _movies.onNext(it)
            }, {
                println(it.localizedMessage)
                _error.onNext(Unit)
            }, {
            })
        val disposeRefreshMovie = refreshMoviesUseCase.execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onError = {
                _error.onNext(Unit)
                _showLoading.onNext(false)
            },
                onComplete = { _showLoading.onNext(false) })

        disposeBag.add(disposeMovie)
        disposeBag.add(disposeRefreshMovie)
    }

    override fun onCleared() {
        disposeBag.clear()
        super.onCleared()
    }

}