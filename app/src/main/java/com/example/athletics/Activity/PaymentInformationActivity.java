package com.example.athletics.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.Athletics.R;
import com.example.athletics.Model.PaymentDataItem;
import com.example.athletics.Model.PaymentDetailsApiResponse;
import com.example.athletics.Model.SignInData;
import com.example.athletics.Retrofit.ApiClient;
import com.example.athletics.Retrofit.ApiInterface;
import com.example.athletics.Utils.Functions;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentInformationActivity extends BaseActivity {
    private ImageView imgBack, imgMenu;
    private Toolbar toolbarMain;
    private TextView TvTitle;
    private RecyclerView rvPaymentDetails;
    private List<PaymentDataItem> PaymentDetailsList;
    private TextView TvNodataFound, TvExpiredAt, TvBuyNow, TvActicationCode;
    private RelativeLayout LLPaymentDetailsMain;
    private SwipeRefreshLayout SwipePaymentPage;
    private TableLayout table;
    private LinearLayout TableLayout, LLPaymentData;
    private PaymentDetailsApiResponse paymentDetailsApiResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_information);
        super.onCreateMenu();
        super.onMenuSelect(5);

        getIntentData();
        initView();
        LoadPaymentData();
        setClickListener();
    }


    private void getIntentData() {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void initView() {
        table = new TableLayout(PaymentInformationActivity.this);
        TableLayout = findViewById(R.id.TableLayout);
        LLPaymentData = findViewById(R.id.LLPaymentData);
        toolbarMain = findViewById(R.id.toolbarMain);
        TvNodataFound = findViewById(R.id.TvNodataFound);
        TvExpiredAt = findViewById(R.id.TvExpiredAt);
        TvBuyNow = findViewById(R.id.TvBuyNow);
        TvActicationCode = findViewById(R.id.TvActicationCode);
        imgBack = toolbarMain.findViewById(R.id.imgBack);
        imgMenu = toolbarMain.findViewById(R.id.imgMenu);
        TvTitle = toolbarMain.findViewById(R.id.TvTitle);
        LLPaymentDetailsMain = findViewById(R.id.LLPaymentDetailsMain);
        rvPaymentDetails = (RecyclerView) findViewById(R.id.rvPaymentDetails);
        SwipePaymentPage = (SwipeRefreshLayout) findViewById(R.id.SwipePaymentPage);

        TvTitle.setText(getResources().getString(R.string.payments));


    }

    private void LoadPaymentData() {
        if (cd.isConnectingToInternet()) {
            Functions.dialogShow(PaymentInformationActivity.this);
            LLPaymentData.setVisibility(View.GONE);
            LoadPaymentDetailsApiData();
            callProfileApiResponse();
        } else {
            Snackbar snackbar = Snackbar.make(LLPaymentDetailsMain, getResources().getString(R.string.check_internet_connection), Snackbar.LENGTH_LONG);
            snackbar.show();
        }

    }


    public void LoadPaymentDetailsApiData() {

        apiInterface = ApiClient.getClient(this).create(ApiInterface.class);
        Call<PaymentDetailsApiResponse> loginApiResponseCall = apiInterface.GetPaymentDetailsApi();
        loginApiResponseCall.enqueue(new Callback<PaymentDetailsApiResponse>() {
            @Override
            public void onResponse(Call<PaymentDetailsApiResponse> call, Response<PaymentDetailsApiResponse> response) {
                try {
                    if (response.isSuccessful()) {

                        Functions.dialogHide();
                        LLPaymentData.setVisibility(View.VISIBLE);
                        if (SwipePaymentPage.isRefreshing()) {
                            SwipePaymentPage.setRefreshing(false);
                        }

                        paymentDetailsApiResponse = response.body();

//                        if (response.body().getData().size() > 0) {
//                            TvNodataFound.setVisibility(View.GONE);
//                            PaymentDetailsList = new ArrayList<>();
//
//                            PaymentDetailsList.addAll(response.body().getData());
//
//
//                            if (PaymentDetailsList.size() > 0) {
//                                rvPaymentDetails.setVisibility(View.VISIBLE);
//                                TvNodataFound.setVisibility(View.GONE);
//
//                                rvPaymentDetails.setLayoutManager(new LinearLayoutManager(PaymentActivity.this, RecyclerView.VERTICAL, false));
//                                rvPaymentDetails.setAdapter(new PaymentDetailsAdapter(PaymentActivity.this, PaymentDetailsList));
//                            } else {
//                                rvPaymentDetails.setVisibility(View.GONE);
//                                TvNodataFound.setVisibility(View.VISIBLE);
//                            }
//
//
//                        } else {
//                            TvNodataFound.setVisibility(View.VISIBLE);
//                        }

                        if (response.body().getData().size() > 0) {
//                            TvNodataFound.setVisibility(View.GONE);
                            TableLayout.setVisibility(View.VISIBLE);
                            table.removeAllViews();
                            TableLayout.removeAllViews();
                            LoadTableData(5, response.body().getData().size() + 1);
                        } else {
//                            TvNodataFound.setVisibility(View.VISIBLE);
                            TableLayout.setVisibility(View.VISIBLE);
                            TableLayout.setVisibility(View.VISIBLE);
                            table.removeAllViews();
                            LoadTableData(5, 1);
                        }


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<PaymentDetailsApiResponse> call, Throwable t) {
                Functions.dialogHide();
            }
        });
    }


    public void callProfileApiResponse() {

        apiInterface = ApiClient.getClient(this).create(ApiInterface.class);
        Call<SignInData> loginApiResponseCall = apiInterface.GetProfileInfoApi();
        loginApiResponseCall.enqueue(new Callback<SignInData>() {
            @Override
            public void onResponse(Call<SignInData> call, Response<SignInData> response) {
                try {
                    if (response.isSuccessful()) {
                        if (response.body().getIsActive().equalsIgnoreCase("1")) {

                            if (response.body().getExpiredAt() != null) {
                                String PlanExpiresDate = formatDate(response.body().getExpiredAt());
                                TvExpiredAt.setText(getResources().getString(R.string.plan_expired_at) + " " + PlanExpiresDate);
                            } else {
                                TvExpiredAt.setText(getResources().getString(R.string.plan_expired_at));
                            }


                        }


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<SignInData> call, Throwable t) {
                Functions.dialogHide();
            }
        });
    }


    private void LoadTableData(int Columnumber, int rownumber) {

        table.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        table.setShrinkAllColumns(true);
        table.setStretchAllColumns(true);
        table.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        table.setBackgroundColor(getResources().getColor(R.color.gray));


        try {

            for (int i = 0; i < rownumber; i++) {
                TableRow row = new TableRow(PaymentInformationActivity.this);
                row.setBackgroundColor(getResources().getColor(R.color.white));

                TableLayout.LayoutParams tableRowParams =
                        new TableLayout.LayoutParams
                                (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                int leftMargin = 1;
                int topMargin = 1;
                int rightMargin = 1;
                int bottomMargin = 1;
                tableRowParams.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);

                row.setLayoutParams(tableRowParams);

                for (int j = 0; j < Columnumber; j++) {
                    TextView tv = new TextView(PaymentInformationActivity.this);
//                    tv.setBackgroundColor(getResources().getColor(R.color.white));
                    tv.setPadding(5, 10, 5, 10);
                    tv.setTextColor(getResources().getColor(R.color.black));
                    tv.setGravity(Gravity.CENTER);

                    if (i == 0) {
                        if (j == 0) {
                            tv.setText("Payment Method");
                        } else if (j == 1) {
                            tv.setText("Referral Code");
                        } else if (j == 2) {
                            tv.setText("Date");
                        } else if (j == 3) {
                            tv.setText("Status");
                        } else if (j == 4) {
                            tv.setText("Total");
                        }
                    } else {
                        if (j == 0) {
                            tv.setText(paymentDetailsApiResponse.getData().get(i - 1).getPaymentMethod());
                        } else if (j == 1) {
                            tv.setText(paymentDetailsApiResponse.getData().get(i - 1).getUniqueCode());
                        } else if (j == 2) {
                            String FormattedDate = formatDate(paymentDetailsApiResponse.getData().get(i - 1).getCreatedAt());
                            tv.setText(FormattedDate);
                        } else if (j == 3) {
                            String PaymenStatus = "";
                            if (paymentDetailsApiResponse.getData().get(i - 1).getStatus().equalsIgnoreCase("1")) {
                                PaymenStatus = "Complete";
                            } else {
                                PaymenStatus = "Not Redeemed yet";
                            }
                            tv.setText(PaymenStatus);
                            if (PaymenStatus.equalsIgnoreCase("Complete")) {
                                tv.setTextColor(getResources().getColor(R.color.Order_Placed));
                            } else {
                                tv.setTextColor(getResources().getColor(R.color.yellow));
                            }
                        } else if (j == 4) {
                            tv.setText("$ " + paymentDetailsApiResponse.getData().get(i - 1).getAmount() + " for " + paymentDetailsApiResponse.getData().get(i - 1).getDays() + " Days");
                        }
                    }


                    if (i == 0) {
                        tv.setTextSize(10);
                        tv.setTypeface(tv.getTypeface(), Typeface.BOLD);
                        Typeface typeface = ResourcesCompat.getFont(PaymentInformationActivity.this, R.font.rubik_medium);
                        tv.setTypeface(typeface);
                    } else {
                        tv.setTextSize(10);
                        tv.setTypeface(tv.getTypeface(), Typeface.NORMAL);
                        Typeface typeface = ResourcesCompat.getFont(PaymentInformationActivity.this, R.font.rubik_regular);
                        tv.setTypeface(typeface);
                    }
                    row.addView(tv);
                }
                table.addView(row);
            }


            TableLayout.addView(table);
        } catch (Exception e) {
            e.printStackTrace();
        }

        TableLayout.setVisibility(View.VISIBLE);

    }

    private String formatDate(String dateString) {
        try {
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            Date d = sd.parse(dateString);
            sd = new SimpleDateFormat("dd MMM yyyy");
            return sd.format(d);
        } catch (ParseException e) {
        }
        return "";
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Functions.animBack(PaymentInformationActivity.this);
    }


    private void setClickListener() {

        TvBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(PaymentInformationActivity.this, SubscribePackageActivity.class);
                startActivity(intent);
                Functions.animNext(PaymentInformationActivity.this);
            }
        });

        TvActicationCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSubscriptionCodeDialog();
            }
        });

        SwipePaymentPage.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadPaymentData();
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PaymentInformationActivity.this, "menu clicked !", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void showSubscriptionCodeDialog() {
        final Dialog builder = new Dialog(activity, R.style.Theme_Dialog);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view1 = LayoutInflater.from(activity).inflate(R.layout.dialog_subscription_activation, null);
//        Window window = builder.getWindow();
//        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);


        TextView tvDialogActivateNow = (TextView) view1.findViewById(R.id.tvDialogActivateNow);
        TextView TvDiaogDetails = (TextView) view1.findViewById(R.id.TvDiaogDetails);
        EditText edtActicationCode = (EditText) view1.findViewById(R.id.edtActicationCode);
        TvDiaogDetails.setText(getResources().getString(R.string.please_enter_your_unique_code_given_by_your_school_club));

        tvDialogActivateNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cd.isConnectingToInternet()) {

                    if (!edtActicationCode.getText().toString().equalsIgnoreCase("")) {
                        Functions.dialogShow(PaymentInformationActivity.this);
//                        builder.dismiss();
                        callGetValidationCodeApi(edtActicationCode.getText().toString(), builder);
                    } else {
                        Snackbar snackbar = Snackbar
                                .make(LLPaymentDetailsMain, getResources().getString(R.string.Please_enter_your_actication_code), Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }


                } else {
                    Snackbar snackbar = Snackbar
                            .make(LLPaymentDetailsMain, getResources().getString(R.string.check_internet_connection), Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }

            }
        });


        builder.setCancelable(true);
        builder.setCanceledOnTouchOutside(true);
        builder.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.dialog_round));
        // builder.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        builder.setContentView(view1);
        builder.show();


    }

    private void callGetValidationCodeApi(String ValidatingCode, Dialog builder) {

        apiInterface = ApiClient.getClient(PaymentInformationActivity.this).create(ApiInterface.class);
        final Call<ResponseBody> loginApiResponseCall = apiInterface.GetValidationCode(ValidatingCode);
        loginApiResponseCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful()) {
                        Functions.dialogHide();
                        String jsonString = response.body().string();
                        JSONObject jsonResult = new JSONObject(jsonString);
                        if (jsonResult.getBoolean("status")) {
                            builder.dismiss();

                            Intent intent = new Intent(activity, MyProfileActivity.class);
                            startActivity(intent);
                            finish();
                            Functions.animNext(activity);

                        } else {
                            Snackbar snackbar = Snackbar.make(LLPaymentDetailsMain, jsonResult.getString("msg"), Snackbar.LENGTH_LONG);
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


}