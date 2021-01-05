package Utils;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class Networkutils {

    public static final String WEB="https://api.themoviedb.org/3/discover/movie";
    public static final String WEB_trailers="https://api.themoviedb.org/3/movie/%s/videos";
    public static final String WEB_rewiews= "https://api.themoviedb.org/3/movie/%s/reviews?";

    public static final String PARAM_API_KEY="api_key";
    public static final String API_KEY="290cd44f92a8e8d7e09f956865f44d23";
    public static final String PARAM_LANGUAGE="language";
    public static final String PARAM_SORT_BY="sort_by";
    public static final String PARAM_PAGE="page";
    public static final String PARAM_VOTE_COUNT_GTE="vote_count.gte";
    public static final String SORT_BY_POPULAR="popularity.desc";
    public static final String SORT_BY_TOP_RATED="vote_average.desc";
    public static final String LANGUAGE="en-US";
    public static final String VOTE_COUNT_GTE="300";

    public static int POPULARITY=0;
    public static int TOP_RATED=1;


    public static JSONObject getJSONURL (int sortby,int page){
        URL url=buildURL(sortby,page);
        JSONObject jsonObject=null;
        try {
            jsonObject=new JSONLOADER().execute(url).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
    public static JSONObject getJSONOBJREWIEWS(int id){
        URL url=buildURLREWIEWS(id);
        JSONObject jsonObject=null;
        try {
            jsonObject=new JSONLOADER().execute(url).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
    public static JSONObject getJSONOBJTRailers(int id){
        URL url=buildURLTRAIILERS(id);
        JSONObject jsonObject=null;
        try {
            jsonObject=new JSONLOADER().execute(url).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
    public static URL buildURL(int sortby,int page){
        URL url=null;
        String method;
        if(sortby==POPULARITY){
            method=SORT_BY_POPULAR;
        }
        else{
            method=SORT_BY_TOP_RATED;
        }
        Uri uri=Uri.parse(WEB).buildUpon().appendQueryParameter(PARAM_API_KEY,API_KEY)
                .appendQueryParameter(PARAM_LANGUAGE,LANGUAGE)
                .appendQueryParameter(PARAM_SORT_BY,method)
                .appendQueryParameter(PARAM_VOTE_COUNT_GTE,VOTE_COUNT_GTE)
                .appendQueryParameter(PARAM_PAGE,Integer.toString(page))
                .build();
        try {
            url=new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
    public static URL buildURLREWIEWS(int id){
        Uri uri=Uri.parse(String.format(WEB_rewiews,id)).buildUpon()
                .appendQueryParameter(PARAM_API_KEY,API_KEY)
                .appendQueryParameter(PARAM_LANGUAGE,LANGUAGE)
                .build();
        try {
            return new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static URL buildURLTRAIILERS(int id){
        Uri uri=Uri.parse(String.format(WEB_trailers,id)).buildUpon()
                .appendQueryParameter(PARAM_API_KEY,API_KEY)
                .appendQueryParameter(PARAM_LANGUAGE,LANGUAGE).build();
        try {
            return new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static class JSONLOADER extends AsyncTask<URL,Void, JSONObject>{

        @Override
        protected JSONObject doInBackground(URL... urls) {
            StringBuilder builder=new StringBuilder();
            JSONObject jsonObject=null;
            HttpURLConnection httpURLConnection=null;
            if(urls==null||urls.length==0){
                return null;
            }
            try {
                httpURLConnection=(HttpURLConnection)urls[0].openConnection();
                InputStream in=httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader=new InputStreamReader(in);
                BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
                String line=bufferedReader.readLine();
                while (line!=null){
                    builder.append(line);
                    line=bufferedReader.readLine();
                }
                jsonObject=new JSONObject(builder.toString());
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            finally {
                if(httpURLConnection!=null){
                    httpURLConnection.disconnect();
                }
            }
            return jsonObject;
        }
    }
    public static class JSONLOADEOPACHKI extends AsyncTaskLoader<JSONObject>{
        public Bundle bundle;
        public ONMOVELOADING onmoveloading;
        public interface ONMOVELOADING{
            void onmoveload();
        }

        public void setOnmoveloading(ONMOVELOADING onmoveloading) {
            this.onmoveloading = onmoveloading;
        }

        public JSONLOADEOPACHKI(@NonNull Context context, Bundle bundle) {
            super(context);
            this.bundle=bundle;

        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            if(onmoveloading!=null){
                onmoveloading.onmoveload();
            }
            forceLoad();
        }

        @Nullable
        @Override
        public JSONObject loadInBackground() {
            HttpURLConnection httpURLConnection=null;
              if(bundle==null){
                  return null;
              }
            String asString=bundle.getString("url");
            URL url=null;
            try {
                url = new URL(asString);
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            }
                 if(url==null){
                     return null;
                 }
            JSONObject jsonObject=null;
                 try {
                     httpURLConnection = (HttpURLConnection) url.openConnection();
                     InputStream in = httpURLConnection.getInputStream();
                     InputStreamReader inputStreamReader = new InputStreamReader(in);
                     BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                     StringBuilder builder=new StringBuilder();
                     String line = bufferedReader.readLine();
                     while (line != null) {
                         builder.append(line);
                         line = bufferedReader.readLine();
                     }
                     jsonObject = new JSONObject(builder.toString());
                 }
              catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            finally {
                if(httpURLConnection!=null){
                    httpURLConnection.disconnect();
                }
            }
            return jsonObject;
        }
    }
}
