package com.example.athletics.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaScannerConnection;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.PowerManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.example.Athletics.R;
import com.example.athletics.Activity.EmailVerifyActivity;
import com.example.athletics.Activity.LoginActivity;
import com.example.athletics.Activity.VideoViewActivity;
import com.example.athletics.Model.AthleteProfileVideosItem;
import com.example.athletics.Model.LikeVideoApiResponse;
import com.example.athletics.Model.VideoCountIncrementResponse;
import com.example.athletics.Retrofit.ApiClient;
import com.example.athletics.Retrofit.ApiInterface;
import com.example.athletics.Utils.ConnectionDetector;
import com.example.athletics.Utils.Functions;
import com.example.athletics.Utils.SessionManager;
import com.google.android.material.snackbar.Snackbar;
import com.jaedongchicken.ytplayer.YoutubePlayerView;
import com.jaedongchicken.ytplayer.model.PlaybackQuality;
import com.jaedongchicken.ytplayer.model.YTParams;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AthleteVideoAdapter extends RecyclerView.Adapter<AthleteVideoAdapter.Myviewholder> {
    Context context;
    String fileN = null;
    List<AthleteProfileVideosItem> muscles;
    Dialog downloadDialog;
    NotificationManager notificationManager;
    PendingIntent pendingIntent;
    String NOTIFICATION_CHANNEL_ID = "101";
    Intent intent;
    public ApiInterface apiInterface;
    public ConnectionDetector cd;
    public boolean isMute = false;


    public AthleteVideoAdapter(Activity activity, List<AthleteProfileVideosItem> muscles) {
        this.context = activity;
        this.muscles = muscles;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Myviewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_explore_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final Myviewholder holder, final int position) {
        final AthleteProfileVideosItem bean = muscles.get(position);
        cd = new ConnectionDetector(context);

        holder.Tv_PostTitle.setText(bean.getTitle());
        holder.TvViewCount.setText(String.valueOf(bean.getViews()));

        if (bean.getLikes().size() > 0) {
            holder.TvLikeCount.setText(String.valueOf(bean.getLikes().size()));
        } else {
            holder.TvLikeCount.setText("0");
        }


        if (bean.isIsLike()) {
            holder.imgLike.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_heart_fill));
            holder.TvLikeCount.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        } else {
            holder.imgLike.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_heart));
            holder.TvLikeCount.setTextColor(context.getResources().getColor(R.color.white));
        }


        YTParams params = new YTParams();
        //  params.setControls(0); // hide control
        params.setVolume(100); // volume control
        params.setPlaybackQuality(PlaybackQuality.small); //

        holder.simpleVideoView.initializeWithCustomURL(bean.getVideo(), bean.getThumb(), params, new YoutubePlayerView.YouTubeListener() {
            @Override
            public void onReady() {
                holder.simpleVideoView.reload();
                holder.simpleVideoView.requestLayout();
                holder.simpleVideoView.play();


            }

            @Override
            public void onStateChange(YoutubePlayerView.STATE state) {

            }

            @Override
            public void onPlaybackQualityChange(String arg) {

            }

            @Override
            public void onPlaybackRateChange(String arg) {

            }

            @Override
            public void onError(String arg) {

            }

            @Override
            public void onApiChange(String arg) {

            }

            @Override
            public void onCurrentSecond(double second) {

            }

            @Override
            public void onDuration(double duration) {

            }

            @Override
            public void logs(String log) {

            }
        });

        holder.simpleVideoView.play();
        if (cd.isConnectingToInternet()) {
            CallVideoCounIncrementResponse(String.valueOf(bean.getId()), bean.getViews(), holder.TvViewCount);
        } else {
            Snackbar snackbar = Snackbar.make(holder.LLItemAthlete, context.getResources().getString(R.string.check_internet_connection), Snackbar.LENGTH_LONG);
            snackbar.show();
        }

        holder.LLMuteUnMute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!isMute) {
                    AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                    am.setStreamMute(AudioManager.STREAM_MUSIC, true);
                    isMute = true;
                    holder.imgPlay.setVisibility(View.VISIBLE);
                    holder.imgPlay.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_volume_off));
                } else {

                    AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                    am.setStreamMute(AudioManager.STREAM_MUSIC, false);
                    isMute = false;
                    holder.imgPlay.setVisibility(View.VISIBLE);
                    holder.imgPlay.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_volume_up));
                }
                ImgageDispplaySomeTimes(holder.imgPlay);

            }
        });


        holder.imgLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (new SessionManager(context).getUserID().equalsIgnoreCase("")) {
                    LoginAlertDialog();
                } else if (new SessionManager(context).getKeyUserActive().equalsIgnoreCase("null")) {
                    Intent intent = new Intent(context, EmailVerifyActivity.class);
                    context.startActivity(intent);
                    Functions.animNext(context);
                } else {
                    if (cd.isConnectingToInternet()) {
                        Functions.dialogShow(context);
                        CallLikeUnlikeVideoApiResponse(String.valueOf(bean.getId()), holder.imgLike, holder.TvLikeCount);

                    } else {
                        Snackbar snackbar = Snackbar.make(holder.LLItemAthlete, context.getResources().getString(R.string.check_internet_connection), Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                }


            }
        });

        holder.imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String path = "android.resource://" + context.getPackageName() + "/" + R.raw.dummy_two; //should be local path of downloaded video
//
//                ContentValues content = new ContentValues(4);
//                content.put(MediaStore.Video.VideoColumns.DATE_ADDED,
//                        System.currentTimeMillis() / 1000);
//                content.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4");
//                content.put(MediaStore.Video.Media.DATA, path);
//
//                ContentResolver resolver = context.getContentResolver();
//                Uri uri = resolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, content);
//
//                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
//                sharingIntent.setType("video/*");
//                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Athletics App");
//                sharingIntent.putExtra(Intent.EXTRA_TEXT, "This is video description");
//                sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
//                context.startActivity(Intent.createChooser(sharingIntent, "Share Video"));


            }
        });

        holder.ImgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Athletic App");
                intent.putExtra(Intent.EXTRA_TEXT, bean.getVideo());
                context.startActivity(Intent.createChooser(intent, "Share Video"));
            }
        });


        holder.imgFullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, VideoViewActivity.class).putExtra("fullScreenInd", "y").putExtra("VideoUrl", bean.getVideo()));
                Functions.animNext(context);
            }
        });


    }

    private void ImgageDispplaySomeTimes(ImageView imgPlay) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                imgPlay.setVisibility(View.GONE);
            }
        }, 1000);


    }


    @Override
    public int getItemCount() {
        return muscles.size();
    }


    public class Myviewholder extends RecyclerView.ViewHolder {
        public ImageView imgLike, imgView, imgFullscreen, imgPlay, ImgShare;
        public TextView TvLikeCount, TvViewCount, Tv_PostTitle;
        public LinearLayout LLUserProfile, LLMuteUnMute;
        public YoutubePlayerView simpleVideoView;
        private RelativeLayout LLItemAthlete;
        //        public CircularProgressIndicator RoundProgress;
        public ProgressBar videoProgressbar;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            Tv_PostTitle = itemView.findViewById(R.id.Tv_PostTitle);
            imgLike = itemView.findViewById(R.id.imgLike);
            imgView = itemView.findViewById(R.id.imgView);
            ImgShare = itemView.findViewById(R.id.ImgShare);
//            imgPlay = itemView.findViewById(R.id.imgPlay);
            imgFullscreen = itemView.findViewById(R.id.imgFullscreen);
            TvLikeCount = itemView.findViewById(R.id.TvLikeCount);
            TvViewCount = itemView.findViewById(R.id.TvViewCount);
            LLUserProfile = itemView.findViewById(R.id.LLUserProfile);
            LLItemAthlete = itemView.findViewById(R.id.LLItemAthlete);
            simpleVideoView = itemView.findViewById(R.id.simpleVideoView);
            LLMuteUnMute = itemView.findViewById(R.id.LLMuteUnMute);
//            RoundProgress = itemView.findViewById(R.id.RoundProgress);
            videoProgressbar = itemView.findViewById(R.id.videoProgressbar);

        }
    }


    public void newDownload(String url) {
        final DownloadTask downloadTask = new DownloadTask(context);
        downloadTask.execute(url);
    }

    public class DownloadTask extends AsyncTask<String, Integer, String> {
        private Context context;
        private PowerManager.WakeLock mWakeLock;

        public DownloadTask(Context context) {
            this.context = context;
        }

        private NumberProgressBar bnp;

        @Override
        protected String doInBackground(String... sUrl) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }

                int fileLength = connection.getContentLength();

                input = connection.getInputStream();
                fileN = "" + UUID.randomUUID().toString().substring(0, 10) + ".mp4";
//                File filename = new File(Environment.getDataDirectory() + "Athletic" + fileN);
                File filename = new File(Environment.getExternalStorageDirectory() + "/DCIM/Camera/" + fileN);

//                String filePath = context.getFilesDir().getPath().toString() + UUID.randomUUID().toString().substring(0, 10) + ".mp4";
//                File filename = new File(filePath);


                output = new FileOutputStream(filename);

                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    if (fileLength > 0)
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire();
            LayoutInflater dialogLayout = LayoutInflater.from(context);
            View DialogView = dialogLayout.inflate(R.layout.dialog_downloading, null);
            downloadDialog = new Dialog(context, R.style.Theme_Dialog);
            downloadDialog.getWindow().setBackgroundDrawable(context.getResources().getDrawable(R.drawable.dialog_round));
            downloadDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            downloadDialog.setContentView(DialogView);
//            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//            lp.copyFrom(downloadDialog.getWindow().getAttributes());
//            lp.width = (context.getResources().getDisplayMetrics().widthPixels);
//            lp.height = (int) (context.getResources().getDisplayMetrics().heightPixels * 0.65);
//            downloadDialog.getWindow().setAttributes(lp);

            TextView cancel = (TextView) DialogView.findViewById(R.id.tvDialogCancel);
            TextView background = (TextView) DialogView.findViewById(R.id.tvBackground);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //stopping the Asynctask
                    cancel(true);
                    downloadDialog.dismiss();

                }
            });

            background.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    downloadDialog.dismiss();
                }
            });
            downloadDialog.setCancelable(false);
            downloadDialog.setCanceledOnTouchOutside(false);
            bnp = (NumberProgressBar) DialogView.findViewById(R.id.number_progress_bar);
            bnp.setProgress(0);
            bnp.setMax(100);
            downloadDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            bnp.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            mWakeLock.release();
            downloadDialog.dismiss();
            if (result != null) {
                Toast.makeText(context, "Download error: " + result, Toast.LENGTH_LONG).show();
            } else {
//                Toast.makeText(context, "File downloaded", Toast.LENGTH_SHORT).show();
                addNotification(fileN);
            }

            MediaScannerConnection.scanFile(context,
//                    new String[]{Environment.getDataDirectory() + "Athletic" + fileN}, null,
                    new String[]{Environment.getExternalStorageDirectory() + "/DCIM/Camera/" + fileN}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String newpath, Uri newuri) {
//                            Log.i("ExternalStorage", "Scanned " + newpath + ":");
//                            Log.i("ExternalStorage", "-> uri=" + newuri);
                        }
                    });

        }
    }

    private void addNotification(String VideoName) {

//        String filename = Environment.getExternalStorageDirectory() + "/DCIM/Camera/";
//        intent = new Intent(Intent.ACTION_VIEW, Uri.parse(filename));
        intent = new Intent(Intent.ACTION_VIEW,
                android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);

        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Notification", NotificationManager.IMPORTANCE_HIGH);

            //Configure Notification Channel
            notificationChannel.enableLights(true);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }


        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_app_logo);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_app_logo)
//                .setSmallIcon(R.drawable.aalaf_qatar_logo)
                .setContentTitle("Athletic App")
                .setAutoCancel(true)
                .setSound(defaultSound)
                .setContentText("Video download successfully")
//                .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                .setContentIntent(pendingIntent)
//                .setLargeIcon(bitmap)
                .setPriority(Notification.PRIORITY_MAX);

        int num = (int) System.currentTimeMillis();
        notificationManager.notify(num, notificationBuilder.build());


    }

    public void CallVideoCounIncrementResponse(String VideoId, int views, TextView tvViewCount) {

        apiInterface = ApiClient.getClient(context).create(ApiInterface.class);
        Call<VideoCountIncrementResponse> loginApiResponseCall = apiInterface.GetVideoIncrementCount(VideoId);
        loginApiResponseCall.enqueue(new Callback<VideoCountIncrementResponse>() {
            @Override
            public void onResponse(Call<VideoCountIncrementResponse> call, Response<VideoCountIncrementResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        int viewCount = views + 1;
                        tvViewCount.setText(String.valueOf(viewCount));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<VideoCountIncrementResponse> call, Throwable t) {
//                Functions.dialogHide();
            }
        });
    }

    public void CallLikeUnlikeVideoApiResponse(String VideoId, ImageView imgLike, TextView tvLikeCount) {

        apiInterface = ApiClient.getClient(context).create(ApiInterface.class);
        Call<LikeVideoApiResponse> loginApiResponseCall = apiInterface.GetLikeUnlikeVideo(VideoId);
        loginApiResponseCall.enqueue(new Callback<LikeVideoApiResponse>() {
            @Override
            public void onResponse(Call<LikeVideoApiResponse> call, Response<LikeVideoApiResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        Functions.dialogHide();

                        if (response.body().getData().isIsLike()) {
                            imgLike.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_heart_fill));
                            tvLikeCount.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                            int count = Integer.parseInt(tvLikeCount.getText().toString()) + 1;
                            tvLikeCount.setText(String.valueOf(count));
                        } else {
                            imgLike.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_heart));
                            tvLikeCount.setTextColor(context.getResources().getColor(R.color.white));
                            int count = Integer.parseInt(tvLikeCount.getText().toString()) - 1;
                            tvLikeCount.setText(String.valueOf(count));
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<LikeVideoApiResponse> call, Throwable t) {
//                Functions.dialogHide();
            }
        });
    }

    public void LoginAlertDialog() {
        final Dialog builder = new Dialog(context, R.style.Theme_Dialog);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view1 = LayoutInflater.from(context).inflate(R.layout.dialog_customise, null);

        TextView tvDialogok = (TextView) view1.findViewById(R.id.tvDialogok);
        TextView tvDialogMessage = (TextView) view1.findViewById(R.id.tvDialogMessage);
        TextView tvDialogCancel = (TextView) view1.findViewById(R.id.tvDialogCancel);
        tvDialogMessage.setText(R.string.please_login_first_to_continue_in_app);

        tvDialogok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);
                Functions.animNext(context);
            }
        });

        tvDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.dismiss();
            }
        });


        builder.setCancelable(true);
        builder.setCanceledOnTouchOutside(true);
        builder.getWindow().setBackgroundDrawable(context.getResources().getDrawable(R.drawable.dialog_round));
        // builder.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        builder.setContentView(view1);
        builder.show();


    }


}
