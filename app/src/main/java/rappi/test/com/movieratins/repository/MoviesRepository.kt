package rappi.test.com.movieratins.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.android.devbyteviewer.database.MoviesDatabase
import com.example.android.devbyteviewer.database.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import rappi.test.com.movieratins.domain.Movie
import rappi.test.com.movieratins.network.Network
import rappi.test.com.movieratins.network.asDatabaseModel

class MoviesRepository(private val database: MoviesDatabase) {
    /**
     * A playlist of movie that can be shown on the screen.
     */
    val movies: LiveData<List<Movie>> =
        Transformations.map(database.movieDao.getMovies()) {
            it.asDomainModel()
        }

    /**
     * Refresh the movie stored in the offline cache.
     *
     * This function uses the IO dispatcher to ensure the database insert database operation
     * happens on the IO dispatcher. By switching to the IO dispatcher using `withContext` this
     * function is now safe to call from any thread including the Main thread.
     *
     * To actually load the movie for use, observe [movies]
     */
    suspend fun refreshVideos() {
        withContext(Dispatchers.IO) {
            val playlist = Network.movies.getMovies("9e3b0239c1013d2aa312864e2c8f8d1f", "en-US").await()
            database.movieDao.insertAll(*playlist.asDatabaseModel())
        }
    }
}