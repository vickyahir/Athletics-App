package com.example.athletics.Activity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.Athletics.R;
import com.example.athletics.Utils.Functions;
import com.google.android.material.progressindicator.CircularProgressIndicator;

public class VideoViewActivity extends AppCompatActivity {


    private VideoView videoView;
    private MediaController mediaController;
    private String fullScreen = "", VideoURL = "";
    Handler handler = new Handler();
    int delay = 0; //1 second=1000 milisecond, 5*1000=5seconds
    Runnable runnable;
    private Boolean IsVideoPlaying = false;
    public CircularProgressIndicator RoundProgressVideoView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);

        initView();
        loadData();
        setClickListener();
    }


    private void initView() {

        try {
            fullScreen = getIntent().getStringExtra("fullScreenInd");
            VideoURL = getIntent().getStringExtra("VideoUrl");
        } catch (Exception e) {
            e.printStackTrace();
        }

        videoView = findViewById(R.id.videoViewRelative);
        RoundProgressVideoView = findViewById(R.id.RoundProgressVideoView);
        if ("y".equals(fullScreen)) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
//            getSupportActionBar().hide();
        }
    }

    private void loadData() {


        Uri videoUri = Uri.parse(VideoURL);

        videoView.setVideoURI(videoUri);

        mediaController = new FullScreenMediaController(this);
        mediaController.setAnchorView(videoView);

        videoView.setMediaController(mediaController);
        videoView.start();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        handler.removeCallbacks(runnable);
        Functions.animBack(VideoViewActivity.this);
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(runnable);
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        handler.removeCallbacks(runnable);
        super.onStop();
    }


    private void setClickListener() {

        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                int old_duration = 0;
                int duration = videoView.getCurrentPosition();
                if (old_duration == duration && videoView.isPlaying()) {
                    if (IsVideoPlaying) {
//                        Functions.dialogHide();
                        RoundProgressVideoView.setVisibility(View.GONE);
                    }
                } else {
                    if (!IsVideoPlaying) {
//                        Functions.dialogShow(VideoViewActivity.this);
                        RoundProgressVideoView.setVisibility(View.VISIBLE);
                        IsVideoPlaying = true;
                    }
                }
                old_duration = duration;

                handler.postDelayed(runnable, delay);
            }
        }, delay);

    }


}