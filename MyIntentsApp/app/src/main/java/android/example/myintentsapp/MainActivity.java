package android.example.myintentsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText textEntry;
    private Button changeActivityButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textEntry = findViewById(R.id.et_text_entry);
        changeActivityButton = findViewById(R.id.change_activity);

        changeActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textEntered = textEntry.getText().toString();
                Context context = MainActivity.this;
                Class DestinationActivity = ChildActivity.class;
                Intent childActivityIntent = new Intent(context, DestinationActivity);
                childActivityIntent.putExtra(Intent.EXTRA_TEXT,textEntered);
                startActivity(childActivityIntent);
            }
        });
    }
}
