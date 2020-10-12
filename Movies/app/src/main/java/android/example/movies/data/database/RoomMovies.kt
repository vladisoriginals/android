package android.example.movies.data.database

import android.content.Context
import androidx.room.*
import io.reactivex.Observable

@Dao
interface MoviesDao {

    @Query("SELECT * FROM databasemovies")
    fun getMovies(): Observable<List<DatabaseMovies>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<DatabaseMovies>)

}

@Dao
interface TrailerDao {

    @Query("SELECT * FROM databasetrailer WHERE ID = :movieId")
    fun getTrailerByMovieId(movieId: Int): Observable<DatabaseTrailer>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertURL(trailer: DatabaseTrailer)

}

@Database(
    entities = [DatabaseMovies::class, DatabaseTrailer::class],
    version = 2,
    exportSchema = false
)
abstract class MoviesDatabase : RoomDatabase() {
    abstract val moviesDao: MoviesDao
    abstract val trailerDao: TrailerDao

}

fun getDatabaseInstance(context: Context): MoviesDatabase {

    return Room.databaseBuilder(
        context.applicationContext,
        MoviesDatabase::class.java,
        "movies.db"
    )
        .fallbackToDestructiveMigration()
        .build()
}
