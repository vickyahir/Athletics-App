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
import com.example.athletics.Adapter.SubscribeAdapter;

import com.example.athletics.Utils.Functions;

import java.util.ArrayList;
import java.util.List;

public class SubscribePackageActivity extends BaseActivity {
    private ImageView imgBack, imgMenu;
    private Toolbar toolbarMain;
    private TextView TvTitle;
    private RecyclerView rvSubscribePackage;
    private List<String> SubscribeCategory;

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
        rvSubscribePackage = (RecyclerView) findViewById(R.id.rvSubscribePackage);

        TvTitle.setText(getResources().getString(R.string.subscribe_package));

    }

    private void loadData() {
        LoadCategoryData();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Functions.animBack(SubscribePackageActivity.this);
    }


    private void LoadCategoryData() {
        SubscribeCategory = new ArrayList<>();
        SubscribeCategory.add("$39");
        SubscribeCategory.add("$65");
        SubscribeCategory.add("$85");

//        ProductCategory.addAll(homePageResponse.getData().getCategory());

        rvSubscribePackage.setLayoutManager(new LinearLayoutManager(SubscribePackageActivity.this, LinearLayoutManager.HORIZONTAL, false));
        rvSubscribePackage.setAdapter(new SubscribeAdapter(SubscribePackageActivity.this, SubscribeCategory));
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
                Toast.makeText(SubscribePackageActivity.this, "menu clicked !", Toast.LENGTH_SHORT).show();
            }
        });


    }


}