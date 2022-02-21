package com.example.athletics.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.chootdev.csnackbar.Align;
import com.chootdev.csnackbar.Duration;
import com.chootdev.csnackbar.Type;
import com.example.Athletics.R;
import com.example.athletics.Retrofit.ApiClient;
import com.example.athletics.Retrofit.ApiInterface;
import com.example.athletics.Utils.ConnectionDetector;
import com.example.athletics.Utils.Constant;
import com.example.athletics.Utils.Functions;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignUpActivity extends AppCompatActivity {


    private ApiInterface apiInterface;
    private ConnectionDetector cd;
    private static CharSequence target;
    private EditText EdtEmail, EdtPassword, EdtFirstName, EdtLastName;
    private TextView TvPrivacyPolicy, TvTermsOfService, TvSignup, TvLogin;
    private RelativeLayout RelLoginMain;
    private ImageView img_password, img_rightArrow;
    private RadioButton RbVisitor, RbAthlete, RbSchoolClub, RbCoach;
    private String UserRoleType = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        cd = new ConnectionDetector(this);
//        fetchDeviceToken();
        initView();
        setClickListener();

    }


    private void initView() {
        EdtEmail = findViewById(R.id.EdtEmail);
        EdtPassword = findViewById(R.id.EdtPassword);
        EdtFirstName = findViewById(R.id.EdtFirstName);
        EdtLastName = findViewById(R.id.EdtLastName);

        TvSignup = findViewById(R.id.TvSignup);
        TvLogin = findViewById(R.id.TvLogin);
        TvPrivacyPolicy = findViewById(R.id.TvPrivacyPolicy);
        TvTermsOfService = findViewById(R.id.TvTermsOfService);
        RelLoginMain = findViewById(R.id.RelLoginMain);
        img_password = findViewById(R.id.img_password);
        img_rightArrow = findViewById(R.id.img_rightArrow);
        RbVisitor = findViewById(R.id.RbVisitor);
        RbAthlete = findViewById(R.id.RbAthlete);
        RbSchoolClub = findViewById(R.id.RbSchoolClub);
        RbCoach = findViewById(R.id.RbCoach);

    }

    public static boolean isValidEmail(CharSequence target) {
        SignUpActivity.target = target;
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

        TvTermsOfService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://www.google.com";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });

        TvPrivacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://www.google.com";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });


        img_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constant.setHideShowPassword(EdtPassword, img_password);
            }
        });


        TvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                Functions.animNext(SignUpActivity.this);
            }
        });


        TvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
//                startActivity(intent);
//                Functions.animNext(SignUpActivity.this);


                if (EdtFirstName.getText().toString().equalsIgnoreCase("")) {
                    Snackbar snackbar = Snackbar.make(RelLoginMain, getResources().getString(R.string.please_enter_your_first_name), Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else if (EdtLastName.getText().toString().equalsIgnoreCase("")) {
                    Snackbar snackbar = Snackbar
                            .make(RelLoginMain, getResources().getString(R.string.please_enter_your_last_name), Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else if (EdtEmail.getText().toString().equalsIgnoreCase("")) {
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
                } else if (!Constant.isValid(EdtPassword.getText().toString())) {
//                    Snackbar snackbar = Snackbar.make(RelLoginMain, getResources().getString(R.string.password_validation), Snackbar.LENGTH_LONG);
//                    snackbar.show();

                    com.chootdev.csnackbar.Snackbar.with(SignUpActivity.this, null)
                            .type(Type.CUSTOM, getResources().getColor(R.color.dark_black))
                            .message(getResources().getString(R.string.password_validation))
                            .duration(Duration.SHORT)
                            .fillParent(true)
                            .textAlign(Align.LEFT)
                            .show();

//                    Toast.makeText(SignUpActivity.this, "" + getResources().getString(R.string.password_validation), Toast.LENGTH_LONG).show();
                } else {
                    if (RbVisitor.isChecked()) {
                        UserRoleType = "1";
                    } else if (RbAthlete.isChecked()) {
                        UserRoleType = "2";
                    } else if (RbSchoolClub.isChecked()) {
                        UserRoleType = "3";
                    } else if (RbCoach.isChecked()) {
                        UserRoleType = "4";
                    }

                    if (cd.isConnectingToInternet()) {

                        Functions.dialogShow(SignUpActivity.this);
                        callSignUpApi();

                    } else {
                        Snackbar snackbar = Snackbar
                                .make(RelLoginMain, getResources().getString(R.string.check_internet_connection), Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                }


            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Functions.animBack(SignUpActivity.this);
    }

    private void callSignUpApi() {

        apiInterface = ApiClient.getClient(SignUpActivity.this).create(ApiInterface.class);
        final Call<ResponseBody> loginApiResponseCall = apiInterface.GetSignup(EdtFirstName.getText().toString(), EdtLastName.getText().toString(), EdtEmail.getText().toString(), EdtPassword.getText().toString(), UserRoleType);
        loginApiResponseCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Functions.dialogHide();
                    if (response.isSuccessful()) {
//                        if (response.body().isStatus()) {
//                            Toast.makeText(SignUpActivity.this, response.body().getMsg(), Toast.LENGTH_LONG).show();
//                            Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
//                            startActivity(intent);
//                            Functions.animNext(SignUpActivity.this);
//                        } else {
//                            Toast.makeText(SignUpActivity.this, response.body().getMsg(), Toast.LENGTH_LONG).show();
//                        }

                        Toast.makeText(SignUpActivity.this, "Register Success. Please login and continue in app !", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                        startActivity(intent);
                        Functions.animNext(SignUpActivity.this);

                    } else if (response.code() == 422) {
                        try {
                            String content = response.errorBody().string();
                            showErrors(new JSONObject(content));
                        } catch (Exception ignore) {
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

    private void showErrors(JSONObject json) throws Exception {
        JSONObject errors = json.getJSONObject("errors");

        if (errors.optJSONArray("email").getString(0) != null) {
            Snackbar snackbar = Snackbar.make(RelLoginMain, errors.optJSONArray("email").getString(0), Snackbar.LENGTH_LONG);
            snackbar.show();
        } else {
            Snackbar snackbar = Snackbar.make(RelLoginMain, errors.optJSONArray("password").getString(0), Snackbar.LENGTH_LONG);
            snackbar.show();

        }

    }

}