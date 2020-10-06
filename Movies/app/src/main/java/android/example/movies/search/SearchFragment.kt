package android.example.movies.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.example.movies.databinding.FragmentSearchBinding
import android.example.movies.utils.AdapterMovies
import android.example.movies.utils.MoviesListener
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import io.reactivex.disposables.CompositeDisposable

class SearchFragment : Fragment() {

    private lateinit var adapter: AdapterMovies
    private lateinit var binding: FragmentSearchBinding
    private val disposeBag = CompositeDisposable()

    private val viewModel: SearchViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProviders.of(this, SearchViewModel.Factory(activity.application))
            .get(SearchViewModel::class.java)
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSearchBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        initMoviesList()

        val dispose =  viewModel.error.subscribe {
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