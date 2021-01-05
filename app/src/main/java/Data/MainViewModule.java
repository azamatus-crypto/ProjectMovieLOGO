package Data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainViewModule extends AndroidViewModel {
   public static MovieDatabase movieDatabase;
   public LiveData<List<Movie>>movies;
   public LiveData<List<FavoriteMovie>>favoritmovies;
    public MainViewModule(@NonNull Application application) {
        super(application);
        movieDatabase=MovieDatabase.getInstance(getApplication());
        movies=movieDatabase.moviesDAO().getAllMovie();
        favoritmovies=movieDatabase.moviesDAO().getAllFavoriteMovie();
    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }

    public LiveData<List<FavoriteMovie>> getFavoritmovies() {
        return favoritmovies;
    }

    public Movie getMovieID(int id){
        try {
            return new GetMovieID().execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
    public FavoriteMovie getFavorID(int id){
        try {
            return new GetFavoriteMovieid().execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void insertMovie(Movie movie){
        new InsertMovie().execute(movie);
    }
    public void insertFavoriteMovie(FavoriteMovie favoriteMovie){
        new InsertFavoriteMovies().execute(favoriteMovie);
    }
    public void deleteMovie(Movie movie){
        new DeleteMovie().execute(movie);
    }
    public void deleteFavorMovie(FavoriteMovie favoriteMovie){
        new DeleteFavoriteMovie().execute(favoriteMovie);
    }
    public void deleteAllMovie(){
        new DEleteAllMovie().execute();
    }
   public static class GetMovieID extends AsyncTask<Integer,Void, Movie>{

       @Override
       protected Movie doInBackground(Integer... integers) {
           if(integers!=null&&integers.length>0){
               return movieDatabase.moviesDAO().getMovieID(integers[0]);
           }
           return null;
       }
   }
   public static class GetFavoriteMovieid extends AsyncTask<Integer,Void,FavoriteMovie>{

       @Override
       protected FavoriteMovie doInBackground(Integer... integers) {
           if(integers!=null&&integers.length>0){
               return movieDatabase.moviesDAO().getFavoriteID(integers[0]);
           }
           return null;
       }
   }
   public static class InsertMovie extends AsyncTask<Movie,Void,Void>{

       @Override
       protected Void doInBackground(Movie... movies) {
           if(movies!=null&&movies.length>0){
               movieDatabase.moviesDAO().insertMovie(movies[0]);
           }
           return null;
       }
   }
   public static class InsertFavoriteMovies extends AsyncTask<FavoriteMovie,Void,Void>{

       @Override
       protected Void doInBackground(FavoriteMovie... favoriteMovies) {
           if(favoriteMovies!=null&&favoriteMovies.length>0){
               movieDatabase.moviesDAO().insertFavoriteMovie(favoriteMovies[0]);
           }
           return null;
       }
   }
   public static class DeleteMovie extends AsyncTask<Movie,Void,Void>{

       @Override
       protected Void doInBackground(Movie... movies) {
           if(movies!=null&&movies.length>0){
               movieDatabase.moviesDAO().deleteMovie(movies[0]);
           }
           return null;
       }
   }
   public static class DeleteFavoriteMovie extends AsyncTask<FavoriteMovie,Void,Void>{

       @Override
       protected Void doInBackground(FavoriteMovie... favoriteMovies) {
           if(favoriteMovies!=null&&favoriteMovies.length>0){
               movieDatabase.moviesDAO().deleteFavoriteMovie(favoriteMovies[0]);
           }
           return null;
       }
   }
   public static class DEleteAllMovie extends AsyncTask<Void,Void,Void>{

       @Override
       protected Void doInBackground(Void... voids) {
           movieDatabase.moviesDAO().deleteAllMovie();
           return null;
       }
   }
   public static class DeleteAllFavoriteMovies extends AsyncTask<Void,Void,Void>{

       @Override
       protected Void doInBackground(Void... voids) {
           movieDatabase.moviesDAO().deleteAllFavoriteMovie();
           return null;
       }
   }
}
