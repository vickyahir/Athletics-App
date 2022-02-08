package com.example.athletics.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.Athletics.R;
import com.example.athletics.Adapter.AllListCategoryAdapter;
import com.example.athletics.Adapter.ProductCategoryAdapter;
import com.example.athletics.Utils.Functions;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity {
    private ImageView imgSearch;
    private Toolbar toolbarMain;
    private RecyclerView rvCategory, rvAllListingData;
    private List<String> ProductCategory;
    private List<String> AllListCategory;
    private TextView tvAll, tvAthletics, tvCoaches;
    private SwipeRefreshLayout SwipeHomePage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        super.onCreateMenu();
        super.onMenuSelect(1);

        initView();
        loadData();
        setClickListener();
    }

    @Override
    protected void onResume() {
        LoadAllCategoryData();
        super.onResume();
    }

    private void initView() {
        toolbarMain = findViewById(R.id.toolbarMain);
        imgSearch = toolbarMain.findViewById(R.id.imgSearch);
        rvCategory = (RecyclerView) findViewById(R.id.rvCategory);
        rvAllListingData = (RecyclerView) findViewById(R.id.rvAllListingData);
        tvAll = findViewById(R.id.tvAll);
        tvAthletics = findViewById(R.id.tvAthletics);
        tvCoaches = findViewById(R.id.tvCoaches);

        SwipeHomePage = (SwipeRefreshLayout) findViewById(R.id.SwipeHomePage);


    }

    private void loadData() {

        LoadCategoryData();
        LoadAllCategoryData();
    }

    @Override
    public void onBackPressed() {
        showBackPressDialog();
    }

    private void LoadCategoryData() {
        ProductCategory = new ArrayList<>();
        ProductCategory.add("Tom Willson");
        ProductCategory.add("Wade Jone");
        ProductCategory.add("Phane Ball");
        ProductCategory.add("Jan Phame");
        ProductCategory.add("Code Fox");
        ProductCategory.add("Mark Henry");
//        ProductCategory.addAll(homePageResponse.getData().getCategory());

        rvCategory.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false));
        rvCategory.setAdapter(new ProductCategoryAdapter(activity, ProductCategory));
    }

    private void LoadAllCategoryData() {
        AllListCategory = new ArrayList<>();
        AllListCategory.add("Tom Willson");
        AllListCategory.add("Wade Jone");
        AllListCategory.add("Phane Ball");
        AllListCategory.add("Jan Phame");
        AllListCategory.add("Code Fox");
        AllListCategory.add("Mark Henry");
//        ProductCategory.addAll(homePageResponse.getData().getCategory());

        rvAllListingData.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.VERTICAL, false));
        rvAllListingData.setAdapter(new AllListCategoryAdapter(activity, AllListCategory));

        if (SwipeHomePage.isRefreshing()) {
            SwipeHomePage.setRefreshing(false);
        }
    }

    private void setClickListener() {

        SwipeHomePage.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadCategoryData();
                LoadAllCategoryData();
            }
        });

        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                intent.putExtra("Title", "Search");
                startActivity(intent);
                Functions.animNext(HomeActivity.this);
            }
        });


        tvAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tvAll.setBackground(getResources().getDrawable(R.drawable.ic_category_bg_selected));
                tvAll.setTextColor(getResources().getColor(R.color.white));
                tvAthletics.setBackground(getResources().getDrawable(R.drawable.ic_category_bg));
                tvCoaches.setBackground(getResources().getDrawable(R.drawable.ic_category_bg));
                tvAthletics.setTextColor(getResources().getColor(R.color.black));
                tvCoaches.setTextColor(getResources().getColor(R.color.black));

            }
        });

        tvAthletics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvAthletics.setBackground(getResources().getDrawable(R.drawable.ic_category_bg_selected));
                tvAthletics.setTextColor(getResources().getColor(R.color.white));
                tvAll.setBackground(getResources().getDrawable(R.drawable.ic_category_bg));
                tvCoaches.setBackground(getResources().getDrawable(R.drawable.ic_category_bg));
                tvAll.setTextColor(getResources().getColor(R.color.black));
                tvCoaches.setTextColor(getResources().getColor(R.color.black));
            }
        });

        tvCoaches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvCoaches.setBackground(getResources().getDrawable(R.drawable.ic_category_bg_selected));
                tvCoaches.setTextColor(getResources().getColor(R.color.white));
                tvAthletics.setBackground(getResources().getDrawable(R.drawable.ic_category_bg));
                tvAll.setBackground(getResources().getDrawable(R.drawable.ic_category_bg));
                tvAll.setTextColor(getResources().getColor(R.color.black));
                tvAthletics.setTextColor(getResources().getColor(R.color.black));
            }
        });

    }


    public void showBackPressDialog() {

        final Dialog builder = new Dialog(HomeActivity.this);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view1 = LayoutInflater.from(HomeActivity.this).inflate(R.layout.logout_dialog, null);

        TextView tvDialogok = (TextView) view1.findViewById(R.id.tvDialogok);
        TextView tvDialogCancel = (TextView) view1.findViewById(R.id.tvDialogCancel);
        TextView tvDialogMessage = (TextView) view1.findViewById(R.id.tvDialogMessage);
        tvDialogMessage.setText(R.string.are_you_sure_you_want_to_exit);

        tvDialogok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
                finishAffinity();
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