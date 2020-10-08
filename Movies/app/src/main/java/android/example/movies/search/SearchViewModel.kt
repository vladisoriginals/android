package android.example.movies.search

import android.example.movies.domain.Movie
import android.example.movies.repository.MoviesRepository
import android.example.movies.usecase.GetMovies
import android.example.movies.usecase.GetMoviesUseCase
import android.example.movies.usecase.RefreshMoviesUseCase
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class SearchViewModel(refreshMoviesUseCase: RefreshMoviesUseCase,
                      getMoviesUseCase: GetMoviesUseCase) : ViewModel(){


    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies

    private val _error = PublishSubject.create<Unit>()
    val error: Observable<Unit> = _error
    private val disposeBag = CompositeDisposable()


    init {
        val disposeMovie = getMoviesUseCase.execute()
             ?.subscribeOn(Schedulers.io())
             ?.observeOn(AndroidSchedulers.mainThread())
             ?.subscribe({
                 _movies.value = it
             },{
                 println(it.localizedMessage)
                 _error.onNext(Unit)
             })
        val disposeRefreshMovie = refreshMoviesUseCase.execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onError = {_error.onNext(Unit)})

        disposeBag.add(disposeMovie!!)
        disposeBag.add(disposeRefreshMovie)
    }


    override fun onCleared() {
        disposeBag.clear()
        super.onCleared()
    }

}