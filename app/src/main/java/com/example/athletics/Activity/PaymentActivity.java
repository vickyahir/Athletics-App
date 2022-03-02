package com.example.athletics.Activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.Athletics.R;
import com.example.athletics.Utils.ConnectionDetector;
import com.example.athletics.Utils.Functions;


public class PaymentActivity extends AppCompatActivity {

    private String PlanID = "", authlink = "";
    WebView payment_webview;
    private ConnectionDetector cd;
    public Toolbar toolbarMain;
    private ImageView iv_Back;
    private TextView tvTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        cd = new ConnectionDetector(this);

        getIntentData();
        initView();
        setClickListener();
    }

    private void getIntentData() {
        try {
            PlanID = getIntent().getStringExtra("Id");
            authlink = getIntent().getExtras().getString("authLink");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        payment_webview = (WebView) findViewById(R.id.payment_webview);
        toolbarMain = findViewById(R.id.toolbarMain);
        iv_Back = toolbarMain.findViewById(R.id.imgBack);
        tvTitle = findViewById(R.id.TvTitle);
        tvTitle.setText(getResources().getString(R.string.payments));

        Functions.dialogShowNotCancellable(PaymentActivity.this);

    }


    private void setClickListener() {

        iv_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        payment_webview.getSettings().setJavaScriptEnabled(true);
        payment_webview.setHorizontalScrollBarEnabled(true);
        payment_webview.getSettings().setLoadWithOverviewMode(true);
        payment_webview.getSettings().setUseWideViewPort(true);
        payment_webview.requestFocus();


        payment_webview.loadUrl(authlink);
        payment_webview.setWebViewClient(new WebViewClient() {
            boolean loader = false;

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.e("URL==", url);
                if (url.startsWith("https:")) {
                    view.loadUrl(url);
                } else {

                    String StatusArray[] = url.split("&");
                    String Status[] = StatusArray[0].split(":");
                    String Transactionid[] = StatusArray[1].split(":");
//                    Toast.makeText(PaymentActivity.this, getResources().getString(R.string.your_payment_is) + Status[1], Toast.LENGTH_SHORT).show();

//                    if (Status[1].equalsIgnoreCase("CANCELLED")) {
//                        Intent intent = new Intent(PaymentActivity.this, OrderDetailsActivity.class);
//                        intent.putExtra("orderId", OrderID);
//                        intent.putExtra("from", "payment");
//                        startActivity(intent);
//                        finish();
//                        Functions.animNext(PaymentActivity.this);
//                    } else {
//                        Intent intent = new Intent(PaymentActivity.this, ThankyouPageActivity.class);
//                        intent.putExtra("orderId", Transactionid[1]);
//                        startActivity(intent);
//                        finish();
//                        Functions.animNext(PaymentActivity.this);
//                    }


                }
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                loader = true;
            }

            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (loader == true) {
                    Functions.dialogHide();
                } else {
                    view.loadUrl(url);
                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Functions.animBack(PaymentActivity.this);
    }

}