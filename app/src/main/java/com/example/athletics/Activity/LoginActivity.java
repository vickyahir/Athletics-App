package com.example.athletics.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.Athletics.R;
import com.example.athletics.Retrofit.ApiClient;
import com.example.athletics.Retrofit.ApiInterface;
import com.example.athletics.Utils.ConnectionDetector;
import com.example.athletics.Utils.Constant;
import com.example.athletics.Utils.Functions;
import com.example.athletics.Utils.SessionManager;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {


    private ApiInterface apiInterface;
    private ConnectionDetector cd;
    private static CharSequence target;
    private EditText EdtEmail, EdtPassword;
    private TextView TvForgotPassword, TvLogin, TvSignup, TvSkipLogin;
    private RelativeLayout RelLoginMain;
    private ImageView img_password, img_rightArrow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        cd = new ConnectionDetector(this);
//        fetchDeviceToken();
        initView();
        setClickListener();

    }


    private void initView() {
        EdtEmail = findViewById(R.id.EdtEmail);
        EdtPassword = findViewById(R.id.EdtPassword);
        TvForgotPassword = findViewById(R.id.TvForgotPassword);
        TvLogin = findViewById(R.id.TvLogin);
        TvSignup = findViewById(R.id.TvSignup);
        TvSkipLogin = findViewById(R.id.TvSkipLogin);
        RelLoginMain = findViewById(R.id.RelLoginMain);
        img_password = findViewById(R.id.img_password);
        img_rightArrow = findViewById(R.id.img_rightArrow);


    }

    public static boolean isValidEmail(CharSequence target) {
        LoginActivity.target = target;
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }


    private void setClickListener() {


        img_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constant.setHideShowPassword(EdtPassword, img_password);
            }
        });

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


        TvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                Functions.animNext(LoginActivity.this);
            }
        });

        TvSkipLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                Functions.animNext(LoginActivity.this);
            }
        });

        TvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
                Functions.animNext(LoginActivity.this);
            }
        });

        TvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (EdtEmail.getText().toString().equalsIgnoreCase("")) {
                    Snackbar snackbar = Snackbar
                            .make(RelLoginMain, getResources().getString(R.string.please_enter_your_email), Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else if (!isValidEmail(EdtEmail.getText().toString())) {
                    Snackbar snackbar = Snackbar
                            .make(RelLoginMain, getResources().getString(R.string.please_enter_valid_your_email), Snackbar.LENGTH_SHORT);
                    snackbar.show();
                } else if (EdtPassword.getText().toString().equalsIgnoreCase("")) {
                    Snackbar snackbar = Snackbar.make(RelLoginMain, getResources().getString(R.string.please_enter_your_password), Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {


                    if (cd.isConnectingToInternet()) {
                        Functions.dialogShow(LoginActivity.this);
                        callSignInApi();
                    } else {
                        Snackbar snackbar = Snackbar
                                .make(RelLoginMain, getResources().getString(R.string.check_internet_connection), Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                }
//                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
//                startActivity(intent);
//                Functions.animNext(LoginActivity.this);


            }
        });

    }


    private void callSignInApi() {

        apiInterface = ApiClient.getClient(LoginActivity.this).create(ApiInterface.class);
        final Call<ResponseBody> loginApiResponseCall = apiInterface.GetLogin(EdtEmail.getText().toString(), EdtPassword.getText().toString());
        loginApiResponseCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Functions.dialogHide();


                    if (response.isSuccessful()) {

                        String jsonString = response.body().string();
                        JSONObject jsonResult = new JSONObject(jsonString);

                        if (jsonResult.getBoolean("status")) {

                            Toast.makeText(LoginActivity.this, "" + jsonResult.getString("msg"), Toast.LENGTH_LONG).show();

                            new SessionManager(LoginActivity.this).setKeyUserName(jsonResult.getJSONObject("data").getString("name"));
                            new SessionManager(LoginActivity.this).setApiToken(jsonResult.getJSONObject("data").getString("token"));
                            new SessionManager(LoginActivity.this).setUserID(String.valueOf(jsonResult.getJSONObject("data").getInt("id")));
                            new SessionManager(LoginActivity.this).setKeyEmail(jsonResult.getJSONObject("data").getString("email"));
                            new SessionManager(LoginActivity.this).setKeyUserRole(jsonResult.getJSONObject("data").getString("role"));
                            new SessionManager(LoginActivity.this).setKeyUserActive(jsonResult.getJSONObject("data").getString("email_verified_at"));

                            if (jsonResult.getJSONObject("data").getString("email_verified_at").equalsIgnoreCase("null")) {
                                Intent intent = new Intent(LoginActivity.this, EmailVerifyActivity.class);
                                startActivity(intent);
                                finish();
                                Functions.animNext(LoginActivity.this);
                            } else {
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish();
                                Functions.animNext(LoginActivity.this);
                            }


                        } else {
                            Snackbar snackbar = Snackbar.make(RelLoginMain, "" + jsonResult.getString("msg"), Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Functions.dialogHide();
            }

        });
    }


    @Override
    public void onBackPressed() {
        showBackPressDialog();
    }

    public void showBackPressDialog() {

        final Dialog builder = new Dialog(LoginActivity.this, R.style.Theme_Dialog);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view1 = LayoutInflater.from(LoginActivity.this).inflate(R.layout.dialog_logout, null);

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


        builder.setCancelable(true);
        builder.setCanceledOnTouchOutside(true);
        builder.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.dialog_round));
        // builder.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        builder.setContentView(view1);
        builder.show();


    }

}