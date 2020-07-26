package android.example.homework5fragments.api;

import android.example.homework5fragments.api.dto.MovieVideosDto;
import android.example.homework5fragments.api.dto.PopularMoviesDto;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TmdbServiceApi {


    @GET("movie/popular")
    Call<PopularMoviesDto> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/{movie_id}/videos")
    Call <MovieVideosDto> getMoviesVideos(@Path ("movie_id") int movieId,
                                          @Query("api_key") String apiKey);
}
