package android.example.homework5fragments.repository;

import android.example.homework5fragments.Secret;
import android.example.homework5fragments.api.TmdbServiceApi;
import android.example.homework5fragments.api.dto.MovieVideosDto;
import android.example.homework5fragments.api.dto.PopularMoviesDto;
import android.example.homework5fragments.data.Movies;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesRepository {

    private TmdbServiceApi tmdbServiceApi;
    private final TmdbServiceMapper tmdbServiceMapper;
    private PopularMoviesDto popularMoviesDto;
    private MovieVideosDto movieVideosDto;


    public MoviesRepository(TmdbServiceApi tmdbServiceApi, TmdbServiceMapper tmdbServiceMapper) {
        this.tmdbServiceApi = tmdbServiceApi;
        this.tmdbServiceMapper = tmdbServiceMapper;
    }

    public void getPopularMovies(CallbackMovies callbackMovies) {

        Call<PopularMoviesDto> call = tmdbServiceApi.getPopularMovies(Secret.SERVER_API_KEY);
        call.enqueue(new Callback<PopularMoviesDto>() {
            @Override
            public void onResponse(@NotNull Call<PopularMoviesDto> call, @NotNull Response<PopularMoviesDto> response) {
                popularMoviesDto = response.body();

                if (popularMoviesDto != null) {
                    callbackMovies.getMovieFromCallback(tmdbServiceMapper.map(popularMoviesDto));
                }
            }

            @Override
            public void onFailure(@NotNull Call<PopularMoviesDto> call, @NotNull Throwable t) {

            }
        });
    }

    public String getMovieTrailerUrl(Movies movies) {
        Call<MovieVideosDto> call = tmdbServiceApi.getMoviesVideos(movies.getId(), Secret.SERVER_API_KEY);
        call.enqueue(new Callback<MovieVideosDto>() {
            @Override
            public void onResponse(@NotNull Call<MovieVideosDto> call, @NotNull Response<MovieVideosDto> response) {
                movieVideosDto = response.body();
            }

            @Override
            public void onFailure(@NotNull Call<MovieVideosDto> call, @NotNull Throwable t) {

            }
        });
        return tmdbServiceMapper.mapTrailerUrl(movieVideosDto.getResults().get(0));
    }
}
