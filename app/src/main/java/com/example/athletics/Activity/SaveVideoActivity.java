package com.example.athletics.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Athletics.R;
import com.example.athletics.Adapter.SaveVideoAdapter;
import com.example.athletics.Utils.Functions;

import java.util.ArrayList;
import java.util.List;

public class SaveVideoActivity extends BaseActivity {
    private ImageView imgBack, imgMenu;
    private Toolbar toolbarMain;
    private TextView TvTitle;
    private RecyclerView rvProfileHome;
    private List<String> HomeVideoCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_video);
        super.onCreateMenu();
        super.onMenuSelect(5);

        initView();
        loadData();
        setClickListener();
    }


    private void initView() {
        toolbarMain = findViewById(R.id.toolbarMain);
        imgBack = toolbarMain.findViewById(R.id.imgBack);
        imgMenu = toolbarMain.findViewById(R.id.imgMenu);
        TvTitle = toolbarMain.findViewById(R.id.TvTitle);
        rvProfileHome = (RecyclerView) findViewById(R.id.rvProfileHome);

        TvTitle.setText(getResources().getString(R.string.save_video));

    }

    private void loadData() {
        LoadCategoryData();
    }

    private void LoadCategoryData() {
        HomeVideoCategory = new ArrayList<>();
        HomeVideoCategory.add("Tom Willson");
        HomeVideoCategory.add("Wade Jone");
        HomeVideoCategory.add("Phane Ball");
        HomeVideoCategory.add("Jan Phame");
        HomeVideoCategory.add("Code Fox");
        HomeVideoCategory.add("Mark Henry");
//        ProductCategory.addAll(homePageResponse.getData().getCategory());

        rvProfileHome.setLayoutManager(new GridLayoutManager(SaveVideoActivity.this, 2));
        rvProfileHome.setAdapter(new SaveVideoAdapter(SaveVideoActivity.this, HomeVideoCategory));
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Functions.animBack(SaveVideoActivity.this);
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
                Toast.makeText(SaveVideoActivity.this, "menu clicked !", Toast.LENGTH_SHORT).show();
            }
        });


    }


}