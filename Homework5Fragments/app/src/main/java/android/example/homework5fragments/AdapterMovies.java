package android.example.homework5fragments;

import android.content.Context;
import android.example.homework5fragments.data.Movies;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterMovies  extends RecyclerView.Adapter<AdapterMovies.MovieHolder>{
    private ClickListener clickListener;
    private List<Movies> movies;

    public  AdapterMovies(ClickListener clickListener, List<Movies> movies){
        this.clickListener = clickListener;
        this.movies = movies;
    }
    @NonNull
    @Override
    //происходим создание viewHolder
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int itemMovies = R.layout.item_movies;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(itemMovies, parent, false);
        return new MovieHolder(view);
    }

    @Override
    //созданным viewHolder задаёт новое значение
    public void onBindViewHolder(@NonNull MovieHolder holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
    public Movies getItem(int position){
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
        void bind(Movies movies){
            poster.setImageResource(movies.getPosterRes());
            title.setText(movies.getTitle());
            overview.setText(movies.getOverview());
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            clickListener.onClick(position);

        }
    }
}
