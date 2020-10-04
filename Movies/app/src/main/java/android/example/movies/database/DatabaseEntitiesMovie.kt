package android.example.movies.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DatabaseMovies(
    @PrimaryKey
    val id: Int,
    val title: String,
    val releaseDate: String,
    val overview: String,
    val posterPath: String,
    val backdropPatch: String
)