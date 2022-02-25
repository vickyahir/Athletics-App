package com.example.athletics.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.example.Athletics.R;
import com.example.athletics.Adapter.AthleteVideoAdapter;
import com.example.athletics.Adapter.LikeVideoCategoryAdapter;
import com.example.athletics.Model.AthleteProfileResponse;
import com.example.athletics.Model.AthleteProfileVideosItem;
import com.example.athletics.Model.UserLikeVideoApiResponse;
import com.example.athletics.Model.UserLikeVideoDataItem;
import com.example.athletics.Retrofit.ApiClient;
import com.example.athletics.Retrofit.ApiInterface;
import com.example.athletics.Utils.Functions;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyVideoActivity extends BaseActivity {
    private ImageView imgBack, imgMenu;
    private Toolbar toolbarMain;
    private TextView TvTitle;
    private RecyclerView rvLikevideo;
    private List<UserLikeVideoDataItem> HomeVideoCategory;
    private List<AthleteProfileVideosItem> athleteProfileVideosItems;
    private RelativeLayout LLLikeVideoMain;
    private TextView TvNodataFound;
    private SwipeRefreshLayout SwipeLikeVideoPage;
    private ViewPager2 LikeVideoViewpager;
    private String AthleteId = "", Position = "";
    private int ViewpagerPos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_video);
        super.onCreateMenu();
        super.onMenuSelect(5);

        getIntentData();
        initView();
        setClickListener();
    }

    private void getIntentData() {
        try {
            AthleteId = getIntent().getStringExtra("Id");
            Position = getIntent().getStringExtra("position");
            ViewpagerPos = Integer.parseInt(Position);
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
        TvTitle = toolbarMain.findViewById(R.id.TvTitle);
        LikeVideoViewpager = (ViewPager2) findViewById(R.id.LikeVideoViewpager);
        SwipeLikeVideoPage = (SwipeRefreshLayout) findViewById(R.id.SwipeLikeVideoPage);

        if (AthleteId.equalsIgnoreCase("")) {
            TvTitle.setText(getResources().getString(R.string.like_video));
        } else {
            TvTitle.setText(getResources().getString(R.string.videos));
        }


        loadData();
    }


    private void loadData() {

        if (AthleteId.equalsIgnoreCase("")) {
            if (cd.isConnectingToInternet()) {
                Functions.dialogShow(MyVideoActivity.this);
                callLikeVideoApiResponse();
            } else {
                Snackbar snackbar = Snackbar.make(LLLikeVideoMain, getResources().getString(R.string.check_internet_connection), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        } else {
            if (cd.isConnectingToInternet()) {
                Functions.dialogShow(MyVideoActivity.this);
                CallMyFollowingApiResponse();
            } else {
                Snackbar snackbar = Snackbar.make(LLLikeVideoMain, getResources().getString(R.string.check_internet_connection), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        }

    }


    public void callLikeVideoApiResponse() {

        apiInterface = ApiClient.getClient(this).create(ApiInterface.class);
        Call<UserLikeVideoApiResponse> loginApiResponseCall = apiInterface.GetUserLikeVideoApi();
        loginApiResponseCall.enqueue(new Callback<UserLikeVideoApiResponse>() {
            @Override
            public void onResponse(Call<UserLikeVideoApiResponse> call, Response<UserLikeVideoApiResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        Functions.dialogHide();

                        if (SwipeLikeVideoPage.isRefreshing()) {
                            SwipeLikeVideoPage.setRefreshing(false);
                        }

                        if (response.body().getData().size() > 0) {
//                            rvLikevideo.setVisibility(View.VISIBLE);
                            TvNodataFound.setVisibility(View.GONE);
//
                            HomeVideoCategory = new ArrayList<>();
                            HomeVideoCategory.addAll(response.body().getData());
//                            rvLikevideo.setLayoutManager(new LinearLayoutManager(LikeVideoActivity.this));
//                            rvLikevideo.setAdapter(new LikeVideoCategoryAdapter(LikeVideoActivity.this, HomeVideoCategory));

                            LikeVideoViewpager.setAdapter(new LikeVideoCategoryAdapter(MyVideoActivity.this, HomeVideoCategory));

                        } else {
                            TvNodataFound.setVisibility(View.VISIBLE);
//                            rvLikevideo.setVisibility(View.GONE);
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<UserLikeVideoApiResponse> call, Throwable t) {
                Functions.dialogHide();
            }
        });
    }

    public void CallMyFollowingApiResponse() {

        apiInterface = ApiClient.getClient(this).create(ApiInterface.class);
        Call<AthleteProfileResponse> loginApiResponseCall = apiInterface.GetAthleteProfileApiResponse(AthleteId);
        loginApiResponseCall.enqueue(new Callback<AthleteProfileResponse>() {
            @Override
            public void onResponse(Call<AthleteProfileResponse> call, Response<AthleteProfileResponse> response) {
                try {
                    if (response.isSuccessful()) {

                        Functions.dialogHide();

                        if (response.body().getData().getVideos().size() > 0) {
                            TvNodataFound.setVisibility(View.GONE);

                            athleteProfileVideosItems = new ArrayList<>();
                            athleteProfileVideosItems.addAll(response.body().getData().getVideos());

                            LikeVideoViewpager.setAdapter(new AthleteVideoAdapter(MyVideoActivity.this, athleteProfileVideosItems));

                            LikeVideoViewpager.setCurrentItem(ViewpagerPos, true);

                        } else {
                            TvNodataFound.setVisibility(View.VISIBLE);
                        }


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<AthleteProfileResponse> call, Throwable t) {
                Functions.dialogHide();
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Functions.animBack(MyVideoActivity.this);
    }


    private void setClickListener() {

        SwipeLikeVideoPage.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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
                Toast.makeText(MyVideoActivity.this, "menu clicked !", Toast.LENGTH_SHORT).show();
            }
        });


    }


}