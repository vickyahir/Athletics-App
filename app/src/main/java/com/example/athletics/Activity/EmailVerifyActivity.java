package com.example.athletics.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.Athletics.R;
import com.example.athletics.Model.DefaultApiResponse;
import com.example.athletics.Retrofit.ApiClient;
import com.example.athletics.Retrofit.ApiInterface;
import com.example.athletics.Utils.ConnectionDetector;
import com.example.athletics.Utils.Functions;
import com.example.athletics.Utils.SessionManager;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmailVerifyActivity extends AppCompatActivity {

    private TextView TvResendEmail, TvBackToHome;
    private RelativeLayout RelEmailVerify;
    private ConnectionDetector cd;
    private ApiInterface apiInterface;
    Handler handler = new Handler();
    int delay = 5 * 1000; //1 second=1000 milisecond, 5*1000=5seconds
    Runnable runnable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verify);
        cd = new ConnectionDetector(EmailVerifyActivity.this);

        initView();
        setClickListener();
        RedirectToNextScreen();

    }

    @Override
    protected void onResume() {
        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                if (cd.isConnectingToInternet()) {
                    callProfileVerifyOrNotApiResponse();

                } else {
                    Toast.makeText(EmailVerifyActivity.this, R.string.check_internet_connection, Toast.LENGTH_SHORT).show();
                }
                handler.postDelayed(runnable, delay);
            }
        }, delay);

        super.onResume();
    }

    private void initView() {
        TvResendEmail = findViewById(R.id.TvResendEmail);
        TvBackToHome = findViewById(R.id.TvBackToHome);
        RelEmailVerify = findViewById(R.id.RelEmailVerify);

    }

    private void setClickListener() {

    }


    private void RedirectToNextScreen() {

        TvResendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (cd.isConnectingToInternet()) {
                    Functions.dialogShow(EmailVerifyActivity.this);
                    callResendEmailApi();
                } else {
                    Snackbar snackbar = Snackbar
                            .make(RelEmailVerify, getResources().getString(R.string.check_internet_connection), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }

            }
        });


        TvBackToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(EmailVerifyActivity.this, HomeActivity.class);
                startActivity(intent);
                Functions.animNext(EmailVerifyActivity.this);

            }
        });
    }


    private void callResendEmailApi() {

        apiInterface = ApiClient.getClient(EmailVerifyActivity.this).create(ApiInterface.class);
        final Call<DefaultApiResponse> loginApiResponseCall = apiInterface.GetEmailVerifyApiResponse();
        loginApiResponseCall.enqueue(new Callback<DefaultApiResponse>() {
            @Override
            public void onResponse(Call<DefaultApiResponse> call, Response<DefaultApiResponse> response) {
                try {
                    Functions.dialogHide();
                    Snackbar snackbar = Snackbar
                            .make(RelEmailVerify, response.body().getMsg(), Snackbar.LENGTH_LONG);
                    snackbar.show();

                } catch (Exception e) {
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(Call<DefaultApiResponse> call, Throwable t) {
                Functions.dialogHide();
            }
        });
    }

    private void callProfileVerifyOrNotApiResponse() {

        apiInterface = ApiClient.getClient(EmailVerifyActivity.this).create(ApiInterface.class);
        final Call<ResponseBody> loginApiResponseCall = apiInterface.GetProfileVerifyOrNotApi();
        loginApiResponseCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {

                    if (response.isSuccessful()) {

                        String jsonString = response.body().string();
                        JSONObject jsonResult = new JSONObject(jsonString);
                        JSONObject DataSuccess = jsonResult.getJSONObject("data");

                        Toast.makeText(EmailVerifyActivity.this, "Email verify successfully.", Toast.LENGTH_SHORT).show();

                        new SessionManager(EmailVerifyActivity.this).setKeyUserName(DataSuccess.getString("name"));
                        new SessionManager(EmailVerifyActivity.this).setUserID(String.valueOf(DataSuccess.getInt("id")));
                        new SessionManager(EmailVerifyActivity.this).setKeyEmail(DataSuccess.getString("email"));
                        new SessionManager(EmailVerifyActivity.this).setKeyUserRole(DataSuccess.getString("role"));
                        new SessionManager(EmailVerifyActivity.this).setKeyUserActive(DataSuccess.getString("email_verified_at"));

                        Intent intent = new Intent(EmailVerifyActivity.this, HomeActivity.class);
                        startActivity(intent);
                        Functions.animNext(EmailVerifyActivity.this);

                        handler.removeCallbacks(runnable);
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
        handler.removeCallbacks(runnable);
        Snackbar snackbar = Snackbar
                .make(RelEmailVerify, getResources().getString(R.string.you_can_not_back_without_verify), Snackbar.LENGTH_LONG);
        snackbar.show();
//        super.onBackPressed();
    }


    @Override
    protected void onDestroy() {
        handler.removeCallbacks(runnable);
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        handler.removeCallbacks(runnable);
        super.onStop();
    }

}