package android.example.homework5fragments.thread;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

public class SimpleAsyncTask {
    private TaskEvent.Lifecycle listener;
    private Thread mBackgroundThread;
    boolean isCanceled = false;

    public SimpleAsyncTask(TaskEvent.Lifecycle listener){
        this.listener = listener;
    }

    private void onPreExecute(){
        listener.onPreExecute();
    }

    private void doInBackground(){
        int end = 10;
        for (int i = 0;i<end;i++){
            if (isCanceled){
                return;
            }
            publishProgress(i);
            SystemClock.sleep(500);

        }
    }

    private void onPostExecute(){
        listener.onPostExecute();
    }

    private void onProgressUpdate(int progress){
        listener.onProgressUpdate(progress);
    }

    public void execute(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                onPreExecute();
                mBackgroundThread = new Thread("Handler_executor_thread"){
                    @Override
                    public void run() {
                        doInBackground();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                onPostExecute();
                            }
                        });
                    }
                };
                mBackgroundThread.start();
            }
        });
    }

    private void runOnUiThread(Runnable runnable){
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(runnable);
    }

    private void publishProgress(final int progress){
        Runnable runnable =new Runnable() {
            @Override
            public void run() {
                onProgressUpdate(progress);
            }
        };
        runOnUiThread(runnable);
    }

    public void cancel(){
        isCanceled=true;
        if (mBackgroundThread != null){
            mBackgroundThread.interrupt();
        }
    }
}
