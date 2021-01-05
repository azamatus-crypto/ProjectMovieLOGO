package com.base.projectmovielogo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WEbWeiewEnglish extends AppCompatActivity {
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
                Intent intentwatch=new Intent(this,WEbWeiewActivity.class);
                startActivity(intentwatch);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_w_eb_weiew_english);
        WebView webView2=findViewById(R.id.webweiewenglish);
        webView2.getSettings().setJavaScriptEnabled(true);
        webView2.loadUrl("https://fenglish.ru/films/");
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
        webView2.setWebViewClient(webViewClient);
    }
    }
