package com.example.athletics.Activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.Athletics.R;
import com.example.athletics.Model.CoachProfileApiResponse;
import com.example.athletics.Model.GetAthleteFollowUnFollowResponse;
import com.example.athletics.Retrofit.ApiClient;
import com.example.athletics.Retrofit.ApiInterface;
import com.example.athletics.Utils.Functions;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoachProfileActivity extends BaseActivity {
    private ImageView imgBack, imgMenu, ImgProfile, ImgHome, iv_User, imgFollow;
    private Toolbar toolbarMain;
    private TextView TvTitle, Tv_Username;
    private TextView TvEmail, TvPosition, TvSports, TvCoachExperience;
    private String CoachId = "";
    private LinearLayout LLAthleteProfileMain, LLDownloadResume;
    private SwipeRefreshLayout SwipeCoachProfilePage;
    private DownloadManager dm;
    public static final int MY_PERMISSIONS_REQUEST_WRITE_STORAGE = 123;
    public boolean result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_profile);
        super.onCreateMenu();
        super.onMenuSelect(5);

        getIntentData();
        initView();
        loadData();
        setClickListener();
    }

    private void getIntentData() {
        try {
            CoachId = getIntent().getStringExtra("Id");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initView() {
        toolbarMain = findViewById(R.id.toolbarMain);
        imgBack = toolbarMain.findViewById(R.id.imgBack);
        imgMenu = toolbarMain.findViewById(R.id.imgMenu);
        TvTitle = toolbarMain.findViewById(R.id.TvTitle);
        ImgProfile = findViewById(R.id.ImgProfile);
        ImgHome = findViewById(R.id.ImgHome);
        iv_User = findViewById(R.id.iv_User);
        imgFollow = findViewById(R.id.imgFollow);
        Tv_Username = findViewById(R.id.Tv_Username);
        TvEmail = findViewById(R.id.TvEmail);
        TvSports = findViewById(R.id.TvSports);
        TvCoachExperience = findViewById(R.id.TvCoachExperience);
        TvPosition = findViewById(R.id.TvPosition);
        LLAthleteProfileMain = findViewById(R.id.LLAthleteProfileMain);
        LLDownloadResume = findViewById(R.id.LLDownloadResume);
        SwipeCoachProfilePage = (SwipeRefreshLayout) findViewById(R.id.SwipeCoachProfilePage);
        imgMenu.setVisibility(View.INVISIBLE);
        TvTitle.setText(getResources().getString(R.string.coach_profie));

        if (ContextCompat.checkSelfPermission(CoachProfileActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            checkPermission(CoachProfileActivity.this);
        }


    }

    private void loadData() {
        if (cd.isConnectingToInternet()) {
            Functions.dialogShow(CoachProfileActivity.this);
            LLAthleteProfileMain.setVisibility(View.GONE);
            CallMyFollowingApiResponse();
        } else {
            Snackbar snackbar = Snackbar.make(LLAthleteProfileMain, getResources().getString(R.string.check_internet_connection), Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Functions.animBack(CoachProfileActivity.this);
    }


    public void CallMyFollowingApiResponse() {

        apiInterface = ApiClient.getClient(this).create(ApiInterface.class);
        Call<CoachProfileApiResponse> loginApiResponseCall = apiInterface.GetCoachProfileApiResponse(CoachId);
        loginApiResponseCall.enqueue(new Callback<CoachProfileApiResponse>() {
            @Override
            public void onResponse(Call<CoachProfileApiResponse> call, Response<CoachProfileApiResponse> response) {
                try {
                    if (response.isSuccessful()) {

                        LLAthleteProfileMain.setVisibility(View.VISIBLE);
                        Functions.dialogHide();
                        if (SwipeCoachProfilePage.isRefreshing()) {
                            SwipeCoachProfilePage.setRefreshing(false);
                        }

                        Tv_Username.setText(response.body().getData().getName());
                        if (response.body().getData().getEmail() != null) {
                            TvEmail.setText(response.body().getData().getEmail());
                        } else {
                            TvEmail.setText(getResources().getString(R.string.not_available));
                        }
                        if (response.body().getData().getCoach().getDetails() != null) {
                            TvCoachExperience.setText(response.body().getData().getCoach().getDetails());
                        } else {
                            TvCoachExperience.setText(getResources().getString(R.string.not_available));
                        }

                        if (response.body().getData().getCoach().getPosition().size() > 0) {
                            TvPosition.setVisibility(View.VISIBLE);
                            String position = "";
                            for (int i = 0; i < response.body().getData().getCoach().getPosition().size(); i++) {
                                position = position + ", " + response.body().getData().getCoach().getPosition().get(i);
                                TvPosition.setText(position.substring(1));
                            }
                        } else {
                            TvPosition.setText(getResources().getString(R.string.not_available));

                        }


                        if (response.body().getData().getCoach().getSports().size() > 0) {
                            TvSports.setVisibility(View.VISIBLE);
                            String Sportname = "";
                            for (int i = 0; i < response.body().getData().getCoach().getSports().size(); i++) {
                                Sportname = Sportname + ", " + response.body().getData().getCoach().getSports().get(i);
                                TvSports.setText(Sportname.substring(1));
                            }
                        } else {
                            TvSports.setText(getResources().getString(R.string.not_available));
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<CoachProfileApiResponse> call, Throwable t) {
                Functions.dialogHide();
            }
        });
    }


    public boolean checkPermission(Activity activity) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(CoachProfileActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(CoachProfileActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(CoachProfileActivity.this);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("Write Storage permission is necessary to Download Resume!!!");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(CoachProfileActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_STORAGE);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions(CoachProfileActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_STORAGE);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkFolder();
                } else {
                    //code for deny
//                    checkAgain();


                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(CoachProfileActivity.this);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("Write Storage permission is necessary to Download Resume!!!");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();


                }
                break;
        }

    }

    public void checkFolder() {
//        String path = Environment.getExternalStorageDirectory() + "/" + Constant.DownloadFileName + "/";
//        String path = Environment.getExternalStorageDirectory().toString();
        // Create the parent path
//        File dir = new File(Environment.getExternalStorageDirectory(), "Athletic");
        File dir = new File(Environment.getExternalStorageDirectory() + "/Athlete/");
        if (!dir.exists()) {
            dir.mkdirs();
        }

    }


    public void CallMyFollowUnFollowApiResponse() {

        apiInterface = ApiClient.getClient(this).create(ApiInterface.class);
        Call<GetAthleteFollowUnFollowResponse> loginApiResponseCall = apiInterface.GetAthleteFollowUnFollow(CoachId);
        loginApiResponseCall.enqueue(new Callback<GetAthleteFollowUnFollowResponse>() {
            @Override
            public void onResponse(Call<GetAthleteFollowUnFollowResponse> call, Response<GetAthleteFollowUnFollowResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        Functions.dialogHide();


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<GetAthleteFollowUnFollowResponse> call, Throwable t) {
                Functions.dialogHide();
            }
        });
    }


    private void setClickListener() {


        LLDownloadResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkPermission(CoachProfileActivity.this)) {
                    try {

                        Toast.makeText(CoachProfileActivity.this, "Downloading...", Toast.LENGTH_SHORT).show();


                        checkFolder();
                        String filename = "http://www.africau.edu/images/default/sample.pdf";
                        filename = filename.substring(filename.lastIndexOf('/') + 1);

                        Uri uri = Uri.parse("http://www.africau.edu/images/default/sample.pdf");

                        dm = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                        DownloadManager.Request request = new DownloadManager.Request(uri);
                        request.setTitle(filename);
//                request.setMimeType("application/pdf");
//                request.allowScanningByMediaScanner();
//                request.setAllowedOverMetered(true);
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                        request.setDestinationInExternalFilesDir(CoachProfileActivity.this, Environment.getExternalStorageDirectory() + "/Athlete/", filename);
                        dm.enqueue(request);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });


        SwipeCoachProfilePage.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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


    }


}