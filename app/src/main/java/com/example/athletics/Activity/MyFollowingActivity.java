package com.example.athletics.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.Athletics.R;
import com.example.athletics.Adapter.MyFollowingAdapter;
import com.example.athletics.Model.FollowingApiResponse;
import com.example.athletics.Model.FollowingDataItem;
import com.example.athletics.Retrofit.ApiClient;
import com.example.athletics.Retrofit.ApiInterface;
import com.example.athletics.Utils.ConnectionDetector;
import com.example.athletics.Utils.Functions;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyFollowingActivity extends AppCompatActivity {

    public Toolbar toolbarMain;
    private ImageView imgBack;
    private TextView tvTitle, TvNoDataFound, TvResult;
    private RecyclerView rvFollowingList;
    private ApiInterface apiInterface;
    private ConnectionDetector cd;
    private LinearLayout LLFollowingMain, LLResultCount;
    private List<FollowingDataItem> FollowingList;
    private String Title = "";
    private SwipeRefreshLayout SwipFollowingPage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_following);
        cd = new ConnectionDetector(MyFollowingActivity.this);

        getIntentData();
        initView();

        setClickListener();

    }

    private void getIntentData() {
        try {
            Title = getIntent().getStringExtra("Title");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        loadData();
        super.onResume();
    }

    private void initView() {

        tvTitle = findViewById(R.id.TvTitle);
        TvNoDataFound = findViewById(R.id.TvNodataFound);
        TvResult = findViewById(R.id.TvResult);
        toolbarMain = findViewById(R.id.toolbarMain);
        imgBack = toolbarMain.findViewById(R.id.imgBack);
        tvTitle.setText(Title);
        rvFollowingList = findViewById(R.id.rvFollowingList);
        LLFollowingMain = findViewById(R.id.LLFollowingMain);
        SwipFollowingPage = (SwipeRefreshLayout) findViewById(R.id.SwipFollowingPage);

    }


    public void loadData() {

        if (Title.equalsIgnoreCase(getResources().getString(R.string.follower))) {
            if (cd.isConnectingToInternet()) {
                Functions.dialogShow(MyFollowingActivity.this);
                CallMyFollowerApiResponse();
            } else {
                Snackbar snackbar = Snackbar.make(LLFollowingMain, getResources().getString(R.string.check_internet_connection), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        } else {
            if (cd.isConnectingToInternet()) {
                Functions.dialogShow(MyFollowingActivity.this);
                CallMyFollowingApiResponse();
            } else {
                Snackbar snackbar = Snackbar.make(LLFollowingMain, getResources().getString(R.string.check_internet_connection), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        }

    }


    private void setClickListener() {

        SwipFollowingPage.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }


    public void CallMyFollowingApiResponse() {

        apiInterface = ApiClient.getClient(this).create(ApiInterface.class);
        Call<FollowingApiResponse> loginApiResponseCall = apiInterface.GetMyFollowingApi();
        loginApiResponseCall.enqueue(new Callback<FollowingApiResponse>() {
            @Override
            public void onResponse(Call<FollowingApiResponse> call, Response<FollowingApiResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        Functions.dialogHide();

                        if (SwipFollowingPage.isRefreshing()) {
                            SwipFollowingPage.setRefreshing(false);
                        }

                        if (response.body().getData().size() > 0) {


                            TvNoDataFound.setVisibility(View.GONE);
                            rvFollowingList.setVisibility(View.VISIBLE);

                            FollowingList = new ArrayList<>();
                            FollowingList.addAll(response.body().getData());

                            rvFollowingList.setLayoutManager(new LinearLayoutManager(MyFollowingActivity.this, RecyclerView.VERTICAL, false));
                            rvFollowingList.setAdapter(new MyFollowingAdapter(MyFollowingActivity.this, FollowingList));

                        } else {
                            TvNoDataFound.setVisibility(View.VISIBLE);
                            rvFollowingList.setVisibility(View.GONE);
                        }


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<FollowingApiResponse> call, Throwable t) {
                Functions.dialogHide();
            }
        });
    }


    public void CallMyFollowerApiResponse() {

        apiInterface = ApiClient.getClient(this).create(ApiInterface.class);
        Call<FollowingApiResponse> loginApiResponseCall = apiInterface.GetMyFollowerApi();
        loginApiResponseCall.enqueue(new Callback<FollowingApiResponse>() {
            @Override
            public void onResponse(Call<FollowingApiResponse> call, Response<FollowingApiResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        Functions.dialogHide();

                        if (SwipFollowingPage.isRefreshing()) {
                            SwipFollowingPage.setRefreshing(false);
                        }

                        if (response.body().getData().size() > 0) {


                            TvNoDataFound.setVisibility(View.GONE);
                            rvFollowingList.setVisibility(View.VISIBLE);

                            FollowingList = new ArrayList<>();
                            FollowingList.addAll(response.body().getData());

                            rvFollowingList.setLayoutManager(new LinearLayoutManager(MyFollowingActivity.this, RecyclerView.VERTICAL, false));
                            rvFollowingList.setAdapter(new MyFollowingAdapter(MyFollowingActivity.this, FollowingList));

                        } else {
                            TvNoDataFound.setVisibility(View.VISIBLE);
                            rvFollowingList.setVisibility(View.GONE);
                        }


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<FollowingApiResponse> call, Throwable t) {
                Functions.dialogHide();
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Functions.animBack(MyFollowingActivity.this);
    }

}