package android.example.movies.search

import android.example.movies.databinding.FragmentSearchBinding
import android.example.movies.utils.AdapterMovies
import android.example.movies.utils.MoviesListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import io.reactivex.disposables.CompositeDisposable
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private lateinit var adapter: AdapterMovies
    private lateinit var binding: FragmentSearchBinding
    private val disposeBag = CompositeDisposable()
    private val  viewModelSearch: SearchViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSearchBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModelSearch



        initMoviesList()

        val dispose =  viewModelSearch.error.subscribe {
            Toast.makeText(requireContext(), "Network error", Toast.LENGTH_LONG).show()
        }
        disposeBag.add(dispose)

        return binding.root
    }

    private fun initMoviesList(){

        adapter = AdapterMovies(MoviesListener { movies ->
            val navController = findNavController()
            navController.navigate(SearchFragmentDirections.actionSearchFragmentToDetailsFragment(movies))
        })
        binding.listMovies.adapter = adapter
    }


    override fun onDestroyView() {
        disposeBag.clear()
        super.onDestroyView()
    }
}