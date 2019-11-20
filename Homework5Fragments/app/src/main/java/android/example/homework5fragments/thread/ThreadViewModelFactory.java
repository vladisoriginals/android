package android.example.homework5fragments.thread;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import kotlin.Suppress;

public class ThreadViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private Context context;

    public ThreadViewModelFactory(Context context) {
        super();
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == ThreadsViewModel.class) {
            return (T) new ThreadsViewModel(new StringProvider(context));
        }
        return  null;
    }
}
