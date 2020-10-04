package android.example.movies.details

import android.content.Intent
import android.example.movies.databinding.FragmentDetailsBinding
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import coil.api.load
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailsFragment : Fragment() {

    private val viewModel: DetailsViewModel by viewModels {
        DetailsViewModel.Factory(args.movie, requireActivity().application)
    }
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentDetailsBinding.inflate(inflater)
        initView(binding)
        initViewModel()
        return binding.root
    }

    private fun initView(binding: FragmentDetailsBinding) = with(binding) {
        val movie = args.movie
        imageView.load(movie.posterPath)
        textView.text = movie.title
        tvDateDetails.text = movie.releaseDate
        tvOverviewDetails.text = movie.overview
        bTrailer.setOnClickListener { viewModel.launchTrailer() }
    }

    private fun initViewModel() = with(viewModel) {
        viewLifecycleOwner.lifecycleScope.launch {
            trailerUrl.collect { openMovieTrailer(it) }
        }
    }

    private fun openMovieTrailer(url: String) {
        Intent(Intent.ACTION_VIEW, Uri.parse(url))
            .run { startActivity(this) }
    }

}