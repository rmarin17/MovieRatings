package rappi.test.com.movieratins.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import rappi.test.com.movieratins.R
import rappi.test.com.movieratins.databinding.FragmentListMovieBinding
import rappi.test.com.movieratins.databinding.TemplateMovieBinding
import rappi.test.com.movieratins.domain.Movie
import rappi.test.com.movieratins.viewmodels.MoviesViewModel


class ListMovieFragment : Fragment() {

    /**
     * One way to delay creation of the viewModel until an appropriate lifecycle method is to use
     * lazy. This requires that viewModel not be referenced before onActivityCreated, which we
     * do in this Fragment.
     */
    private val viewModel: MoviesViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProviders.of(this, MoviesViewModel.Factory(activity.application))
            .get(MoviesViewModel::class.java)
    }

    /**
     * RecyclerView Adapter for converting a list of Video to cards.
     */
    private var viewModelAdapter: ListMoviesAdapter? = null

    /**
     * Called when the fragment's activity has been created and this
     * fragment's view hierarchy instantiated.  It can be used to do final
     * initialization once these pieces are in place, such as retrieving
     * views or restoring state.
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.playlist.observe(viewLifecycleOwner, Observer<List<Movie>> { movies ->
            movies?.apply {
                viewModelAdapter?.movies = movies
            }
        })
    }

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * <p>If you return a View from here, you will later be called in
     * {@link #onDestroyView} when the view is being released.
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return Return the View for the fragment's UI.
     */
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
                    ListMovieFragmentDirections
                        .actionListMovieFragmentToMovieDetailFragment(movie)
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
        inflater?.inflate(R.menu.categories_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        return NavigationUI.onNavDestinationSelected(
            item!!,
            view!!.findNavController()
        )
                || super.onOptionsItemSelected(item)
    }
}

/**
 * Click listener for Videos. By giving the block a name it helps a reader understand what it does.
 *
 */
class MovieClick(val clickListener: (movieId: Long) -> Unit) {
    /**
     * Called when a movie is clicked
     *
     * @param movie the movie that was clicked
     */
    fun onClick(movie: Movie) = clickListener(movie.id)
}

/**
 * RecyclerView Adapter for setting up data binding on the items in the list.
 */
class ListMoviesAdapter(val clickListener: MovieClick) : RecyclerView.Adapter<DevByteViewHolder>() {

    /**
     * The results that our Adapter will show
     */
    var movies: List<Movie> = emptyList()
        set(value) {
            field = value
            // For an extra challenge, update this to use the paging library.

            // Notify any registered observers that the data set has changed. This will cause every
            // element in our RecyclerView to be invalidated.
            notifyDataSetChanged()
        }

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevByteViewHolder {
        val withDataBinding: TemplateMovieBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            DevByteViewHolder.LAYOUT,
            parent,
            false
        )
        return DevByteViewHolder(withDataBinding)
    }

    override fun getItemCount() = movies.size

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     */
    override fun onBindViewHolder(holder: DevByteViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.movie = movies[position]
            it.clickListener = clickListener
        }
    }

}

/**
 * ViewHolder for DevByte items. All work is done by data binding.
 */
class DevByteViewHolder(val viewDataBinding: TemplateMovieBinding) :
    RecyclerView.ViewHolder(viewDataBinding.root) {
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.template_movie
    }
}
