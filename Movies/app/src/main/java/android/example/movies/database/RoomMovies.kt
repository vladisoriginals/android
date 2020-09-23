package android.example.movies.database

import android.content.Context
import android.example.movies.domain.Video
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MoviesDao {

    @Query("SELECT * FROM databasemovies")
    fun getMovies(): LiveData<List<DatabaseMovies>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<DatabaseMovies>)

}

@Dao
interface TrailerDao {

    @Query("SELECT * FROM databasetrailer WHERE ID = :movieId")
    fun getTrailerByMovieId(movieId: Int): LiveData<DatabaseTrailer>

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

    companion object {

        @Volatile
        private var INSTANCE: MoviesDatabase? = null

        fun getInstance(context: Context): MoviesDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MoviesDatabase::class.java,
                        "movies.db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
