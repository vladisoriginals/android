package android.example.homework5fragments.thread;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.example.homework5fragments.R;
import android.os.Bundle;
import android.widget.Toast;

public class ThreadsActivity extends AppCompatActivity implements TaskEvent.Operation {

    private CounterFragment threadsFragment;
    private ThreadsViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thread_activity);

        if (savedInstanceState == null) {
            CounterFragment fragment= CounterFragment.newInstance(getString(R.string.fragment_handler_exe_title));
            threadsFragment = fragment;
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.cont, fragment, FRAGMENT_TAG)
                    .addToBackStack(null)
                    .commit();
        }else{
            threadsFragment = (CounterFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        }
        viewModel = new ViewModelProvider(this, new ThreadViewModelFactory(this))
                .get(ThreadsViewModel.class);
        LiveData<String> text = viewModel.getStringMutableLiveData();
        text.observe(this,(String s)-> threadsFragment.updateFragmentText(s)

        );
    }

    @Override
    public void createTask() {
        viewModel.onCreateTask();
    }

    @Override
    public void startTask() {
        viewModel.onStartTask();
    }

    @Override
    public void cancelTask() {
        viewModel.onCancelTask();
    }

    private static final String FRAGMENT_TAG ="fragment_tag";
}
