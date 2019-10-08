package android.example.homework4recyclerview.data;

import androidx.annotation.DrawableRes;

import java.io.Serializable;
import java.util.Objects;

public class Movie implements Serializable {

    private final String title;
    @DrawableRes
    private final int posterRes;
    @DrawableRes
    private final int backdropRes;
    private final String overview;
    private final String releasedDate;
    private final String trailerUrl;

    public Movie(String title, int posterRes, int backdropRes,
                 String overview, String releasedDate, String trailerUrl) {
        this.title = title;
        this.posterRes = posterRes;
        this.backdropRes = backdropRes;
        this.overview = overview;
        this.releasedDate = releasedDate;
        this.trailerUrl = trailerUrl;
    }

    public String getTitle() {
        return title;
    }

    public int getPosterRes() {
        return posterRes;
    }

    public int getBackdropRes() {
        return backdropRes;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleasedDate() {
        return releasedDate;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }
    public int hashCode(){
        return Objects.hash(title,posterRes,backdropRes,overview,releasedDate,trailerUrl);
    }
}
