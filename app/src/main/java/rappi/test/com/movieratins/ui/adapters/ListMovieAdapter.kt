package rappi.test.com.movieratins.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import rappi.test.com.movieratins.databinding.TemplateMovieBinding
import rappi.test.com.movieratins.domain.Movie
import rappi.test.com.movieratins.ui.MovieClick
import rappi.test.com.movieratins.ui.MovieViewHolder

/**
 * RecyclerView Adapter for setting up data binding on the items in the list.
 */
class ListMoviesAdapter(val clickListener: MovieClick) : RecyclerView.Adapter<MovieViewHolder>() {
    /**
     * The results that our Adapter will show
     */
    var movies: List<Movie> = emptyList()
        set(value) {
            field = value
            // Notify any registered observers that the data set has changed. This will cause every
            // element in our RecyclerView to be invalidated.
            notifyDataSetChanged()
        }
    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val withDataBinding: TemplateMovieBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            MovieViewHolder.LAYOUT,
            parent,
            false
        )
        return MovieViewHolder(withDataBinding)
    }
    override fun getItemCount() = movies.size
    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     */
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.movie = movies[position]
            it.clickListener = clickListener
        }
    }
}