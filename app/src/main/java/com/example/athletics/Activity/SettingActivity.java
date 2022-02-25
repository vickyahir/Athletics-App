package com.example.athletics.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.example.Athletics.R;
import com.example.athletics.Model.SignInData;
import com.example.athletics.Retrofit.ApiClient;
import com.example.athletics.Retrofit.ApiInterface;
import com.example.athletics.Utils.Functions;
import com.example.athletics.Utils.SessionManager;
import com.google.android.material.snackbar.Snackbar;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingActivity extends BaseActivity {
    private ImageView imgBack, imgMenu;
    private Toolbar toolbarMain;
    private TextView TvTitle, Tv_Username, Tv_UserEmail, TvLogout;
    private LinearLayout LLLogout, LLCheckForUpdate, LLRateUs, LLPackageDetail,
            LLHelpSupport, LLAbout, LLLikeVideo, LLNotification, LLProfile, LLMain, LLProfileView;
    private RelativeLayout LLProfileMain;
    private CircleImageView iv_User;
    private SwipeRefreshLayout SwipeSettingPage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        super.onCreateMenu();
        super.onMenuSelect(2);

        initView();

        setClickListener();
    }

    @Override
    protected void onResume() {
        loadData();
        super.onResume();
    }

    private void initView() {
        toolbarMain = findViewById(R.id.toolbarMain);
        imgBack = toolbarMain.findViewById(R.id.imgBack);
        imgMenu = toolbarMain.findViewById(R.id.imgMenu);
        TvTitle = toolbarMain.findViewById(R.id.TvTitle);
        TvLogout = findViewById(R.id.TvLogout);
        LLLogout = findViewById(R.id.LLLogout);
        LLCheckForUpdate = findViewById(R.id.LLCheckForUpdate);
        LLPackageDetail = findViewById(R.id.LLPackageDetail);
        LLHelpSupport = findViewById(R.id.LLHelpSupport);
        LLAbout = findViewById(R.id.LLAbout);
        LLLikeVideo = findViewById(R.id.LLLikeVideo);
        LLNotification = findViewById(R.id.LLNotification);
        LLMain = findViewById(R.id.LLMain);
        LLProfileView = findViewById(R.id.LLProfileView);
        iv_User = findViewById(R.id.iv_User);
        Tv_Username = findViewById(R.id.Tv_Username);
        Tv_UserEmail = findViewById(R.id.Tv_UserEmail);
        SwipeSettingPage = (SwipeRefreshLayout) findViewById(R.id.SwipeSettingPage);
        LLRateUs = findViewById(R.id.LLRateUs);
        LLProfile = findViewById(R.id.LLProfile);
        LLProfileMain = findViewById(R.id.LLProfileMain);

        TvTitle.setText(getResources().getString(R.string.settings));

        if (new SessionManager(SettingActivity.this).getUserID().equalsIgnoreCase("")) {
            TvLogout.setText(getResources().getString(R.string.login));
            LLProfileView.setVisibility(View.GONE);
            LLPackageDetail.setVisibility(View.GONE);
        } else {
            TvLogout.setText(getResources().getString(R.string.logout));
            LLProfileView.setVisibility(View.VISIBLE);
            LLPackageDetail.setVisibility(View.VISIBLE);
        }

    }

    private void loadData() {

        if (!new SessionManager(SettingActivity.this).getUserID().equalsIgnoreCase("")) {
            if (cd.isConnectingToInternet()) {
                Functions.dialogShow(SettingActivity.this);
                callProfileApiResponse();
            } else {
                Snackbar snackbar = Snackbar.make(LLProfileMain, getResources().getString(R.string.check_internet_connection), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        } else {
            if (SwipeSettingPage.isRefreshing()) {
                SwipeSettingPage.setRefreshing(false);
            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Functions.animBack(SettingActivity.this);
    }

    public void callProfileApiResponse() {

        apiInterface = ApiClient.getClient(this).create(ApiInterface.class);
        Call<SignInData> loginApiResponseCall = apiInterface.GetProfileInfoApi();
        loginApiResponseCall.enqueue(new Callback<SignInData>() {
            @Override
            public void onResponse(Call<SignInData> call, Response<SignInData> response) {
                try {
                    if (response.isSuccessful()) {
                        Functions.dialogHide();

                        if (SwipeSettingPage.isRefreshing()) {
                            SwipeSettingPage.setRefreshing(false);
                        }

                        LLMain.setVisibility(View.VISIBLE);
                        Tv_Username.setText(response.body().getName());
                        Tv_UserEmail.setText(response.body().getEmail());

                        if (!response.body().getImage().equalsIgnoreCase("")) {
                            Glide.with(SettingActivity.this).load(response.body().getImage()).into(iv_User);

                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<SignInData> call, Throwable t) {
//                Functions.dialogHide();
            }
        });
    }


    private void setClickListener() {

        SwipeSettingPage.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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
                Toast.makeText(SettingActivity.this, "menu clicked !", Toast.LENGTH_SHORT).show();
            }
        });

        LLProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SettingActivity.this, EditProfileActivity.class);
                startActivity(intent);
                Functions.animNext(SettingActivity.this);

            }
        });


        LLPackageDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, SubscribePackageActivity.class);
                startActivity(intent);
                Functions.animNext(SettingActivity.this);
            }
        });

        LLLikeVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, LikeVideoActivity.class);
                intent.putExtra("Id", "");
                startActivity(intent);
                Functions.animNext(SettingActivity.this);
            }
        });


        LLLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (new SessionManager(SettingActivity.this).getUserID().equalsIgnoreCase("")) {
                    Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                    startActivity(intent);
                    Functions.animNext(SettingActivity.this);
                } else {
                    showLogoutDialog();
                }

            }
        });

        LLAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, WebviewActivity.class);
                intent.putExtra("Title", "About Us");
                startActivity(intent);
                Functions.animNext(SettingActivity.this);
            }
        });

        LLHelpSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, WebviewActivity.class);
                intent.putExtra("Title", "Help & Support");
                startActivity(intent);
                Functions.animNext(SettingActivity.this);
            }
        });

        LLNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, NotificationActivity.class);
                intent.putExtra("Title", "Help & Support");
                startActivity(intent);
                Functions.animNext(SettingActivity.this);
            }
        });

        LLRateUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String link = "https://play.google.com/store/apps/details?id=";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link + getPackageName())));

            }
        });

        LLCheckForUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String link = "https://play.google.com/store/apps/details?id=";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link + getPackageName())));

            }
        });
    }

    public void showLogoutDialog() {
        final Dialog builder = new Dialog(activity, R.style.Theme_Dialog);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view1 = LayoutInflater.from(activity).inflate(R.layout.dialog_logout, null);

        TextView tvDialogok = (TextView) view1.findViewById(R.id.tvDialogok);
        TextView tvDialogCancel = (TextView) view1.findViewById(R.id.tvDialogCancel);
        TextView tvDialogMessage = (TextView) view1.findViewById(R.id.tvDialogMessage);
        tvDialogMessage.setText(R.string.are_you_sure_you_want_to_logout);

        tvDialogok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cd.isConnectingToInternet()) {
                    builder.dismiss();
//                    callLogoutApi();

                    new SessionManager(activity).logoutUser();
                    Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                    startActivity(intent);
                    Functions.animNext(SettingActivity.this);
                } else {
                    Snackbar snackbar = Snackbar
                            .make(LLProfileMain, getResources().getString(R.string.check_internet_connection), Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }

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

}