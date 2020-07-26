package android.example.homework5fragments;

import android.example.homework5fragments.data.Movies;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import coil.Coil;
import coil.DefaultRequestOptions;
import coil.request.LoadRequest;
import coil.request.LoadRequestBuilder;

public class AdapterMovies extends RecyclerView.Adapter<AdapterMovies.MovieHolder> {
    @Nullable
    private ClickListener clickListener;
    @Nullable
    private List<Movies> movies;

    public AdapterMovies(@NonNull ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void updateList(@NonNull List<Movies> moviesList) {
        this.movies = moviesList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    //происходим создание viewHolder
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movies, parent, false);
        return new MovieHolder(view);
    }

    @Override
    //созданным viewHolder задаёт новое значение
    public void onBindViewHolder(@NonNull MovieHolder holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    public int getItemCount() {
        if (movies == null) return 0;
        return movies.size();
    }

    public Movies getItem(int position) {
        return movies.get(position);
    }

    // MovieHolder обёртка для списка

    class MovieHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView poster;
        TextView title;
        TextView overview;

        public MovieHolder(@NonNull View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.poster);
            title = itemView.findViewById(R.id.tv_title);
            overview = itemView.findViewById(R.id.overview);
            itemView.setOnClickListener(this);
        }

        //bind Заполняет viewHolder новым значением
        void bind(Movies movies) {
            LoadRequestBuilder builder =
                    new LoadRequestBuilder(itemView.getContext(), new DefaultRequestOptions());

            LoadRequest request = builder
                    .data(movies.getPosterRes())
                    .target(poster)
                    .build();

            Coil.loader().load(request);

            title.setText(movies.getTitle());
            overview.setText(movies.getOverview());
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            clickListener.onClick(movies.get(position));

        }
    }
}
