package android.example.homework5fragments.details;

import android.content.Context;
import android.example.homework5fragments.R;
import android.example.homework5fragments.data.Movies;
import android.example.homework5fragments.repository.MoviesRepository;
import android.example.homework5fragments.thread.StringProvider;
import android.example.homework5fragments.utils.SingleEventLiveData;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class FragmentDetailsViewModel extends ViewModel {
    private MoviesRepository moviesRepository;
    private StringProvider stringProvider;
    private Movies movies;

    public FragmentDetailsViewModel(MoviesRepository moviesRepository,
                                    StringProvider stringProvider, Movies movies){
        this.moviesRepository = moviesRepository;
        this.stringProvider = stringProvider;
        this.movies = movies;
    }

    private SingleEventLiveData<String> trailerUrlMutableLiveData = new SingleEventLiveData<>();
    private SingleEventLiveData<String> errorMutableLiveData = new SingleEventLiveData<>();

    LiveData<String> openTrailerUrl = trailerUrlMutableLiveData;
    LiveData<String> error = errorMutableLiveData;

    public void onTrailerButtonClicked(){
        Thread mThread = new Thread(()->{
            try {
                trailerUrlMutableLiveData.setValue(moviesRepository.getMovieTrailerUrl(movies));
            }catch (Throwable error){
                errorMutableLiveData.setValue(stringProvider.getString(R.string.error_load_movies,error.getMessage()));
            }
        });
        mThread.start();
    }
}

class DetailsViewModelFactory implements ViewModelProvider.Factory {
    private MoviesRepository moviesRepository;
    private Context context;
    private Movies movies;
    DetailsViewModelFactory(MoviesRepository moviesRepository, Context context, Movies movies){
        this.moviesRepository = moviesRepository;
        this.context =context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == FragmentDetailsViewModel.class){
            return (T) new FragmentDetailsViewModel(moviesRepository,
                    new StringProvider(context), movies);
        }
        return null;
    }
}
