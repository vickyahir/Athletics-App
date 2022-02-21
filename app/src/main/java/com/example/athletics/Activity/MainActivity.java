package com.example.athletics.Activity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.Athletics.R;

public class MainActivity extends BaseActivity {
    private ImageView imgSearch;
    private Toolbar toolbarMain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        super.onCreateMenu();
        super.onMenuSelect(1);

        initView();
        loadData();
        setClickListener();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initView() {
        toolbarMain = findViewById(R.id.toolbarMain);
        imgSearch = toolbarMain.findViewById(R.id.imgSearch);


    }

    private void loadData() {

    }

    @Override
    public void onBackPressed() {
        showBackPressDialog();
    }

    private void setClickListener() {
    }


    public void showBackPressDialog() {

        final Dialog builder = new Dialog(MainActivity.this);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view1 = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_logout, null);

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