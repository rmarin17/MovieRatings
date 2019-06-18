package rappi.test.com.movieratins.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.example.android.devbyteviewer.database.DatabaseMovie
import com.example.android.devbyteviewer.database.MovieDao
import com.example.android.devbyteviewer.database.getDatabase
import kotlinx.coroutines.Job
import rappi.test.com.movieratins.R
import rappi.test.com.movieratins.databinding.FragmentMovieDetailBinding

class MovieDetailFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentMovieDetailBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_movie_detail, container, false)

        val application = requireNotNull(this.activity).application
        val arguments = MovieDetailFragmentArgs.fromBundle(arguments!!)

        // Create an instance of the ViewModel Factory.
        val dataSource = getDatabase(application).movieDao
        val viewModelFactory = MovieDetailViewModelFactory(arguments.movieKey, dataSource)

        // Get a reference to the ViewModel associated with this fragment.
        val movieDetailViewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(MovieDetailViewModel::class.java)

        // To use the View Model with data binding, you have to explicitly
        // give the binding object a reference to it.
        binding.movieDetailViewModel = movieDetailViewModel

        binding.setLifecycleOwner(this)

        return binding.root
    }
}

class MovieDetailViewModel(
    private val movieKey: Long = 0L,
    dataSource: MovieDao) : ViewModel() {
    /**
     * Hold a reference to SleepDatabase via its SleepDatabaseDao.
     */
    val database = dataSource

    /** Coroutine setup variables */

    /**
     * viewModelJob allows us to cancel all coroutines started by this ViewModel.
     */
    private val viewModelJob = Job()

    private val movie = MediatorLiveData<DatabaseMovie>()

    fun getMovie() = movie

    init {
        movie.addSource(database.getMovieWithId(movieKey), movie::setValue)
    }
    /**
     * Variable that tells the fragment whether it should navigate to [SleepTrackerFragment].
     *
     * This is `private` because we don't want to expose the ability to set [MutableLiveData] to
     * the [Fragment]
     */
    private val _navigateToListMovie = MutableLiveData<Boolean?>()

    /**
     * When true immediately navigate back to the [SleepTrackerFragment]
     */
    val navigateToListMovie: LiveData<Boolean?>
        get() = _navigateToListMovie
    /**
     * Cancels all coroutines when the ViewModel is cleared, to cleanup any pending work.
     *
     * onCleared() gets called when the ViewModel is destroyed.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
    /**
     * Call this immediately after navigating to [SleepTrackerFragment]
     */
    fun doneNavigating() {
        _navigateToListMovie.value = null
    }
    fun onClose() {
        _navigateToListMovie.value = true
    }
}

class MovieDetailViewModelFactory(
    private val sleepNightKey: Long,
    private val dataSource: MovieDao) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieDetailViewModel::class.java)) {
            return MovieDetailViewModel(sleepNightKey, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}