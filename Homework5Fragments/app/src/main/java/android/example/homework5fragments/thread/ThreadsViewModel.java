package android.example.homework5fragments.thread;

import android.example.homework5fragments.R;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ThreadsViewModel extends ViewModel {
    public ThreadsViewModel(StringProvider stringProvider){
        this.stringProvider = stringProvider;
    }
    private StringProvider stringProvider;
    private MutableLiveData<String> stringMutableLiveData = new MutableLiveData<>();
    private SimpleAsyncTask asyncTask;
    private TaskEvent.Lifecycle listener = new TaskEvent.Lifecycle() {
        @Override
        public void onPreExecute() {
        }

        @Override
        public void onPostExecute() {
            stringMutableLiveData.postValue(stringProvider.getString(R.string.done));
        }

        @Override
        public void onProgressUpdate(int progress) {
            stringMutableLiveData.postValue(String.valueOf(progress));
        }

        @Override
        public void onCancel() {

        }
    };

    public MutableLiveData<String> getStringMutableLiveData() {
        return stringMutableLiveData;
    }

    void onCreateTask(){
        stringMutableLiveData.postValue(stringProvider.getString((R.string.msg_thread_oncreate)));
        asyncTask = new SimpleAsyncTask(listener);
    }
    void onStartTask(){
        SimpleAsyncTask copyAsyncTask = asyncTask;
        if (copyAsyncTask == null || copyAsyncTask.isCanceled){
            stringMutableLiveData.postValue(stringProvider.getString(R.string.msg_should_create_task));
        }else{
            asyncTask.execute();
        }
    }
    void onCancelTask(){
        if (asyncTask==null){
            stringMutableLiveData.postValue(stringProvider.getString(R.string.msg_should_create_task));
        }else{
            asyncTask.cancel();
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        asyncTask.cancel();
    }
}
