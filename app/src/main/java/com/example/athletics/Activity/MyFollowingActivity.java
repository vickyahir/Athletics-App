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
    private RecyclerView rvSearchList;
    private ApiInterface apiInterface;
    private ConnectionDetector cd;
    private LinearLayout LLFollowingMain, LLResultCount;
    private List<FollowingDataItem> FollowingList;
    private String Title = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_following);
        cd = new ConnectionDetector(MyFollowingActivity.this);

        getIntentData();
        initView();
        loadData();
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
        super.onResume();
    }

    private void initView() {

        tvTitle = findViewById(R.id.TvTitle);
        TvNoDataFound = findViewById(R.id.TvNodataFound);
        TvResult = findViewById(R.id.TvResult);
        toolbarMain = findViewById(R.id.toolbarMain);
        imgBack = toolbarMain.findViewById(R.id.imgBack);
        tvTitle.setText(Title);
        rvSearchList = findViewById(R.id.rvSearchList);
        LLFollowingMain = findViewById(R.id.LLFollowingMain);


    }


    public void loadData() {

        if (cd.isConnectingToInternet()) {
            Functions.dialogShow(MyFollowingActivity.this);
            CallMyFollowingApiResponse();
        } else {
            Snackbar snackbar = Snackbar.make(LLFollowingMain, getResources().getString(R.string.check_internet_connection), Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }


    private void setClickListener() {

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

                        if (response.body().getData().size() > 0) {


                            TvNoDataFound.setVisibility(View.GONE);
                            rvSearchList.setVisibility(View.VISIBLE);

                            FollowingList = new ArrayList<>();
                            FollowingList.addAll(response.body().getData());

                            rvSearchList.setLayoutManager(new LinearLayoutManager(MyFollowingActivity.this, RecyclerView.VERTICAL, false));
                            rvSearchList.setAdapter(new MyFollowingAdapter(MyFollowingActivity.this, FollowingList));

                        } else {
                            TvNoDataFound.setVisibility(View.VISIBLE);
                            rvSearchList.setVisibility(View.GONE);
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