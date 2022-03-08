package com.example.athletics.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Athletics.R;
import com.example.athletics.Adapter.AthleteSearchedAdapter;
import com.example.athletics.Adapter.CoachSearchedAdapter;
import com.example.athletics.Adapter.ExclusiveSportsAdapter;
import com.example.athletics.Adapter.SportsAdapter;
import com.example.athletics.Model.CoachCategoryResponse;
import com.example.athletics.Model.HomeAthleteApiResponse;
import com.example.athletics.Model.HomeAthleteDataItem;
import com.example.athletics.Model.HomeCoachApiResponse;
import com.example.athletics.Model.HomeCoachDataItem;
import com.example.athletics.Retrofit.ApiClient;
import com.example.athletics.Retrofit.ApiInterface;
import com.example.athletics.Utils.ConnectionDetector;
import com.example.athletics.Utils.Functions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchActivity extends AppCompatActivity {

    public Toolbar toolbarMain;
    private ImageView iv_Back, imgFilter;
    private TextView tvTitle, TvNoDataFound, TvResult, tvAthlete, tvCoach, TvExclusiveSportNodataFound, TvSportNodataFound;
    private RecyclerView rvSearchList, rvFilterSport, rvFilterExclusiveSport;
    private ApiInterface apiInterface;
    private ConnectionDetector cd;
    private LinearLayout LLSearchList, LLResultCount, LLSport, LLExclusiveSport;
    private SearchView searchView;
    private List<HomeAthleteDataItem> AthleteSearchCategory;
    private List<HomeCoachDataItem> CoachSearchCategpry;
    private List<String> SportsList;
    private List<String> ExclusiveSportsList;
    private BottomSheetDialog bottomSheetDialog;
    private String Title = "", SelectedTab = "Athlete";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        cd = new ConnectionDetector(SearchActivity.this);

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
        tvAthlete = findViewById(R.id.tvAthlete);
        tvCoach = findViewById(R.id.tvCoach);
        LLResultCount = findViewById(R.id.LLResultCount);
        toolbarMain = findViewById(R.id.toolbarMain);
        iv_Back = toolbarMain.findViewById(R.id.iv_Back);
        imgFilter = findViewById(R.id.imgFilter);
        tvTitle.setText(Title);
        rvSearchList = findViewById(R.id.rvSearchList);
        LLSearchList = findViewById(R.id.LLSearchList);
        searchView = (SearchView) findViewById(R.id.searchView);
        imgFilter.setVisibility(View.VISIBLE);


    }


    public void loadData() {
        TvNoDataFound.setVisibility(View.VISIBLE);
        rvSearchList.setVisibility(View.GONE);
        LLResultCount.setVisibility(View.GONE);

        if (cd.isConnectingToInternet()) {
            Functions.dialogShow(SearchActivity.this);
            CallCoachCategoryResponse();
        } else {
            Snackbar snackbar = Snackbar.make(LLSearchList, getResources().getString(R.string.check_internet_connection), Snackbar.LENGTH_LONG);
            snackbar.show();
        }


    }

    public void callAthleteListApi(String athleteQuery, String Category) {


        apiInterface = ApiClient.getClient(SearchActivity.this).create(ApiInterface.class);
        final Call<HomeAthleteApiResponse> loginApiResponseCall = apiInterface.AthleteSearchApiResponse("", athleteQuery, "world", Category);
        loginApiResponseCall.enqueue(new Callback<HomeAthleteApiResponse>() {
            @Override
            public void onResponse(Call<HomeAthleteApiResponse> call, Response<HomeAthleteApiResponse> response) {
                try {
                    if (response.isSuccessful()) {

//                        Functions.dialogHide();

                        if (response.body().getData().size() > 0) {
                            rvSearchList.setVisibility(View.VISIBLE);
                            TvNoDataFound.setVisibility(View.GONE);

                            AthleteSearchCategory = new ArrayList<>();

                            AthleteSearchCategory.addAll(response.body().getData());

                            rvSearchList.setLayoutManager(new LinearLayoutManager(SearchActivity.this, RecyclerView.VERTICAL, false));
                            rvSearchList.setAdapter(new AthleteSearchedAdapter(SearchActivity.this, AthleteSearchCategory));
                        } else {
                            rvSearchList.setVisibility(View.GONE);
                            TvNoDataFound.setVisibility(View.VISIBLE);
                        }


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<HomeAthleteApiResponse> call, Throwable t) {
//                Functions.dialogHide();
            }
        });
    }


    public void SearchFromBottomSheet(String Category) {
        bottomSheetDialog.dismiss();
        if (cd.isConnectingToInternet()) {
            if (SelectedTab.equalsIgnoreCase("Athlete")) {
                callAthleteListApi("", Category);
            } else {
                callHomeCoachListingDataApi("", Category);
            }
        } else {
            Snackbar snackbar = Snackbar.make(searchView, getResources().getString(R.string.check_internet_connection), Snackbar.LENGTH_LONG);
            snackbar.show();
        }


    }

    public void callHomeCoachListingDataApi(String CoachQuery, String Category) {

        apiInterface = ApiClient.getClient(SearchActivity.this).create(ApiInterface.class);
        final Call<HomeCoachApiResponse> loginApiResponseCall = apiInterface.CoachSearchApiResponse("", CoachQuery, "world", Category);
        loginApiResponseCall.enqueue(new Callback<HomeCoachApiResponse>() {
            @Override
            public void onResponse(Call<HomeCoachApiResponse> call, Response<HomeCoachApiResponse> response) {
                try {
                    if (response.isSuccessful()) {

//                        Functions.dialogHide();

                        if (response.body().getData().size() > 0) {
                            rvSearchList.setVisibility(View.VISIBLE);
                            TvNoDataFound.setVisibility(View.GONE);

                            CoachSearchCategpry = new ArrayList<>();

                            CoachSearchCategpry.addAll(response.body().getData());

                            rvSearchList.setLayoutManager(new LinearLayoutManager(SearchActivity.this, RecyclerView.VERTICAL, false));
                            rvSearchList.setAdapter(new CoachSearchedAdapter(SearchActivity.this, CoachSearchCategpry));
                        } else {
                            rvSearchList.setVisibility(View.GONE);
                            TvNoDataFound.setVisibility(View.VISIBLE);
                        }


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<HomeCoachApiResponse> call, Throwable t) {
//                Functions.dialogHide();
            }
        });
    }


    public void CallCoachCategoryResponse() {

        apiInterface = ApiClient.getClient(this).create(ApiInterface.class);
        Call<CoachCategoryResponse> loginApiResponseCall = apiInterface.GetCoachCategoryApi();
        loginApiResponseCall.enqueue(new Callback<CoachCategoryResponse>() {
            @Override
            public void onResponse(Call<CoachCategoryResponse> call, Response<CoachCategoryResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        Functions.dialogHide();

//                        if (SelectedTab.equalsIgnoreCase("Athlete")) {

                        SportsList = new ArrayList<>();
                        if (response.body().getData().size() > 0) {
                            for (int i = 0; i < response.body().getData().size(); i++) {
                                if (response.body().getData().get(i).getExclusive().equalsIgnoreCase("0")) {
                                    SportsList.add(response.body().getData().get(i).getName());
                                }
                            }

                        }

                        ExclusiveSportsList = new ArrayList<>();
                        if (response.body().getData().size() > 0) {

                            for (int i = 0; i < response.body().getData().size(); i++) {
                                if (response.body().getData().get(i).getExclusive().equalsIgnoreCase("1")) {
                                    ExclusiveSportsList.add(response.body().getData().get(i).getName());
                                }
                            }
                        }
//                        } else {
//
//
//                            SportsList = new ArrayList<>();
//                            if (response.body().getData().size() > 0) {
//                                for (int i = 0; i < response.body().getData().size(); i++) {
//                                    SportsList.add(response.body().getData().get(i).getName());
//                                }
//
//
//                            }
//
//                        }


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<CoachCategoryResponse> call, Throwable t) {
                Functions.dialogHide();
            }
        });
    }


    private void setClickListener() {

        imgFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bottomSheetDialog = new BottomSheetDialog(SearchActivity.this, R.style.BottomSheetTheme);
                bottomSheetDialog.setContentView(R.layout.dialog_filter);

                rvFilterSport = bottomSheetDialog.findViewById(R.id.rvFilterSport);
                rvFilterExclusiveSport = bottomSheetDialog.findViewById(R.id.rvFilterExclusiveSport);

                LLSport = bottomSheetDialog.findViewById(R.id.LLSport);
                LLExclusiveSport = bottomSheetDialog.findViewById(R.id.LLExclusiveSport);

//                if (SelectedTab.equalsIgnoreCase("Athlete")) {
//                    LLSport.setVisibility(View.VISIBLE);
//                    LLExclusiveSport.setVisibility(View.VISIBLE);
//                } else {
//                    LLSport.setVisibility(View.VISIBLE);
//                    LLExclusiveSport.setVisibility(View.GONE);
//                }

                if (cd.isConnectingToInternet()) {
//                    Functions.dialogShow(SearchActivity.this);
                    CallCoachCategoryResponse();
                } else {
                    Snackbar snackbar = Snackbar.make(LLSearchList, getResources().getString(R.string.check_internet_connection), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }


                TvExclusiveSportNodataFound = bottomSheetDialog.findViewById(R.id.TvExclusiveSportNodataFound);
                TvSportNodataFound = bottomSheetDialog.findViewById(R.id.TvSportNodataFound);

                if (SportsList.size() > 0) {
                    rvFilterSport.setVisibility(View.VISIBLE);
                    TvSportNodataFound.setVisibility(View.GONE);

                    rvFilterSport.setLayoutManager(new LinearLayoutManager(SearchActivity.this, RecyclerView.VERTICAL, false));
                    rvFilterSport.setAdapter(new SportsAdapter(SearchActivity.this, SportsList));
                } else {
                    rvFilterSport.setVisibility(View.GONE);
                    TvSportNodataFound.setVisibility(View.VISIBLE);
                }

                if (ExclusiveSportsList.size() > 0) {
                    rvFilterExclusiveSport.setVisibility(View.VISIBLE);
                    TvExclusiveSportNodataFound.setVisibility(View.GONE);

                    rvFilterExclusiveSport.setLayoutManager(new LinearLayoutManager(SearchActivity.this, RecyclerView.VERTICAL, false));
                    rvFilterExclusiveSport.setAdapter(new ExclusiveSportsAdapter(SearchActivity.this, ExclusiveSportsList));
                } else {
                    rvFilterExclusiveSport.setVisibility(View.GONE);
                    TvExclusiveSportNodataFound.setVisibility(View.VISIBLE);

                }

                bottomSheetDialog.show();


            }
        });

        tvAthlete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvAthlete.setTextColor(getResources().getColor(R.color.white));
                tvAthlete.setBackground(getResources().getDrawable(R.drawable.bg_tab_selected));

                tvCoach.setTextColor(getResources().getColor(R.color.black));
                tvCoach.setBackground(getResources().getDrawable(R.drawable.bg_tab_two));

                SelectedTab = "Athlete";
                if (!searchView.getQuery().toString().equalsIgnoreCase("")) {
                    if (cd.isConnectingToInternet()) {
                        callAthleteListApi(searchView.getQuery().toString(), "");
                    } else {
                        Snackbar snackbar = Snackbar.make(searchView, getResources().getString(R.string.check_internet_connection), Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                } else {
                    rvSearchList.setVisibility(View.GONE);
                    TvNoDataFound.setVisibility(View.VISIBLE);
                }

            }
        });

        tvCoach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tvCoach.setTextColor(getResources().getColor(R.color.white));
                tvCoach.setBackground(getResources().getDrawable(R.drawable.bg_tab_deselected));

                tvAthlete.setTextColor(getResources().getColor(R.color.black));
                tvAthlete.setBackground(getResources().getDrawable(R.drawable.bg_tab_one));

                SelectedTab = "Coach";
                if (!searchView.getQuery().toString().equalsIgnoreCase("")) {
                    if (cd.isConnectingToInternet()) {
                        callHomeCoachListingDataApi(searchView.getQuery().toString(), "");
                    } else {
                        Snackbar snackbar = Snackbar.make(searchView, getResources().getString(R.string.check_internet_connection), Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                } else {
                    rvSearchList.setVisibility(View.GONE);
                    TvNoDataFound.setVisibility(View.VISIBLE);
                }
            }
        });


        iv_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (cd.isConnectingToInternet()) {

                    if (!newText.trim().isEmpty()) {
//                        Functions.dialogShow(SearchActivity.this);
                        if (SelectedTab.equalsIgnoreCase("Athlete")) {
                            callAthleteListApi(newText.trim(), "");
                        } else {
                            callHomeCoachListingDataApi(newText.trim(), "");
                        }
                    } else {
                        TvNoDataFound.setVisibility(View.VISIBLE);
                        rvSearchList.setVisibility(View.GONE);
                        LLResultCount.setVisibility(View.GONE);
                    }
                } else {
                    Snackbar snackbar = Snackbar.make(searchView, getResources().getString(R.string.check_internet_connection), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }

                return false;
            }
        });


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Functions.animBack(SearchActivity.this);
    }

}