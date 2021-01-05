package Data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Movie.class,FavoriteMovie.class},version = 6,exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {
    public static final String DB_NAME="movies.db";
    public static final Object LOCK=new Object();
    public static MovieDatabase movieDatabase;

    public static MovieDatabase getInstance(Context context){
        synchronized (LOCK){
            if(movieDatabase==null){
                movieDatabase= Room.databaseBuilder(context,MovieDatabase.class,DB_NAME).fallbackToDestructiveMigration().build();
            }
        }
        return movieDatabase;
    }
    public abstract MoviesDAO moviesDAO();
}
