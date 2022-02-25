package com.example.athletics.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.example.Athletics.R;
import com.example.athletics.Model.FollowingApiResponse;
import com.example.athletics.Model.SignInData;
import com.example.athletics.Model.UserLikeVideoApiResponse;
import com.example.athletics.Retrofit.ApiClient;
import com.example.athletics.Retrofit.ApiInterface;
import com.example.athletics.Utils.Functions;
import com.example.athletics.Utils.SessionManager;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyProfileActivity extends BaseActivity {
    private ImageView imgBack, ImgProfile, ImgHome, iv_User;
    private Toolbar toolbarMain;
    private TextView TvTitle, TvNodataFound, Tv_Username, Tv_UserType, Tv_UserEmail, TvFollower, TvFollowing, TvLikeVideo;
    private RecyclerView rvProfileHome;
    private List<String> HomeVideoCategory;
    private LinearLayout FrmProfileBg;
    private LinearLayout LLVideoData, LLPersonalData, LLFollowing, LLFollower,
            LLProfile, LLLikeVideo, LLProfileMenu, LLLikeVideoMenu, LLPaymentMenu, LLMyVideoMenu;
    private RelativeLayout RelMyProfileMain;
    private SwipeRefreshLayout SwipeProfilePage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);
        super.onCreateMenu();
        super.onMenuSelect(5);

        initView();
        setClickListener();
    }


    private void initView() {
        toolbarMain = findViewById(R.id.toolbarMain);
        imgBack = toolbarMain.findViewById(R.id.imgBack);
        TvTitle = toolbarMain.findViewById(R.id.TvTitle);
        rvProfileHome = (RecyclerView) findViewById(R.id.rvProfileHome);
        ImgProfile = findViewById(R.id.ImgProfile);
        iv_User = findViewById(R.id.iv_User);
        ImgHome = findViewById(R.id.ImgHome);
        FrmProfileBg = findViewById(R.id.FrmProfileBg);
        LLVideoData = findViewById(R.id.LLVideoData);
        LLPersonalData = findViewById(R.id.LLPersonalData);
        LLFollowing = findViewById(R.id.LLFollowing);
        LLFollower = findViewById(R.id.LLFollower);
        TvNodataFound = findViewById(R.id.TvNodataFound);
        LLProfile = findViewById(R.id.LLProfile);
        LLLikeVideo = findViewById(R.id.LLLikeVideo);
        LLProfileMenu = findViewById(R.id.LLProfileMenu);
        LLLikeVideoMenu = findViewById(R.id.LLLikeVideoMenu);
        LLPaymentMenu = findViewById(R.id.LLPaymentMenu);
        LLMyVideoMenu = findViewById(R.id.LLMyVideoMenu);
        Tv_UserType = findViewById(R.id.Tv_UserType);
        Tv_Username = findViewById(R.id.Tv_Username);
        Tv_UserEmail = findViewById(R.id.Tv_UserEmail);
        TvFollower = findViewById(R.id.TvFollower);
        TvFollowing = findViewById(R.id.TvFollowing);
        TvLikeVideo = findViewById(R.id.TvLikeVideo);
        RelMyProfileMain = findViewById(R.id.RelMyProfileMain);
        SwipeProfilePage = (SwipeRefreshLayout) findViewById(R.id.SwipeProfilePage);
        TvTitle.setText(getResources().getString(R.string.profie));

    }

    @Override
    protected void onResume() {
        if (new SessionManager(MyProfileActivity.this).getUserID().equalsIgnoreCase("")) {
            LoginAlertDialog();
        } else {
            loadData();
        }
        super.onResume();
    }

    private void loadData() {
        if (cd.isConnectingToInternet()) {
            LLProfile.setVisibility(View.GONE);
            Functions.dialogShow(MyProfileActivity.this);
            callProfileApiResponse();
            CallMyFollowingApiResponse();
            CallMyFollowerApiResponse();
            callLikeVideoApiResponse();
        } else {
            Snackbar snackbar1 = Snackbar.make(RelMyProfileMain, getResources().getString(R.string.check_internet_connection), Snackbar.LENGTH_LONG);
            snackbar1.show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Functions.animBack(MyProfileActivity.this);
    }


    public void LoginAlertDialog() {
        final Dialog builder = new Dialog(activity, R.style.Theme_Dialog);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view1 = LayoutInflater.from(activity).inflate(R.layout.dialog_customise, null);

        TextView tvDialogok = (TextView) view1.findViewById(R.id.tvDialogok);
        TextView tvDialogMessage = (TextView) view1.findViewById(R.id.tvDialogMessage);
        TextView tvDialogCancel = (TextView) view1.findViewById(R.id.tvDialogCancel);
        tvDialogMessage.setText(R.string.please_login_first_to_continue_in_app);

        tvDialogok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyProfileActivity.this, LoginActivity.class);
                startActivity(intent);
                Functions.animNext(MyProfileActivity.this);
            }
        });

        tvDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        builder.setCancelable(true);
        builder.setCanceledOnTouchOutside(true);
        builder.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.dialog_round));
        // builder.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        builder.setContentView(view1);
        builder.show();


    }


    public void callProfileApiResponse() {

        apiInterface = ApiClient.getClient(this).create(ApiInterface.class);
        Call<SignInData> loginApiResponseCall = apiInterface.GetProfileInfoApi();
        loginApiResponseCall.enqueue(new Callback<SignInData>() {
            @Override
            public void onResponse(Call<SignInData> call, Response<SignInData> response) {
                try {
                    if (response.isSuccessful()) {

                        if (SwipeProfilePage.isRefreshing()) {
                            SwipeProfilePage.setRefreshing(false);
                        }

                        Tv_Username.setText(response.body().getName());
                        Tv_UserEmail.setText(response.body().getEmail());

                        if (response.body().getRole() == 1) {
                            Tv_UserType.setText(getResources().getString(R.string.visitor));

                        } else if (response.body().getRole() == 2) {
                            Tv_UserType.setText(getResources().getString(R.string.athlete));

                        } else if (response.body().getRole() == 3) {
                            Tv_UserType.setText(getResources().getString(R.string.school_club));

                        } else if (response.body().getRole() == 4) {
                            Tv_UserType.setText(getResources().getString(R.string.coach));

                        }

                        if (!response.body().getImage().equalsIgnoreCase("")) {
                            Glide.with(MyProfileActivity.this).load(response.body().getImage()).into(iv_User);

                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<SignInData> call, Throwable t) {
                Functions.dialogHide();
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

                        if (SwipeProfilePage.isRefreshing()) {
                            SwipeProfilePage.setRefreshing(false);
                        }

                        if (response.body().getData().size() > 0) {
                            TvFollowing.setText(String.valueOf(response.body().getData().size()));
                        } else {
                            TvFollowing.setText("0");
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


    public void CallMyFollowerApiResponse() {

        apiInterface = ApiClient.getClient(this).create(ApiInterface.class);
        Call<FollowingApiResponse> loginApiResponseCall = apiInterface.GetMyFollowerApi();
        loginApiResponseCall.enqueue(new Callback<FollowingApiResponse>() {
            @Override
            public void onResponse(Call<FollowingApiResponse> call, Response<FollowingApiResponse> response) {
                try {
                    if (response.isSuccessful()) {

                        if (SwipeProfilePage.isRefreshing()) {
                            SwipeProfilePage.setRefreshing(false);
                        }

                        if (response.body().getData().size() > 0) {
                            TvFollower.setText(String.valueOf(response.body().getData().size()));
                        } else {
                            TvFollower.setText("0");
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


    public void callLikeVideoApiResponse() {

        apiInterface = ApiClient.getClient(this).create(ApiInterface.class);
        Call<UserLikeVideoApiResponse> loginApiResponseCall = apiInterface.GetUserLikeVideoApi();
        loginApiResponseCall.enqueue(new Callback<UserLikeVideoApiResponse>() {
            @Override
            public void onResponse(Call<UserLikeVideoApiResponse> call, Response<UserLikeVideoApiResponse> response) {
                try {
                    if (response.isSuccessful()) {

                        if (SwipeProfilePage.isRefreshing()) {
                            SwipeProfilePage.setRefreshing(false);
                        }

                        Functions.dialogHide();
                        LLProfile.setVisibility(View.VISIBLE);

                        if (response.body().getData().size() > 0) {
                            TvLikeVideo.setText(String.valueOf(response.body().getData().size()));
                        } else {
                            TvLikeVideo.setText("0");
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<UserLikeVideoApiResponse> call, Throwable t) {
                Functions.dialogHide();
            }
        });
    }


    private void setClickListener() {

        SwipeProfilePage.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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
        LLLikeVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyProfileActivity.this, LikeVideoActivity.class);
                intent.putExtra("Id", "");
                startActivity(intent);
                Functions.animNext(MyProfileActivity.this);
            }
        });

        LLLikeVideoMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyProfileActivity.this, LikeVideoActivity.class);
                intent.putExtra("Id", "");
                startActivity(intent);
                Functions.animNext(MyProfileActivity.this);
            }
        });

        LLPaymentMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyProfileActivity.this, PaymentInformationActivity.class);
                startActivity(intent);
                Functions.animNext(MyProfileActivity.this);
            }
        });

        LLMyVideoMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyProfileActivity.this, MyVideoActivity.class);
                intent.putExtra("Id", "");
                startActivity(intent);
                Functions.animNext(MyProfileActivity.this);
            }
        });

        LLProfileMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MyProfileActivity.this, EditProfileActivity.class);
                startActivity(intent);
                Functions.animNext(MyProfileActivity.this);
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
                    Intent intent = new Intent(MyProfileActivity.this, LoginActivity.class);
                    startActivity(intent);
                    Functions.animNext(MyProfileActivity.this);
                } else {
                    Snackbar snackbar = Snackbar
                            .make(RelMyProfileMain, getResources().getString(R.string.check_internet_connection), Snackbar.LENGTH_SHORT);
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