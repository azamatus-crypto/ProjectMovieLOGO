package com.base.projectmovielogo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowMetrics;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import org.json.JSONObject;

import java.net.URL;
import java.util.List;

import Adapters.MoviesAdapter;
import Data.MainViewModule;
import Data.Movie;
import Utils.JSONUTILS;
import Utils.Networkutils;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<JSONObject> {
    private TextView textViewpopular;
    private TextView textViewtoprated;
    private Switch swwitchrating;
    private RecyclerView recyclerViewmain;
    private MoviesAdapter moviesAdapter;
    private MainViewModule mainViewModule;
    private Movie movie;
    public int method;
    public static int page=1;
    public boolean isloading=false;
    private ProgressBar progressBar;
    private LoaderManager loaderManager;
    private static  int loaderid=122;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int menuid=item.getItemId();
        switch (menuid){
            case R.id.main_menu:
                Intent intentmain=new Intent(this,MainActivity.class);
                startActivity(intentmain);
                break;
            case R.id.main_favorite:
                Intent intentfavor=new Intent(this,FavoriteActivity.class);
                startActivity(intentfavor);
                break;
            case R.id.main_webweiew:
                Intent intentwatchonline=new Intent(this,WEbWeiewActivity.class);
                startActivity(intentwatchonline);
                break;
            case R.id.main_webweiewenglish:
                Intent intentwatchonlineenglish=new Intent(this,WEbWeiewEnglish.class);
                startActivity(intentwatchonlineenglish);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public int coulumnCount(){
        DisplayMetrics displayMetrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width=(int)(displayMetrics.widthPixels/displayMetrics.density);
        return width/185>2?width/185:2;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        recyclerViewmain=findViewById(R.id.recycklerViewMain);
        textViewtoprated=findViewById(R.id.textViewTopRated);
        textViewpopular=findViewById(R.id.textViewpopularity);
        swwitchrating=findViewById(R.id.switchRating);
        progressBar=findViewById(R.id.progressbar);
        loaderManager=LoaderManager.getInstance(this);
        moviesAdapter=new MoviesAdapter();
        mainViewModule= ViewModelProviders.of(this).get(MainViewModule.class);
        recyclerViewmain.setAdapter(moviesAdapter);
        recyclerViewmain.setLayoutManager(new GridLayoutManager(this,coulumnCount()));
        swwitchrating.setChecked(true);
        swwitchrating.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                page=1;
                isHave(isChecked);

            }
        });
        swwitchrating.setChecked(false);
        moviesAdapter.setOnpostertouch(new MoviesAdapter.ONPOSTERTOUCH() {
            @Override
            public void onpostercklick(int position) {
                 movie=moviesAdapter.getMovies().get(position);
                Intent intent=new Intent(MainActivity.this,DeatailActivity.class);
                intent.putExtra("id",movie.getId());
                startActivity(intent);
            }
        });
        moviesAdapter.setOnreachend(new MoviesAdapter.ONREACHEND() {
            @Override
            public void onreaches() {
                if(!isloading){
                    getdata(method,page);
                }
            }
        });
        LiveData<List<Movie>>moviesfromdata=mainViewModule.getMovies();
        moviesfromdata.observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movieshka) {
                if(page==1){
                    moviesAdapter.setMovies(movieshka);
                }
            }
        });
    }

    public void isHave(boolean isHad){
        if(isHad){
            method= Networkutils.TOP_RATED;
            textViewtoprated.setTextColor(getResources().getColor(R.color.design_default_color_error));
            textViewpopular.setTextColor(getResources().getColor(R.color.white));
        }
        else{
            method=Networkutils.POPULARITY;
            textViewpopular.setTextColor(getResources().getColor(R.color.design_default_color_error));
            textViewtoprated.setTextColor(getResources().getColor(R.color.white));
        }
        getdata(method,page);
    }
    public void getdata(int method,int page){
        URL url=Networkutils.buildURL(method,page);
        Bundle bundle=new Bundle();
        bundle.putString("url",url.toString());
        loaderManager.restartLoader(loaderid,bundle,this);
    }

    public void onCklickpopular(View view) {
        isHave(false);
        swwitchrating.setChecked(false);
    }

    public void oncklicktoprated(View view) {
        isHave(true);
        swwitchrating.setChecked(true);
    }

    @NonNull
    @Override
    public Loader<JSONObject> onCreateLoader(int id, @Nullable Bundle args) {
        Networkutils.JSONLOADEOPACHKI jsonloadeopachki=new Networkutils.JSONLOADEOPACHKI(this,args);
         jsonloadeopachki.setOnmoveloading(new Networkutils.JSONLOADEOPACHKI.ONMOVELOADING() {
             @Override
             public void onmoveload() {
                 progressBar.setVisibility(View.VISIBLE);
                 isloading=true;
             }
         });
        return jsonloadeopachki;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<JSONObject> loader, JSONObject data) {

         List<Movie>movik= JSONUTILS.getJSONARRAY(data);
        if (movik!=null&&!movik.isEmpty()) {
            if (page == 1) {
                mainViewModule.deleteAllMovie();
                moviesAdapter.clearMovie();
                for (Movie movie : movik) {
                    mainViewModule.insertMovie(movie);
                }
                moviesAdapter.addMovies(movik);
                page++;
            }

            isloading=false;
            progressBar.setVisibility(View.INVISIBLE);
            loaderManager.destroyLoader(loaderid);
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<JSONObject> loader) {

    }
}