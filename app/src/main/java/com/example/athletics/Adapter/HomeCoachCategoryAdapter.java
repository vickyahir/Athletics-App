package com.example.athletics.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.Athletics.R;
import com.example.athletics.Activity.CoachProfileActivity;
import com.example.athletics.Activity.VideoViewActivity;
import com.example.athletics.Model.HomeCoachDataItem;
import com.example.athletics.Model.VideoCountIncrementResponse;
import com.example.athletics.Retrofit.ApiClient;
import com.example.athletics.Retrofit.ApiInterface;
import com.example.athletics.Utils.ConnectionDetector;
import com.example.athletics.Utils.Functions;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeCoachCategoryAdapter extends RecyclerView.Adapter<HomeCoachCategoryAdapter.Myviewholder> {
    Context context;
    List<HomeCoachDataItem> muscles;
    private MediaController mediaController;
    public ApiInterface apiInterface;
    public ConnectionDetector cd;


    public HomeCoachCategoryAdapter(Activity activity, List<HomeCoachDataItem> muscles) {
        this.context = activity;
        this.muscles = muscles;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Myviewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coach_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final Myviewholder holder, final int position) {
        final HomeCoachDataItem bean = muscles.get(position);
        cd = new ConnectionDetector(context);


        holder.Tv_Username.setText(bean.getName());

        if (bean.getCoach().getPosition().size() > 0) {

            holder.Tv_CoachGame.setVisibility(View.VISIBLE);

            String GameName = "";
            for (int i = 0; i < bean.getCoach().getPosition().size(); i++) {
                GameName = GameName + ", " + bean.getCoach().getPosition().get(i);
                holder.Tv_CoachGame.setText(GameName.substring(1));
            }
        } else {
            holder.Tv_CoachGame.setVisibility(View.GONE);
        }


        Glide.with(context).load(bean.getImage()).into(holder.iv_User);

//        holder.simpleVideoView.setVideoURI(Uri.parse(bean.getCoach().getProfileVideo()));

//        holder.simpleVideoView.start();

        holder.imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.imgPlay.setVisibility(View.GONE);
                holder.simpleVideoView.setVideoURI(Uri.parse(bean.getCoach().getProfileVideo()));
//                holder.simpleVideoView.start();
                holder.RoundProgress.setVisibility(View.VISIBLE);


            }
        });


        holder.simpleVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                holder.imgPlay.setVisibility(View.VISIBLE);
                holder.RoundProgress.setVisibility(View.GONE);
            }
        });


        holder.simpleVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
//                holder.VideoProgress.setVisibility(View.GONE);
                holder.RoundProgress.setVisibility(View.GONE);
                holder.simpleVideoView.start();
                if (cd.isConnectingToInternet()) {
                    CallVideoCounIncrementResponse(String.valueOf(bean.getId()));

                } else {
                    Snackbar snackbar = Snackbar.make(holder.LLCoachMain, context.getResources().getString(R.string.check_internet_connection), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });

        holder.ImgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context, holder.ImgMenu);

                // Inflating popup menu from popup_menu.xml file
                popupMenu.getMenuInflater().inflate(R.menu.home_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        // Toast message on menu item clicked
//                        Toast.makeText(context, "You Clicked " + menuItem.getTitle(), Toast.LENGTH_SHORT).show();

                        if (menuItem.getItemId() == R.id.MenuDownloadVideo) {
                            Toast.makeText(context, "Video Download later ", Toast.LENGTH_SHORT).show();
                        } else if (menuItem.getItemId() == R.id.MenuPictureInPicture) {
                            Toast.makeText(context, "Picture in Picture mode ", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                });
                // Showing the popup menu
                popupMenu.show();
            }
        });


        holder.imgShare.setOnClickListener(new View.OnClickListener() {
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

        holder.Tv_Username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                context.startActivity(new Intent(context, CoachProfileActivity.class).putExtra("Id", String.valueOf(bean.getId())));
                Functions.animNext(context);

            }
        });

        holder.Tv_UserType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                context.startActivity(new Intent(context, CoachProfileActivity.class).putExtra("Id", String.valueOf(bean.getId())));
                Functions.animNext(context);

            }
        });

        holder.iv_User.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                context.startActivity(new Intent(context, CoachProfileActivity.class).putExtra("Id", String.valueOf(bean.getId())));
                Functions.animNext(context);

            }
        });

        holder.imgFullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, VideoViewActivity.class).putExtra("fullScreenInd", "y").putExtra("VideoUrl", bean.getCoach().getProfileVideo()));
                Functions.animNext(context);
            }
        });


    }


    @Override
    public int getItemCount() {
        return muscles.size();
    }


    public class Myviewholder extends RecyclerView.ViewHolder {
        private ImageView iv_User, imgLike, imgShare, ImgMenu, imgFullscreen, imgPlay;
        private TextView Tv_Username, Tv_UserType, Tv_CoachGame;
        private LinearLayout LLUserProfile, LLCoachMain;
        private VideoView simpleVideoView;
        public CircularProgressIndicator RoundProgress;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            iv_User = itemView.findViewById(R.id.iv_User);
            Tv_Username = itemView.findViewById(R.id.Tv_Username);
            Tv_UserType = itemView.findViewById(R.id.Tv_UserType);
            Tv_CoachGame = itemView.findViewById(R.id.Tv_CoachGame);
            imgLike = itemView.findViewById(R.id.imgLike);
            imgShare = itemView.findViewById(R.id.imgShare);
            imgPlay = itemView.findViewById(R.id.imgPlay);
            ImgMenu = itemView.findViewById(R.id.ImgMenu);
            imgFullscreen = itemView.findViewById(R.id.imgFullscreen);
            LLUserProfile = itemView.findViewById(R.id.LLUserProfile);
            LLCoachMain = itemView.findViewById(R.id.LLCoachMain);
            simpleVideoView = itemView.findViewById(R.id.simpleVideoView);
            RoundProgress = itemView.findViewById(R.id.RoundProgress);

        }
    }

    public void CallVideoCounIncrementResponse(String VideoId) {

        apiInterface = ApiClient.getClient(context).create(ApiInterface.class);
        Call<VideoCountIncrementResponse> loginApiResponseCall = apiInterface.GetVideoIncrementCount(VideoId);
        loginApiResponseCall.enqueue(new Callback<VideoCountIncrementResponse>() {
            @Override
            public void onResponse(Call<VideoCountIncrementResponse> call, Response<VideoCountIncrementResponse> response) {
                try {
                    if (response.isSuccessful()) {

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


}
