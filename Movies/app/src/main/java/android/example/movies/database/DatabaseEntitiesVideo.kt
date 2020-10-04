package android.example.movies.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DatabaseTrailer(
    @PrimaryKey
    val id: Int,
    val url: String
)