package com.example.athletics.Activity;

import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;


import com.example.Athletics.R;
import com.example.athletics.Utils.Functions;

public class VideoViewActivity extends AppCompatActivity {


    private VideoView videoView;
    private MediaController mediaController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);

        videoView = findViewById(R.id.videoViewRelative);
        String fullScreen = getIntent().getStringExtra("fullScreenInd");
        if ("y".equals(fullScreen)) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
//            getSupportActionBar().hide();
        }


        initView();
        loadData();
        setClickListener();
    }


    private void initView() {


    }

    private void loadData() {

        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.dummy_two);

        videoView.setVideoURI(videoUri);

        mediaController = new FullScreenMediaController(this);
        mediaController.setAnchorView(videoView);

        videoView.setMediaController(mediaController);
        videoView.start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Functions.animBack(VideoViewActivity.this);
    }

    private void LoadCategoryData() {
    }


    private void setClickListener() {


    }


}