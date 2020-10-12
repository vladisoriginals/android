package android.example.movies.presentation.utils

import android.example.movies.databinding.ListItemBinding
import android.example.movies.domain.Movie
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load


class AdapterMovies(
    private val clickListener: MoviesListener
) : ListAdapter<Movie, AdapterMovies.MovieHolder>(MoviesDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) =
        holder.bind(getItem(position), clickListener)

    class MovieHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movies: Movie, clickListener: MoviesListener) {
            binding.apply {
                ivPoster.load(movies.backdropPatch)
                tvNameOfFilm.text = movies.title
                tvDate.text = movies.releaseDate
                tvOverview.text = movies.overview
                root.setOnClickListener{
                    clickListener.onClick(movies)
                }
                executePendingBindings()
            }
        }
    }

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