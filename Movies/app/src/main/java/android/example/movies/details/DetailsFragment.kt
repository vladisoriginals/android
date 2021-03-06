package android.example.movies.details

import android.content.Intent
import android.example.movies.R
import android.example.movies.databinding.FragmentDetailsBinding
import android.example.movies.domain.Movie
import android.example.movies.search.SearchViewModel
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import coil.api.load

class DetailsFragment : Fragment() {

    private lateinit var viewModel: DetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentDetailsBinding.inflate(inflater)

        val args = DetailsFragmentArgs.fromBundle(requireArguments())
        val movie = args.movie

        viewModel = ViewModelProviders.of(
            this,
            DetailsViewModel.Factory(movie, requireActivity().application)
        )
            .get(DetailsViewModel::class.java)

        binding.apply {
            imageView.load(movie.posterPath)
            textView.text = movie.title
            tvDateDetails.text = movie.releaseDate
            tvOverviewDetails.text = movie.overview
            bTrailer.setOnClickListener {
                viewModel.trailer.observe(viewLifecycleOwner) { trailer ->
                    openMovieTrailer(trailer.url)
                }
            }

        }

        return binding.root
    }


    private fun openMovieTrailer(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}