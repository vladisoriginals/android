package android.example.homework5fragments.thread;

import android.content.Context;

import androidx.annotation.AnyRes;
import androidx.annotation.StringRes;

public class StringProvider {
    Context context;
    public StringProvider(Context context){
        this.context = context;
    }
     String getString(@StringRes int stringResId){
       return context.getString(stringResId);
    }

    public String getString(@StringRes int stringResId, String s){
        return context.getString(stringResId,s);
    }
}
