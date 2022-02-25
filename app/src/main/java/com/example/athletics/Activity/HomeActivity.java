package com.example.athletics.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.example.Athletics.R;
import com.example.athletics.Adapter.HomeAthleteCategoryAdapter;
import com.example.athletics.Adapter.HomeCoachCategoryAdapter;
import com.example.athletics.Adapter.HomeExploreCategoryAdapter;
import com.example.athletics.Adapter.ProductCategoryAdapter;
import com.example.athletics.Model.HomeAthleteApiResponse;
import com.example.athletics.Model.HomeAthleteDataItem;
import com.example.athletics.Model.HomeCoachApiResponse;
import com.example.athletics.Model.HomeCoachDataItem;
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

public class HomeActivity extends BaseActivity {
    private ImageView imgSearch;
    private Toolbar toolbarMain;
    private RecyclerView rvAthleteData, rvExploreData, rvCoachData, rvAthlete;
    private List<HomeExploreDataItem> HomeExploreList;
    private List<HomeAthleteDataItem> HomeAthleteList;
    private List<HomeCoachDataItem> HomeCoachList;
    private List<HomeAthleteDataItem> AthleticsUserList;
    private TextView tvExplore, tvAthletics, tvCoaches, TvNodataFound;
    private SwipeRefreshLayout SwipeHomePage;
    private RelativeLayout relHomePageListing;
    private LinearLayout LLHomeMain;
    private String MenuClicked = "Explore";
    public static final int MY_PERMISSIONS_REQUEST_WRITE_STORAGE = 123;
    public boolean result;
    public HomeExploreCategoryAdapter homeExploreCategoryAdapter;
    public HomeExploreApiResponse homeExploreApiResponse;
    private ViewPager2 videoViewPager2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
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
        LLHomeMain = findViewById(R.id.LLHomeMain);
        imgSearch = toolbarMain.findViewById(R.id.imgSearch);
//        rvAthlete = (RecyclerView) findViewById(R.id.rvAthlete);
//        rvAthleteData = (RecyclerView) findViewById(R.id.rvAthleteData);
//        rvExploreData = (RecyclerView) findViewById(R.id.rvExploreData);
//        rvCoachData = (RecyclerView) findViewById(R.id.rvCoachData);
        relHomePageListing = findViewById(R.id.relHomePageListing);
        tvExplore = toolbarMain.findViewById(R.id.tvExplore);
        tvAthletics = toolbarMain.findViewById(R.id.tvAthletics);
        tvCoaches = toolbarMain.findViewById(R.id.tvCoaches);
        TvNodataFound = findViewById(R.id.TvNodataFound);
        videoViewPager2 = findViewById(R.id.videoViewPager2);

        SwipeHomePage = (SwipeRefreshLayout) findViewById(R.id.SwipeHomePage);


    }

    private void loadData() {
        if (cd.isConnectingToInternet()) {

            Functions.dialogShow(HomeActivity.this);
            LLHomeMain.setVisibility(View.GONE);

//            callAthleteListApi();

            if (MenuClicked.equalsIgnoreCase("Explore")) {
                callHomeExploreListingDataApi();
            }
            if (MenuClicked.equalsIgnoreCase("Athlete")) {
                callHomeAthleteListingDataApi();
            }
            if (MenuClicked.equalsIgnoreCase("Coach")) {
                callHomeCoachListingDataApi();
            }


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


        SwipeHomePage.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });

        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                intent.putExtra("Title", "Search");
                startActivity(intent);
                Functions.animNext(HomeActivity.this);
            }
        });


        tvExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tvExplore.setBackground(getResources().getDrawable(R.drawable.ic_category_bg_selected));
                tvExplore.setTextColor(getResources().getColor(R.color.white));
                tvAthletics.setBackground(getResources().getDrawable(R.drawable.ic_category_bg));
                tvCoaches.setBackground(getResources().getDrawable(R.drawable.ic_category_bg));
                tvAthletics.setTextColor(getResources().getColor(R.color.black));
                tvCoaches.setTextColor(getResources().getColor(R.color.black));


                if (cd.isConnectingToInternet()) {
                    Functions.dialogShow(HomeActivity.this);
                    callHomeExploreListingDataApi();

//                    rvAthleteData.setVisibility(View.GONE);
//                    rvCoachData.setVisibility(View.GONE);

                } else {
                    Snackbar snackbar = Snackbar.make(relHomePageListing, getResources().getString(R.string.check_internet_connection), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }

                MenuClicked = "Explore";

            }
        });

        tvAthletics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvAthletics.setBackground(getResources().getDrawable(R.drawable.ic_category_bg_selected));
                tvAthletics.setTextColor(getResources().getColor(R.color.white));
                tvExplore.setBackground(getResources().getDrawable(R.drawable.ic_category_bg));
                tvCoaches.setBackground(getResources().getDrawable(R.drawable.ic_category_bg));
                tvExplore.setTextColor(getResources().getColor(R.color.black));
                tvCoaches.setTextColor(getResources().getColor(R.color.black));

                if (cd.isConnectingToInternet()) {
                    Functions.dialogShow(HomeActivity.this);
                    callHomeAthleteListingDataApi();
//                    rvExploreData.setVisibility(View.GONE);
//                    rvCoachData.setVisibility(View.GONE);
                } else {
                    Snackbar snackbar = Snackbar.make(relHomePageListing, getResources().getString(R.string.check_internet_connection), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                MenuClicked = "Athlete";
            }
        });

        tvCoaches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvCoaches.setBackground(getResources().getDrawable(R.drawable.ic_category_bg_selected));
                tvCoaches.setTextColor(getResources().getColor(R.color.white));
                tvAthletics.setBackground(getResources().getDrawable(R.drawable.ic_category_bg));
                tvExplore.setBackground(getResources().getDrawable(R.drawable.ic_category_bg));
                tvExplore.setTextColor(getResources().getColor(R.color.black));
                tvAthletics.setTextColor(getResources().getColor(R.color.black));

                if (cd.isConnectingToInternet()) {
                    Functions.dialogShow(HomeActivity.this);
                    callHomeCoachListingDataApi();

//                    rvAthleteData.setVisibility(View.GONE);
//                    rvExploreData.setVisibility(View.GONE);

                } else {
                    Snackbar snackbar = Snackbar.make(relHomePageListing, getResources().getString(R.string.check_internet_connection), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }


                MenuClicked = "Coach";
            }
        });

    }


    public void showBackPressDialog() {

        final Dialog builder = new Dialog(HomeActivity.this, R.style.Theme_Dialog);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view1 = LayoutInflater.from(HomeActivity.this).inflate(R.layout.dialog_logout, null);

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


        builder.setCancelable(true);
        builder.setCanceledOnTouchOutside(true);
        builder.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.dialog_round));
        // builder.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        builder.setContentView(view1);
        builder.show();


    }


    private void callAthleteListApi() {

        apiInterface = ApiClient.getClient(HomeActivity.this).create(ApiInterface.class);
        final Call<HomeAthleteApiResponse> loginApiResponseCall = apiInterface.HomeAthleteApiResponse("");
        loginApiResponseCall.enqueue(new Callback<HomeAthleteApiResponse>() {
            @Override
            public void onResponse(Call<HomeAthleteApiResponse> call, Response<HomeAthleteApiResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        Functions.dialogHide();

                        if (SwipeHomePage.isRefreshing()) {
                            SwipeHomePage.setRefreshing(false);
                        }

                        if (response.body().getData().size() > 0) {
                            rvAthlete.setVisibility(View.VISIBLE);

                            AthleticsUserList = new ArrayList<>();
                            AthleticsUserList.addAll(response.body().getData());
                            rvAthlete.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false));
                            rvAthlete.setAdapter(new ProductCategoryAdapter(activity, AthleticsUserList));
                        } else {

                            rvAthlete.setVisibility(View.GONE);
                        }


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<HomeAthleteApiResponse> call, Throwable t) {
                Functions.dialogHide();
            }
        });
    }


    private void callHomeAthleteListingDataApi() {

        apiInterface = ApiClient.getClient(HomeActivity.this).create(ApiInterface.class);
        final Call<HomeAthleteApiResponse> loginApiResponseCall = apiInterface.HomeAthleteApiResponse("");
        loginApiResponseCall.enqueue(new Callback<HomeAthleteApiResponse>() {
            @Override
            public void onResponse(Call<HomeAthleteApiResponse> call, Response<HomeAthleteApiResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        Functions.dialogHide();

                        LLHomeMain.setVisibility(View.VISIBLE);

                        if (SwipeHomePage.isRefreshing()) {
                            SwipeHomePage.setRefreshing(false);
                        }


                        if (response.body().getData().size() > 0) {
                            TvNodataFound.setVisibility(View.GONE);
//                            rvAthleteData.setVisibility(View.VISIBLE);

                            HomeAthleteList = new ArrayList<>();
                            HomeAthleteList.addAll(response.body().getData());
//
//                            rvAthleteData.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.VERTICAL, false));
//                            rvAthleteData.setAdapter(new HomeAthleteCategoryAdapter(activity, HomeAthleteList));

                            videoViewPager2.setAdapter(new HomeAthleteCategoryAdapter(HomeActivity.this, HomeAthleteList));


                        } else {
//                            rvAthleteData.setVisibility(View.GONE);
                            TvNodataFound.setVisibility(View.VISIBLE);
                        }


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<HomeAthleteApiResponse> call, Throwable t) {
                Functions.dialogHide();
            }
        });
    }


    private void callHomeExploreListingDataApi() {

        apiInterface = ApiClient.getClient(HomeActivity.this).create(ApiInterface.class);
        final Call<HomeExploreApiResponse> loginApiResponseCall = apiInterface.HomeExploreApiResponse();
        loginApiResponseCall.enqueue(new Callback<HomeExploreApiResponse>() {
            @Override
            public void onResponse(Call<HomeExploreApiResponse> call, Response<HomeExploreApiResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        Functions.dialogHide();

                        LLHomeMain.setVisibility(View.VISIBLE);

                        if (SwipeHomePage.isRefreshing()) {
                            SwipeHomePage.setRefreshing(false);
                        }

                        homeExploreApiResponse = response.body();

                        if (response.body().getData().size() > 0) {
                            TvNodataFound.setVisibility(View.GONE);
//                            rvExploreData.setVisibility(View.VISIBLE);

                            HomeExploreList = new ArrayList<>();
                            HomeExploreList.addAll(response.body().getData());

//                            rvExploreData.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.VERTICAL, false));

//                            homeExploreCategoryAdapter = new HomeExploreCategoryAdapter(activity, HomeExploreList);
//                            rvExploreData.setAdapter(homeExploreCategoryAdapter);


                            homeExploreCategoryAdapter = new HomeExploreCategoryAdapter(activity, HomeExploreList);
//                            videoViewPager2.setAdapter(new HomeExploreCategoryAdapter(HomeActivity.this, HomeExploreList));
                            videoViewPager2.setAdapter(homeExploreCategoryAdapter);

//                            Content content = new Content();
//                            content.execute();

                        } else {
//                            rvExploreData.setVisibility(View.GONE);
                            TvNodataFound.setVisibility(View.VISIBLE);
                        }


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


    private void callHomeCoachListingDataApi() {

        apiInterface = ApiClient.getClient(HomeActivity.this).create(ApiInterface.class);
        final Call<HomeCoachApiResponse> loginApiResponseCall = apiInterface.HomeCoachApiResponse();
        loginApiResponseCall.enqueue(new Callback<HomeCoachApiResponse>() {
            @Override
            public void onResponse(Call<HomeCoachApiResponse> call, Response<HomeCoachApiResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        Functions.dialogHide();

                        LLHomeMain.setVisibility(View.VISIBLE);

                        if (SwipeHomePage.isRefreshing()) {
                            SwipeHomePage.setRefreshing(false);
                        }


                        if (response.body().getData().size() > 0) {
                            TvNodataFound.setVisibility(View.GONE);
//                            rvCoachData.setVisibility(View.VISIBLE);

                            HomeCoachList = new ArrayList<>();
                            HomeCoachList.addAll(response.body().getData());

//                            rvCoachData.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.VERTICAL, false));
//                            rvCoachData.setAdapter(new HomeCoachCategoryAdapter(activity, HomeCoachList));

                            videoViewPager2.setAdapter(new HomeCoachCategoryAdapter(HomeActivity.this, HomeCoachList));

                        } else {
//                            rvCoachData.setVisibility(View.GONE);
                            TvNodataFound.setVisibility(View.VISIBLE);
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<HomeCoachApiResponse> call, Throwable t) {
                Functions.dialogHide();
            }
        });
    }


    private class Content extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected void onPostExecute(Void avoid) {
            super.onPostExecute(avoid);
            homeExploreCategoryAdapter.notifyDataSetChanged();
        }


        @Override
        protected Void doInBackground(Void... voids) {

            try {


            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}