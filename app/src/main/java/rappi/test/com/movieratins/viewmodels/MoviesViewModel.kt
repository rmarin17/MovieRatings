package rappi.test.com.movieratins.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.lifecycle.*
import com.example.android.devbyteviewer.database.getDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import rappi.test.com.movieratins.repository.MoviesRepository

class MoviesViewModel(application: Application) : AndroidViewModel(application) {
    /**
     * This is the job for all coroutines started by this ViewModel.
     *
     * Cancelling this job will cancel all coroutines started by this ViewModel.
     */
    private val viewModelJob = SupervisorJob()

    private val _navigateToMovieData = MutableLiveData<Long>()
    val navigateToMovieData
        get() = _navigateToMovieData

    fun onMovieClicked(id: Long) {
        _navigateToMovieData.value = id
    }

    fun onMovieDataNavigated() {
        _navigateToMovieData.value = null
    }

    /**
     * This is the main scope for all coroutines launched by MainViewModel.
     *
     * Since we pass viewModelJob, you can cancel all coroutines launched by uiScope by calling
     * viewModelJob.cancel()
     */
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val database = getDatabase(application)
    private val moviesRepository = MoviesRepository(database)

    /**
     * init{} is called immediately when this ViewModel is created
     */
    init {
        val cm = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnected == true
        if (isConnected) {
            viewModelScope.launch {
                moviesRepository.refreshMovies()
            }
        }
    }

    val movieList = moviesRepository.movies
    val topMoviesList = moviesRepository.topMovies
    val upcomingMoviesList = moviesRepository.upcomingMovies
    val popularityMoviesList = moviesRepository.popularityMovies

    /**
     * Cancel all coroutines when the ViewModel is cleared
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    /**
     * Factory for constructing MovieViewModel with parameter
     */
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MoviesViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MoviesViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}