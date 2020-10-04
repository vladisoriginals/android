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
    private val clickListener: (Movie) -> Unit
) : ListAdapter<Movie, AdapterMovies.MovieHolder>(MoviesDiffCallback()), BindableAdapter<Movie> {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    override fun setData(data: List<Movie>) = submitList(data)

    class MovieHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie, clickListener: (Movie) -> Unit) {
            with(binding) {
                ivPoster.load(movie.backdropPatch)
                tvNameOfFilm.text = movie.title
                tvDate.text = movie.releaseDate
                tvOverview.text = movie.overview
                binding.root.setOnClickListener { clickListener.invoke(movie) }
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