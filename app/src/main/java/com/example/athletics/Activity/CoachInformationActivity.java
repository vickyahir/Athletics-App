package com.example.athletics.Activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Athletics.R;
import com.example.athletics.Adapter.CoachPositionAdapter;
import com.example.athletics.Adapter.CoachSportsAdapter;
import com.example.athletics.Model.CategoryPositionResponse;
import com.example.athletics.Model.CoachCategoryDataItem;
import com.example.athletics.Model.CoachCategoryResponse;
import com.example.athletics.Model.CoachInformationApiResponse;
import com.example.athletics.Model.DefaultApiResponse;
import com.example.athletics.Retrofit.ApiClient;
import com.example.athletics.Retrofit.ApiInterface;
import com.example.athletics.Utils.Functions;
import com.example.athletics.Utils.SessionManager;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoachInformationActivity extends BaseActivity {
    private ImageView imgBack, imgMenu;
    private Toolbar toolbarMain;
    private TextView TvTitle, TvNodataFoundPositions, TvSave, TvProfilePic, TvProfileName, TvUploadVideo, TvVideoName, TvResumeUpload, TvResumeName;
    private LinearLayout LLCoachInformationMain;
    private RelativeLayout RelCoachInformationMain;
    public String SportsIDS = "", PositionStrings = "";
    private RecyclerView rvSports, rvPositions;
    private List<CoachCategoryDataItem> SportsList;
    private List<String> PositionList;
    private EditText edtExperience;
    private CoachSportsAdapter coachSportsAdapter;
    private CoachPositionAdapter coachPositionAdapter;
    private String[] permissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
    };
    String ImageUri = "", VideoUri = "", ResumeUri = "";
    private static final int SELECT_VIDEO = 200;
    private static final int PICK_PDF_REQUEST = 300;
    private static final int READ_STORAGE_PERMISSION_REQUEST_CODE = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_information);
        super.onCreateMenu();
        super.onMenuSelect(5);

        getIntentData();
        initView();
        loadData();
        setClickListener();
    }

    private void getIntentData() {
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initView() {
        toolbarMain = findViewById(R.id.toolbarMain);
        imgBack = toolbarMain.findViewById(R.id.imgBack);
        imgMenu = toolbarMain.findViewById(R.id.imgMenu);
        TvTitle = toolbarMain.findViewById(R.id.TvTitle);
        TvNodataFoundPositions = findViewById(R.id.TvNodataFoundPositions);
        TvResumeUpload = findViewById(R.id.TvResumeUpload);
        TvResumeName = findViewById(R.id.TvResumeName);
        TvSave = findViewById(R.id.TvSave);
        TvProfilePic = findViewById(R.id.TvProfilePic);
        TvProfileName = findViewById(R.id.TvProfileName);
        TvUploadVideo = findViewById(R.id.TvUploadVideo);
        TvVideoName = findViewById(R.id.TvVideoName);
        LLCoachInformationMain = findViewById(R.id.LLCoachInformationMain);
        RelCoachInformationMain = findViewById(R.id.RelCoachInformationMain);
        rvSports = (RecyclerView) findViewById(R.id.rvSports);
        edtExperience = findViewById(R.id.edtExperience);
        rvPositions = (RecyclerView) findViewById(R.id.rvPositions);
        imgMenu.setVisibility(View.INVISIBLE);
        TvTitle.setText(getResources().getString(R.string.basic_information));

        new SessionManager(CoachInformationActivity.this).setKeyCoachSportsids("");
        new SessionManager(CoachInformationActivity.this).setKeyCoachPositionstrings("");

    }

    private void loadData() {
        if (cd.isConnectingToInternet()) {
            Functions.dialogShow(CoachInformationActivity.this);
            LLCoachInformationMain.setVisibility(View.GONE);
            CallCoachCategoryResponse();
            CallMyFollowingApiResponse();
        } else {
            Snackbar snackbar = Snackbar.make(RelCoachInformationMain, getResources().getString(R.string.check_internet_connection), Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Functions.animBack(CoachInformationActivity.this);
    }


    public void CallCoachCategoryResponse() {

        apiInterface = ApiClient.getClient(this).create(ApiInterface.class);
        Call<CoachCategoryResponse> loginApiResponseCall = apiInterface.GetCoachCategoryApi();
        loginApiResponseCall.enqueue(new Callback<CoachCategoryResponse>() {
            @Override
            public void onResponse(Call<CoachCategoryResponse> call, Response<CoachCategoryResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        Functions.dialogHide();
                        LLCoachInformationMain.setVisibility(View.VISIBLE);

                        SportsList = new ArrayList<>();
                        if (response.body().getData().size() > 0) {
                            rvSports.setVisibility(View.VISIBLE);
                            SportsList.addAll(response.body().getData());
                            rvSports.setLayoutManager(new GridLayoutManager(CoachInformationActivity.this, 3));
                            rvSports.setAdapter(new CoachSportsAdapter(CoachInformationActivity.this, SportsList));
                            coachSportsAdapter = new CoachSportsAdapter(CoachInformationActivity.this, SportsList);
//                            rvSports.setAdapter(new CoachSportsAdapter(CoachInformationActivity.this, SportsList));
                            rvSports.setAdapter(coachSportsAdapter);
                        } else {
                            rvSports.setVisibility(View.GONE);
                        }


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<CoachCategoryResponse> call, Throwable t) {
                Functions.dialogHide();
            }
        });
    }

    public void CallCoachCategoryPositionResponse(String PositionIds) {

        apiInterface = ApiClient.getClient(this).create(ApiInterface.class);
        Call<CategoryPositionResponse> loginApiResponseCall = apiInterface.GetCoachCategoryPositionApi(PositionIds);
        loginApiResponseCall.enqueue(new Callback<CategoryPositionResponse>() {
            @Override
            public void onResponse(Call<CategoryPositionResponse> call, Response<CategoryPositionResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        Functions.dialogHide();

                        PositionList = new ArrayList<>();
                        if (response.body().getData().size() > 0) {
                            TvNodataFoundPositions.setVisibility(View.GONE);
                            rvPositions.setVisibility(View.VISIBLE);
                            PositionList.addAll(response.body().getData());
                            rvPositions.setLayoutManager(new GridLayoutManager(CoachInformationActivity.this, 3));
//                            rvPositions.setAdapter(new CoachPositionAdapter(CoachInformationActivity.this, PositionList));
                            coachPositionAdapter = new CoachPositionAdapter(CoachInformationActivity.this, PositionList);
                            rvPositions.setAdapter(coachPositionAdapter);
                        } else {
                            rvPositions.setVisibility(View.GONE);
                            TvNodataFoundPositions.setVisibility(View.VISIBLE);
                        }


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<CategoryPositionResponse> call, Throwable t) {
                Functions.dialogHide();
            }
        });
    }


    public void CallMyFollowingApiResponse() {

        apiInterface = ApiClient.getClient(this).create(ApiInterface.class);
        Call<CoachInformationApiResponse> loginApiResponseCall = apiInterface.GetCoachInformationApi();
        loginApiResponseCall.enqueue(new Callback<CoachInformationApiResponse>() {
            @Override
            public void onResponse(Call<CoachInformationApiResponse> call, Response<CoachInformationApiResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        Functions.dialogHide();
                        if (!response.body().getData().getDetails().equalsIgnoreCase("")) {
                            edtExperience.setText(response.body().getData().getDetails());
                        }


                        List<String> SportsIDSList = new ArrayList<>();
                        SportsIDSList.addAll(response.body().getData().getSports());

                        for (int i = 0; i < SportsIDSList.size(); i++) {
                            SportsIDS = TextUtils.join(",", SportsIDSList);
                        }
                        new SessionManager(CoachInformationActivity.this).setKeyCoachSportsids(SportsIDS);
                        coachSportsAdapter.notifyDataSetChanged();


                        List<String> PositionStringsList = new ArrayList<>();
                        PositionStringsList.addAll(response.body().getData().getPosition());

                        for (int i = 0; i < PositionStringsList.size(); i++) {
                            PositionStrings = TextUtils.join(",", PositionStringsList);
                        }
                        new SessionManager(CoachInformationActivity.this).setKeyCoachPositionstrings(PositionStrings);
                        coachPositionAdapter.notifyDataSetChanged();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<CoachInformationApiResponse> call, Throwable t) {
                Functions.dialogHide();
            }
        });
    }


    private void setClickListener() {

        TvProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!EasyPermissions.hasPermissions(CoachInformationActivity.this, permissions)) {
                    EasyPermissions.requestPermissions(CoachInformationActivity.this, getString(R.string.please_allow_app), 1, permissions);
                } else {

                    Intent intent = new Intent();
                    intent.setType("image/*");
                    //intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), 100);
                }
            }
        });


        TvUploadVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select a Video "), SELECT_VIDEO);

            }
        });

        TvResumeUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!EasyPermissions.hasPermissions(CoachInformationActivity.this, permissions)) {
                    EasyPermissions.requestPermissions(CoachInformationActivity.this, getString(R.string.please_allow_app), 1, permissions);
                } else {
                    Intent intent = new Intent();
                    intent.setType("application/pdf");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PICK_PDF_REQUEST);
                }


            }
        });


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        TvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (cd.isConnectingToInternet()) {
                    Functions.dialogShow(CoachInformationActivity.this);
                    CallCoachProfileUpdateApiResponse();
                } else {
                    Snackbar snackbar = Snackbar.make(RelCoachInformationMain, getResources().getString(R.string.check_internet_connection), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }

            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        if (requestCode == 100 && resultCode == RESULT_OK && null != data) {
            try {
                ImageUri = getPath(CoachInformationActivity.this, data.getData());
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(CoachInformationActivity.this.getContentResolver(), data.getData());
                // ivProfilePic.setImageBitmap(bitmap);
                TvProfileName.setVisibility(View.VISIBLE);
                TvProfileName.setText(queryName(getContentResolver(), data.getData()));
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("Image Exception=", e.toString());
            }

        } else if (requestCode == SELECT_VIDEO && resultCode == RESULT_OK) {

            if (requestCode == SELECT_VIDEO) {

                System.out.println("SELECT_VIDEO");

                Uri selectedVideoUri = data.getData();
                VideoUri = getPath(CoachInformationActivity.this, selectedVideoUri);


                TvVideoName.setVisibility(View.VISIBLE);
//                Uri VideofilePath = null;
//                VideofilePath = data.getData();
//                String filename = "VID_" + VideofilePath.toString().substring(VideofilePath.toString().lastIndexOf("/") + 1);
                TvVideoName.setText(queryName(getContentResolver(), data.getData()));
            }
        } else if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri ResUri;
            ResUri = data.getData();
            ResumeUri = getResumePath(CoachInformationActivity.this, ResUri);
            TvResumeName.setVisibility(View.VISIBLE);
            TvResumeName.setText(queryName(getContentResolver(), data.getData()));
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private String queryName(ContentResolver resolver, Uri uri) {
        Cursor returnCursor =
                resolver.query(uri, null, null, null, null);
        assert returnCursor != null;
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        returnCursor.moveToFirst();
        String name = returnCursor.getString(nameIndex);
        returnCursor.close();
        return name;
    }


    public void CallCoachProfileUpdateApiResponse() {
        RequestBody sports = null;
        if (!new SessionManager(CoachInformationActivity.this).getKeyCoachSportsids().equalsIgnoreCase("")) {
            sports = RequestBody.create(MediaType.parse("multipart/form-data"), new SessionManager(CoachInformationActivity.this).getKeyCoachSportsids().substring(1));
        } else {
            sports = RequestBody.create(MediaType.parse("multipart/form-data"), "");
        }
        RequestBody position = null;
        if (!new SessionManager(CoachInformationActivity.this).getKeyCoachPositionstrings().equalsIgnoreCase("")) {
            position = RequestBody.create(MediaType.parse("multipart/form-data"), new SessionManager(CoachInformationActivity.this).getKeyCoachPositionstrings().substring(1));
        } else {
            position = RequestBody.create(MediaType.parse("multipart/form-data"), "");
        }
        RequestBody details = null;
        if (!edtExperience.getText().toString().equalsIgnoreCase("")) {
            details = RequestBody.create(MediaType.parse("multipart/form-data"), edtExperience.getText().toString());
        } else {
            details = RequestBody.create(MediaType.parse("multipart/form-data"), "");
        }


        MultipartBody.Part image = null;
        if (!ImageUri.equals("")) {
            File image_banner = new File(ImageUri);
            RequestBody surveyBody = RequestBody.create(MediaType.parse("*/*"), image_banner);
            image = MultipartBody.Part.createFormData("image", image_banner.getName(), surveyBody);
        }

        MultipartBody.Part profile_video = null;
        if (!VideoUri.equals("")) {
            File video_banner = new File(VideoUri);
            RequestBody surveyBody = RequestBody.create(MediaType.parse("*/*"), video_banner);
            profile_video = MultipartBody.Part.createFormData("profile_video", video_banner.getName(), surveyBody);
        }

        MultipartBody.Part resume = null;
        if (!ResumeUri.equals("")) {
            File resume_banner = new File(ResumeUri);
            RequestBody surveyBody = RequestBody.create(MediaType.parse("*/*"), resume_banner);
            resume = MultipartBody.Part.createFormData("resume", resume_banner.getName(), surveyBody);
        }
//        RequestBody resume = RequestBody.create(MediaType.parse("multipart/form-data"), ResumeUri);


        apiInterface = ApiClient.getClient(this).create(ApiInterface.class);
        Call<DefaultApiResponse> loginApiResponseCall = apiInterface.CoachProfileUpdateApiResponse(sports, position, details, image, profile_video, resume);
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


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getResumePath(final Context context, final Uri uri) {
        //check here to KITKAT or new version
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {

            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }

            //DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                String fileName = getFilePath(context, uri);
                if (fileName != null) {
                    return Environment.getExternalStorageDirectory().toString() + "/Download/" + fileName;
//                    return Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/" + fileName;
                }

                String id = DocumentsContract.getDocumentId(uri);
                if (id.startsWith("raw:")) {
                    id = id.replaceFirst("raw:", "");
                    File file = new File(id);
                    if (file.exists())
                        return id;
                }

                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getFilePath(context, contentUri);

            }

            // MediaProvider
            else if (isMediaDocument(uri)) {
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
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }


    public static String getFilePath(Context context, Uri uri) {

        Cursor cursor = null;
        final String[] projection = {
                MediaStore.MediaColumns.DISPLAY_NAME
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, null, null,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;

    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }


}