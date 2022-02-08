package com.example.athletics.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Athletics.R;
import com.example.athletics.Adapter.SearchedAdapter;
import com.example.athletics.Retrofit.ApiInterface;
import com.example.athletics.Utils.ConnectionDetector;
import com.example.athletics.Utils.Functions;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;


public class SearchActivity extends AppCompatActivity {

    public Toolbar toolbarMain;
    private ImageView iv_Back;
    private TextView tvTitle, TvNoDataFound, TvResult;
    private RecyclerView rvSearchList;
    private ApiInterface apiInterface;
    private ConnectionDetector cd;
    private LinearLayout LLSearchList, LLResultCount;
    private SearchView searchView;
    private List<String> AllSearchCategpry;
    private String Title = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        cd = new ConnectionDetector(SearchActivity.this);

        getIntentData();
        initView();
        loadData();
        setClickListener();

    }

    private void getIntentData() {
        try {
            Title = getIntent().getStringExtra("Title");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initView() {

        tvTitle = findViewById(R.id.TvTitle);
        TvNoDataFound = findViewById(R.id.TvNodataFound);
        TvResult = findViewById(R.id.TvResult);
        LLResultCount = findViewById(R.id.LLResultCount);
        toolbarMain = findViewById(R.id.toolbarMain);
        iv_Back = toolbarMain.findViewById(R.id.iv_Back);
        tvTitle.setText(Title);
        rvSearchList = findViewById(R.id.rvSearchList);
        LLSearchList = findViewById(R.id.LLSearchList);
        searchView = (SearchView) findViewById(R.id.searchView);


    }


    public void loadData() {
        TvNoDataFound.setVisibility(View.VISIBLE);
        rvSearchList.setVisibility(View.GONE);
        LLResultCount.setVisibility(View.GONE);
    }


    private void setClickListener() {

        iv_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

//                if(list.contains(query)){
//                    adapter.getFilter().filter(query);
//                }else{
//                    Toast.makeText(MainActivity.this, "No Match found",Toast.LENGTH_LONG).show();
//                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //    adapter.getFilter().filter(newText);
                if (cd.isConnectingToInternet()) {
//                    Functions.dialogShow(SearchActivity.this);
                    if (!newText.trim().isEmpty()) {
//                        GetSearchListApi(newText);
                        LoadAllCategoryData();
                    } else {
                        TvNoDataFound.setVisibility(View.VISIBLE);
                        rvSearchList.setVisibility(View.GONE);
                        LLResultCount.setVisibility(View.GONE);
                    }
                } else {
                    Snackbar snackbar = Snackbar.make(searchView, getResources().getString(R.string.check_internet_connection), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }

                return false;
            }
        });


    }

    private void LoadAllCategoryData() {

        TvNoDataFound.setVisibility(View.GONE);
        LLResultCount.setVisibility(View.VISIBLE);
        rvSearchList.setVisibility(View.VISIBLE);

        AllSearchCategpry = new ArrayList<>();
        AllSearchCategpry.add("Tom Willson");
        AllSearchCategpry.add("Wade Jone");
        AllSearchCategpry.add("Phane Ball");
        AllSearchCategpry.add("Jan Phame");
        AllSearchCategpry.add("Code Fox");
        AllSearchCategpry.add("Mark Henry");
//        ProductCategory.addAll(homePageResponse.getData().getCategory());

        rvSearchList.setLayoutManager(new LinearLayoutManager(SearchActivity.this, RecyclerView.VERTICAL, false));
        rvSearchList.setAdapter(new SearchedAdapter(SearchActivity.this, AllSearchCategpry));
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Functions.animBack(SearchActivity.this);
    }

}