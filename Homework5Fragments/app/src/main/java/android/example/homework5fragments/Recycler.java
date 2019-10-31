package android.example.homework5fragments;


import android.example.homework5fragments.data.DataMovies;
import android.example.homework5fragments.data.Movies;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import static android.example.homework5fragments.FragmentDetails.MOVIE_KEY;


/**
 * A simple {@link Fragment} subclass.
 */
public class Recycler extends Fragment {
    private RecyclerView recyclerView;
    private List<Movies> movies;

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
        movies = DataMovies.getMovieList();
        AdapterMovies adapterMovies = new AdapterMovies(new ClickListener() {
            @Override
            public void onClick(int position) {
                Movies movie = movies.get(position);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = null;
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
            }
        }, movies);
        recyclerView.setAdapter(adapterMovies);
    }
}
