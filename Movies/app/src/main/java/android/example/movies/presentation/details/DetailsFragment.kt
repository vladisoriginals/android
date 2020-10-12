package android.example.movies.presentation.details

import android.content.Intent
import android.example.movies.databinding.FragmentDetailsBinding
import android.example.movies.domain.Video
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.api.load
import io.reactivex.disposables.CompositeDisposable
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailsFragment : Fragment() {

    private val disposeBag = CompositeDisposable()
    private lateinit var trailer: Video
    private val args: DetailsFragmentArgs by navArgs()
    private val viewModelDetails: DetailsViewModel by viewModel { parametersOf(args.movie) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentDetailsBinding.inflate(inflater)
        val movie = args.movie
        val disposeTrailer = viewModelDetails.trailer
            .subscribe {
                trailer = it
            }

        binding.apply {
            imageView.load(movie.posterPath)
            textView.text = movie.title
            tvDateDetails.text = movie.releaseDate
            tvOverviewDetails.text = movie.overview
            bTrailer.setOnClickListener {
                openMovieTrailer(trailer.url)
            }
        }

        val dispose = viewModelDetails.error.subscribe {
            Toast.makeText(requireContext(), "Network error", Toast.LENGTH_LONG).show()
        }
        disposeBag.addAll(dispose, disposeTrailer)

        return binding.root
    }

    private fun openMovieTrailer(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    override fun onDestroyView() {
        disposeBag.clear()
        super.onDestroyView()
    }
}