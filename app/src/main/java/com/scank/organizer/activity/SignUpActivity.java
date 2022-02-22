package com.scank.organizer.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.scank.organizer.R;
import com.scank.organizer.Utility.Constants;
import com.scank.organizer.helper.WebViewAccess;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.annotation.Documented;

public class SignUpActivity extends AppCompatActivity {

    WebView webView;
    ProgressBar progressBar;
    AppCompatImageView ivClose;
    int elementNum;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        init();
        setListener();
    }

    public void init() {
        elementNum = 0;

        webView = findViewById(R.id.webView);

        progressBar = findViewById(R.id.progressBar);
        ivClose = findViewById(R.id.ivClose);
    }

    public void setListener() {

        ivClose.setOnClickListener(view -> {
            onBackPressed();
        });

        webView.getSettings().setJavaScriptEnabled(true); // enable javascript
        webView.setWebViewClient(new MyWebViewClient());
        webView.loadUrl(Constants.registerLink);
//        System.out.println(webView.getUrl());
        System.out.println(webView.getProgress());

    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            System.out.println(url);
            if(url.equals("https://scank.azurewebsites.net/")){
                finish();
                return true;

            }
            else if ("scank.azurewebsites.net".equals(Uri.parse(url).getHost())) {
                System.out.println("xx");
                // This is my website, so do not override; let my WebView load the page
                return false;
            }
            // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
            return true;
        }



        @Override
        public void onPageCommitVisible(android.webkit.WebView view, String url) {
            progressBar.setVisibility(View.GONE);
            System.out.println(webView.getUrl());
            //new WebViewAccess().execute();
//            System.out.println("url"+webView.getUrl());
//            elementNum++;
//            if (elementNum==2){
//                System.out.println("GO TO LOGIN SCREEN");
//                Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
//                startActivity(intent);
//                finish();
//            }
            super.onPageCommitVisible(view, url);
        }



        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            System.out.println(url);
            super.onPageStarted(view, url, favicon);

        }


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }



}

