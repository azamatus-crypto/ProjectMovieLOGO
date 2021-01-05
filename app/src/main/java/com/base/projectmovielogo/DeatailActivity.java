package com.base.projectmovielogo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

import Adapters.MoviesAdapter;
import Adapters.Rewiewsadapter;
import Adapters.TrailersAdapter;
import Data.FavoriteMovie;
import Data.MainViewModule;
import Data.Movie;
import Data.Rewiews;
import Data.Trailers;
import Utils.JSONUTILS;
import Utils.Networkutils;

public class DeatailActivity extends AppCompatActivity {
    private ImageView imageViewBigposter;
    private TextView textViewtitle;
    private TextView textVieworiginaltitle;
    private TextView textViewratings;
    private TextView textViewdatarealise;
    private TextView textViewoverview;
    private ImageView imageViewaddtofavorites;
    private int id;
    private MoviesAdapter moviesAdapter;
    private MainViewModule mainViewModule;
    private  Movie movie;
    private Rewiewsadapter rewiewsadapter;
    private TrailersAdapter trailersAdapter;
    private RecyclerView recyclerViewrewiews;
    private RecyclerView recyclerViewTrailers;
    private FavoriteMovie favoriteMovie;
    private ScrollView scrollView;
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

  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deatail);
        imageViewBigposter=findViewById(R.id.imageViewbigposter);
        imageViewaddtofavorites=findViewById(R.id.imageViewaddtofavorites);
        textViewtitle=findViewById(R.id.textViewtitle);
        textVieworiginaltitle=findViewById(R.id.textVieworiginaltitle);
        textViewratings=findViewById(R.id.textViewratings);
        scrollView=findViewById(R.id.scrolviewinfomovie);
        textViewdatarealise=findViewById(R.id.textViewdatarealise);
        textViewoverview=findViewById(R.id.textViewOverview);
        moviesAdapter=new MoviesAdapter();
        mainViewModule= ViewModelProviders.of(this).get(MainViewModule.class);
        Intent intent=getIntent();
        if(intent.hasExtra("id")){
            id=intent.getIntExtra("id",-1);
        }
        else{
            finish();
        }
        movie=mainViewModule.getMovieID(id);
        Picasso.get().load(movie.getBigPosterPath()).into(imageViewBigposter);
        textViewtitle.setText(movie.getTitle());
        textVieworiginaltitle.setText(movie.getOriginalTitle());
        textViewratings.setText(Double.toString(movie.getVoteAverage()));
        textViewdatarealise.setText(movie.getReleaseDate());
        textViewoverview.setText(movie.getOverview());
        setData();
        rewiewsadapter=new Rewiewsadapter();
        trailersAdapter=new TrailersAdapter();
        recyclerViewrewiews=findViewById(R.id.recyklerviewrewiews);
        recyclerViewTrailers=findViewById(R.id.recyklerviewtrailers);
        recyclerViewrewiews.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTrailers.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewrewiews.setAdapter(rewiewsadapter);
        recyclerViewTrailers.setAdapter(trailersAdapter);

        trailersAdapter.setOntrailertouch(new TrailersAdapter.ONTRAILERTOUCH() {
            @Override
            public void ontrailercklick(String url) {
                Intent intenttovideo=new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intenttovideo);
            }
        });
        JSONObject objecttrailers= Networkutils.getJSONOBJTRailers(id);
        JSONObject objectrewiews=Networkutils.getJSONOBJREWIEWS(id);
        ArrayList<Trailers>trailers= JSONUTILS.getJSONTRAILERarray(objecttrailers);
        ArrayList<Rewiews>rewiews=JSONUTILS.getJSONAARAYRewiews(objectrewiews);
        trailersAdapter.setTrailersList(trailers);
        rewiewsadapter.setRewiewsList(rewiews);
         scrollView.smoothScrollTo(0,0);

    }

    public void setData(){
        favoriteMovie=mainViewModule.getFavorID(id);
        if(favoriteMovie==null){
            imageViewaddtofavorites.setImageResource(R.drawable.starsforandroid);
        }
        else{
            imageViewaddtofavorites.setImageResource(R.drawable.christmas);
        }
    }

    public void onCklickaddtofavorite(View view) {
       if(favoriteMovie==null){
           mainViewModule.insertFavoriteMovie(new FavoriteMovie(movie));
           Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show();
       }
       else{
           mainViewModule.deleteFavorMovie(favoriteMovie);
           Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
       }
       setData();
    }
}