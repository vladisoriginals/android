package android.example.homework5fragments.list;


import android.example.homework5fragments.AdapterMovies;
import android.example.homework5fragments.R;
import android.example.homework5fragments.data.Movies;
import android.example.homework5fragments.dependency.Dependencies;
import android.example.homework5fragments.details.FragmentDetails;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class Recycler extends Fragment {
    private AdapterMovies adapterMovies;
    private RecyclerView recyclerView;
    private static String MOVIE_KEY = "movie_key";
    private RecyclerViewModel viewModel;
    private ProgressBar progressBar;

    public Recycler() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycler, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.moviesList);
        progressBar = view.findViewById(R.id.movies_ProgressBar);
        initViewModel();

        initMovieLIst();
        initProgressBar();
        initErrorHandler();

        loadMovies();
    }

    private void initViewModel(){
        viewModel = ViewModelProviders.of(this,
               new RecyclerViewModelFactory(Dependencies.getINSTANCE().getMoviesRepository(),requireContext()))
                .get(RecyclerViewModel.class);
    }

    private void initMovieLIst(){
         adapterMovies = new AdapterMovies((Movies movie)-> {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction;
            if (fragmentManager == null) {
                return;
            }
            fragmentTransaction = fragmentManager.beginTransaction();
            FragmentDetails fragmentDetails = new FragmentDetails();
            Bundle bundle = new Bundle();
            bundle.putSerializable(MOVIE_KEY, movie);
            fragmentDetails.setArguments(bundle);
            fragmentTransaction.replace(R.id.container, fragmentDetails);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        });
        recyclerView.setAdapter(adapterMovies);
        DividerItemDecoration decoration = new DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL);
        decoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(requireContext(), R.color.grey)));
        recyclerView.addItemDecoration(decoration);
    }

    private void initProgressBar(){
        viewModel.loading.observe(this, loading-> {
            if (loading != null) {
                if (loading) {
                    progressBar.setVisibility(View.VISIBLE);
                }else{
                    progressBar.setVisibility(View.GONE);
                }
            }

        });
    }

    private void initErrorHandler(){
        viewModel.error.observe(this, errorMessage->
                // todo errorMessage should be checked for null
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        );
    }

    private void loadMovies(){
        viewModel.movies.observe(this, movies -> {
            if (movies != null) {
                adapterMovies.updateList(movies);
            }
        });
    }

}
