package com.example.athletics.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.Athletics.R;
import com.example.athletics.Adapter.SubscribeAdapter;
import com.example.athletics.Model.PackageDetailAthleteItem;
import com.example.athletics.Model.SuitablePlanApiResponse;
import com.example.athletics.Model.SuitablePlanDataItem;
import com.example.athletics.Retrofit.ApiClient;
import com.example.athletics.Retrofit.ApiInterface;
import com.example.athletics.Utils.Functions;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubscribePackageActivity extends BaseActivity {
    private ImageView imgBack, imgMenu;
    private Toolbar toolbarMain;
    private TextView TvTitle, TvNodataFound, TvActicationCode;
    private RecyclerView rvSubscribePackage;
    private List<PackageDetailAthleteItem> SubscribeAthletePlan;
    private List<PackageDetailAthleteItem> SubscribeCoachPlan;
    private List<PackageDetailAthleteItem> SubscribeVisitotPlan;
    private List<PackageDetailAthleteItem> SubscribeSchoolClubPlan;
    private List<SuitablePlanDataItem> SuitablePlanList;
    private RelativeLayout RelSubscribePackageMain;
    private LinearLayout LLSubscriptionMain;
    private SwipeRefreshLayout SwipSubscribePackagePage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe_package);
        super.onCreateMenu();
        super.onMenuSelect(2);

        initView();
        loadData();
        setClickListener();
    }


    private void initView() {
        toolbarMain = findViewById(R.id.toolbarMain);
        imgBack = toolbarMain.findViewById(R.id.imgBack);
        imgMenu = toolbarMain.findViewById(R.id.imgMenu);
        TvTitle = toolbarMain.findViewById(R.id.TvTitle);
        TvNodataFound = findViewById(R.id.TvNodataFound);
        TvActicationCode = findViewById(R.id.TvActicationCode);
        LLSubscriptionMain = findViewById(R.id.LLSubscriptionMain);
        RelSubscribePackageMain = findViewById(R.id.RelSubscribePackageMain);
        rvSubscribePackage = (RecyclerView) findViewById(R.id.rvSubscribePackage);
        SwipSubscribePackagePage = (SwipeRefreshLayout) findViewById(R.id.SwipSubscribePackagePage);

        TvTitle.setText(getResources().getString(R.string.subscribe_package));

    }

    private void loadData() {
        if (cd.isConnectingToInternet()) {
            LLSubscriptionMain.setVisibility(View.GONE);
            Functions.dialogShow(SubscribePackageActivity.this);
            CallSuitablePlanApiResponse();

        } else {
            Snackbar snackbar = Snackbar.make(RelSubscribePackageMain, getResources().getString(R.string.check_internet_connection), Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }


    public void CallSuitablePlanApiResponse() {

        apiInterface = ApiClient.getClient(SubscribePackageActivity.this).create(ApiInterface.class);
        Call<SuitablePlanApiResponse> loginApiResponseCall = apiInterface.GetSuitablePlanPackageApi();
        loginApiResponseCall.enqueue(new Callback<SuitablePlanApiResponse>() {
            @Override
            public void onResponse(Call<SuitablePlanApiResponse> call, Response<SuitablePlanApiResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        Functions.dialogHide();
                        SuitablePlanList = new ArrayList<>();

                        if (SwipSubscribePackagePage.isRefreshing()) {
                            SwipSubscribePackagePage.setRefreshing(false);
                        }

                        LLSubscriptionMain.setVisibility(View.VISIBLE);

                        if (response.body().getData().size() > 0) {
                            rvSubscribePackage.setVisibility(View.VISIBLE);
                            TvNodataFound.setVisibility(View.GONE);

                            SuitablePlanList.addAll(response.body().getData());
                            rvSubscribePackage.setLayoutManager(new LinearLayoutManager(SubscribePackageActivity.this, LinearLayoutManager.HORIZONTAL, false));
                            rvSubscribePackage.setAdapter(new SubscribeAdapter(SubscribePackageActivity.this, SuitablePlanList));
                        } else {
                            rvSubscribePackage.setVisibility(View.GONE);
                            TvNodataFound.setVisibility(View.VISIBLE);
                        }


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<SuitablePlanApiResponse> call, Throwable t) {
//                Functions.dialogHide();
            }
        });
    }


//    public void CallPackageDetailsApiResponese() {
//
//        apiInterface = ApiClient.getClient(SubscribePackageActivity.this).create(ApiInterface.class);
//        Call<PackageDetailApiResponse> loginApiResponseCall = apiInterface.GetSubscriptionPackageApi();
//        loginApiResponseCall.enqueue(new Callback<PackageDetailApiResponse>() {
//            @Override
//            public void onResponse(Call<PackageDetailApiResponse> call, Response<PackageDetailApiResponse> response) {
//                try {
//                    if (response.isSuccessful()) {
//                        Functions.dialogHide();
//
//                        SubscribeAthletePlan = new ArrayList<>();
//                        SubscribeCoachPlan = new ArrayList<>();
//                        SubscribeVisitotPlan = new ArrayList<>();
//                        SubscribeSchoolClubPlan = new ArrayList<>();
//
//                        if (new SessionManager(SubscribePackageActivity.this).getUserRole().equalsIgnoreCase("1")) {
//
//
//                            if (response.body().getData().getVisitor().size() > 0) {
//                                rvSubscribePackage.setVisibility(View.VISIBLE);
//                                TvNodataFound.setVisibility(View.GONE);
//
//                                SubscribeVisitotPlan.addAll(response.body().getData().getVisitor());
//                                rvSubscribePackage.setLayoutManager(new LinearLayoutManager(SubscribePackageActivity.this, LinearLayoutManager.HORIZONTAL, false));
//                                rvSubscribePackage.setAdapter(new SubscribeAdapter(SubscribePackageActivity.this, SubscribeVisitotPlan));
//                            } else {
//                                rvSubscribePackage.setVisibility(View.GONE);
//                                TvNodataFound.setVisibility(View.VISIBLE);
//                            }
//
//
//                        } else if (new SessionManager(SubscribePackageActivity.this).getUserRole().equalsIgnoreCase("2")) {
//
//                            if (response.body().getData().getAthlete().size() > 0) {
//                                rvSubscribePackage.setVisibility(View.VISIBLE);
//                                TvNodataFound.setVisibility(View.GONE);
//
//                                SubscribeAthletePlan.addAll(response.body().getData().getAthlete());
//                                rvSubscribePackage.setLayoutManager(new LinearLayoutManager(SubscribePackageActivity.this, LinearLayoutManager.HORIZONTAL, false));
//                                rvSubscribePackage.setAdapter(new SubscribeAdapter(SubscribePackageActivity.this, SubscribeAthletePlan));
//
//                            } else {
//                                rvSubscribePackage.setVisibility(View.GONE);
//                                TvNodataFound.setVisibility(View.VISIBLE);
//                            }
//
//
//                        } else if (new SessionManager(SubscribePackageActivity.this).getUserRole().equalsIgnoreCase("3")) {
//
//                            if (response.body().getData().getSchool().size() > 0) {
//                                rvSubscribePackage.setVisibility(View.VISIBLE);
//                                TvNodataFound.setVisibility(View.GONE);
//
//                                SubscribeSchoolClubPlan.addAll(response.body().getData().getSchool());
//                                rvSubscribePackage.setLayoutManager(new LinearLayoutManager(SubscribePackageActivity.this, LinearLayoutManager.HORIZONTAL, false));
//                                rvSubscribePackage.setAdapter(new SubscribeAdapter(SubscribePackageActivity.this, SubscribeSchoolClubPlan));
//
//                            } else {
//                                rvSubscribePackage.setVisibility(View.GONE);
//                                TvNodataFound.setVisibility(View.VISIBLE);
//                            }
//
//
//                        } else if (new SessionManager(SubscribePackageActivity.this).getUserRole().equalsIgnoreCase("4")) {
//
//                            if (response.body().getData().getSchool().size() > 0) {
//                                rvSubscribePackage.setVisibility(View.VISIBLE);
//                                TvNodataFound.setVisibility(View.GONE);
//
//                                SubscribeCoachPlan.addAll(response.body().getData().getSchool());
//                                rvSubscribePackage.setLayoutManager(new LinearLayoutManager(SubscribePackageActivity.this, LinearLayoutManager.HORIZONTAL, false));
//                                rvSubscribePackage.setAdapter(new SubscribeAdapter(SubscribePackageActivity.this, SubscribeCoachPlan));
//
//                            } else {
//                                rvSubscribePackage.setVisibility(View.GONE);
//                                TvNodataFound.setVisibility(View.VISIBLE);
//                            }
//
//
//                        }
//
//
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<PackageDetailApiResponse> call, Throwable t) {
////                Functions.dialogHide();
//            }
//        });
//    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Functions.animBack(SubscribePackageActivity.this);
    }


    private void setClickListener() {

        TvActicationCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSubscriptionCodeDialog();
            }
        });


        SwipSubscribePackagePage.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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
                Toast.makeText(SubscribePackageActivity.this, "menu clicked !", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void showSubscriptionCodeDialog() {
        final Dialog builder = new Dialog(activity, R.style.Theme_Dialog);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view1 = LayoutInflater.from(activity).inflate(R.layout.dialog_subscription_activation, null);
//        Window window = builder.getWindow();
//        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);


        TextView tvDialogActivateNow = (TextView) view1.findViewById(R.id.tvDialogActivateNow);
        TextView TvDiaogDetails = (TextView) view1.findViewById(R.id.TvDiaogDetails);
        EditText edtActicationCode = (EditText) view1.findViewById(R.id.edtActicationCode);
        TvDiaogDetails.setText(getResources().getString(R.string.please_enter_your_unique_code_given_by_your_school_club));

        tvDialogActivateNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cd.isConnectingToInternet()) {

                    if (!edtActicationCode.getText().toString().equalsIgnoreCase("")) {
                        Functions.dialogShow(SubscribePackageActivity.this);
//                        builder.dismiss();
                        callGetValidationCodeApi(edtActicationCode.getText().toString(), builder);

                    } else {
                        Snackbar snackbar = Snackbar
                                .make(RelSubscribePackageMain, getResources().getString(R.string.Please_enter_your_actication_code), Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }


                } else {
                    Snackbar snackbar = Snackbar
                            .make(RelSubscribePackageMain, getResources().getString(R.string.check_internet_connection), Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }

            }
        });


        builder.setCancelable(true);
        builder.setCanceledOnTouchOutside(true);
        builder.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.dialog_round));
        // builder.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        builder.setContentView(view1);
        builder.show();


    }

    private void callGetValidationCodeApi(String ValidatingCode, Dialog builder) {

        apiInterface = ApiClient.getClient(SubscribePackageActivity.this).create(ApiInterface.class);
        final Call<ResponseBody> loginApiResponseCall = apiInterface.GetValidationCode(ValidatingCode);
        loginApiResponseCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful()) {
                        Functions.dialogHide();
                        String jsonString = response.body().string();
                        JSONObject jsonResult = new JSONObject(jsonString);
                        if (jsonResult.getBoolean("status")) {
                            builder.dismiss();

                            Intent intent = new Intent(activity, MyProfileActivity.class);
                            startActivity(intent);
                            Functions.animNext(activity);
                            finish();

                        } else {
                            Snackbar snackbar = Snackbar.make(RelSubscribePackageMain, jsonResult.getString("msg"), Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Functions.dialogHide();
            }
        });
    }


}