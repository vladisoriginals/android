package android.example.homework5fragments.data;

import androidx.annotation.DrawableRes;

import java.io.Serializable;

public class Movies implements Serializable {

    private final String title;
    @DrawableRes
    private final int posterRes;
    @DrawableRes
    private final int backdropRes;
    private final String overview;
    private final String releasedDate;
    private final String trailerUrl;

    public Movies(String title, int posterRes, int backdropRes, String overview,
                  String releasedDate, String trailerUrl) {
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
}
