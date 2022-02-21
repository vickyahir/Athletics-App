package com.example.athletics.Adapter;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Athletics.R;
import com.example.athletics.Model.HomeExploreDataItem;

import java.util.List;


public class HomeViewPagerAdapter extends RecyclerView.Adapter<HomeViewPagerAdapter.Myviewholder> {
    Context context;
    List<HomeExploreDataItem> muscles;


    public HomeViewPagerAdapter(Activity activity, List<HomeExploreDataItem> muscles) {
        this.context = activity;
        this.muscles = muscles;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Myviewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_viewpager, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final Myviewholder holder, final int position) {
        final HomeExploreDataItem bean = muscles.get(position);

        holder.videoView.setVideoPath(bean.getVideo());
        holder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                holder.videoProgressbar.setVisibility(View.GONE);
                mediaPlayer.start();

//                float videoratio = mediaPlayer.getVideoWidth() / (float) mediaPlayer.getVideoHeight();
//                float screenRatio = holder.videoView.getWidth() / (float) holder.videoView.getHeight();
//
//                float scale = videoratio / screenRatio;
//                if (scale >= 1f) {
//                    holder.videoView.setScaleX(scale);
//                } else {
////                    holder.videoView.setScaleY(1f / scale);
//                    holder.videoView.setScaleY(scale);
//                }

//                ViewGroup.LayoutParams lp = holder.videoView.getLayoutParams();
//                int videoWidth = mediaPlayer.getVideoWidth();
//                int videoHeight = mediaPlayer.getVideoHeight();
//                float videoProportion = (float) videoWidth / (float) videoHeight;
//                int screenWidth = holder.videoView.getWidth();
//                int screenHeight = holder.videoView.getHeight();
//                float screenProportion = (float) screenWidth / (float) screenHeight;
//                if (videoProportion > screenProportion) {
//                    lp.width = screenWidth;
//                    lp.height = (int) ((float) screenWidth / videoProportion);
//                } else {
//                    lp.width = (int) (videoProportion * (float) screenHeight);
//                    lp.height = screenHeight;
//                }
//                holder.videoView.setLayoutParams(lp);


//                holder.videoView.setLayoutParams(new FrameLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));


            }

        });

        holder.videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });


    }

    @Override
    public int getItemCount() {
        return muscles.size();
    }


    public class Myviewholder extends RecyclerView.ViewHolder {
        private ProgressBar videoProgressbar;
        private VideoView videoView;


        public Myviewholder(@NonNull View itemView) {
            super(itemView);

            videoView = itemView.findViewById(R.id.videoView);
            videoProgressbar = itemView.findViewById(R.id.videoProgressbar);

        }
    }
}
