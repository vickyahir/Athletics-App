package com.example.athletics.Activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import com.example.Athletics.R;
import com.example.athletics.Model.DefaultApiResponse;
import com.example.athletics.Retrofit.ApiClient;
import com.example.athletics.Retrofit.ApiInterface;
import com.example.athletics.Utils.Functions;
import com.example.athletics.Utils.SessionManager;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadVideoActivity extends BaseActivity {
    private ImageView imgSearch, imgBack, imgMenu, imgVideoView, imgVideoUpload, imgImageUpload, imgThumbnailView;
    private Toolbar toolbarMain;
    private TextView TvTitle, TvUploadNow;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 100;
    private FrameLayout frmVideoView, frmImageView;
    private EditText EdtAthleteTitle;
    String ImageUri = "", VideoUri = "";
    private RelativeLayout RelUploadVideoMain;
    private static String Base64Images = "";
    private static final int SELECT_VIDEO = 200;
    private String[] permissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_video);
        super.onCreateMenu();
        super.onMenuSelect(3);


        initView();
        loadData();
        setClickListener();
    }

    @Override
    protected void onResume() {
        if (!new SessionManager(UploadVideoActivity.this).getUserRole().equalsIgnoreCase("2")) {
            UploadVideoAlertDialog();
        }
        super.onResume();
    }

    private void initView() {
        toolbarMain = findViewById(R.id.toolbarMain);
        imgBack = toolbarMain.findViewById(R.id.imgBack);
        TvTitle = toolbarMain.findViewById(R.id.TvTitle);
        TvUploadNow = findViewById(R.id.TvUploadNow);
        imgMenu = toolbarMain.findViewById(R.id.imgMenu);
        imgVideoView = findViewById(R.id.imgVideoView);
        frmVideoView = findViewById(R.id.frmVideoView);
        frmImageView = findViewById(R.id.frmImageView);
        imgVideoUpload = findViewById(R.id.imgVideoUpload);
        imgImageUpload = findViewById(R.id.imgImageUpload);
        imgThumbnailView = findViewById(R.id.imgThumbnailView);
        EdtAthleteTitle = findViewById(R.id.EdtAthleteTitle);
        RelUploadVideoMain = findViewById(R.id.RelUploadVideoMain);

        TvTitle.setText(getResources().getString(R.string.upload_video));


    }

    private void loadData() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Functions.animBack(UploadVideoActivity.this);
    }


    public void UploadVideoAlertDialog() {
        final Dialog builder = new Dialog(activity, R.style.Theme_Dialog);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view1 = LayoutInflater.from(activity).inflate(R.layout.dialog_customise, null);

        TextView tvDialogok = (TextView) view1.findViewById(R.id.tvDialogok);
        TextView tvDialogMessage = (TextView) view1.findViewById(R.id.tvDialogMessage);
        TextView tvDialogCancel = (TextView) view1.findViewById(R.id.tvDialogCancel);
        tvDialogMessage.setText(R.string.only_athlete_upload_video);

        tvDialogok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SessionManager(activity).logoutUser();
                Intent intent = new Intent(UploadVideoActivity.this, LoginActivity.class);
                startActivity(intent);
                Functions.animNext(UploadVideoActivity.this);
            }
        });

        tvDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        builder.setCancelable(false);
        builder.setCanceledOnTouchOutside(false);
        builder.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.dialog_round));
        // builder.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        builder.setContentView(view1);
        builder.show();


    }


    private void setClickListener() {

        TvUploadNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (EdtAthleteTitle.getText().toString().equalsIgnoreCase("")) {
                    Snackbar snackbar = Snackbar
                            .make(RelUploadVideoMain, getResources().getString(R.string.please_enter_your_video_title), Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else if (VideoUri.equalsIgnoreCase("")) {
                    Snackbar snackbar = Snackbar
                            .make(RelUploadVideoMain, getResources().getString(R.string.please_enter_your_profile_video), Snackbar.LENGTH_SHORT);
                    snackbar.show();
                } else {
                    if (cd.isConnectingToInternet()) {
                        Functions.dialogShow(UploadVideoActivity.this);
                        CallAthleteProfileVideoUploadApiResponse();
                    } else {
                        Snackbar snackbar = Snackbar.make(RelUploadVideoMain, getResources().getString(R.string.check_internet_connection), Snackbar.LENGTH_LONG);
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


        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(UploadVideoActivity.this, "Menu clicked", Toast.LENGTH_SHORT).show();
            }
        });

        imgVideoUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!EasyPermissions.hasPermissions(UploadVideoActivity.this, permissions)) {
                    EasyPermissions.requestPermissions(UploadVideoActivity.this, getString(R.string.please_allow_app), 1, permissions);
                } else {
                    Intent intent = new Intent();
                    intent.setType("video/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select a Video "), SELECT_VIDEO);
                }


            }
        });

        imgImageUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!EasyPermissions.hasPermissions(UploadVideoActivity.this, permissions)) {
                    EasyPermissions.requestPermissions(UploadVideoActivity.this, getString(R.string.please_allow_app), 1, permissions);
                } else {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    //intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, getResources().getString(R.string.select_picture)), 100);
                }


            }
        });

    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                AlertDialog alertDialog = new AlertDialog.Builder(UploadVideoActivity.this).create();
                alertDialog.setTitle("Athletics App");
                alertDialog.setMessage("Read Storage permission is necessary to upload video.");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", getPackageName(), null);
                                intent.setData(uri);
                                startActivity(intent);
                            }
                        });

                alertDialog.show();


            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_VIDEO) {

                Uri selectedVideoUri = data.getData();

                if (getVideoPath(selectedVideoUri)) {

                    Snackbar snackbar = Snackbar.make(RelUploadVideoMain, getResources().getString(R.string.upload_video_max_validation), Snackbar.LENGTH_LONG);
                    snackbar.show();

                } else {
                    Bitmap bmThumbnail = null;
                    frmVideoView.setVisibility(View.VISIBLE);

                    VideoUri = getPath(UploadVideoActivity.this, selectedVideoUri);


                    MediaMetadataRetriever mMMR = new MediaMetadataRetriever();
                    mMMR.setDataSource(UploadVideoActivity.this, selectedVideoUri);
                    bmThumbnail = mMMR.getFrameAtTime();
                    imgVideoView.setImageBitmap(bmThumbnail);

//                    ImageUri = BitMapToString(bmThumbnail);
                }

            } else if (requestCode == 100 && null != data) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    if (bitmap != null) {
                        frmImageView.setVisibility(View.VISIBLE);
                        imgThumbnailView.setImageBitmap(bitmap);
                        ImageUri = getPath(UploadVideoActivity.this, data.getData());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }


    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }


    public boolean getVideoPath(Uri uri) {

        boolean IsResult = false;

        String[] projection = {MediaStore.Video.Media.DATA, MediaStore.Video.Media.SIZE, MediaStore.Video.Media.DURATION};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        cursor.moveToFirst();
        String filePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
        long fileSize = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));
        long duration = TimeUnit.MILLISECONDS.toSeconds(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)));


        //some extra potentially useful data to help with filtering if necessary
        System.out.println("size: " + fileSize);
        System.out.println("path: " + filePath);
        System.out.println("duration: " + duration);
        String size = formatSize(UploadVideoActivity.this, fileSize);
        int sizeofVideo = 0;
        if (size.contains("MB")) {
            sizeofVideo = Integer.parseInt(size.replaceAll("MB", ""));
        }
        if (duration > 60) {
            IsResult = true;
        } else if (sizeofVideo > 50) {
            IsResult = true;
        }

        if (IsResult) {
            return true;
        } else {
            return false;
        }
    }

    public static String formatSize(Context context, long size) {
        String suffix = null;

        if (size >= 1024) {
            suffix = "Bytes";
            size /= 1024;
            if (size >= 1024) {
                suffix = "MB";
                size /= 1024;
            }
        }
        StringBuilder resultBuffer = new StringBuilder(Long.toString(size));

        int commaOffset = resultBuffer.length() - 3;
        while (commaOffset > 0) {
            resultBuffer.insert(commaOffset, ',');
            commaOffset -= 3;
        }
        if (suffix != null) resultBuffer.append(suffix);
        return resultBuffer.toString();
    }


    public void CallAthleteProfileVideoUploadApiResponse() {
        RequestBody title = null;
        if (!EdtAthleteTitle.getText().toString().equalsIgnoreCase("")) {
            title = RequestBody.create(MediaType.parse("multipart/form-data"), EdtAthleteTitle.getText().toString());
        } else {
            title = RequestBody.create(MediaType.parse("multipart/form-data"), "");
        }


        MultipartBody.Part thumb = null;
        if (!ImageUri.equals("")) {
            File image_banner = new File(ImageUri);
            RequestBody surveyBody = RequestBody.create(MediaType.parse("*/*"), image_banner);
            thumb = MultipartBody.Part.createFormData("thumb", image_banner.getName(), surveyBody);
        }

        MultipartBody.Part video = null;
        if (!VideoUri.equals("")) {
            File video_banner = new File(VideoUri);
            RequestBody surveyBody = RequestBody.create(MediaType.parse("*/*"), video_banner);
            video = MultipartBody.Part.createFormData("video", video_banner.getName(), surveyBody);
        }


        apiInterface = ApiClient.getClient(this).create(ApiInterface.class);
        Call<DefaultApiResponse> loginApiResponseCall = apiInterface.GetAthleteVideoUpload(title, thumb, video);
        loginApiResponseCall.enqueue(new Callback<DefaultApiResponse>() {
            @Override
            public void onResponse(Call<DefaultApiResponse> call, Response<DefaultApiResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        Functions.dialogHide();
                        Toast.makeText(activity, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
//                        Snackbar snackbar = Snackbar.make(RelCoachInformationMain, response.body().getMsg(), Snackbar.LENGTH_LONG);
//                        snackbar.show();
                        Intent intent = new Intent(activity, MyProfileActivity.class);
                        startActivity(intent);
                        Functions.animNext(activity);
                    }
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


}

