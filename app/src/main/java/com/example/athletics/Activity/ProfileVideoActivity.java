package com.example.athletics.Activity;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.Athletics.R;
import com.example.athletics.Model.AthleteProfileVideosItem;
import com.example.athletics.Model.UserLikeVideoDataItem;
import com.example.athletics.Utils.Functions;
import com.jaedongchicken.ytplayer.YoutubePlayerView;
import com.jaedongchicken.ytplayer.model.PlaybackQuality;
import com.jaedongchicken.ytplayer.model.YTParams;

import java.util.List;

public class ProfileVideoActivity extends BaseActivity {
    private ImageView imgBack, imgMenu, imgPlay;
    private Toolbar toolbarMain;
    private TextView TvTitle;
    private RecyclerView rvLikevideo;
    private List<UserLikeVideoDataItem> HomeVideoCategory;
    private List<AthleteProfileVideosItem> athleteProfileVideosItems;
    private RelativeLayout LLLikeVideoMain;
    private TextView TvNodataFound;
    private SwipeRefreshLayout SwipeProfileVideoPage;
    private String ProfileVideoURI = "", Position = "";
    private int ViewpagerPos = 0;
    public YoutubePlayerView simpleProfileVideoView;
    private LinearLayout LLMuteUnMute;
    public boolean isMute = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_video);
        super.onCreateMenu();
        super.onMenuSelect(5);

        getIntentData();
        initView();
        setClickListener();
    }

    private void getIntentData() {
        try {
            ProfileVideoURI = getIntent().getStringExtra("video");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void initView() {
        toolbarMain = findViewById(R.id.toolbarMain);
        LLLikeVideoMain = findViewById(R.id.LLLikeVideoMain);
        TvNodataFound = findViewById(R.id.TvNodataFound);
        imgBack = toolbarMain.findViewById(R.id.imgBack);
        imgMenu = toolbarMain.findViewById(R.id.imgMenu);
        imgPlay = findViewById(R.id.imgPlay);
        TvTitle = toolbarMain.findViewById(R.id.TvTitle);
        LLMuteUnMute = findViewById(R.id.LLMuteUnMute);
        simpleProfileVideoView = findViewById(R.id.simpleProfileVideoView);
        SwipeProfileVideoPage = (SwipeRefreshLayout) findViewById(R.id.SwipeProfileVideoPage);

        TvTitle.setText(getResources().getString(R.string.videos));


        loadData();
    }


    private void loadData() {

        if (SwipeProfileVideoPage.isRefreshing()) {
            SwipeProfileVideoPage.setRefreshing(false);
        }


        YTParams params = new YTParams();
        //  params.setControls(0); // hide control
        params.setVolume(100); // volume control
        params.setPlaybackQuality(PlaybackQuality.small); //

        simpleProfileVideoView.initializeWithCustomURL(ProfileVideoURI, "https://54athletics.s3.amazonaws.com/upload/thumb/6213296f7839d.svg", params, new YoutubePlayerView.YouTubeListener() {
            @Override
            public void onReady() {
                simpleProfileVideoView.reload();
                simpleProfileVideoView.requestLayout();
                simpleProfileVideoView.play();


            }

            @Override
            public void onStateChange(YoutubePlayerView.STATE state) {

            }

            @Override
            public void onPlaybackQualityChange(String arg) {

            }

            @Override
            public void onPlaybackRateChange(String arg) {

            }

            @Override
            public void onError(String arg) {

            }

            @Override
            public void onApiChange(String arg) {

            }

            @Override
            public void onCurrentSecond(double second) {

            }

            @Override
            public void onDuration(double duration) {

            }

            @Override
            public void logs(String log) {

            }
        });

        simpleProfileVideoView.play();

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Functions.animBack(ProfileVideoActivity.this);
    }


    private void setClickListener() {

        LLMuteUnMute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!isMute) {
                    AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                    am.setStreamMute(AudioManager.STREAM_MUSIC, true);
                    isMute = true;
                    imgPlay.setVisibility(View.VISIBLE);
                    imgPlay.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_volume_off));
                } else {

                    AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                    am.setStreamMute(AudioManager.STREAM_MUSIC, false);
                    isMute = false;
                    imgPlay.setVisibility(View.VISIBLE);
                    imgPlay.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_volume_up));
                }
                ImgageDispplaySomeTimes(imgPlay);

            }
        });

        SwipeProfileVideoPage.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ProfileVideoActivity.this, "menu clicked !", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void ImgageDispplaySomeTimes(ImageView imgPlay) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                imgPlay.setVisibility(View.GONE);
            }
        }, 1000);


    }


}