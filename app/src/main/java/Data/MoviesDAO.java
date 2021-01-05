package Data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MoviesDAO {
    @Query("SELECT * FROM moviesbluya")
    LiveData<List<Movie>>getAllMovie();
    @Query("SELECT * FROM favoritemovies")
    LiveData<List<FavoriteMovie>>getAllFavoriteMovie();
    @Query("SELECT * FROM moviesbluya WHERE id == :id")
    Movie getMovieID(int id);
    @Query("SELECT * FROM favoritemovies WHERE id == :id")
    FavoriteMovie getFavoriteID(int id);
    @Insert
    void insertMovie(Movie movie);
    @Insert
    void insertFavoriteMovie(FavoriteMovie favoriteMovie);
    @Delete
    void deleteMovie(Movie movie);
    @Delete
    void deleteFavoriteMovie(FavoriteMovie favoriteMovie);
    @Query("DELETE FROM moviesbluya")
    void deleteAllMovie();
    @Query("DELETE FROM favoritemovies")
    void deleteAllFavoriteMovie();
}
