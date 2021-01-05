package com.base.projectmovielogo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import Adapters.MoviesAdapter;
import Data.FavoriteMovie;
import Data.MainViewModule;
import Data.Movie;

public class FavoriteActivity extends AppCompatActivity {
     private RecyclerView recyclerViewfavorites;
     private MoviesAdapter moviesAdapter;
     private MainViewModule mainViewModule;
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
        setContentView(R.layout.activity_favorite);
        recyclerViewfavorites=findViewById(R.id.recycklerviewfavorites);
        moviesAdapter=new MoviesAdapter();
        mainViewModule= ViewModelProviders.of(this).get(MainViewModule.class);
        recyclerViewfavorites.setLayoutManager(new GridLayoutManager(this,2));
        recyclerViewfavorites.setAdapter(moviesAdapter);
        LiveData<List<FavoriteMovie>>moviesfromdata=mainViewModule.getFavoritmovies();
        moviesfromdata.observe(this, new Observer<List<FavoriteMovie>>() {
            @Override
            public void onChanged(List<FavoriteMovie> favoriteMovies) {
                ArrayList<Movie>movies=new ArrayList<>();
                if(favoriteMovies!=null){
                    movies.addAll(favoriteMovies);
                }
                moviesAdapter.setMovies(movies);
            }
        });
        moviesAdapter.setOnpostertouch(new MoviesAdapter.ONPOSTERTOUCH() {
            @Override
            public void onpostercklick(int position) {
                Movie movie=moviesAdapter.getMovies().get(position);
                Intent intent=new Intent(FavoriteActivity.this,DeatailActivity.class);
                intent.putExtra("id",movie.getId());
                startActivity(intent);
            }
        });
    }
}