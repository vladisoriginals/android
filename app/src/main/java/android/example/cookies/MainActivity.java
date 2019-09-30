package android.example.cookies;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button eatCookie = findViewById(R.id.b_eat_cookie);
        eatCookie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView imageView = findViewById(R.id.android_cookie_image_view);
                imageView.setImageResource(R.drawable.after_cookie);
                TextView textView = findViewById(R.id.status_text_view);
                textView.setText("I'm so full");
            }
        });
    }

}
