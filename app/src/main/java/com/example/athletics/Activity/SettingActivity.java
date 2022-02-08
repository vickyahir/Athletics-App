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


import com.example.Athletics.R;
import com.example.athletics.Utils.Functions;
import com.google.android.material.snackbar.Snackbar;

public class SettingActivity extends BaseActivity {
    private ImageView imgBack, imgMenu;
    private Toolbar toolbarMain;
    private TextView TvTitle;
    private LinearLayout LLLogout, LLCheckForUpdate, LLRateUs, LLPackageDetail, LLHelpSupport, LLAbout, LLLikeVideo, LLNotification, LLProfile;
    private RelativeLayout LLProfileMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
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
        LLLogout = findViewById(R.id.LLLogout);
        LLCheckForUpdate = findViewById(R.id.LLCheckForUpdate);
        LLPackageDetail = findViewById(R.id.LLPackageDetail);
        LLHelpSupport = findViewById(R.id.LLHelpSupport);
        LLAbout = findViewById(R.id.LLAbout);
        LLLikeVideo = findViewById(R.id.LLLikeVideo);
        LLNotification = findViewById(R.id.LLNotification);

        LLRateUs = findViewById(R.id.LLRateUs);
        LLProfile = findViewById(R.id.LLProfile);
        LLProfileMain = findViewById(R.id.LLProfileMain);

        TvTitle.setText(getResources().getString(R.string.settings));

    }

    private void loadData() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Functions.animBack(SettingActivity.this);
    }


    private void setClickListener() {

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

                Intent intent = new Intent(SettingActivity.this, FollowingActivity.class);
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
                Intent intent = new Intent(SettingActivity.this, SaveVideoActivity.class);
                startActivity(intent);
                Functions.animNext(SettingActivity.this);
            }
        });


        LLLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLogoutDialog();
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
        final Dialog builder = new Dialog(activity);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view1 = LayoutInflater.from(activity).inflate(R.layout.logout_dialog, null);

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

        builder.setCancelable(false);
        builder.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.dialog_round));
        // builder.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        builder.setContentView(view1);
        builder.show();


    }

}