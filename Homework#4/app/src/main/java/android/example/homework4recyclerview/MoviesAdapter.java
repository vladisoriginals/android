package android.example.homework4recyclerview;

import android.content.Context;
import android.example.homework4recyclerview.data.Movie;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    ClickListener clickListener;
    List<Movie> movies ;
    public  MoviesAdapter(Context context,ClickListener clickListener, List<Movie> movies){
        this.clickListener = clickListener;
        this.movies = movies;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int itemMovies = R.layout.item_movies;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(itemMovies,parent,false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
    private Movie getItem(int position){
         return movies.get(position);
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView poster;
        TextView title;
        TextView overview;


        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.poster);
            title = itemView.findViewById(R.id.tv_title);
            overview = itemView.findViewById(R.id.overview);
            itemView.setOnClickListener(this);
        }
        void bind(Movie movie){
            poster.setImageResource(movie.getPosterRes());
            title.setText(movie.getTitle());
            overview.setText(movie.getOverview());
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            clickListener.onClick(position);
        }
    }
}
