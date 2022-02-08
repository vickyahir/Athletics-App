package com.example.athletics.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.Athletics.R;
import com.example.athletics.Activity.SplashActivity;

import com.example.athletics.Utils.Functions;


public class IntroViewPagerAdapter extends PagerAdapter {
    private LayoutInflater layoutInflater;
    private Context mContext;
    private ViewPager viewPager;


    public IntroViewPagerAdapter(Context context, ViewPager viewSlide) {
        this.mContext = context;
        this.viewPager = viewSlide;

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_slider, container, false);


        ImageView img = view.findViewById(R.id.img);
        TextView tv_title = view.findViewById(R.id.tv_title);
        TextView tv_details = view.findViewById(R.id.tv_details);
        TextView tvNext = view.findViewById(R.id.tvNext);


        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = viewPager.getCurrentItem() + 1;
                if (current < 3) {
                    // move to next screen
                    viewPager.setCurrentItem(current);
                } else {
                    Intent intent = new Intent(mContext, SplashActivity.class);
                    mContext.startActivity(intent);
                    Functions.animNext(mContext);
                }


            }
        });

        if (position == 0) {
            img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.slider_one));
            tv_title.setText(R.string.martialartls);
            tv_details.setText(R.string.dummytext);
//                tv_next.setVisibility(View.GONE);
        } else if (position == 1) {
            img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.slider_two));
            tv_title.setText(R.string.fieldhockey);
            tv_details.setText(R.string.dummytext);
//                tv_next.setVisibility(View.GONE);
        } else if (position == 2) {
            img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.slider_three));
            tv_title.setText(R.string.rugby);
            tv_details.setText(R.string.dummytext);
//                tv_next.setVisibility(View.VISIBLE);
        }
        container.addView(view);

        return view;
    }


    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }

}