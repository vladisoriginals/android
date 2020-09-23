package android.example.movies.utils

import android.example.movies.databinding.ListItemBinding
import android.example.movies.domain.Movie
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load

interface BindableAdapter<T> {
    fun setData(data: List<T>)
}

class AdapterMovies(
    private val clickListener: MoviesListener
) : ListAdapter<Movie, AdapterMovies.MovieHolder>(MoviesDiffCallback()), BindableAdapter<Movie> {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) =
        holder.bind(getItem(position), clickListener)

    class MovieHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movies: Movie, clickListener: MoviesListener) {
            binding.apply {
                this.clickListener = clickListener
                this.movies = movies
                ivPoster.load(movies.backdropPatch)
                executePendingBindings()
            }
        }
    }

    override fun setData(data: List<Movie>) = submitList(data)
}

class MoviesDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
        oldItem == newItem
}

class MoviesListener(val clickListener: (movie: Movie) -> Unit) {
    fun onClick(movie: Movie) = clickListener(movie)
}