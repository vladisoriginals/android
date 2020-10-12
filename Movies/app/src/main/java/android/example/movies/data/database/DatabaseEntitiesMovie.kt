package android.example.movies.data.database

import android.example.movies.domain.Movie
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DatabaseMovies constructor(
    @PrimaryKey
    val id: Int,
    val title: String,
    val releaseDate: String,
    val overview: String,
    val posterPath: String,
    val backdropPatch: String
)

fun List<DatabaseMovies>.asDomainModel(): List<Movie>{
    return map {
        Movie(
            id = it.id,
            title = it.title,
            releaseDate = it.releaseDate,
            overview = it.overview,
            posterPath = it.posterPath,
            backdropPatch = it.posterPath
        )
    }
}

