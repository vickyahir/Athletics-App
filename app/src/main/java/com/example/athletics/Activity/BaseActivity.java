package com.example.athletics.Activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.Athletics.R;
import com.example.athletics.Retrofit.ApiInterface;
import com.example.athletics.Utils.ConnectionDetector;
import com.example.athletics.Utils.Functions;


public class BaseActivity extends AppCompatActivity {

    public Activity activity = BaseActivity.this;
    public ApiInterface apiInterface;
    public ConnectionDetector cd;
    public ImageView imgPlusMenu, imgHomeMenu, imgSettingMenu, imgNotificationMenu, imgProfileMenu;


    protected void onCreateMenu() {
        cd = new ConnectionDetector(activity);


        imgPlusMenu = findViewById(R.id.imgPlusMenu);
        imgHomeMenu = findViewById(R.id.imgHomeMenu);
        imgSettingMenu = findViewById(R.id.imgSettingMenu);
        imgNotificationMenu = findViewById(R.id.imgNotificationMenu);
        imgProfileMenu = findViewById(R.id.imgProfileMenu);


        imgPlusMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, UploadVideoActivity.class);
                startActivity(intent);
                Functions.animNext(activity);

            }
        });

        imgHomeMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, HomeActivity.class);
//                Intent intent = new Intent(activity, MainActivity.class);
                startActivity(intent);
                Functions.animNext(activity);

            }
        });

        imgSettingMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, SettingActivity.class);
                startActivity(intent);
                Functions.animNext(activity);
            }
        });

        imgNotificationMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(activity, NotificationActivity.class);
                startActivity(intent);
                Functions.animNext(activity);
            }
        });

        imgProfileMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(activity, MyProfileActivity.class);
                startActivity(intent);
                Functions.animNext(activity);
            }
        });

    }

    protected void onMenuSelect(int position) {
        if (position == 1) {
            imgHomeMenu.setColorFilter(activity.getResources().getColor(R.color.colorPrimary));
            imgPlusMenu.setColorFilter(activity.getResources().getColor(R.color.black));
            imgNotificationMenu.setColorFilter(activity.getResources().getColor(R.color.black));
            imgProfileMenu.setColorFilter(activity.getResources().getColor(R.color.black));
            imgSettingMenu.setColorFilter(activity.getResources().getColor(R.color.black));
        } else if (position == 2) {
            imgSettingMenu.setColorFilter(activity.getResources().getColor(R.color.colorPrimary));
            imgHomeMenu.setColorFilter(activity.getResources().getColor(R.color.black));
            imgNotificationMenu.setColorFilter(activity.getResources().getColor(R.color.black));
            imgProfileMenu.setColorFilter(activity.getResources().getColor(R.color.black));
            imgPlusMenu.setColorFilter(activity.getResources().getColor(R.color.black));
        } else if (position == 3) {
            imgPlusMenu.setColorFilter(activity.getResources().getColor(R.color.colorPrimary));
            imgHomeMenu.setColorFilter(activity.getResources().getColor(R.color.black));
            imgNotificationMenu.setColorFilter(activity.getResources().getColor(R.color.black));
            imgProfileMenu.setColorFilter(activity.getResources().getColor(R.color.black));
            imgSettingMenu.setColorFilter(activity.getResources().getColor(R.color.black));
        } else if (position == 4) {
            imgNotificationMenu.setColorFilter(activity.getResources().getColor(R.color.colorPrimary));
            imgHomeMenu.setColorFilter(activity.getResources().getColor(R.color.black));
            imgPlusMenu.setColorFilter(activity.getResources().getColor(R.color.black));
            imgProfileMenu.setColorFilter(activity.getResources().getColor(R.color.black));
            imgSettingMenu.setColorFilter(activity.getResources().getColor(R.color.black));

        } else if (position == 5) {
            imgProfileMenu.setColorFilter(activity.getResources().getColor(R.color.colorPrimary));
            imgHomeMenu.setColorFilter(activity.getResources().getColor(R.color.black));
            imgNotificationMenu.setColorFilter(activity.getResources().getColor(R.color.black));
            imgPlusMenu.setColorFilter(activity.getResources().getColor(R.color.black));
            imgSettingMenu.setColorFilter(activity.getResources().getColor(R.color.black));

        }
    }


}
