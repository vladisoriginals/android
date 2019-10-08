package android.example.homework4recyclerview;

import android.content.Intent;
import android.example.homework4recyclerview.data.Movie;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;

public class DetailsActivity extends AppCompatActivity {
    public final static String MOVIE_KEY = "Movie_key";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent movieIntent = getIntent();
        final Movie movie = (Movie) movieIntent.getSerializableExtra(MOVIE_KEY);

        ImageView poster = findViewById(R.id.iv_details_poster);
        ImageView image = findViewById(R.id.im_details_image);
        TextView title = findViewById(R.id.tv_title_details);
        TextView releasedData = findViewById(R.id.tv_details_released_date);
        TextView overviewText = findViewById(R.id.tv_overview_text);
        poster.setImageResource(movie.getBackdropRes());
        image.setImageResource(movie.getPosterRes());
        title.setText(movie.getTitle());
        releasedData.setText(movie.getReleasedDate());
        overviewText.setText(movie.getOverview());

        Button movieButton = findViewById(R.id.b_movie_trailer);
        movieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMovieTrailer(movie.getTrailerUrl());
            }
        });

    }
    private void openMovieTrailer(String url){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        if(intent.resolveActivity(getPackageManager()) !=null){
            startActivity(intent);
        }else {
            Log.d("Intent", "Can't process intent!");

        }
    }

}
