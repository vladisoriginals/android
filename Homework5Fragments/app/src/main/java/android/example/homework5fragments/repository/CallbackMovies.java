package android.example.homework5fragments.repository;

import android.example.homework5fragments.data.Movies;

import java.util.List;

public interface CallbackMovies {
    void getMovieFromCallback(List<Movies> movies);
}
