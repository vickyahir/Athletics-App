package com.example.athletics.Activity;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.Athletics.R;
import com.example.athletics.Retrofit.ApiInterface;
import com.example.athletics.Utils.ConnectionDetector;
import com.example.athletics.Utils.Functions;

import java.util.concurrent.TimeUnit;


public class BaseActivity extends AppCompatActivity {

    public Activity activity = BaseActivity.this;
    public ApiInterface apiInterface;
    public ConnectionDetector cd;
    public ImageView imgPlusMenu, imgHomeMenu, imgSettingMenu, imgNotificationMenu, imgProfileMenu;


    protected void onCreateMenu() {
        cd = new ConnectionDetector(activity);


        imgPlusMenu = findViewById(R.id.imgPlusMenu);
        imgHomeMenu = findViewById(R.id.imgHomeMenu);
        imgSettingMenu = findViewById(R.id.imgSettingMenu);
        imgNotificationMenu = findViewById(R.id.imgNotificationMenu);
        imgProfileMenu = findViewById(R.id.imgProfileMenu);


        imgPlusMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, UploadVideoActivity.class);
                startActivity(intent);
                Functions.animNext(activity);

            }
        });

        imgHomeMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, HomeActivity.class);
//                Intent intent = new Intent(activity, MainActivity.class);
                startActivity(intent);
                Functions.animNext(activity);

            }
        });

        imgSettingMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, SettingActivity.class);
                startActivity(intent);
                Functions.animNext(activity);
            }
        });

        imgNotificationMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(activity, NotificationActivity.class);
                startActivity(intent);
                Functions.animNext(activity);
            }
        });

        imgProfileMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(activity, MyProfileActivity.class);
                startActivity(intent);
                Functions.animNext(activity);
            }
        });

    }

    protected void onMenuSelect(int position) {
        if (position == 1) {
            imgHomeMenu.setColorFilter(activity.getResources().getColor(R.color.colorPrimary));
            imgPlusMenu.setColorFilter(activity.getResources().getColor(R.color.black));
            imgNotificationMenu.setColorFilter(activity.getResources().getColor(R.color.black));
            imgProfileMenu.setColorFilter(activity.getResources().getColor(R.color.black));
            imgSettingMenu.setColorFilter(activity.getResources().getColor(R.color.black));
        } else if (position == 2) {
            imgSettingMenu.setColorFilter(activity.getResources().getColor(R.color.colorPrimary));
            imgHomeMenu.setColorFilter(activity.getResources().getColor(R.color.black));
            imgNotificationMenu.setColorFilter(activity.getResources().getColor(R.color.black));
            imgProfileMenu.setColorFilter(activity.getResources().getColor(R.color.black));
            imgPlusMenu.setColorFilter(activity.getResources().getColor(R.color.black));
        } else if (position == 3) {
            imgPlusMenu.setColorFilter(activity.getResources().getColor(R.color.colorPrimary));
            imgHomeMenu.setColorFilter(activity.getResources().getColor(R.color.black));
            imgNotificationMenu.setColorFilter(activity.getResources().getColor(R.color.black));
            imgProfileMenu.setColorFilter(activity.getResources().getColor(R.color.black));
            imgSettingMenu.setColorFilter(activity.getResources().getColor(R.color.black));
        } else if (position == 4) {
            imgNotificationMenu.setColorFilter(activity.getResources().getColor(R.color.colorPrimary));
            imgHomeMenu.setColorFilter(activity.getResources().getColor(R.color.black));
            imgPlusMenu.setColorFilter(activity.getResources().getColor(R.color.black));
            imgProfileMenu.setColorFilter(activity.getResources().getColor(R.color.black));
            imgSettingMenu.setColorFilter(activity.getResources().getColor(R.color.black));

        } else if (position == 5) {
            imgProfileMenu.setColorFilter(activity.getResources().getColor(R.color.colorPrimary));
            imgHomeMenu.setColorFilter(activity.getResources().getColor(R.color.black));
            imgNotificationMenu.setColorFilter(activity.getResources().getColor(R.color.black));
            imgPlusMenu.setColorFilter(activity.getResources().getColor(R.color.black));
            imgSettingMenu.setColorFilter(activity.getResources().getColor(R.color.black));

        }
    }

    private String getVideoPath(Uri uri) {
        String[] projection = { MediaStore.Video.Media.DATA, MediaStore.Video.Media.SIZE, MediaStore.Video.Media.DURATION};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        cursor.moveToFirst();
        String filePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
        int fileSize = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));
        long duration = TimeUnit.MILLISECONDS.toSeconds(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)));


        //some extra potentially useful data to help with filtering if necessary
        System.out.println("size: " + fileSize);
        System.out.println("path: " + filePath);
        System.out.println("duration: " + duration);

        return filePath;
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





}
