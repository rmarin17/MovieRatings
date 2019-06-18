package rappi.test.com.movieratins.ui

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import rappi.test.com.movieratins.R
import rappi.test.com.movieratins.databinding.FragmentListMovieBinding
import rappi.test.com.movieratins.domain.Movie
import rappi.test.com.movieratins.ui.adapters.ListMoviesAdapter
import rappi.test.com.movieratins.viewmodels.MoviesViewModel

class TopMovieFragment : Fragment() {

    private val viewModel: MoviesViewModel by lazy {
        val activity = requireNotNull(this.activity) { }
        ViewModelProviders.of(this, MoviesViewModel.Factory(activity.application))
            .get(MoviesViewModel::class.java)
    }

    private var viewModelAdapter: ListMoviesAdapter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.topMoviesList.observe(viewLifecycleOwner, Observer<List<Movie>> { movies ->
            movies?.apply {
                viewModelAdapter?.movies = movies
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentListMovieBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_list_movie, container, false)
        // Set the lifecycleOwner so DataBinding can observe LiveData
        binding.setLifecycleOwner(viewLifecycleOwner)
        binding.viewModel = viewModel
        viewModelAdapter = ListMoviesAdapter(MovieClick { movieId ->
            viewModel.onMovieClicked(movieId)
        })
        viewModel.navigateToMovieData.observe(this, Observer { movie ->
            movie?.let {
                this.findNavController().navigate(
                    TopMovieFragmentDirections
                        .actionTopMovieFragmentToMovieDetailFragment(movie)
                )
                viewModel.onMovieDataNavigated()
            }
        })
        binding.root.findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewModelAdapter
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.about_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return NavigationUI.onNavDestinationSelected(
            item!!,
            view!!.findNavController()
        )
                || super.onOptionsItemSelected(item)
    }
}