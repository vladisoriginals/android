package android.example.homework5fragments.api.dto;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PopularMoviesDto {
    @SerializedName("results")
    private List<MovieDto> results;

    public List<MovieDto> getResults() {
        return results;
    }

    public PopularMoviesDto(List<MovieDto> results) {
        this.results = results;
    }
}

