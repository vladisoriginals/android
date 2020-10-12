package android.example.movies.data.database

import android.example.movies.domain.Video
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DatabaseTrailer constructor(
    @PrimaryKey
    val id: Int,
    val url: String )

fun DatabaseTrailer.asDomainModel(): Video = Video(id,url)