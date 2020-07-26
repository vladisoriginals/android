package android.example.homework5fragments.api.dto;


import com.google.gson.annotations.SerializedName;

public class MovieDto {
    @SerializedName("id")
    private final int id;
    @SerializedName( "title")
    private final String title;
    @SerializedName( "overview")
    private final String overview;
    @SerializedName( "release_date")
    private final String releaseDate;
    @SerializedName("poster_path")
    private final String posterPath;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    @SerializedName("backdrop_path")
    private final String backdropPath;

    public MovieDto(int id, String title, String overview, String releaseDate, String posterPath, String backdropPath) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
    }
}
