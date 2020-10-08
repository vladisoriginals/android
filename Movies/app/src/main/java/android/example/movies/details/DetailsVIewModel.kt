package android.example.movies.details

import android.example.movies.domain.Movie
import android.example.movies.domain.Video
import android.example.movies.repository.MoviesRepository
import android.example.movies.usecase.GetTrailerFromNetworkUseCase
import android.example.movies.usecase.GetTrailerUseCase
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class DetailsViewModel(
    movie: Movie,
    getTrailerFromNetworkUseCase: GetTrailerFromNetworkUseCase,
    getTrailerUseCase: GetTrailerUseCase
) : ViewModel() {

    private val _trailer = BehaviorSubject.create<Video>()
    val trailer: Observable<Video> = _trailer

    private val _error = PublishSubject.create<Unit>()
    val error: Observable<Unit> = _error

    private val disposeBag = CompositeDisposable()

    init {
        val disposeGetTrailer = getTrailerFromNetworkUseCase.execute(movie)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onError = {_error.onNext(Unit)})

        val disposeTrailer = getTrailerUseCase.execute(movie)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _trailer.onNext(it)
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

}
