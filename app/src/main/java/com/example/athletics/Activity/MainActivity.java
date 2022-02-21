package com.example.athletics.Activity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.example.Athletics.R;
import com.example.athletics.Adapter.HomeViewPagerAdapter;
import com.example.athletics.Model.HomeExploreApiResponse;
import com.example.athletics.Model.HomeExploreDataItem;
import com.example.athletics.Retrofit.ApiClient;
import com.example.athletics.Retrofit.ApiInterface;
import com.example.athletics.Utils.Functions;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {
    private ImageView imgSearch;
    private Toolbar toolbarMain;
    private List<HomeExploreDataItem> HomeExploreList;
    private ViewPager2 videoViewPager;
    private RelativeLayout relHomePageListing;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        super.onCreateMenu();
        super.onMenuSelect(1);

        initView();
        loadData();
        setClickListener();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initView() {
        toolbarMain = findViewById(R.id.toolbarMain);
        imgSearch = toolbarMain.findViewById(R.id.imgSearch);
        videoViewPager = findViewById(R.id.videoViewPager);
        relHomePageListing = findViewById(R.id.relHomePageListing);


    }

    private void loadData() {
        if (cd.isConnectingToInternet()) {
            Functions.dialogShow(MainActivity.this);
            callHomeExploreListingDataApi();
        } else {
            Snackbar snackbar = Snackbar.make(relHomePageListing, getResources().getString(R.string.check_internet_connection), Snackbar.LENGTH_LONG);
            snackbar.show();
        }

    }

    @Override
    public void onBackPressed() {
        showBackPressDialog();
    }

    private void setClickListener() {
    }


    public void showBackPressDialog() {

        final Dialog builder = new Dialog(MainActivity.this);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view1 = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_logout, null);

        TextView tvDialogok = (TextView) view1.findViewById(R.id.tvDialogok);
        TextView tvDialogCancel = (TextView) view1.findViewById(R.id.tvDialogCancel);
        TextView tvDialogMessage = (TextView) view1.findViewById(R.id.tvDialogMessage);
        tvDialogMessage.setText(R.string.are_you_sure_you_want_to_exit);

        tvDialogok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
                finishAffinity();
            }
        });


        tvDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });


        builder.setCancelable(false);
        builder.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.dialog_round));
        // builder.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        builder.setContentView(view1);
        builder.show();


    }


    private void callHomeExploreListingDataApi() {

        apiInterface = ApiClient.getClient(MainActivity.this).create(ApiInterface.class);
        final Call<HomeExploreApiResponse> loginApiResponseCall = apiInterface.HomeExploreApiResponse();
        loginApiResponseCall.enqueue(new Callback<HomeExploreApiResponse>() {
            @Override
            public void onResponse(Call<HomeExploreApiResponse> call, Response<HomeExploreApiResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        Functions.dialogHide();

                        HomeExploreList = new ArrayList<>();
                        HomeExploreList.addAll(response.body().getData());

                        videoViewPager.setAdapter(new HomeViewPagerAdapter(MainActivity.this, HomeExploreList));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<HomeExploreApiResponse> call, Throwable t) {
                Functions.dialogHide();
            }
        });
    }


}