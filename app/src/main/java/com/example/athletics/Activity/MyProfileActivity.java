package com.example.athletics.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Athletics.R;
import com.example.athletics.Adapter.HomeVideoAdapter;
import com.example.athletics.Utils.Functions;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MyProfileActivity extends BaseActivity {
    private ImageView imgBack,  ImgProfile, ImgHome;
    private Toolbar toolbarMain;
    private TextView TvTitle, TvNodataFound;
    private RecyclerView rvProfileHome;
    private List<String> HomeVideoCategory;
    private LinearLayout FrmProfileBg;
    private LinearLayout LLVideoData, LLPersonalData, LLFollowing, LLFollower;
    private RelativeLayout RelFollowingMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);
        super.onCreateMenu();
        super.onMenuSelect(5);

        initView();
        loadData();
        setClickListener();
    }


    private void initView() {
        toolbarMain = findViewById(R.id.toolbarMain);
        imgBack = toolbarMain.findViewById(R.id.imgBack);
        TvTitle = toolbarMain.findViewById(R.id.TvTitle);
        rvProfileHome = (RecyclerView) findViewById(R.id.rvProfileHome);
        ImgProfile = findViewById(R.id.ImgProfile);
        ImgHome = findViewById(R.id.ImgHome);
        FrmProfileBg = findViewById(R.id.FrmProfileBg);
        LLVideoData = findViewById(R.id.LLVideoData);
        LLPersonalData = findViewById(R.id.LLPersonalData);
        LLFollowing = findViewById(R.id.LLFollowing);
        LLFollower = findViewById(R.id.LLFollower);
        TvNodataFound = findViewById(R.id.TvNodataFound);
        RelFollowingMain = findViewById(R.id.RelFollowingMain);
        TvTitle.setText(getResources().getString(R.string.profie));

    }

    private void loadData() {
        LoadCategoryData();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Functions.animBack(MyProfileActivity.this);
    }


    private void LoadCategoryData() {
        HomeVideoCategory = new ArrayList<>();
        HomeVideoCategory.add("Tom Willson");
        HomeVideoCategory.add("Wade Jone");
        HomeVideoCategory.add("Phane Ball");
        HomeVideoCategory.add("Jan Phame");
        HomeVideoCategory.add("Code Fox");
        HomeVideoCategory.add("Mark Henry");
        HomeVideoCategory.add("Mark Henry");
        HomeVideoCategory.add("Mark Henry");
//        ProductCategory.addAll(homePageResponse.getData().getCategory());
        rvProfileHome.setLayoutManager(new GridLayoutManager(MyProfileActivity.this, 2));
        rvProfileHome.setAdapter(new HomeVideoAdapter(MyProfileActivity.this, HomeVideoCategory));
    }


    private void setClickListener() {

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });




        ImgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FrmProfileBg.setBackground(getResources().getDrawable(R.drawable.profile_bg_one));
                ImgProfile.setImageDrawable(getResources().getDrawable(R.drawable.ic_interface_filled));
                ImgHome.setImageDrawable(getResources().getDrawable(R.drawable.ic_layout_home));

                LLPersonalData.setVisibility(View.VISIBLE);
                LLVideoData.setVisibility(View.GONE);

            }
        });

        ImgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FrmProfileBg.setBackground(getResources().getDrawable(R.drawable.profile_bg));
                ImgProfile.setImageDrawable(getResources().getDrawable(R.drawable.ic_interface));
                ImgHome.setImageDrawable(getResources().getDrawable(R.drawable.ic_layout_home_filled));

                LLPersonalData.setVisibility(View.GONE);
                LLVideoData.setVisibility(View.VISIBLE);

            }
        });


        LLFollower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyProfileActivity.this, MyFollowingActivity.class);
                intent.putExtra("Title", "Follower");
                startActivity(intent);
                Functions.animNext(MyProfileActivity.this);
            }
        });

        LLFollowing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyProfileActivity.this, MyFollowingActivity.class);
                intent.putExtra("Title", "Following");
                startActivity(intent);
                Functions.animNext(MyProfileActivity.this);
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
                    Intent intent = new Intent(MyProfileActivity.this, LoginActivity.class);
                    startActivity(intent);
                    Functions.animNext(MyProfileActivity.this);
                } else {
                    Snackbar snackbar = Snackbar
                            .make(RelFollowingMain, getResources().getString(R.string.check_internet_connection), Snackbar.LENGTH_SHORT);
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