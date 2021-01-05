package com.base.projectmovielogo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WEbWeiewActivity extends AppCompatActivity {
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
            case R.id.main_webweiewenglish:
                Intent intentwatchonlineenglish=new Intent(this,WEbWeiewEnglish.class);
                startActivity(intentwatchonlineenglish);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_w_eb_weiew);
//        this.getWindow().getDecorView().setSystemUiVisibility(
//
//                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//
//                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//
//                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//
//                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//
//                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
        WebView webView=findViewById(R.id.webweiew);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://filmix.co/fantastiks/86843-sotnja-yea-2017.html");
        WebViewClient webViewClient=new WebViewClient(){
            @SuppressWarnings("deprecation") @Override

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
        }
            @Override

            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

                view.loadUrl(request.getUrl().toString());

                return true;

            }

        };
       webView.setWebViewClient(webViewClient);
    }
}