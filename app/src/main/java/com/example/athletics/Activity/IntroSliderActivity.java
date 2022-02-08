package com.example.athletics.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.Athletics.R;
import com.example.athletics.Adapter.IntroViewPagerAdapter;

import com.example.athletics.Utils.ExtraPrefrence;
import com.example.athletics.Utils.Functions;

public class IntroSliderActivity extends AppCompatActivity {


    protected ViewPager viewSlide;
    protected LinearLayout layoutDotsSlide;
    Activity activity = IntroSliderActivity.this;
    private TextView[] dots;
    IntroViewPagerAdapter myViewPagerAdapter;
    private TextView TvSkip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_slider);

        initView();
        SetListener();
        new ExtraPrefrence(IntroSliderActivity.this).setKeyAppFirst("true");
    }

    //<---------------------------------- for initialize data ------------------------------------------------------------------------->

    private void initView() {


        viewSlide = (ViewPager) findViewById(R.id.view_slide);
        TvSkip = (TextView) findViewById(R.id.TvSkip);
        layoutDotsSlide = (LinearLayout) findViewById(R.id.layoutDots_slide);
//        llMain = (LinearLayout) findViewById(R.id.llMain);
        myViewPagerAdapter = new IntroViewPagerAdapter(IntroSliderActivity.this, viewSlide);
        viewSlide.setAdapter(myViewPagerAdapter);
        viewSlide.addOnPageChangeListener(viewPagerPageChangeListener);
        addBottomDots(0);


    }
//<---------------------------------- set on click listener ------------------------------------------------------------------------->

    private void SetListener() {


        TvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IntroSliderActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                Functions.animNext(IntroSliderActivity.this);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Functions.animBack(IntroSliderActivity.this);
    }

//<---------------------------------- add bottom dots ------------------------------------------------------------------------->

    public void addBottomDots(int currentPage) {
        // dots = new TextView[SliderList.size()];
        dots = new TextView[3];
        int[] colorsActive = new int[3];
        int[] colorsInactive = new int[3];
        for (int i = 0; i < 3; i++) {
            colorsActive[i] = getResources().getColor(R.color.colorPrimary);
            colorsInactive[i] = getResources().getColor(R.color.white_gray);
        }

        layoutDotsSlide.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(activity);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            layoutDotsSlide.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);

    }
//<---------------------------------- for view pager ------------------------------------------------------------------------->

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(final int position) {
            addBottomDots(position);


        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    };
//<----------------------------------  my view pager adapter ------------------------------------------------------------------------->


}