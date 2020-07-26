package android.example.homework5fragments.dependency;

import android.example.homework5fragments.api.TmdbServiceApi;
import android.example.homework5fragments.repository.MoviesRepository;
import android.example.homework5fragments.repository.TmdbServiceMapper;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Dependencies {

    private Dependencies() {

    }

    static private Dependencies INSTANCE = new Dependencies();

    public static Dependencies getINSTANCE() {
        return INSTANCE;
    }


    public MoviesRepository getMoviesRepository() {
        return new MoviesRepository(createTmdbServiceApi(), createTmdbServiceMapper());
    }


    private TmdbServiceMapper createTmdbServiceMapper() {
        return new TmdbServiceMapper();
    }

    private TmdbServiceApi createTmdbServiceApi() {
        return createRetrofit().create(TmdbServiceApi.class);
    }

    private Retrofit createRetrofit() {

        return new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}
