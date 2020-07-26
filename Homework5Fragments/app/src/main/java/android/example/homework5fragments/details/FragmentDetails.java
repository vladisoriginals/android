package android.example.homework5fragments.details;


import android.content.Intent;
import android.example.homework5fragments.R;
import android.example.homework5fragments.data.Movies;
import android.example.homework5fragments.dependency.Dependencies;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import coil.Coil;
import coil.DefaultRequestOptions;
import coil.request.LoadRequest;
import coil.request.LoadRequestBuilder;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDetails extends Fragment {

    private  FragmentDetailsViewModel viewModel;
    private Movies movies;
    private final static String MOVIE_KEY = "Movie_key";

    public FragmentDetails() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movies = (Movies) getArguments().getSerializable(MOVIE_KEY);
        }
        viewModel = ViewModelProviders.of(this,
                new DetailsViewModelFactory(Dependencies.getINSTANCE().getMoviesRepository(), getContext() ,movies ))
                .get(FragmentDetailsViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_deatails, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() == null) {
            return;
        }
        viewModel.openTrailerUrl.observe(this, this::openMovieTrailer);
        viewModel.error.observe(this, errorMessage->
                Toast.makeText(getContext(),errorMessage,Toast.LENGTH_SHORT).show());
        ImageView poster = view.findViewById(R.id.iv_details_poster);
        ImageView image = view.findViewById(R.id.im_details_image);
        TextView title = view.findViewById(R.id.tv_title_details);
        TextView releasedData = view.findViewById(R.id.tv_details_released_date);
        TextView overviewText = view.findViewById(R.id.tv_overview_text);
        LoadRequestBuilder builder =
                new LoadRequestBuilder(view.getContext(), new DefaultRequestOptions());

        LoadRequest request = builder
                .data(movies.getBackdropRes())
                .target(poster)
                .build();

        Coil.loader().load(request);

        LoadRequest request1 = builder
                .data(movies.getPosterRes())
                .target(image)
                .build();

        title.setText(movies.getTitle());
        releasedData.setText(movies.getReleasedDate());
        overviewText.setText(movies.getOverview());
        Log.d("Movie", movies.toString());

        Button movieButton = view.findViewById(R.id.b_movie_trailer);
        movieButton.setOnClickListener( view1 ->
                viewModel.onTrailerButtonClicked());
    }
        private void openMovieTrailer(String url){
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

            if(intent.resolveActivity(requireContext().getPackageManager()) !=null){
                startActivity(intent);
            }else {
                Log.d("Intent", "Can't process intent!");

            }

    }

}

