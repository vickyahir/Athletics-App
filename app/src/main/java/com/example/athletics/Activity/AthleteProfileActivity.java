package com.example.athletics.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.example.Athletics.R;
import com.example.athletics.Adapter.AthleteProfileVideoAdapter;
import com.example.athletics.Adapter.ProfileSportAdapter;
import com.example.athletics.Adapter.ProfileSportNameAdapter;
import com.example.athletics.Model.AthleteProfileResponse;
import com.example.athletics.Model.AthleteProfileStatItem;
import com.example.athletics.Model.AthleteProfileVideosItem;
import com.example.athletics.Model.GetAthleteFollowUnFollowResponse;
import com.example.athletics.Retrofit.ApiClient;
import com.example.athletics.Retrofit.ApiInterface;
import com.example.athletics.Utils.Functions;
import com.example.athletics.Utils.SessionManager;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AthleteProfileActivity extends BaseActivity {
    private ImageView imgBack, imgMenu, ImgProfile, ImgHome, iv_User, imgFollow;
    private Toolbar toolbarMain;
    private TextView TvTitle, TvNodataFound, Tv_Username, Tv_UserType, TvLikeVideo, TvFollowing, TvFollower, TvStateYear;
    private TextView TvEmail, TvSports, TvSportsWithoudLogin, TvPosition, TvMajor, TvAge, TvGender, TvSpeed, TvUniversity, TvSchool, TvSchoolComplete, TvGPA;
    private RecyclerView rvProfileHome, RvAthleteStateList, RvStateSportNameList;
    private List<AthleteProfileVideosItem> AthleteProfileVideoCategory;
    private List<String> AthleteStateCategory;
    private List<AthleteProfileStatItem> AthleteSportNameList;
    private FrameLayout FrmProfileBg;
    private LinearLayout LLVideoData, LLPersonalData, LLFollowing, LLFollower, LLAthleteProfileMain, LLJoinToView, LLSportDataWithoutLogin;
    private RelativeLayout RelFollowingMain;
    private String AthleteId = "";
    private SwipeRefreshLayout SwipeAthleteProfilePage;
    private AthleteProfileResponse athleteProfileResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_athlete_profile);
        super.onCreateMenu();
        super.onMenuSelect(5);

        getIntentData();
        initView();
        loadData();
        setClickListener();
    }

    private void getIntentData() {
        try {
            AthleteId = getIntent().getStringExtra("Id");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initView() {
        toolbarMain = findViewById(R.id.toolbarMain);
        imgBack = toolbarMain.findViewById(R.id.imgBack);
        imgMenu = toolbarMain.findViewById(R.id.imgMenu);
        TvTitle = toolbarMain.findViewById(R.id.TvTitle);
        rvProfileHome = (RecyclerView) findViewById(R.id.rvProfileHome);
        RvAthleteStateList = (RecyclerView) findViewById(R.id.RvAthleteStateList);
        RvStateSportNameList = (RecyclerView) findViewById(R.id.RvStateSportNameList);
        ImgProfile = findViewById(R.id.ImgProfile);
        ImgHome = findViewById(R.id.ImgHome);
        iv_User = findViewById(R.id.iv_User);
        imgFollow = findViewById(R.id.imgFollow);
        FrmProfileBg = findViewById(R.id.FrmProfileBg);
        LLVideoData = findViewById(R.id.LLVideoData);
        LLPersonalData = findViewById(R.id.LLPersonalData);
        LLFollowing = findViewById(R.id.LLFollowing);
        LLFollower = findViewById(R.id.LLFollower);
        LLAthleteProfileMain = findViewById(R.id.LLAthleteProfileMain);
        LLJoinToView = findViewById(R.id.LLJoinToView);
        LLSportDataWithoutLogin = findViewById(R.id.LLSportDataWithoutLogin);
        TvNodataFound = findViewById(R.id.TvNodataFound);
        Tv_Username = findViewById(R.id.Tv_Username);
        Tv_UserType = findViewById(R.id.Tv_UserType);
        TvLikeVideo = findViewById(R.id.TvLikeVideo);
        TvFollowing = findViewById(R.id.TvFollowing);
        TvFollower = findViewById(R.id.TvFollower);
        TvStateYear = findViewById(R.id.TvStateYear);
        TvEmail = findViewById(R.id.TvEmail);
        TvSports = findViewById(R.id.TvSports);
        TvSportsWithoudLogin = findViewById(R.id.TvSportsWithoudLogin);
        TvPosition = findViewById(R.id.TvPosition);
        TvMajor = findViewById(R.id.TvMajor);
        TvAge = findViewById(R.id.TvAge);
        TvGender = findViewById(R.id.TvGender);
        TvSpeed = findViewById(R.id.TvSpeed);
        TvUniversity = findViewById(R.id.TvUniversity);
        TvSchool = findViewById(R.id.TvSchool);
        TvSchoolComplete = findViewById(R.id.TvSchoolComplete);
        TvGPA = findViewById(R.id.TvGPA);
        RelFollowingMain = findViewById(R.id.RelFollowingMain);
        SwipeAthleteProfilePage = (SwipeRefreshLayout) findViewById(R.id.SwipeAthleteProfilePage);
        imgMenu.setVisibility(View.INVISIBLE);
        TvTitle.setText(getResources().getString(R.string.athlete_profie));

    }

    private void loadData() {
        if (cd.isConnectingToInternet()) {
            LLAthleteProfileMain.setVisibility(View.GONE);
            Functions.dialogShow(AthleteProfileActivity.this);
            CallMyFollowingApiResponse();
        } else {
            Snackbar snackbar = Snackbar.make(RelFollowingMain, getResources().getString(R.string.check_internet_connection), Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Functions.animBack(AthleteProfileActivity.this);
    }


    public void CallMyFollowingApiResponse() {

        apiInterface = ApiClient.getClient(this).create(ApiInterface.class);
        Call<AthleteProfileResponse> loginApiResponseCall = apiInterface.GetAthleteProfileApiResponse(AthleteId);
        loginApiResponseCall.enqueue(new Callback<AthleteProfileResponse>() {
            @Override
            public void onResponse(Call<AthleteProfileResponse> call, Response<AthleteProfileResponse> response) {
                try {
                    if (response.isSuccessful()) {

                        LLAthleteProfileMain.setVisibility(View.VISIBLE);
                        Functions.dialogHide();
                        if (SwipeAthleteProfilePage.isRefreshing()) {
                            SwipeAthleteProfilePage.setRefreshing(false);
                        }
                        athleteProfileResponse = response.body();

                        Tv_Username.setText(response.body().getData().getName());
                        Tv_UserType.setText(response.body().getData().getAthlete().getCountry());

//                        if (response.body().getData().getRole() == 1) {
//                            Tv_UserType.setText(getResources().getString(R.string.visitor));
//                        } else if (response.body().getData().getRole() == 2) {
//                            Tv_UserType.setText(getResources().getString(R.string.athlete));
//                        } else if (response.body().getData().getRole() == 3) {
//                            Tv_UserType.setText(getResources().getString(R.string.school_club));
//                        } else if (response.body().getData().getRole() == 4) {
//                            Tv_UserType.setText(getResources().getString(R.string.coach));
//                        }
                        Glide.with(AthleteProfileActivity.this).load(response.body().getData().getImage()).into(iv_User);


                        if (response.body().getData().getAthlete().getSports() != null) {
                            if (response.body().getData().getAthlete().getSports().size() > 0) {
                                TvSportsWithoudLogin.setVisibility(View.VISIBLE);
                                String Sportname = "";
                                for (int i = 0; i < response.body().getData().getAthlete().getSports().size(); i++) {
                                    Sportname = Sportname + ", " + response.body().getData().getAthlete().getSports().get(i);
                                    TvSportsWithoudLogin.setText(Sportname.substring(1));
                                }
                            } else {
                                TvSportsWithoudLogin.setVisibility(View.GONE);
                            }
                        }


                        if (response.body().getData().getAthlete().isIsFollowing()) {
                            imgFollow.setColorFilter(getResources().getColor(R.color.colorPrimary));
                        } else {
                            imgFollow.setColorFilter(getResources().getColor(R.color.black));
                        }


                        if (response.body().getData().getAthlete().getFollowers() != null) {
                            if (response.body().getData().getAthlete().getFollowers().size() > 0) {
                                TvFollower.setText(String.valueOf(response.body().getData().getAthlete().getFollowers().size()));
                            } else {
                                TvFollower.setText("0");
                            }
                        }

                        if (response.body().getData().getVideos() != null) {
                            if (response.body().getData().getVideos().size() > 0) {
                                TvLikeVideo.setText(String.valueOf(response.body().getData().getVideos().size()));
                            } else {
                                TvLikeVideo.setText("0");
                            }
                        }


                        if (response.body().getData().getAthlete().getSports() != null) {
                            if (response.body().getData().getAthlete().getSports().size() > 0) {
                                TvSports.setVisibility(View.VISIBLE);
                                String Sportname = "";
                                for (int i = 0; i < response.body().getData().getAthlete().getSports().size(); i++) {
                                    Sportname = Sportname + ", " + response.body().getData().getAthlete().getSports().get(i);
                                    TvSports.setText(Sportname.substring(1));
                                }
                            } else {
                                TvSports.setVisibility(View.GONE);
                            }
                        }


                        if (response.body().getData().getVideos().size() > 0) {
                            TvNodataFound.setVisibility(View.GONE);
                            rvProfileHome.setVisibility(View.VISIBLE);

                            AthleteProfileVideoCategory = new ArrayList<>();
                            AthleteProfileVideoCategory.addAll(response.body().getData().getVideos());
                            rvProfileHome.setLayoutManager(new GridLayoutManager(AthleteProfileActivity.this, 2));
                            rvProfileHome.setAdapter(new AthleteProfileVideoAdapter(AthleteProfileActivity.this, AthleteProfileVideoCategory));

                        } else {
                            TvNodataFound.setVisibility(View.VISIBLE);
                            rvProfileHome.setVisibility(View.GONE);
                        }


//                        if (response.body().getData().getAthlete().getMajor().size() > 0) {
//                            TvMajor.setVisibility(View.VISIBLE);
//                            String Sportname = "";
//                            for (int i = 0; i < response.body().getData().getAthlete().getMajor().size(); i++) {
//                                Sportname = Sportname + ", " + response.body().getData().getAthlete().getMajor().get(i);
//                                TvMajor.setText(Sportname.substring(1));
//                            }
//                        } else {
//                            TvMajor.setVisibility(View.GONE);
//                        }


                        if (response.body().getData().getEmail() != null) {
                            TvEmail.setText(response.body().getData().getEmail());
                        }

                        if (response.body().getData().getAthlete().getMajor() != null) {
                            TvMajor.setText(response.body().getData().getAthlete().getMajor());
                        }
                        if (response.body().getData().getAthlete().getGender() != null) {
                            TvGender.setText(response.body().getData().getAthlete().getGender());
                        }

                        if (response.body().getData().getAthlete().getSpeed() != null) {
                            TvSpeed.setText(response.body().getData().getAthlete().getSpeed());
                        }
                        if (response.body().getData().getAthlete().getUniversity().getName() != null) {
                            TvUniversity.setText(response.body().getData().getAthlete().getUniversity().getName());
                        }
                        if (response.body().getData().getAthlete().getSchool() != null) {
                            TvSchool.setText(response.body().getData().getAthlete().getSchool());
                        }

                        if (response.body().getData().getAthlete().getGpa() != null) {
                            TvGPA.setText(response.body().getData().getAthlete().getGpa());
                        }
                        if (String.valueOf(response.body().getData().getAthlete().getAge()) != null) {
                            TvAge.setText(String.valueOf(response.body().getData().getAthlete().getAge()));
                        }
                        if (String.valueOf(response.body().getData().getAthlete().getYearComplete()) != null) {
                            TvSchoolComplete.setText(String.valueOf(response.body().getData().getAthlete().getYearComplete()));
                        }
                        if (String.valueOf(response.body().getData().getAthlete().getYear()) != null) {
                            TvStateYear.setText(getResources().getString(R.string.state_of) + " " + String.valueOf(response.body().getData().getAthlete().getYear() + " :"));
                        }


                        if (new SessionManager(AthleteProfileActivity.this).getUserID().equalsIgnoreCase("")) {
                            LLJoinToView.setVisibility(View.VISIBLE);
                            rvProfileHome.setVisibility(View.GONE);
                            imgFollow.setVisibility(View.GONE);
                            Tv_UserType.setVisibility(View.GONE);
                        } else {
                            LLJoinToView.setVisibility(View.GONE);
                            imgFollow.setVisibility(View.VISIBLE);
                            Tv_UserType.setVisibility(View.VISIBLE);
                        }


                        if (response.body().getData().getAthlete().getPosition().size() > 0) {
                            TvPosition.setVisibility(View.VISIBLE);
                            String Sportname = "";
                            for (int i = 0; i < response.body().getData().getAthlete().getPosition().size(); i++) {
                                for (int j = 0; j < response.body().getData().getAthlete().getPosition().get(i).getPos().size(); j++) {
                                    Sportname = Sportname + ", " + response.body().getData().getAthlete().getPosition().get(i).getPos().get(j);
                                }
                            }
                            TvPosition.setText(Sportname.substring(1));

                        } else {
                            TvPosition.setVisibility(View.GONE);
                        }

                        if (response.body().getData().getAthlete().getState().size() > 0) {

                            RvAthleteStateList.setVisibility(View.VISIBLE);

                            AthleteStateCategory = new ArrayList<>();
                            AthleteStateCategory.addAll(response.body().getData().getAthlete().getSports());
                            RvAthleteStateList.setLayoutManager(new LinearLayoutManager(AthleteProfileActivity.this, RecyclerView.HORIZONTAL, false));
                            RvAthleteStateList.setAdapter(new ProfileSportAdapter(AthleteProfileActivity.this, AthleteStateCategory, response.body().getData().getAthlete().getState().size()));

                        } else {
                            RvAthleteStateList.setVisibility(View.GONE);
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<AthleteProfileResponse> call, Throwable t) {
                Functions.dialogHide();
            }
        });
    }

    public void sportNameDisplay(int pos) {

        if (athleteProfileResponse.getData().getAthlete().getState().get(pos).getStat().size() > 0) {

            RvStateSportNameList.setVisibility(View.VISIBLE);
            AthleteSportNameList = new ArrayList<>();
            AthleteSportNameList.addAll(athleteProfileResponse.getData().getAthlete().getState().get(pos).getStat());
            RvStateSportNameList.setLayoutManager(new GridLayoutManager(AthleteProfileActivity.this, 2));
            RvStateSportNameList.setAdapter(new ProfileSportNameAdapter(AthleteProfileActivity.this, AthleteSportNameList));
        } else {
            RvStateSportNameList.setVisibility(View.GONE);
        }


    }


    public void CallMyFollowUnFollowApiResponse() {

        apiInterface = ApiClient.getClient(this).create(ApiInterface.class);
        Call<GetAthleteFollowUnFollowResponse> loginApiResponseCall = apiInterface.GetAthleteFollowUnFollow(AthleteId);
        loginApiResponseCall.enqueue(new Callback<GetAthleteFollowUnFollowResponse>() {
            @Override
            public void onResponse(Call<GetAthleteFollowUnFollowResponse> call, Response<GetAthleteFollowUnFollowResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        Functions.dialogHide();
                        if (response.body().getData().isIsFollowing()) {
                            imgFollow.setColorFilter(getResources().getColor(R.color.colorPrimary));
                        } else {
                            imgFollow.setColorFilter(getResources().getColor(R.color.black));
                        }

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


        SwipeAthleteProfilePage.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });


        imgFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cd.isConnectingToInternet()) {
                    Functions.dialogShow(AthleteProfileActivity.this);
                    CallMyFollowUnFollowApiResponse();
                } else {
                    Snackbar snackbar = Snackbar.make(RelFollowingMain, getResources().getString(R.string.check_internet_connection), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }

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
                PopupMenu popupMenu = new PopupMenu(AthleteProfileActivity.this, imgMenu);

                // Inflating popup menu from popup_menu.xml file
                popupMenu.getMenuInflater().inflate(R.menu.following_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        // Toast message on menu item clicked
//                        Toast.makeText(FollowingActivity.this, "You Clicked " + menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        if (menuItem.getItemId() == R.id.MenuLogout) {
                            showLogoutDialog();
                        }

                        return true;
                    }
                });
                // Showing the popup menu
                popupMenu.show();
            }
        });


        ImgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                FrmProfileBg.setBackground(getResources().getDrawable(R.drawable.profile_bg_one));
                ImgProfile.setImageDrawable(getResources().getDrawable(R.drawable.ic_interface_filled));
                ImgHome.setImageDrawable(getResources().getDrawable(R.drawable.ic_layout_home));


                if (new SessionManager(AthleteProfileActivity.this).getUserID().equalsIgnoreCase("")) {
                    LLSportDataWithoutLogin.setVisibility(View.VISIBLE);
                    LLJoinToView.setVisibility(View.VISIBLE);
                } else {
                    LLPersonalData.setVisibility(View.VISIBLE);
                    LLSportDataWithoutLogin.setVisibility(View.GONE);
                    LLJoinToView.setVisibility(View.GONE);
                    LLVideoData.setVisibility(View.GONE);
                }


            }
        });

        ImgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                FrmProfileBg.setBackground(getResources().getDrawable(R.drawable.profile_bg));
                ImgProfile.setImageDrawable(getResources().getDrawable(R.drawable.ic_interface));
                ImgHome.setImageDrawable(getResources().getDrawable(R.drawable.ic_layout_home_filled));


                if (new SessionManager(AthleteProfileActivity.this).getUserID().equalsIgnoreCase("")) {
                    LLSportDataWithoutLogin.setVisibility(View.GONE);
                    LLJoinToView.setVisibility(View.VISIBLE);
                } else {
                    LLPersonalData.setVisibility(View.GONE);
                    LLVideoData.setVisibility(View.VISIBLE);
                    LLSportDataWithoutLogin.setVisibility(View.GONE);
                    LLJoinToView.setVisibility(View.GONE);
                }


            }
        });

        LLJoinToView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AthleteProfileActivity.this, LoginActivity.class);
                startActivity(intent);
                Functions.animNext(AthleteProfileActivity.this);
            }
        });


        LLFollower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(UserProfileActivity.this, MyFollowingActivity.class);
//                intent.putExtra("Title", "Follower");
//                startActivity(intent);
//                Functions.animNext(UserProfileActivity.this);
            }
        });

        LLFollowing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(UserProfileActivity.this, MyFollowingActivity.class);
//                intent.putExtra("Title", "Following");
//                startActivity(intent);
//                Functions.animNext(UserProfileActivity.this);
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
                    Intent intent = new Intent(AthleteProfileActivity.this, LoginActivity.class);
                    startActivity(intent);
                    Functions.animNext(AthleteProfileActivity.this);
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

        builder.setCancelable(true);
        builder.setCanceledOnTouchOutside(true);
        builder.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.dialog_round));
        // builder.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        builder.setContentView(view1);
        builder.show();


    }


}