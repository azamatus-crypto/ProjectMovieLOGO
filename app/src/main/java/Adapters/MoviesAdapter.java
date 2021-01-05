package Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.base.projectmovielogo.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import Data.Movie;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {
   public List<Movie>movies;
   public ONPOSTERTOUCH onpostertouch;
   public ONREACHEND onreachend;

    public void setOnpostertouch(ONPOSTERTOUCH onpostertouch) {
        this.onpostertouch = onpostertouch;
    }

    public void setOnreachend(ONREACHEND onreachend) {
        this.onreachend = onreachend;
    }

    public MoviesAdapter() {
      movies=new ArrayList<>();
    }

     public interface ONPOSTERTOUCH{
        void onpostercklick(int position);
     }
     public interface ONREACHEND{
        void onreaches();
     }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.iteam_main,parent,false);
        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, int position) {
        if(movies.size()>=20&&position>movies.size()-4&&onreachend!=null){
            onreachend.onreaches();
        }
        Movie movie=movies.get(position);
        Picasso.get().load(movie.getPosterPath()).into(holder.imageViewposters);

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class MoviesViewHolder extends RecyclerView.ViewHolder{
       private ImageView imageViewposters;
        public MoviesViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewposters=itemView.findViewById(R.id.imageviewposters);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onpostertouch!=null){
                        onpostertouch.onpostercklick(getAdapterPosition());
                    }
                }
            });
        }
    }
    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }
    public void addMovies(List<Movie>movies){
        this.movies.addAll(movies);
        notifyDataSetChanged();
    }
    public void clearMovie(){
        this.movies.clear();
        notifyDataSetChanged();
    }

    public List<Movie> getMovies() {
        return movies;
    }
}
