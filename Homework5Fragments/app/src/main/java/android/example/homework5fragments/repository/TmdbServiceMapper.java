package android.example.homework5fragments.repository;

import android.example.homework5fragments.api.dto.MovieDto;
import android.example.homework5fragments.api.dto.MovieVideoDto;
import android.example.homework5fragments.api.dto.PopularMoviesDto;
import android.example.homework5fragments.data.Movies;

import java.util.ArrayList;
import java.util.List;

public class TmdbServiceMapper {
    List<Movies> map(PopularMoviesDto popularMoviesDto) {
        return convert(popularMoviesDto.getResults());
    }

    private List<Movies> convert(List<MovieDto> movieDtos) {
        List<Movies> moviesList = new ArrayList<>();
        for (MovieDto movieDto : movieDtos) {
            moviesList.add(map(movieDto));
        }
        return moviesList;
    }

    private Movies map(MovieDto movieDto) {
        return new Movies(movieDto.getId(), movieDto.getTitle(), POSTER_BASE_URL + movieDto.getPosterPath(),
                BACKDROP_BASE_URL + movieDto.getBackdropPath(), movieDto.getOverview(), movieDto.getReleaseDate()) {
        };
    }

    public String mapTrailerUrl(MovieVideoDto movieVideoDto) {
        return YOUTUBE_BASE_URL + movieVideoDto.getKey();
    }


    private static final String POSTER_BASE_URL = "https://image.tmdb.org/t/p/w342";
    private static final String BACKDROP_BASE_URL = "https://image.tmdb.org/t/p/w780";
    private static final String YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v=";
}
