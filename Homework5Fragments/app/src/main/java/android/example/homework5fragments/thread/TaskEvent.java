package android.example.homework5fragments.thread;

public interface TaskEvent {
    interface Operation{
        void createTask();
        void startTask();
        void cancelTask();
    }

    interface Lifecycle {
        void onPreExecute();
        void onPostExecute();
        void onProgressUpdate(int progress);
        void onCancel();
    }
}
