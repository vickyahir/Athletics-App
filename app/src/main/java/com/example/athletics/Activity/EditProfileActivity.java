package com.example.athletics.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.chootdev.csnackbar.Align;
import com.chootdev.csnackbar.Duration;
import com.chootdev.csnackbar.Type;
import com.example.Athletics.R;
import com.example.athletics.Model.ProfileUpdateApiResponse;
import com.example.athletics.Model.SignInData;
import com.example.athletics.Retrofit.ApiClient;
import com.example.athletics.Retrofit.ApiInterface;
import com.example.athletics.Utils.ConnectionDetector;
import com.example.athletics.Utils.Constant;
import com.example.athletics.Utils.Functions;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditProfileActivity extends AppCompatActivity {

    private ApiInterface apiInterface;
    private ConnectionDetector cd;
    private LinearLayout LLProfileMain, LLEditProfile;
    private EditText edtCurrentPassword, edtNewPassword, edtConfirmPassword;
    private String resultUri = "";
    private static String Base64Images = "";
    private CircleImageView ic_profile;
    private TextView TvSubmit, TvTitle, TvUpdateProfile;
    private EditText EdtName;
    private ImageView Img_Edit, img_currentpassword, img_Newpassword, img_confirm_password, imgBack;
    private Boolean IsRemoveProfile = false;
    private Toolbar toolbarMain;

    private final String[] permissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        cd = new ConnectionDetector(EditProfileActivity.this);

        initView();
        setClickListener();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Functions.animBack(EditProfileActivity.this);
    }

    @Override
    protected void onResume() {
        if (cd.isConnectingToInternet()) {
            Functions.dialogShow(EditProfileActivity.this);
            LLEditProfile.setVisibility(View.GONE);
            callProfileApiResponse();
        } else {
            Snackbar snackbar = Snackbar.make(LLProfileMain, getResources().getString(R.string.check_internet_connection), Snackbar.LENGTH_LONG);
            snackbar.show();
        }
        super.onResume();
    }


    private void initView() {

        TvSubmit = findViewById(R.id.TvSubmit);
        LLProfileMain = findViewById(R.id.LLProfileMain);
        LLEditProfile = findViewById(R.id.LLEditProfile);
        EdtName = findViewById(R.id.EdtName);
        edtCurrentPassword = findViewById(R.id.edtCurrentPassword);
        edtNewPassword = findViewById(R.id.edtNewPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        img_currentpassword = findViewById(R.id.img_currentpassword);
        img_Newpassword = findViewById(R.id.img_Newpassword);
        img_confirm_password = findViewById(R.id.img_confirm_password);
        TvUpdateProfile = findViewById(R.id.TvUpdateProfile);
        toolbarMain = findViewById(R.id.toolbarMain);
        imgBack = toolbarMain.findViewById(R.id.imgBack);
        TvTitle = toolbarMain.findViewById(R.id.TvTitle);

        TvTitle.setText(getResources().getString(R.string.edit_profie));

        ic_profile = findViewById(R.id.ic_profile);
        Img_Edit = findViewById(R.id.Img_Edit);


    }


    public void callProfileApiResponse() {

        apiInterface = ApiClient.getClient(this).create(ApiInterface.class);
        Call<SignInData> loginApiResponseCall = apiInterface.GetProfileInfoApi();
        loginApiResponseCall.enqueue(new Callback<SignInData>() {
            @Override
            public void onResponse(Call<SignInData> call, Response<SignInData> response) {
                try {
                    if (response.isSuccessful()) {

                        Functions.dialogHide();
                        LLEditProfile.setVisibility(View.VISIBLE);

                        EdtName.setText(response.body().getName());

                        if (!response.body().getImage().equalsIgnoreCase("")) {
                            Glide.with(EditProfileActivity.this).load(response.body().getImage()).into(ic_profile);

                            IsRemoveProfile = !response.body().getImage().contains("default.png");
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

//    private void callRemoveProfileApiResponse() {
//
//        apiInterface = ApiClient.getClient(this).create(ApiInterface.class);
//        Call<AccountVerifyResponse> loginApiResponseCall = apiInterface.RemoveUserProfile();
//        loginApiResponseCall.enqueue(new Callback<AccountVerifyResponse>() {
//            @Override
//            public void onResponse(Call<AccountVerifyResponse> call, Response<AccountVerifyResponse> response) {
//                try {
//                    if (response.body().isResult()) {
//                        Functions.dialogHide();
//                        IsRemoveProfile = false;
//                        Snackbar snackbar = Snackbar.make(LLProfileMain, response.body().getMessage(), Snackbar.LENGTH_LONG);
//                        snackbar.show();
//                        if (cd.isConnectingToInternet()) {
//                            callProfileApiResponse();
//                        } else {
//                            Snackbar snackbar1 = Snackbar.make(LLProfileMain, getResources().getString(R.string.check_internet_connection), Snackbar.LENGTH_LONG);
//                            snackbar1.show();
//                        }
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<AccountVerifyResponse> call, Throwable t) {
//            }
//        });
//    }


    private void setClickListener() {

        TvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edtCurrentPassword.getText().toString().equalsIgnoreCase("")) {
                    Snackbar snackbar = Snackbar.make(LLProfileMain, getResources().getString(R.string.please_enter_your_password), Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else if (edtNewPassword.getText().toString().equalsIgnoreCase("")) {
                    Snackbar snackbar = Snackbar.make(LLProfileMain, getResources().getString(R.string.please_enter_your_new_password), Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else if (!Constant.isValid(edtNewPassword.getText().toString())) {
//                    Snackbar snackbar = Snackbar.make(LLProfileMain, getResources().getString(R.string.password_validation), Snackbar.LENGTH_LONG);
//                    snackbar.show();

                    com.chootdev.csnackbar.Snackbar.with(EditProfileActivity.this, null)
                            .type(Type.CUSTOM, getResources().getColor(R.color.dark_black))
                            .message(getResources().getString(R.string.password_validation))
                            .duration(Duration.SHORT)
                            .fillParent(true)
                            .textAlign(Align.LEFT)
                            .show();


//                    Toast.makeText(ProfileActivity.this, "" + getResources().getString(R.string.password_validation), Toast.LENGTH_LONG).show();
                } else if (edtConfirmPassword.getText().toString().equalsIgnoreCase("")) {
                    Snackbar snackbar = Snackbar.make(LLProfileMain, getResources().getString(R.string.please_enter_your_confirm_password), Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else if (!edtNewPassword.getText().toString().equalsIgnoreCase(edtConfirmPassword.getText().toString())) {
                    Snackbar snackbar = Snackbar.make(LLProfileMain, getResources().getString(R.string.password_and_confirm_password_not_match), Snackbar.LENGTH_LONG);
                    snackbar.show();

                } else {
                    if (cd.isConnectingToInternet()) {
                        Functions.dialogShow(EditProfileActivity.this);
                        callChangePasswordApi();
                    } else {
                        Snackbar snackbar = Snackbar.make(LLProfileMain, getResources().getString(R.string.check_internet_connection), Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                }

            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        img_confirm_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.setHideShowPassword(edtConfirmPassword, img_confirm_password);
            }
        });


        img_currentpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.setHideShowPassword(edtCurrentPassword, img_currentpassword);
            }
        });


        img_Newpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.setHideShowPassword(edtNewPassword, img_Newpassword);
            }
        });


        ic_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!EasyPermissions.hasPermissions(EditProfileActivity.this, permissions)) {
                    EasyPermissions.requestPermissions(EditProfileActivity.this, getResources().getString(R.string.allow_access_camera_gallery), 1, permissions);
                } else {
                    SelectImageDialog(IsRemoveProfile);
                }
            }
        });

        Img_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!EasyPermissions.hasPermissions(EditProfileActivity.this, permissions)) {
                    EasyPermissions.requestPermissions(EditProfileActivity.this, getResources().getString(R.string.allow_access_camera_gallery), 1, permissions);
                } else {
                    SelectImageDialog(IsRemoveProfile);
                }
            }
        });


        TvUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (EdtName.getText().toString().equalsIgnoreCase("")) {
                    Snackbar snackbar = Snackbar.make(LLProfileMain, getResources().getString(R.string.please_enter_your_full_name), Snackbar.LENGTH_SHORT);
                    snackbar.show();
                } else {
                    if (cd.isConnectingToInternet()) {
                        Functions.dialogShow(EditProfileActivity.this);
                        callGetProfileApi();
                    } else {
                        Snackbar snackbar = Snackbar.make(LLProfileMain, getResources().getString(R.string.check_internet_connection), Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }

                }
            }
        });


//        TvDateOfBirth.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                calendar = Calendar.getInstance();
//                DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                        calendar.set(Calendar.YEAR, year);
//                        calendar.set(Calendar.MONTH, month);
//                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//
//                        int day = dayOfMonth;
//                        int months = month + 1;  // here I added 1 to the month
//                        int years = year;
//                        ProfileDateOfBirth = years + "-" + months + "-" + day;
//                        TvDateOfBirth.setText(ProfileDateOfBirth);
//                    }
//                };
//                new DatePickerDialog(ProfileActivity.this, R.style.DialogTheme, onDateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
//
//            }
//        });


    }


    private void callChangePasswordApi() {

        apiInterface = ApiClient.getClient(EditProfileActivity.this).create(ApiInterface.class);
        final Call<ResponseBody> loginApiResponseCall = apiInterface.ChangePassword(edtCurrentPassword.getText().toString(), edtNewPassword.getText().toString(), edtConfirmPassword.getText().toString());
        loginApiResponseCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Functions.dialogHide();
                    if (response.isSuccessful()) {

                        String jsonString = response.body().string();
                        JSONObject jsonResult = new JSONObject(jsonString);

                        Snackbar snackbar = Snackbar.make(LLProfileMain, jsonResult.optJSONArray("msg").getString(0), Snackbar.LENGTH_LONG);
                        snackbar.show();


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

        Snackbar snackbar = Snackbar.make(LLProfileMain, errors.optJSONArray("password").getString(0), Snackbar.LENGTH_LONG);
        snackbar.show();

    }


    public void SelectImageDialog(Boolean isRemoveProfile) {
        final Dialog builder = new Dialog(EditProfileActivity.this);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view1 = LayoutInflater.from(EditProfileActivity.this).inflate(R.layout.image_dialog, null);
        LinearLayout lnr_gallery = view1.findViewById(R.id.lnr_gallery);
        LinearLayout lnr_camara = view1.findViewById(R.id.lnr_camara);
        LinearLayout lnr_removeProfile = view1.findViewById(R.id.lnr_removeProfile);
        View ViewProfile = view1.findViewById(R.id.ViewProfile);

        if (isRemoveProfile) {
            lnr_removeProfile.setVisibility(View.GONE);
            ViewProfile.setVisibility(View.VISIBLE);
        } else {
            lnr_removeProfile.setVisibility(View.GONE);
            ViewProfile.setVisibility(View.GONE);
        }


        lnr_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();

                Intent intent = new Intent();
                intent.setType("image/*");
                //intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, getResources().getString(R.string.select_picture)), 100);
            }
        });
        lnr_camara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 10);
            }
        });

        lnr_removeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
                if (cd.isConnectingToInternet()) {
                    Functions.dialogShow(EditProfileActivity.this);
//                    callRemoveProfileApiResponse();
                } else {
                    Snackbar snackbar1 = Snackbar.make(LLProfileMain, getResources().getString(R.string.check_internet_connection), Snackbar.LENGTH_LONG);
                    snackbar1.show();
                }
            }
        });

        builder.setContentView(view1);
        builder.show();


    }


    @SuppressLint("Range")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 100 && resultCode == RESULT_OK && null != data) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                if (bitmap != null) {
                    ic_profile.setImageBitmap(bitmap);
                    resultUri = getPath(EditProfileActivity.this, data.getData());

                    InputStream imageStream = null;
                    try {
                        Uri tempUri = getImageUri(getApplicationContext(), bitmap);
                        imageStream = this.getContentResolver().openInputStream(tempUri);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
                    encodeTobase64(yourSelectedImage);

                    if (cd.isConnectingToInternet()) {
                        Functions.dialogShow(EditProfileActivity.this);
                        callImageUpdateApi();
                    } else {
                        Snackbar snackbar = Snackbar.make(LLProfileMain, getResources().getString(R.string.check_internet_connection), Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
//                Log.e("Image Exception=", e.toString());
            }


        } else if (requestCode == 10 && resultCode == RESULT_OK && null != data) {
            try {

                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                if (thumbnail != null) {
                    ic_profile.setImageBitmap(thumbnail);
                }

                InputStream imageStream = null;
                try {
                    Uri tempUri = getImageUri(getApplicationContext(), thumbnail);
                    imageStream = this.getContentResolver().openInputStream(tempUri);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
                encodeTobase64(yourSelectedImage);

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

                // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                Uri tempUri = getImageUri(getApplicationContext(), thumbnail);

                // CALL THIS METHOD TO GET THE ACTUAL PATH
                File finalFile = new File(getRealPathFromURI(tempUri));
                String path1 = Environment.getExternalStorageDirectory()
                        .toString();
                File direct = new File(path1 + "/Eazipin");
                if (!direct.exists()) {
                    direct.mkdirs();
                }

                File destination = new File(direct.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");

                if (destination.exists()) {
                    destination.delete();
                }

                try {
                    FileOutputStream out = new FileOutputStream(destination);
                    out.flush();
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                final String[] imageColumns = {MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA};
                final String imageOrderBy = MediaStore.Images.Media._ID + " DESC";
                Cursor imageCursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imageColumns, null, null, imageOrderBy);

                if (imageCursor.moveToFirst()) {
                    resultUri = imageCursor.getString(imageCursor.getColumnIndex(MediaStore.Images.Media.DATA));


                    if (cd.isConnectingToInternet()) {
                        Functions.dialogShow(EditProfileActivity.this);
                        callImageUpdateApi();

                    } else {
                        Snackbar snackbar = Snackbar.make(LLProfileMain, getResources().getString(R.string.check_internet_connection), Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
//                Log.e("ProfileUrl==", resultUri);
                    /*String filename = PhotoIdUrl.substring(PhotoIdUrl.lastIndexOf("/") + 1);
                    tvPhotoidfront.setText(filename);*/
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            Snackbar snackbar = Snackbar.make(LLProfileMain, getResources().getString(R.string.you_hant_pic_image), Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void callImageUpdateApi() {

        MultipartBody.Part photosfront_parts = null;
        if (!resultUri.equals("")) {
            File file_banner = new File(resultUri);
            RequestBody surveyBody = RequestBody.create(MediaType.parse("*/*"), file_banner);
            photosfront_parts = MultipartBody.Part.createFormData("image", file_banner.getName(), surveyBody);
        }


        apiInterface = ApiClient.getClient(EditProfileActivity.this).create(ApiInterface.class);
        final Call<ProfileUpdateApiResponse> loginApiResponseCall = apiInterface.UpdateUserProfile(photosfront_parts);
        loginApiResponseCall.enqueue(new Callback<ProfileUpdateApiResponse>() {
            @Override
            public void onResponse(Call<ProfileUpdateApiResponse> call, Response<ProfileUpdateApiResponse> response) {
                try {
                    if (response.isSuccessful()) {

                        Functions.dialogHide();
                        IsRemoveProfile = true;
                        Toast.makeText(EditProfileActivity.this, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();

                        if (cd.isConnectingToInternet()) {
                            Functions.dialogShow(EditProfileActivity.this);
                            callProfileApiResponse();
                        } else {
                            Snackbar snackbar1 = Snackbar.make(LLProfileMain, getResources().getString(R.string.check_internet_connection), Snackbar.LENGTH_LONG);
                            snackbar1.show();
                        }


                    } else {
                        Toast.makeText(EditProfileActivity.this, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<ProfileUpdateApiResponse> call, Throwable t) {
                t.printStackTrace();
//                Functions.dialogHide();
            }
        });
    }


    private void callGetProfileApi() {

        apiInterface = ApiClient.getClient(EditProfileActivity.this).create(ApiInterface.class);
        final Call<ProfileUpdateApiResponse> loginApiResponseCall = apiInterface.UpdateProfileDetails(EdtName.getText().toString());
        loginApiResponseCall.enqueue(new Callback<ProfileUpdateApiResponse>() {
            @Override
            public void onResponse(Call<ProfileUpdateApiResponse> call, Response<ProfileUpdateApiResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        Functions.dialogHide();

                        Toast.makeText(EditProfileActivity.this, response.body().getMsg(), Toast.LENGTH_LONG).show();
                        if (cd.isConnectingToInternet()) {
                            Functions.dialogShow(EditProfileActivity.this);
                            callProfileApiResponse();
                        } else {
                            Snackbar snackbar1 = Snackbar.make(LLProfileMain, getResources().getString(R.string.check_internet_connection), Snackbar.LENGTH_LONG);
                            snackbar1.show();
                        }


                    } else {

                        Toast.makeText(EditProfileActivity.this, response.body().getMsg(), Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<ProfileUpdateApiResponse> call, Throwable t) {
                Functions.dialogHide();
            }
        });
    }

//    private void callChangePasswordApi() {
//
//        apiInterface = ApiClient.getClient(ProfileActivity.this).create(ApiInterface.class);
//        final Call<AccountVerifyResponse> loginApiResponseCall = apiInterface.ChangePasswordApi(edtCurrentPassword.getText().toString(), edtConfirmPassword.getText().toString());
//        loginApiResponseCall.enqueue(new Callback<AccountVerifyResponse>() {
//            @Override
//            public void onResponse(Call<AccountVerifyResponse> call, Response<AccountVerifyResponse> response) {
//                try {
//                    Functions.dialogHide();
//                    if (response.body().isResult()) {
//                        Snackbar snackbar = Snackbar
//                                .make(LLProfileMain, response.body().getMessage(), Snackbar.LENGTH_SHORT);
//                        snackbar.show();
//                        edtCurrentPassword.setText("");
//                        edtNewPassword.setText("");
//                        edtConfirmPassword.setText("");
//
//                    } else {
//                        Snackbar snackbar = Snackbar
//                                .make(LLProfileMain, response.body().getMessage(), Snackbar.LENGTH_LONG);
//                        snackbar.show();
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<AccountVerifyResponse> call, Throwable t) {
//                Functions.dialogHide();
//            }
//        });
//    }

    public static String encodeTobase64(Bitmap image) {
        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        Base64Images = Base64.encodeToString(b, Base64.DEFAULT);
        return imageEncoded;
    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "IMG_" + Calendar.getInstance().getTime(), null);
        return Uri.parse(path);
    }


    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getContentResolver() != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }

    public static String getPath(Context context, Uri uri) {
        // DocumentProvider
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(uri)) {// DownloadsProvider
                String decodedURI = Uri.decode(uri.toString());
                if (decodedURI.contains("raw:")) {
                    return decodedURI.substring(decodedURI.indexOf("raw:") + 4);
                }

                final String id = DocumentsContract.getDocumentId(Uri.parse(decodedURI));

                Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
              /*  String[] contentUriPrefixesToTry = new String[]{
                        "content://downloads/public_downloads",
                        "content://downloads/my_downloads",
                        "content://downloads/all_downloads"
                };
                for (String contentUriPrefix : contentUriPrefixesToTry) {
                    Uri contentUri = ContentUris.withAppendedId(Uri.parse(contentUriPrefix), Long.valueOf(id));
                    try {
                        return getDataColumn(context, contentUri, null, null);
                    } catch (NumberFormatException e) {
                        return "empty";
                    }
                }*/


            } else if (isMediaDocument(uri)) { // MediaProvider
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(context, contentUri, selection, selectionArgs);

            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {// MediaStore (and general)
            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();
            return getDataColumn(context, uri, null, null);

        } else if ("file".equalsIgnoreCase(uri.getScheme())) {// File
            return uri.getPath();
        }
        return null;
    }


    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
