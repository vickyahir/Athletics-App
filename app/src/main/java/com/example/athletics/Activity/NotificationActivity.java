package com.example.athletics.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Athletics.R;
import com.example.athletics.Adapter.NotificationAdapter;
import com.example.athletics.Utils.Functions;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends BaseActivity {
    private ImageView imgBack, imgMenu;
    private Toolbar toolbarMain;
    private RecyclerView rvNotifications;
    private List<String> NotificationList;
    private TextView TvTitle;


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
        rvNotifications = (RecyclerView) findViewById(R.id.rvNotifications);

        TvTitle.setText(getResources().getString(R.string.notifications));
    }

    private void loadData() {
        LoadCategoryData();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Functions.animBack(NotificationActivity.this);

    }

    private void LoadCategoryData() {
        NotificationList = new ArrayList<>();
        NotificationList.add("Tom Willson");
        NotificationList.add("Wade Jone");
        NotificationList.add("Phane Ball");
        NotificationList.add("Jan Phame");
        NotificationList.add("Code Fox");
        NotificationList.add("Mark Henry");

        rvNotifications.setLayoutManager(new LinearLayoutManager(NotificationActivity.this, RecyclerView.VERTICAL, false));
        rvNotifications.setAdapter(new NotificationAdapter(NotificationActivity.this, NotificationList));
    }


    private void setClickListener() {

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