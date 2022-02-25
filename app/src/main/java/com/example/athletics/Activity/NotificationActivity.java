package com.example.athletics.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.Athletics.R;
import com.example.athletics.Adapter.NotificationAdapter;
import com.example.athletics.Utils.Functions;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends BaseActivity {
    private ImageView imgBack, imgMenu;
    private Toolbar toolbarMain;
    private RecyclerView rvNotifications;
    private List<String> NotificationList;
    private TextView TvTitle, TvNodataFound;
    private RelativeLayout RelNotificationMain;
    private SwipeRefreshLayout SwipNotificationPage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        super.onCreateMenu();
        super.onMenuSelect(4);

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
        RelNotificationMain = findViewById(R.id.RelNotificationMain);
        rvNotifications = (RecyclerView) findViewById(R.id.rvNotifications);
        SwipNotificationPage = (SwipeRefreshLayout) findViewById(R.id.SwipNotificationPage);

        TvTitle.setText(getResources().getString(R.string.notifications));
    }

    private void loadData() {


        if (cd.isConnectingToInternet()) {
            Functions.dialogShow(NotificationActivity.this);
            LoadCategoryData();
        } else {
            Snackbar snackbar = Snackbar.make(RelNotificationMain, getResources().getString(R.string.check_internet_connection), Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Functions.animBack(NotificationActivity.this);

    }

    private void LoadCategoryData() {

        Functions.dialogHide();

        if (SwipNotificationPage.isRefreshing()) {
            SwipNotificationPage.setRefreshing(false);
        }

        NotificationList = new ArrayList<>();
//        NotificationList.add("Tom Willson");
//        NotificationList.add("Wade Jone");
//        NotificationList.add("Phane Ball");
//        NotificationList.add("Jan Phame");
//        NotificationList.add("Code Fox");
//        NotificationList.add("Mark Henry");


        if (NotificationList.size() > 0) {
            rvNotifications.setVisibility(View.VISIBLE);
            TvNodataFound.setVisibility(View.GONE);

            rvNotifications.setLayoutManager(new LinearLayoutManager(NotificationActivity.this, RecyclerView.VERTICAL, false));
            rvNotifications.setAdapter(new NotificationAdapter(NotificationActivity.this, NotificationList));
        } else {
            rvNotifications.setVisibility(View.GONE);
            TvNodataFound.setVisibility(View.VISIBLE);
        }


    }


    private void setClickListener() {

        SwipNotificationPage.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });


        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(NotificationActivity.this, "menu clicked !", Toast.LENGTH_SHORT).show();
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