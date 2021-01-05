package Data;

import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(tableName = "favoritemovies")
public class FavoriteMovie extends Movie {
    public FavoriteMovie(int uniqueID, int id, int voteCount, String title, String originalTitle, String overview, String posterPath, String bigPosterPath, String backdropPath, double voteAverage, String releaseDate) {
        super(uniqueID, id, voteCount, title, originalTitle, overview, posterPath, bigPosterPath, backdropPath, voteAverage, releaseDate);
    }
    @Ignore
    public FavoriteMovie(Movie movie) {
        super(movie.getUniqueID(),movie.getId(),movie.getVoteCount(),movie.getTitle(),movie.getOriginalTitle(),movie.getOverview(),movie.getPosterPath(),movie.getBigPosterPath(),movie.getBackdropPath(),movie.getVoteAverage(),movie.getReleaseDate());
    }
}
