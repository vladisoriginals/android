package android.example.movies.presentation.search

import android.example.movies.databinding.FragmentSearchBinding
import android.example.movies.presentation.utils.AdapterMovies
import android.example.movies.presentation.utils.MoviesListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private lateinit var adapter: AdapterMovies
    private lateinit var binding: FragmentSearchBinding
    private val disposeBag = CompositeDisposable()
    private val viewModelSearch: SearchViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSearchBinding.inflate(inflater)

        initMoviesList()

        viewModelSearch.movies.subscribe {
            adapter.submitList(it)
        }.addTo(disposeBag)

        viewModelSearch.error.subscribe {
            Toast.makeText(requireContext(), "Network error", Toast.LENGTH_LONG).show()
        }.addTo(disposeBag)

        viewModelSearch.showLoading.subscribe {
            if (it) binding.pbLoading.visibility = View.VISIBLE
            else binding.pbLoading.visibility = View.GONE
        }.addTo(disposeBag)

        return binding.root
    }

    private fun initMoviesList() {

        adapter = AdapterMovies(MoviesListener {
            val navController = findNavController()
            navController.navigate(
                SearchFragmentDirections.actionSearchFragmentToDetailsFragment(it)
            )
        })
        binding.listMovies.adapter = adapter
    }

    override fun onDestroyView() {
        disposeBag.clear()
        super.onDestroyView()
    }
}