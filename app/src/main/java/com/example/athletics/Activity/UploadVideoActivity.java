package com.example.athletics.Activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;


import com.example.Athletics.R;
import com.example.athletics.Utils.Functions;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class UploadVideoActivity extends BaseActivity {
    private ImageView imgSearch, imgBack, imgMenu, imgVideoView, imgVideoUpload;
    private Toolbar toolbarMain;
    private TextView TvTitle;
    private static final int SELECT_VIDEO = 3;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 100;
    private FrameLayout frmVideoView;


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


    private void initView() {
        toolbarMain = findViewById(R.id.toolbarMain);
        imgBack = toolbarMain.findViewById(R.id.imgBack);
        TvTitle = toolbarMain.findViewById(R.id.TvTitle);
        imgMenu = toolbarMain.findViewById(R.id.imgMenu);
        imgVideoView = findViewById(R.id.imgVideoView);
        frmVideoView = findViewById(R.id.frmVideoView);
        imgVideoUpload = findViewById(R.id.imgVideoUpload);

        TvTitle.setText(getResources().getString(R.string.upload_video));

    }

    private void loadData() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Functions.animBack(UploadVideoActivity.this);
    }


    private void setClickListener() {

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

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {

                        // Should we show an explanation?
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            shouldShowRequestPermissionRationale(
                                    Manifest.permission.READ_EXTERNAL_STORAGE);// Explain to the user why we need to read the contacts
                        }

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                        }

                        // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
                        // app-defined int constant that should be quite unique

                        return;
                    } else {
                        Intent intent = new Intent();
                        intent.setType("video/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select a Video "), SELECT_VIDEO);

                    }
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
                alertDialog.setMessage("Permission denied from setting ! Please give permission to upload video.");
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
                Bitmap bmThumbnail;

                System.out.println("SELECT_VIDEO");
                Uri selectedImageUri = data.getData();
//                Toast.makeText(activity, "" + selectedImageUri, Toast.LENGTH_SHORT).show();
                frmVideoView.setVisibility(View.VISIBLE);
//                Uri myUri = Uri.parse(getRealPathFromURIForVideo(selectedImageUri));

                try {

                    String GoogleUrl = selectedImageUri.toString();
                    if (GoogleUrl.contains("google")) {
                        Uri myUri = Uri.parse(getImageUrlWithAuthority(UploadVideoActivity.this, selectedImageUri));
                        imgVideoView.setImageURI(myUri);
                    } else {
                        bmThumbnail = ThumbnailUtils.createVideoThumbnail(getRealPathFromURIForVideo(selectedImageUri), MediaStore.Images.Thumbnails.MINI_KIND);
                        imgVideoView.setImageBitmap(bmThumbnail);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


//                selectedPath = getPath(selectedImageUri);
//                System.out.println("SELECT_VIDEO Path : " + selectedPath);

            }
        }
    }

    private String getRealPathFromURIForVideo(Uri selectedVideoUri) {
        String wholeID = DocumentsContract.getDocumentId(selectedVideoUri);
        String id = wholeID.split(":")[1];

        String[] column = {MediaStore.Video.Media.DATA};
        String sel = MediaStore.Video.Media._ID + "=?";
        Cursor cursor = getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, column, sel, new String[]{id}, null);
        String filePath = "";

        int columnIndex = cursor.getColumnIndex(column[0]);
        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return filePath;
    }


    private static String getImageUrlWithAuthority(Context context, Uri uri) {
        InputStream is = null;

        if (uri.getAuthority() != null) {
            try {
                is = context.getContentResolver().openInputStream(uri);
                Bitmap bmp = BitmapFactory.decodeStream(is);
                return writeToTempImageAndGetPathUri(context, bmp).toString();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private static Uri writeToTempImageAndGetPathUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


}

