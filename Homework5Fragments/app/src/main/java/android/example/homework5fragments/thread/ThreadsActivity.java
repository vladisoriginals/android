package android.example.homework5fragments.thread;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.example.homework5fragments.R;
import android.os.Bundle;
import android.widget.Toast;

public class ThreadsActivity extends AppCompatActivity implements TaskEvent.Lifecycle,TaskEvent.Operation {

    private SimpleAsyncTask task;
    private CounterFragment threadsFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thread_activity);

        if (savedInstanceState == null) {
            CounterFragment fragment= CounterFragment.newInstance(getString(R.string.fragment_handler_exe_title));
            threadsFragment = fragment;
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.cont, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void createTask() {
        Toast.makeText(this, getString(R.string.msg_thread_oncreate), Toast.LENGTH_SHORT).show();
        task = new SimpleAsyncTask(this);
    }

    @Override
    public void startTask() {
        SimpleAsyncTask taskCopy = task;
        if (taskCopy == null || taskCopy.isCanceled){
            Toast.makeText(this, R.string.msg_should_create_task, Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, getString(R.string.msg_thread_onstart), Toast.LENGTH_SHORT).show();
            task.execute();
        }
    }

    @Override
    public void cancelTask() {
        if (task == null) {
            Toast.makeText(this, R.string.msg_should_create_task, Toast.LENGTH_SHORT).show();
        } else {
            task.cancel();
        }

    }

    @Override
    public void onPreExecute() {
        Toast.makeText(this, getString(R.string.msg_preexecute), Toast.LENGTH_SHORT).show();

        threadsFragment.updateFragmentText(getString(R.string.task_created));
    }

    @Override
    public void onPostExecute() {
        Toast.makeText(this, getString(R.string.msg_postexecute), Toast.LENGTH_SHORT).show();

        threadsFragment.updateFragmentText(getString(R.string.done));
        task = null;

    }

    @Override
    public void onProgressUpdate(int progress) {
        threadsFragment.updateFragmentText(String.valueOf(progress));

    }

    @Override
    public void onCancel() {
        Toast.makeText(this, getString(R.string.msg_thread_oncancel), Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onDestroy() {
        if (task!=null){
            task.cancel();
            task = null;
        }
        super.onDestroy();
    }
}
