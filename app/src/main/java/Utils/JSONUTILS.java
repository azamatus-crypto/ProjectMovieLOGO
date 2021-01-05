package Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Data.Movie;
import Data.Rewiews;
import Data.Trailers;

public class JSONUTILS {
    public static final String YOUTUBE_URL="https://www.youtube.com/watch?v=";
    public static final String KEY_KEY="key";
    public static final String KEY_NAME="name";


    public static final String KEY_AUTHOR="author";
    public static final String KEY_CONTENT="content";

    public static final String BASE_POSTER_URL = "https://image.tmdb.org/t/p/";
    public static final String KEY_RESULTS="results";
    public static final String KEY_ID="id";
    public static final String KEY_VOTE_COUNT="vote_count";
    public static final String KEY_TITLE="title";
    public static final String KEY_ORIGINAL_TITLE="original_title";
    public static final String KEY_OVERVIEW="overview";
    public static final String KEY_POSTER_PATH="poster_path";
    public static final String KEY_BIG_POSTER_PATH="w780";
    public static final String SMALL_POSTER="w185";
    public static final String KEY_BACK_DROP_PATH="backdrop_path";
    public static final String KEY_VOTE_AVERAGE="vote_average";
    public static final String KEY_REALISE_DATA="release_date";




    public static ArrayList<Movie>getJSONARRAY(JSONObject jsonObject){
        ArrayList<Movie>movies=new ArrayList<>();
        if(jsonObject==null){
            return movies;
        }
        try {
            JSONArray jsonArray=jsonObject.getJSONArray(KEY_RESULTS);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject objectmovies=jsonArray.getJSONObject(i);
                int id=objectmovies.getInt(KEY_ID);
                int vote_count=objectmovies.getInt(KEY_VOTE_COUNT);
                String title=objectmovies.getString(KEY_TITLE);
                String original_title=objectmovies.getString(KEY_ORIGINAL_TITLE);
                String overview=objectmovies.getString(KEY_OVERVIEW);
                String posterpath=BASE_POSTER_URL+SMALL_POSTER+objectmovies.getString(KEY_POSTER_PATH);
                String bigposterpath=BASE_POSTER_URL+KEY_BIG_POSTER_PATH+objectmovies.getString(KEY_POSTER_PATH);
                String backdroppath=objectmovies.getString(KEY_BACK_DROP_PATH);
                double vote_average=objectmovies.getDouble(KEY_VOTE_AVERAGE);
                String realisedata=objectmovies.getString(KEY_REALISE_DATA);
                Movie movie=new Movie(id,vote_count,title,original_title,overview,posterpath,bigposterpath,backdroppath,vote_average,realisedata);
                movies.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movies;
    }
  public static ArrayList<Rewiews>getJSONAARAYRewiews(JSONObject jsonObject){
        ArrayList<Rewiews>rewiewsArrayList=new ArrayList<>();
        if(jsonObject==null){
            return rewiewsArrayList;
        }
      try {
          JSONArray jsonArray=jsonObject.getJSONArray(KEY_RESULTS);
          for(int i=0;i<jsonArray.length();i++){
              JSONObject objectrewiews=jsonArray.getJSONObject(i);
              String author=objectrewiews.getString(KEY_AUTHOR);
              String content=objectrewiews.getString(KEY_CONTENT);
              Rewiews rewiews=new Rewiews(author,content);
              rewiewsArrayList.add(rewiews);
          }
      } catch (JSONException e) {
          e.printStackTrace();
      }
      return rewiewsArrayList;
  }
  public static ArrayList<Trailers>getJSONTRAILERarray(JSONObject jsonObject){
        ArrayList<Trailers>trailersArrayList=new ArrayList<>();
        if(jsonObject==null){
            return trailersArrayList;
        }
      try {
          JSONArray jsonArray=jsonObject.getJSONArray(KEY_RESULTS);
          for(int i=0;i<jsonArray.length();i++){
              JSONObject objectTrailers=jsonArray.getJSONObject(i);
              String key=YOUTUBE_URL+objectTrailers.getString(KEY_KEY);
              String name=objectTrailers.getString(KEY_NAME);
              Trailers trailers=new Trailers(key,name);
              trailersArrayList.add(trailers);
          }
      } catch (JSONException e) {
          e.printStackTrace();
      }
      return trailersArrayList;
  }
}
