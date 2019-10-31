package android.example.homework5fragments;


import android.content.Intent;
import android.example.homework5fragments.data.Movies;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDetails extends Fragment {

    final static String MOVIE_KEY = "Movie_key";

    public FragmentDetails() {
        // Required empty public constructor
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
        final Movies movies = (Movies) getArguments().getSerializable(MOVIE_KEY);
        ImageView poster = view.findViewById(R.id.iv_details_poster);
        ImageView image = view.findViewById(R.id.im_details_image);
        TextView title = view.findViewById(R.id.tv_title_details);
        TextView releasedData = view.findViewById(R.id.tv_details_released_date);
        TextView overviewText = view.findViewById(R.id.tv_overview_text);
        poster.setImageResource(movies.getBackdropRes());
        image.setImageResource(movies.getPosterRes());
        title.setText(movies.getTitle());
        releasedData.setText(movies.getReleasedDate());
        overviewText.setText(movies.getOverview());
        Log.d("Movie", movies.toString());

        Button movieButton = view.findViewById(R.id.b_movie_trailer);
        movieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMovieTrailer(movies.getTrailerUrl());
            }
        });
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

