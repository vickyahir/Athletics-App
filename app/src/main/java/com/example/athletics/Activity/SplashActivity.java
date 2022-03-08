package com.example.athletics.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.Athletics.R;
import com.example.athletics.Utils.ExtraPrefrence;
import com.example.athletics.Utils.Functions;
import com.example.athletics.Utils.SessionManager;

public class SplashActivity extends AppCompatActivity {


    int progressStatus = 0;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (!new ExtraPrefrence(SplashActivity.this).getKeyAppFirst().equalsIgnoreCase("true")) {
            Intent intent = new Intent(SplashActivity.this, IntroSliderActivity.class);
            startActivity(intent);
            Functions.animBack(SplashActivity.this);
        } else {
            initView();
            setClickListener();
            startNew();
        }


    }

    private void initView() {
        progressBar = findViewById(R.id.progressBar1);


    }

    private void setClickListener() {

    }

    public void startNew() {
        final Handler handler = new Handler();
        new Thread(() ->
        {
            while (progressStatus < 100) {
                progressStatus += 2;
                handler.post(() -> progressBar.setProgress(progressStatus));
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (progressStatus == 100) {
                RedirectToNextScreen();
            }
        }).start();
    }


    private void RedirectToNextScreen() {
        if (!new SessionManager(SplashActivity.this).getUserID().equalsIgnoreCase("") && !new SessionManager(SplashActivity.this).getKeyUserActive().equalsIgnoreCase("null")) {
//            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
            Functions.animNext(SplashActivity.this);
        } else if (!new SessionManager(SplashActivity.this).getUserID().equalsIgnoreCase("") && new SessionManager(SplashActivity.this).getKeyUserActive().equalsIgnoreCase("null")) {
            Intent intent = new Intent(SplashActivity.this, EmailVerifyActivity.class);
            startActivity(intent);
            finish();
            Functions.animNext(SplashActivity.this);
        } else {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            Functions.animNext(SplashActivity.this);
        }


    }


}