package android.example.homework5fragments.api.dto;


import java.util.List;

public class MovieVideosDto {
    private final List<MovieVideoDto> results;

    public List<MovieVideoDto> getResults() {
        return results;
    }

    public MovieVideosDto(List<MovieVideoDto> results) {
        this.results = results;
    }
}
