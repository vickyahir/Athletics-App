package com.example.athletics.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.Athletics.R;
import com.example.athletics.Model.DefaultApiResponse;
import com.example.athletics.Model.LikeVideoApiResponse;
import com.example.athletics.Model.MyVideoDataItem;
import com.example.athletics.Retrofit.ApiClient;
import com.example.athletics.Retrofit.ApiInterface;
import com.example.athletics.Utils.ConnectionDetector;
import com.example.athletics.Utils.Functions;
import com.google.android.material.snackbar.Snackbar;
import com.jaedongchicken.ytplayer.YoutubePlayerView;
import com.jaedongchicken.ytplayer.model.PlaybackQuality;
import com.jaedongchicken.ytplayer.model.YTParams;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyVideoCategoryAdapter extends RecyclerView.Adapter<MyVideoCategoryAdapter.Myviewholder> {
    Context context;
    List<MyVideoDataItem> muscles;
    public ApiInterface apiInterface;
    public ConnectionDetector cd;
    public boolean isMute = false;


    public MyVideoCategoryAdapter(Activity activity, List<MyVideoDataItem> muscles) {
        this.context = activity;
        this.muscles = muscles;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Myviewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_video_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final Myviewholder holder, final int position) {
        final MyVideoDataItem bean = muscles.get(position);
        cd = new ConnectionDetector(context);

        holder.Tv_Username.setText(bean.getAthlete().getName());
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


        Glide.with(context).load(bean.getThumb()).into(holder.iv_User);


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

                if (cd.isConnectingToInternet()) {
                    Functions.dialogShow(context);
                    CallLikeUnlikeVideoApiResponse(String.valueOf(bean.getId()), holder.imgLike, holder.TvLikeCount);
                } else {
                    Snackbar snackbar = Snackbar.make(holder.LLExploreItem, context.getResources().getString(R.string.check_internet_connection), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
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

        holder.ImgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (cd.isConnectingToInternet()) {
                    showVideoDeleteDialog(holder.LLExploreItem, bean, muscles, position);
                } else {
                    Snackbar snackbar = Snackbar.make(holder.LLExploreItem, context.getResources().getString(R.string.check_internet_connection), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }

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
        public ImageView iv_User, imgLike, imgView, ImgMenu, imgFullscreen, imgPlay, ImgShare, ImgDelete;
        public TextView Tv_Username, TvLikeCount, TvViewCount, Tv_UserType, Tv_PostTitle;
        public LinearLayout LLUserProfile, LLMuteUnMute;
        public YoutubePlayerView simpleVideoView;
        private RelativeLayout LLExploreItem;
        //        public CircularProgressIndicator RoundProgress;
        public ProgressBar videoProgressbar;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            iv_User = itemView.findViewById(R.id.iv_User);
            Tv_Username = itemView.findViewById(R.id.Tv_Username);
            Tv_UserType = itemView.findViewById(R.id.Tv_UserType);
            Tv_PostTitle = itemView.findViewById(R.id.Tv_PostTitle);
            imgLike = itemView.findViewById(R.id.imgLike);
            imgView = itemView.findViewById(R.id.imgView);
            ImgShare = itemView.findViewById(R.id.ImgShare);
            ImgDelete = itemView.findViewById(R.id.ImgDelete);
            ImgMenu = itemView.findViewById(R.id.ImgMenu);
            imgPlay = itemView.findViewById(R.id.imgPlay);
            imgFullscreen = itemView.findViewById(R.id.imgFullscreen);
            TvLikeCount = itemView.findViewById(R.id.TvLikeCount);
            TvViewCount = itemView.findViewById(R.id.TvViewCount);
            LLUserProfile = itemView.findViewById(R.id.LLUserProfile);
            LLExploreItem = itemView.findViewById(R.id.LLExploreItem);
            simpleVideoView = itemView.findViewById(R.id.simpleVideoView);
            LLMuteUnMute = itemView.findViewById(R.id.LLMuteUnMute);
//            RoundProgress = itemView.findViewById(R.id.RoundProgress);
            videoProgressbar = itemView.findViewById(R.id.videoProgressbar);

        }
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


    public void showVideoDeleteDialog(RelativeLayout LLExploreItem, MyVideoDataItem bean, List<MyVideoDataItem> muscles, int position) {
        final Dialog builder = new Dialog(context, R.style.Theme_Dialog);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view1 = LayoutInflater.from(context).inflate(R.layout.dialog_logout, null);

        TextView tvDialogok = (TextView) view1.findViewById(R.id.tvDialogok);
        TextView tvDialogCancel = (TextView) view1.findViewById(R.id.tvDialogCancel);
        TextView tvDialogMessage = (TextView) view1.findViewById(R.id.tvDialogMessage);
        tvDialogMessage.setText(R.string.are_you_sure_you_want_to_delete_this_video);

        tvDialogok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cd.isConnectingToInternet()) {
                    builder.dismiss();
                    Functions.dialogShow(context);
                    CallDeleteVideoApiResponse(LLExploreItem, bean, MyVideoCategoryAdapter.this.muscles, position);
                } else {
                    Snackbar snackbar = Snackbar
                            .make(LLExploreItem, context.getResources().getString(R.string.check_internet_connection), Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }

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
        builder.getWindow().setBackgroundDrawable(context.getResources().getDrawable(R.drawable.dialog_round));
        // builder.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        builder.setContentView(view1);
        builder.show();


    }


    public void CallDeleteVideoApiResponse(RelativeLayout LLExploreItem, MyVideoDataItem myVideoDataItem, List<MyVideoDataItem> muscles, int position) {

        apiInterface = ApiClient.getClient(context).create(ApiInterface.class);
        Call<DefaultApiResponse> loginApiResponseCall = apiInterface.GetDeleteMyVideo(String.valueOf(myVideoDataItem.getId()));
        loginApiResponseCall.enqueue(new Callback<DefaultApiResponse>() {
            @Override
            public void onResponse(Call<DefaultApiResponse> call, Response<DefaultApiResponse> response) {
                try {
                    if (response.isSuccessful()) {

                        Functions.dialogHide();
                        muscles.remove(position);
                        notifyDataSetChanged();
                        Snackbar snackbar = Snackbar
                                .make(LLExploreItem, response.body().getMsg(), Snackbar.LENGTH_SHORT);
                        snackbar.show();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<DefaultApiResponse> call, Throwable t) {
//                Functions.dialogHide();
            }
        });
    }


}
