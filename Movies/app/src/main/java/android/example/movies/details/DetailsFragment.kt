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
import androidx.navigation.fragment.navArgs
import coil.api.load
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo

class DetailsFragment : Fragment() {

    private val disposable = CompositeDisposable()
    private val viewModel: DetailsViewModel by viewModels {
        DetailsViewModel.Factory(args.movie.id, requireActivity().application)
    }
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentDetailsBinding.inflate(inflater)
        initView(binding)
        initViewModel()
        return binding.root
    }

    override fun onDestroyView() {
        disposable.clear()
        super.onDestroyView()
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
        trailerUrl.subscribe(::openMovieTrailer).addTo(disposable)
    }

    private fun openMovieTrailer(url: String) {
        Intent(Intent.ACTION_VIEW, Uri.parse(url))
            .run { startActivity(this) }
    }

}