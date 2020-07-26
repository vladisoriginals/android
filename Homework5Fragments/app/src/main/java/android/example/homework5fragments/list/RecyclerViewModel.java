package android.example.homework5fragments.list;

import android.content.Context;
import android.example.homework5fragments.R;
import android.example.homework5fragments.data.Movies;
import android.example.homework5fragments.repository.MoviesRepository;
import android.example.homework5fragments.thread.StringProvider;
import android.example.homework5fragments.utils.SingleEventLiveData;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;


class RecyclerViewModel extends ViewModel {
    private final static String TAG = "tag";
    RecyclerViewModel(MoviesRepository moviesRepository, StringProvider stringProvider) {
        new Thread(() -> {
            try {
                Log.d(TAG, "hIIIIIIIIII");
                _loading.postValue(true);
                moviesRepository.getPopularMovies((List<Movies> movies)->
                        _movies.setValue(movies)

                );


            } catch (Exception e) {
                errorMutableLiveData.setValue(stringProvider.getString(R.string.error_load_movies, e.getMessage()));
                e.printStackTrace();
            } finally {
                _loading.postValue(false);

            }
        }).start();
    }

    private SingleEventLiveData<List<Movies>> _movies = new SingleEventLiveData<>();
    private SingleEventLiveData<String> errorMutableLiveData = new SingleEventLiveData<>();

    // Movies
    LiveData<List<Movies>> movies = _movies;

    //Loading
    private MutableLiveData<Boolean> _loading = new MutableLiveData<>(false);
    LiveData<Boolean> loading = _loading;


    LiveData<String> error = errorMutableLiveData;
}

class RecyclerViewModelFactory implements ViewModelProvider.Factory {
    private MoviesRepository moviesRepository;
    private Context context;

    RecyclerViewModelFactory(MoviesRepository moviesRepository, Context context) {
        this.moviesRepository = moviesRepository;
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == RecyclerViewModel.class) {
            return (T) new RecyclerViewModel(moviesRepository, new StringProvider(context));
        }
        throw new ClassCastException("Invalid model class was provided");
    }
}