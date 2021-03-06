package com.example.athletics.Activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.example.Athletics.R;
import com.example.athletics.Retrofit.ApiInterface;
import com.example.athletics.Utils.ConnectionDetector;
import com.example.athletics.Utils.Functions;
import com.google.android.material.snackbar.Snackbar;


public class ForgotPasswordActivity extends AppCompatActivity {

    private static CharSequence target;
    private ApiInterface apiInterface;
    private ConnectionDetector cd;
    private EditText EdtEmail;
    private TextView TvNext;
    private ImageView img_rightArrow;
    private RelativeLayout RelForgotMain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        cd = new ConnectionDetector(this);
//        fetchDeviceToken();
        initView();
        setClickListener();

    }


    private void initView() {
        EdtEmail = findViewById(R.id.EdtEmail);
        TvNext = findViewById(R.id.TvNext);
        RelForgotMain = findViewById(R.id.RelForgotMain);
        img_rightArrow = findViewById(R.id.img_rightArrow);

    }

    public static boolean isValidEmail(CharSequence target) {
        ForgotPasswordActivity.target = target;
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }


    private void setClickListener() {

        EdtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!isValidEmail(EdtEmail.getText().toString())) {
                    img_rightArrow.setVisibility(View.GONE);
                } else {
                    img_rightArrow.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        TvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (EdtEmail.getText().toString().equalsIgnoreCase("")) {
                    Snackbar snackbar = Snackbar
                            .make(RelForgotMain, getResources().getString(R.string.please_enter_your_email), Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else if (!isValidEmail(EdtEmail.getText().toString())) {
                    Snackbar snackbar = Snackbar
                            .make(RelForgotMain, getResources().getString(R.string.please_enter_valid_your_email), Snackbar.LENGTH_SHORT);
                    snackbar.show();
                } else {
                    if (cd.isConnectingToInternet()) {
                        callLoginApi();
                    } else {
                        Snackbar snackbar = Snackbar
                                .make(RelForgotMain, getResources().getString(R.string.check_internet_connection), Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                }


            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Functions.animBack(ForgotPasswordActivity.this);
    }

    //    private void callSignInApi() {
//
//        apiInterface = ApiClient.getClient(LoginActivity.this).create(ApiInterface.class);
//        final Call<SignInResponse> loginApiResponseCall = apiInterface.GetLogin(edtUsername.getText().toString(), edtPassword.getText().toString(), "", Latitude, Longitude);
//        loginApiResponseCall.enqueue(new Callback<SignInResponse>() {
//            @Override
//            public void onResponse(Call<SignInResponse> call, Response<SignInResponse> response) {
//                try {
//                    Functions.dialogHide();
//                    if (response.body().isResult()) {
//
//
//                        new SessionManager(LoginActivity.this).setKeyMobileNo(response.body().getUser().getPhone());
//                        new SessionManager(LoginActivity.this).setKeyUserName(response.body().getUser().getName());
//                        new SessionManager(LoginActivity.this).setApiToken(response.body().getAccessToken());
//                        new SessionManager(LoginActivity.this).setUserID(String.valueOf(response.body().getUser().getId()));
//                        new SessionManager(LoginActivity.this).setKeyEmail(response.body().getUser().getEmail());
////                        Snackbar snackbar = Snackbar.make(LLMain, response.body().getMessage(), Snackbar.LENGTH_LONG);
////                        snackbar.show();
//                        Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
//                        if (!FirebaseToken.equalsIgnoreCase("")) {
//                            callSendFirebaseTokenApi();
//                        }
//
//                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
//                        startActivity(intent);
//                        Functions.animNext(LoginActivity.this);
//
//                    } else {
////                        Snackbar snackbar = Snackbar.make(LLMain, response.body().getMessage(), Snackbar.LENGTH_LONG);
////                        snackbar.show();
//                        Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<SignInResponse> call, Throwable t) {
//                Functions.dialogHide();
//            }
//
//        });
//    }


//    private void fetchDeviceToken() {
//        FirebaseInstanceId.getInstance().getInstanceId()
//                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
//                        if (!task.isSuccessful()) {
////                            Log.w(TAG, getString(R.string.instant_id_failed), task.getException());
//                            return;
//                        }
//
//                        // Get new Instance ID token
//                        FirebaseToken = task.getResult().getToken();
//                        Constant.FIREBASE_TOKEN = FirebaseToken;
//                        new SessionManager(LoginActivity.this).setFirebaseToken(Constant.FIREBASE_TOKEN);
//
//                    }
//                });
//    }


//    private void callSendFirebaseTokenApi() {
//
//        apiInterface = ApiClient.getClient(LoginActivity.this).create(ApiInterface.class);
//        Call<DefaultApiResponse> loginApiResponseCall = apiInterface.SendFirebaseToken(FirebaseToken);
//        loginApiResponseCall.enqueue(new Callback<DefaultApiResponse>() {
//            @Override
//            public void onResponse(Call<DefaultApiResponse> call, Response<DefaultApiResponse> response) {
//                try {
//                    if (response.body().isResult()) {
//
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<DefaultApiResponse> call, Throwable t) {
//                Functions.dialogHide();
//            }
//        });
//    }


    private void callLoginApi() {

    }


}