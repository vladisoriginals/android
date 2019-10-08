package android.example.homework4recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.example.homework4recyclerview.data.DataMovies;
import android.example.homework4recyclerview.data.Movie;
import android.os.Bundle;
import android.widget.Adapter;

import java.util.ArrayList;
import java.util.List;

import static android.example.homework4recyclerview.DetailsActivity.MOVIE_KEY;

public class MoviesActivity extends AppCompatActivity implements ClickListener{

    List<Movie> movies;
    private RecyclerView moviewRecycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movies_activity);
        moviewRecycler = findViewById(R.id.moviesList);
        movies = DataMovies.getMovieList();
        MoviesAdapter moviesAdapter = new  MoviesAdapter(this, this, movies);
        moviewRecycler.setAdapter(moviesAdapter);
    }

    @Override
    public void onClick(int position) {
        Movie movie = movies.get(position);
        Intent intent = new Intent(this,DetailsActivity.class);
        intent.putExtra(MOVIE_KEY, movie);
        startActivity(intent);

    }
}
