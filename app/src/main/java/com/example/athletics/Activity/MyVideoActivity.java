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
import com.example.athletics.Adapter.MyVideoCategoryAdapter;
import com.example.athletics.Model.AthleteProfileVideosItem;
import com.example.athletics.Model.MyVideoApiResponse;
import com.example.athletics.Model.MyVideoDataItem;
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
    private List<MyVideoDataItem> HomeVideoCategory;
    private RelativeLayout LLMyVideoMain;
    private TextView TvNodataFound;
    private SwipeRefreshLayout SwipeMyVideoPage;
    private ViewPager2 MyVideoViewpager;
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
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void initView() {
        toolbarMain = findViewById(R.id.toolbarMain);
        LLMyVideoMain = findViewById(R.id.LLMyVideoMain);
        TvNodataFound = findViewById(R.id.TvNodataFound);
        imgBack = toolbarMain.findViewById(R.id.imgBack);
        imgMenu = toolbarMain.findViewById(R.id.imgMenu);
        TvTitle = toolbarMain.findViewById(R.id.TvTitle);
        MyVideoViewpager = (ViewPager2) findViewById(R.id.MyVideoViewpager);
        SwipeMyVideoPage = (SwipeRefreshLayout) findViewById(R.id.SwipeMyVideoPage);

        TvTitle.setText(getResources().getString(R.string.my_video));


        loadData();
    }


    private void loadData() {

        if (cd.isConnectingToInternet()) {
            Functions.dialogShow(MyVideoActivity.this);
            callMyVideoApiResponse();
        } else {
            Snackbar snackbar = Snackbar.make(LLMyVideoMain, getResources().getString(R.string.check_internet_connection), Snackbar.LENGTH_LONG);
            snackbar.show();
        }


    }


    public void callMyVideoApiResponse() {

        apiInterface = ApiClient.getClient(this).create(ApiInterface.class);
        Call<MyVideoApiResponse> loginApiResponseCall = apiInterface.GetMyVideoApi();
        loginApiResponseCall.enqueue(new Callback<MyVideoApiResponse>() {
            @Override
            public void onResponse(Call<MyVideoApiResponse> call, Response<MyVideoApiResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        Functions.dialogHide();

                        if (SwipeMyVideoPage.isRefreshing()) {
                            SwipeMyVideoPage.setRefreshing(false);
                        }

                        if (response.body().getData().size() > 0) {
                            TvNodataFound.setVisibility(View.GONE);
//
                            HomeVideoCategory = new ArrayList<>();
                            HomeVideoCategory.addAll(response.body().getData());

                            MyVideoViewpager.setAdapter(new MyVideoCategoryAdapter(MyVideoActivity.this, HomeVideoCategory));

                        } else {
                            TvNodataFound.setVisibility(View.VISIBLE);
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<MyVideoApiResponse> call, Throwable t) {
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

        SwipeMyVideoPage.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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