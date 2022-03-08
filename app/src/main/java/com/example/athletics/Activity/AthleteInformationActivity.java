package com.example.athletics.Activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.Athletics.R;
import com.example.athletics.Adapter.AthleteSportPositionStateAdapter;
import com.example.athletics.Adapter.AthleteSportsAdapter;
import com.example.athletics.Adapter.PositionAdapter;
import com.example.athletics.Adapter.SportPositionAdapter;
import com.example.athletics.Model.AthleteCategoryPositionApiResponse;
import com.example.athletics.Model.AthleteCategoryPositionDataItem;
import com.example.athletics.Model.AthleteInformationApiResponse;
import com.example.athletics.Model.AthleteReqCategoriesItem;
import com.example.athletics.Model.AthleteReqCountriesItem;
import com.example.athletics.Model.AthleteReqDataResponse;
import com.example.athletics.Model.AthleteReqUniversityItem;
import com.example.athletics.Model.StateListApiResponse;
import com.example.athletics.Model.StateListDataItem;
import com.example.athletics.Retrofit.ApiClient;
import com.example.athletics.Retrofit.ApiInterface;
import com.example.athletics.Utils.Functions;
import com.example.athletics.Utils.SessionManager;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AthleteInformationActivity extends BaseActivity {
    private ImageView imgBack, imgMenu;
    private Toolbar toolbarMain;
    private TextView TvTitle, TvNodataFoundPositions, TvSave, TvProfilePic, TvProfileName, TvUploadVideo, TvVideoName;
    private LinearLayout LLAthleteInformationMain;
    private RelativeLayout RelCoachInformationMain;
    private RecyclerView rvAthleteSports, rvAthleteSportsPosition, rvAthleteState;
    private AthleteSportsAdapter AthleteSportsAdapter;
    private List<AthleteReqCategoriesItem> AthleteSportsList;
    private List<AthleteCategoryPositionDataItem> AthleteSportsPositionList;
    private SportPositionAdapter athleteSportPositionAdapter;
    private List<AthleteCategoryPositionDataItem> AthleteSportsPositionStateList;
    private AthleteSportPositionStateAdapter athleteSportPositionStateAdapter;

    private PositionAdapter positionAdapter;
    private List<String> DummyList;

    private AthleteInformationApiResponse athleteInformationApiResponse;

    private List<String> GenderList;

    private List<String> ScholasticYearList;
    private List<String> SportStateYearList;
    ArrayAdapter SportStateYearArrayAdapter;
    ArrayAdapter ScholasticYearArrayAdapter;

    private List<String> CountryList;
    private List<String> CountryIdList;

    private List<String> StateList;
    private List<String> StateIdList;

    private List<String> UniversityList;
    private List<String> UniversityId;

    private String[] permissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
    };

    private EditText edtSchool, edtGPA, edtTeamName, edtAge, edtSpeed, edtHeight, edtWeight, edtPayingWeight, edtMajor;

    String ImageUri = "", VideoUri = "", AthleteCategoryIds = "";
    private static final int SELECT_VIDEO = 200;
    private SwipeRefreshLayout SwipeCoachInformationPage;

    private Spinner spinnerUniversity, spinnerGender, spinnerScholasticSchool, spinnerSportStateYear,
            spinnerCountry, spinnerState;

    private String UniversityID = "", Gender = "", YearComplete = "", Year = "", CountryId = "", StateId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_athlete_information);
        super.onCreateMenu();
        super.onMenuSelect(5);

        getIntentData();
        initView();
        loadData();
        LoadSpinnerClick();
        setClickListener();
    }


    private void getIntentData() {
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initView() {
        toolbarMain = findViewById(R.id.toolbarMain);
        imgBack = toolbarMain.findViewById(R.id.imgBack);
        imgMenu = toolbarMain.findViewById(R.id.imgMenu);
        TvTitle = toolbarMain.findViewById(R.id.TvTitle);
        TvNodataFoundPositions = findViewById(R.id.TvNodataFoundPositions);
        TvSave = findViewById(R.id.TvSave);
        TvProfilePic = findViewById(R.id.TvProfilePic);
        TvProfileName = findViewById(R.id.TvProfileName);
        TvUploadVideo = findViewById(R.id.TvUploadVideo);
        spinnerUniversity = findViewById(R.id.spinnerUniversity);
        spinnerGender = findViewById(R.id.spinnerGender);
        spinnerScholasticSchool = findViewById(R.id.spinnerScholasticSchool);
        spinnerSportStateYear = findViewById(R.id.spinnerSportStateYear);
        spinnerCountry = findViewById(R.id.spinnerCountry);
        spinnerState = findViewById(R.id.spinnerState);
        edtSchool = findViewById(R.id.edtSchool);
        edtGPA = findViewById(R.id.edtGPA);
        edtTeamName = findViewById(R.id.edtTeamName);
        edtAge = findViewById(R.id.edtAge);
        edtSpeed = findViewById(R.id.edtSpeed);
        edtHeight = findViewById(R.id.edtHeight);
        edtWeight = findViewById(R.id.edtWeight);
        edtPayingWeight = findViewById(R.id.edtPayingWeight);
        edtMajor = findViewById(R.id.edtMajor);
        TvVideoName = findViewById(R.id.TvVideoName);
        LLAthleteInformationMain = findViewById(R.id.LLAthleteInformationMain);
        RelCoachInformationMain = findViewById(R.id.RelCoachInformationMain);
        rvAthleteSports = (RecyclerView) findViewById(R.id.rvAthleteSports);
        rvAthleteSportsPosition = (RecyclerView) findViewById(R.id.rvAthleteSportsPosition);
        rvAthleteState = (RecyclerView) findViewById(R.id.rvAthleteState);
        SwipeCoachInformationPage = (SwipeRefreshLayout) findViewById(R.id.SwipeCoachInformationPage);
        imgMenu.setVisibility(View.INVISIBLE);
        TvTitle.setText(getResources().getString(R.string.basic_information));


    }

    private void loadData() {
        if (cd.isConnectingToInternet()) {
            Functions.dialogShow(AthleteInformationActivity.this);
            LLAthleteInformationMain.setVisibility(View.GONE);
            CallCoachCategoryResponse();

        } else {
            Snackbar snackbar = Snackbar.make(RelCoachInformationMain, getResources().getString(R.string.check_internet_connection), Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Functions.animBack(AthleteInformationActivity.this);
    }


    public void CallCoachCategoryResponse() {

        apiInterface = ApiClient.getClient(this).create(ApiInterface.class);
        Call<AthleteReqDataResponse> loginApiResponseCall = apiInterface.GetAthleteReqDataApi();
        loginApiResponseCall.enqueue(new Callback<AthleteReqDataResponse>() {
            @Override
            public void onResponse(Call<AthleteReqDataResponse> call, Response<AthleteReqDataResponse> response) {
                try {
                    if (response.isSuccessful()) {

                        Functions.dialogHide();
                        LLAthleteInformationMain.setVisibility(View.VISIBLE);

                        if (SwipeCoachInformationPage.isRefreshing()) {
                            SwipeCoachInformationPage.setRefreshing(false);
                        }

                        SetGenderData();
                        SetYearData();
                        SportStateYearData();

                        if (response.body().getData().getUniversity() != null) {
                            SetUniversityData(response.body().getData().getUniversity());
                        }
                        if (response.body().getData().getCountries() != null) {
                            SetCountryData(response.body().getData().getCountries());
                        }


                        AthleteSportsList = new ArrayList<>();
                        if (response.body().getData().getCategories().size() > 0) {
                            rvAthleteSports.setVisibility(View.VISIBLE);
                            AthleteSportsList.addAll(response.body().getData().getCategories());
                            rvAthleteSports.setLayoutManager(new GridLayoutManager(AthleteInformationActivity.this, 3));
                            AthleteSportsAdapter = new AthleteSportsAdapter(AthleteInformationActivity.this, AthleteSportsList);
                            rvAthleteSports.setAdapter(AthleteSportsAdapter);
                        } else {
                            rvAthleteSports.setVisibility(View.GONE);
                        }

                        CallAthleteInformationApiResponse();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<AthleteReqDataResponse> call, Throwable t) {
                Functions.dialogHide();
            }
        });
    }

    private void SetCountryData(List<AthleteReqCountriesItem> countries) {

        CountryList = new ArrayList<>();
        CountryIdList = new ArrayList<>();

        for (int i = 0; i < countries.size(); i++) {
            CountryList.add(countries.get(i).getName());
            CountryIdList.add(String.valueOf(countries.get(i).getId()));
        }

        ArrayAdapter CountryArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, CountryList);
        CountryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCountry.setAdapter(CountryArrayAdapter);

    }

    private void SetYearData() {
        ScholasticYearList = new ArrayList<>();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int startYear = year - 2;
        int endYear = startYear + 17;

        for (int i = startYear; i <= endYear; i++) {
            ScholasticYearList.add(String.valueOf(startYear));
            startYear++;
        }

        ScholasticYearArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, ScholasticYearList);
        ScholasticYearArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerScholasticSchool.setAdapter(ScholasticYearArrayAdapter);
    }

    private void SportStateYearData() {
        SportStateYearList = new ArrayList<>();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int startYear = year - 2;
        int endYear = startYear + 17;

        for (int i = startYear; i <= endYear; i++) {
            SportStateYearList.add(String.valueOf(startYear));
            startYear++;
        }

        SportStateYearArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, SportStateYearList);
        SportStateYearArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSportStateYear.setAdapter(SportStateYearArrayAdapter);
    }

    private void SetGenderData() {

        GenderList = new ArrayList<>();
        GenderList.add("Male");
        GenderList.add("Female");
        GenderList.add("Other");


        ArrayAdapter GenderArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, GenderList);
        GenderArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(GenderArrayAdapter);
    }

    private void SetUniversityData(List<AthleteReqUniversityItem> university) {

        UniversityList = new ArrayList<>();
        UniversityId = new ArrayList<>();

        for (int i = 0; i < university.size(); i++) {
            UniversityList.add(university.get(i).getName());
            UniversityId.add(String.valueOf(university.get(i).getId()));
        }

        ArrayAdapter UniversiryArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, UniversityList);
        UniversiryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUniversity.setAdapter(UniversiryArrayAdapter);
    }

    public void CallAthleteInformationApiResponse() {

        apiInterface = ApiClient.getClient(this).create(ApiInterface.class);
        Call<AthleteInformationApiResponse> loginApiResponseCall = apiInterface.GetAthleteInformationApi();
        loginApiResponseCall.enqueue(new Callback<AthleteInformationApiResponse>() {
            @Override
            public void onResponse(Call<AthleteInformationApiResponse> call, Response<AthleteInformationApiResponse> response) {
                try {
                    if (response.isSuccessful()) {

//                        Functions.dialogHide();
//                        LLAthleteInformationMain.setVisibility(View.VISIBLE);

                        athleteInformationApiResponse = response.body();

                        if (!response.body().getData().getStateId().equalsIgnoreCase("")) {
                            CallStateFromCountryResponse(response.body().getData().getStateId());
                        }

                        List<String> AthleteSportsIDS = new ArrayList<>(response.body().getData().getCategoryId());
                        if (AthleteSportsIDS.size() > 0) {
                            AthleteCategoryIds = TextUtils.join(",", AthleteSportsIDS);
                        }
                        new SessionManager(AthleteInformationActivity.this).setKeyAthleteSportsids(AthleteCategoryIds);
                        if (!AthleteCategoryIds.equalsIgnoreCase("")) {
                            CallAthleteSportsCategoryApiResponse(AthleteCategoryIds);
                        }

                        AthleteSportsAdapter.notifyDataSetChanged();


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<AthleteInformationApiResponse> call, Throwable t) {
                Functions.dialogHide();
            }
        });
    }


    public void CallAthleteSportsCategoryApiResponse(String SportsIDS) {

        apiInterface = ApiClient.getClient(this).create(ApiInterface.class);
        Call<AthleteCategoryPositionApiResponse> loginApiResponseCall = apiInterface.GetCategoryPositionSportsApi(SportsIDS);
        loginApiResponseCall.enqueue(new Callback<AthleteCategoryPositionApiResponse>() {
            @Override
            public void onResponse(Call<AthleteCategoryPositionApiResponse> call, Response<AthleteCategoryPositionApiResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        Functions.dialogHide();

                        AthleteSportsPositionList = new ArrayList<>();
                        if (response.body().getData().size() > 0) {
                            rvAthleteSportsPosition.setVisibility(View.VISIBLE);
                            AthleteSportsPositionList.addAll(response.body().getData());
                            rvAthleteSportsPosition.setLayoutManager(new LinearLayoutManager(AthleteInformationActivity.this));
                            athleteSportPositionAdapter = new SportPositionAdapter(AthleteInformationActivity.this, AthleteSportsPositionList, athleteInformationApiResponse.getData().getPosition());
                            rvAthleteSportsPosition.setAdapter(athleteSportPositionAdapter);
                        } else {
                            rvAthleteSportsPosition.setVisibility(View.GONE);
                        }


                        AthleteSportsPositionStateList = new ArrayList<>();
                        if (response.body().getData().size() > 0) {
                            rvAthleteState.setVisibility(View.VISIBLE);
                            AthleteSportsPositionStateList.addAll(response.body().getData());
                            rvAthleteState.setLayoutManager(new LinearLayoutManager(AthleteInformationActivity.this));
                            athleteSportPositionStateAdapter = new AthleteSportPositionStateAdapter(AthleteInformationActivity.this, AthleteSportsPositionStateList, athleteInformationApiResponse.getData().getState());
                            rvAthleteState.setAdapter(athleteSportPositionStateAdapter);
                        } else {
                            rvAthleteState.setVisibility(View.GONE);
                        }


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<AthleteCategoryPositionApiResponse> call, Throwable t) {
                Functions.dialogHide();
            }
        });
    }

    public void SetAthleteInformationData(AthleteInformationApiResponse athleteInformationApiResponse) {

        edtSchool.setText(athleteInformationApiResponse.getData().getSchool());
        edtGPA.setText(athleteInformationApiResponse.getData().getGpa());
        edtTeamName.setText(athleteInformationApiResponse.getData().getTeam());
        edtAge.setText(athleteInformationApiResponse.getData().getAge());
        edtSpeed.setText(athleteInformationApiResponse.getData().getSpeed());
        edtHeight.setText(athleteInformationApiResponse.getData().getHeight());
        edtWeight.setText(athleteInformationApiResponse.getData().getWeight());
        edtPayingWeight.setText(athleteInformationApiResponse.getData().getPlayingWeight());
        edtMajor.setText(athleteInformationApiResponse.getData().getMajor());


        if (UniversityId != null) {
            for (int i = 0; i < UniversityId.size(); i++) {
                if (athleteInformationApiResponse.getData().getUniversityId().equalsIgnoreCase(UniversityId.get(i))) {
                    spinnerUniversity.setSelection(i);
                }
            }
        }


        if (CountryIdList != null) {
            for (int i = 0; i < CountryIdList.size(); i++) {
                if (athleteInformationApiResponse.getData().getCountryId().equalsIgnoreCase(CountryIdList.get(i))) {
                    spinnerCountry.setSelection(i);
                }
            }
        }

        if (SportStateYearList != null) {
            for (int i = 0; i < SportStateYearList.size(); i++) {
                if (athleteInformationApiResponse.getData().getYear().equalsIgnoreCase(SportStateYearList.get(i))) {
                    spinnerSportStateYear.setSelection(i);
                }
            }
        }

        if (ScholasticYearList != null) {
            for (int i = 0; i < ScholasticYearList.size(); i++) {
                if (athleteInformationApiResponse.getData().getYearComplete().equalsIgnoreCase(ScholasticYearList.get(i))) {
                    spinnerScholasticSchool.setSelection(i);
                }
            }
        }

        if (StateIdList != null) {
            for (int i = 0; i < StateIdList.size(); i++) {
                if (athleteInformationApiResponse.getData().getStateId().equalsIgnoreCase(StateIdList.get(i))) {
                    spinnerState.setSelection(i);
                }
            }
        }


    }

    private void LoadSpinnerClick() {
        spinnerUniversity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
                ((TextView) adapterView.getChildAt(0)).setTextSize(12);
//                Toast.makeText(activity, UniversityList.get(i), Toast.LENGTH_SHORT).show();
                UniversityID = UniversityId.get(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
                ((TextView) adapterView.getChildAt(0)).setTextSize(12);
//                Toast.makeText(activity, GenderList.get(i), Toast.LENGTH_SHORT).show();
                Gender = GenderList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerScholasticSchool.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
                ((TextView) adapterView.getChildAt(0)).setTextSize(12);
//                Toast.makeText(activity, ScholasticYearList.get(i), Toast.LENGTH_SHORT).show();
                YearComplete = ScholasticYearList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerSportStateYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
                ((TextView) adapterView.getChildAt(0)).setTextSize(12);
//                Toast.makeText(activity, SportStateYearList.get(i), Toast.LENGTH_SHORT).show();
                Year = SportStateYearList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
                ((TextView) adapterView.getChildAt(0)).setTextSize(12);
//                Toast.makeText(activity, CountryList.get(i), Toast.LENGTH_SHORT).show();
//                Functions.dialogShow(AthleteInformationActivity.this);
                CountryId = CountryIdList.get(i);
                CallStateFromCountryResponse(CountryIdList.get(i));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }


    private void setClickListener() {


        SwipeCoachInformationPage.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });


        TvProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!EasyPermissions.hasPermissions(AthleteInformationActivity.this, permissions)) {
                    EasyPermissions.requestPermissions(AthleteInformationActivity.this, getString(R.string.please_allow_app), 1, permissions);
                } else {

                    Intent intent = new Intent();
                    intent.setType("image/*");
                    //intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), 100);
                }
            }
        });


        TvUploadVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select a Video "), SELECT_VIDEO);

            }
        });


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        TvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                for (int i = 0; i < AthleteSportsList.size(); i++) {
//                    Toast.makeText(activity, "" + AthleteSportsList.get(i).isSelected(), Toast.LENGTH_SHORT).show();
//                }


//                Toast.makeText(activity, "" + athleteSportPositionAdapter.athletePositionAdapter.chkSports.isChecked(), Toast.LENGTH_SHORT).show();


                try {
                    JSONArray PositionArrayOneData = new JSONArray();

                    JSONObject JsonObjectPositionData = new JSONObject();

                    List<String> list = new ArrayList<String>(Arrays.asList(new SessionManager(AthleteInformationActivity.this).getKeyAthleteSportsids().split(",")));

                    for (int i = 0; i < list.size(); i++) {

                        JsonObjectPositionData.put("id", list.get(i));

                        JSONArray array = new JSONArray();
                        array.put("element_1");

                        JsonObjectPositionData.put("pos", array);
                        PositionArrayOneData.put(JsonObjectPositionData);
                    }


                    System.out.println("JsonArray>>>>>>>" + PositionArrayOneData.toString());
//                    Toast.makeText(activity, "" + PositionArrayOneData.toString(), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


//                if (cd.isConnectingToInternet()) {
//                    Functions.dialogShow(AthleteInformationActivity.this);
//                    CallCoachProfileUpdateApiResponse();
//                } else {
//                    Snackbar snackbar = Snackbar.make(RelCoachInformationMain, getResources().getString(R.string.check_internet_connection), Snackbar.LENGTH_LONG);
//                    snackbar.show();
//                }

            }
        });


    }

    public void CallStateFromCountryResponse(String position) {

        apiInterface = ApiClient.getClient(AthleteInformationActivity.this).create(ApiInterface.class);
        Call<StateListApiResponse> loginApiResponseCall = apiInterface.GetStateListApi(position);
        loginApiResponseCall.enqueue(new Callback<StateListApiResponse>() {
            @Override
            public void onResponse(Call<StateListApiResponse> call, Response<StateListApiResponse> response) {
                try {
                    if (response.isSuccessful()) {
//                        Functions.dialogHide();

                        SetStateData(response.body().getData());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<StateListApiResponse> call, Throwable t) {
//                Functions.dialogHide();
            }
        });
    }

    private void SetStateData(List<StateListDataItem> data) {

        StateList = new ArrayList<>();
        StateIdList = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            StateList.add(data.get(i).getName());
            StateIdList.add(String.valueOf(data.get(i).getId()));
        }

        ArrayAdapter StateArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, StateList);
        StateArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerState.setAdapter(StateArrayAdapter);

        spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
                ((TextView) adapterView.getChildAt(0)).setTextSize(12);
//                Toast.makeText(activity, StateList.get(i), Toast.LENGTH_SHORT).show();
                StateId = StateIdList.get(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        SetAthleteInformationData(athleteInformationApiResponse);

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        if (requestCode == 100 && resultCode == RESULT_OK && null != data) {
            try {
                ImageUri = getPath(AthleteInformationActivity.this, data.getData());
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(AthleteInformationActivity.this.getContentResolver(), data.getData());
                // ivProfilePic.setImageBitmap(bitmap);
                TvProfileName.setVisibility(View.VISIBLE);
                TvProfileName.setText(queryName(getContentResolver(), data.getData()));
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("Image Exception=", e.toString());
            }

        } else if (requestCode == SELECT_VIDEO && resultCode == RESULT_OK) {

            if (requestCode == SELECT_VIDEO) {

                System.out.println("SELECT_VIDEO");

                Uri selectedVideoUri = data.getData();
                VideoUri = getPath(AthleteInformationActivity.this, selectedVideoUri);


                TvVideoName.setVisibility(View.VISIBLE);
//                Uri VideofilePath = null;
//                VideofilePath = data.getData();
//                String filename = "VID_" + VideofilePath.toString().substring(VideofilePath.toString().lastIndexOf("/") + 1);
                TvVideoName.setText(queryName(getContentResolver(), data.getData()));
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private String queryName(ContentResolver resolver, Uri uri) {
        Cursor returnCursor =
                resolver.query(uri, null, null, null, null);
        assert returnCursor != null;
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        returnCursor.moveToFirst();
        String name = returnCursor.getString(nameIndex);
        returnCursor.close();
        return name;
    }


    public void CallCoachProfileUpdateApiResponse() {

        RequestBody position = RequestBody.create(MediaType.parse("multipart/form-data"), "");
        RequestBody state = RequestBody.create(MediaType.parse("multipart/form-data"), "");
        RequestBody university_id = RequestBody.create(MediaType.parse("multipart/form-data"), UniversityID);
        RequestBody state_id = RequestBody.create(MediaType.parse("multipart/form-data"), StateId);
        RequestBody country_id = RequestBody.create(MediaType.parse("multipart/form-data"), CountryId);
        RequestBody school = RequestBody.create(MediaType.parse("multipart/form-data"), edtSchool.getText().toString());
        RequestBody speed = RequestBody.create(MediaType.parse("multipart/form-data"), edtSpeed.getText().toString());
        RequestBody year_complete = RequestBody.create(MediaType.parse("multipart/form-data"), YearComplete);
        RequestBody major = RequestBody.create(MediaType.parse("multipart/form-data"), edtMajor.getText().toString());
        RequestBody gpa = RequestBody.create(MediaType.parse("multipart/form-data"), edtGPA.getText().toString());
        RequestBody age = RequestBody.create(MediaType.parse("multipart/form-data"), edtAge.getText().toString());
        RequestBody year = RequestBody.create(MediaType.parse("multipart/form-data"), Year);
        RequestBody height = RequestBody.create(MediaType.parse("multipart/form-data"), edtHeight.getText().toString());
        RequestBody team = RequestBody.create(MediaType.parse("multipart/form-data"), edtTeamName.getText().toString());
        RequestBody weight = RequestBody.create(MediaType.parse("multipart/form-data"), edtWeight.getText().toString());
        RequestBody playing_weight = RequestBody.create(MediaType.parse("multipart/form-data"), edtPayingWeight.getText().toString());
        RequestBody category_id = RequestBody.create(MediaType.parse("multipart/form-data"), new SessionManager(AthleteInformationActivity.this).getKeyAthleteSportsids());
        RequestBody references = RequestBody.create(MediaType.parse("multipart/form-data"), "");
        RequestBody gender = RequestBody.create(MediaType.parse("multipart/form-data"), Gender);

        MultipartBody.Part image = null;
        if (!ImageUri.equals("")) {
            File image_banner = new File(ImageUri);
            RequestBody surveyBody = RequestBody.create(MediaType.parse("*/*"), image_banner);
            image = MultipartBody.Part.createFormData("image", image_banner.getName(), surveyBody);
        }

        MultipartBody.Part profile_video = null;
        if (!VideoUri.equals("")) {
            File video_banner = new File(VideoUri);
            RequestBody surveyBody = RequestBody.create(MediaType.parse("*/*"), video_banner);
            profile_video = MultipartBody.Part.createFormData("profile_video", video_banner.getName(), surveyBody);
        }


        apiInterface = ApiClient.getClient(this).create(ApiInterface.class);
        Call<AthleteInformationApiResponse> loginApiResponseCall = apiInterface.AthleteProfileUpdateApiResponse(position, state, university_id, state_id, country_id
                , school, speed, year_complete, major, gpa, age, year, height, team, weight, playing_weight, category_id, references, gender, image, profile_video);
        loginApiResponseCall.enqueue(new Callback<AthleteInformationApiResponse>() {
            @Override
            public void onResponse(Call<AthleteInformationApiResponse> call, Response<AthleteInformationApiResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        Functions.dialogHide();
                        Toast.makeText(activity, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
//                        Snackbar snackbar = Snackbar.make(RelCoachInformationMain, response.body().getMsg(), Snackbar.LENGTH_LONG);
//                        snackbar.show();
                        Intent intent = new Intent(activity, MyProfileActivity.class);
                        startActivity(intent);
                        Functions.animNext(activity);
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<AthleteInformationApiResponse> call, Throwable t) {
                Functions.dialogHide();
            }
        });
    }

    public static String ltrim(String str, String trimchar) {
        if (trimchar.length() <= str.length()) {
            if (str.substring(0, trimchar.length()).equalsIgnoreCase(trimchar)) {
                str = str.substring(trimchar.length(), str.length());
            }
        }
        return str;
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getResumePath(final Context context, final Uri uri) {
        //check here to KITKAT or new version
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {

            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }

            //DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                String fileName = getFilePath(context, uri);
                if (fileName != null) {
                    return Environment.getExternalStorageDirectory().toString() + "/Download/" + fileName;
//                    return Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/" + fileName;
                }

                String id = DocumentsContract.getDocumentId(uri);
                if (id.startsWith("raw:")) {
                    id = id.replaceFirst("raw:", "");
                    File file = new File(id);
                    if (file.exists())
                        return id;
                }

                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getFilePath(context, contentUri);

            }

            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }


    public static String getFilePath(Context context, Uri uri) {

        Cursor cursor = null;
        final String[] projection = {
                MediaStore.MediaColumns.DISPLAY_NAME
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, null, null,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;

    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }


}