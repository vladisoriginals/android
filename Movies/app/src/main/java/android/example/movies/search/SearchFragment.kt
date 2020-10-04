package android.example.movies.search

import android.example.movies.databinding.FragmentSearchBinding
import android.example.movies.domain.Movie
import android.example.movies.utils.AdapterMovies
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo

class SearchFragment : Fragment() {

    private val disposable = CompositeDisposable()
    private val adapter = AdapterMovies(::onItemClick)
    private val viewModel: SearchViewModel by viewModels {
        SearchViewModel.Factory(requireActivity().application)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentSearchBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.listMovies.adapter = adapter
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.error.subscribe { showNetworkError() }.addTo(disposable)
    }

    override fun onDestroyView() {
        disposable.clear()
        super.onDestroyView()
    }

    private fun onItemClick(movie: Movie) {
        findNavController().navigate(SearchFragmentDirections.toDetailsFragment(movie))
    }

    private fun showNetworkError() {
        Toast.makeText(activity, "NetworkError", Toast.LENGTH_LONG).show()
    }

}