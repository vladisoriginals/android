package android.example.homework5fragments.data;


import java.io.Serializable;
import java.util.Objects;

public class Movies implements Serializable {



    private final  int id;
    private final String title;
    private final String posterRes;
    private final String backdropRes;
    private final String overview;
    private final String releasedDate;

    public Movies(int id, String title, String posterRes, String backdropRes, String overview,
                  String releasedDate) {
        this.id = id;
        this.title = title;
        this.posterRes = posterRes;
        this.backdropRes = backdropRes;
        this.overview = overview;
        this.releasedDate = releasedDate;
    }

    public int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }

    public String getPosterRes() {
        return posterRes;
    }

    public String getBackdropRes() {
        return backdropRes;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleasedDate() {
        return releasedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movies movies = (Movies) o;
        return id == movies.id &&
                title.equals(movies.title) &&
                posterRes.equals(movies.posterRes) &&
                backdropRes.equals(movies.backdropRes) &&
                overview.equals(movies.overview) &&
                releasedDate.equals(movies.releasedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, posterRes, backdropRes, overview, releasedDate);
    }
}
