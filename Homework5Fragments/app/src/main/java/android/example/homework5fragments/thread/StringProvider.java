package android.example.homework5fragments.thread;

import android.content.Context;

import androidx.annotation.StringRes;

public class StringProvider {
    Context context;
    public StringProvider(Context context){
        this.context = context;
    }
    String getString(@StringRes int stringResId){
       return context.getString(stringResId);
    }
}
