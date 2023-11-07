package com.phuoc.news.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.phuoc.news.R;
import com.phuoc.news.model.New_Details;

public class DetailNewsActivity extends AppCompatActivity {
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news);

        webView = (WebView) findViewById(R.id.wvTinTuc);

        Intent intent = getIntent();
        New_Details newDetails = (New_Details) intent.getSerializableExtra("News");
        String link = newDetails.getLinkNews();

        webView.loadUrl(link);
        webView.setWebViewClient(new WebViewClient());
    }
}