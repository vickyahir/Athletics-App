package com.example.athletics.Activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.Athletics.R;
import com.example.athletics.Utils.Functions;

public class WebviewActivity extends AppCompatActivity {
    private ImageView imgBack, imgMenu;
    private Toolbar toolbarMain;
    private TextView TvTitle;
    private RelativeLayout LLProfileMain;
    private String PageTitle = "";
    private WebView simpleWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        getIntentData();
        initView();
        loadData();
        setClickListener();
    }

    private void getIntentData() {
        try {
            PageTitle = getIntent().getStringExtra("Title");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initView() {
        toolbarMain = findViewById(R.id.toolbarMain);
        imgBack = toolbarMain.findViewById(R.id.imgBack);
        imgMenu = toolbarMain.findViewById(R.id.imgMenu);
        TvTitle = toolbarMain.findViewById(R.id.TvTitle);

        LLProfileMain = findViewById(R.id.LLProfileMain);
        simpleWebView = findViewById(R.id.simpleWebView);
        imgMenu.setVisibility(View.INVISIBLE);

        TvTitle.setText(PageTitle);

    }

    private void loadData() {

        simpleWebView.getSettings().setJavaScriptEnabled(true);
        simpleWebView.setHorizontalScrollBarEnabled(true);
        simpleWebView.getSettings().setLoadWithOverviewMode(true);
        simpleWebView.getSettings().setUseWideViewPort(true);
        simpleWebView.requestFocus();


        simpleWebView.loadUrl("https://www.google.com");

        simpleWebView.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if (url.startsWith("https:")) {
                    view.loadUrl(url);
                }
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                view.loadUrl(url);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Functions.animBack(WebviewActivity.this);
    }


    private void setClickListener() {

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }


}