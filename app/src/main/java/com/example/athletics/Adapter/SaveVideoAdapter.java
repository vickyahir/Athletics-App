package com.example.athletics.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.Athletics.R;
import com.example.athletics.Activity.VideoViewActivity;
import com.example.athletics.Model.UserLikeVideoDataItem;
import com.example.athletics.Utils.Functions;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class SaveVideoAdapter extends RecyclerView.Adapter<SaveVideoAdapter.Myviewholder> {
    Context context;
    List<UserLikeVideoDataItem> muscles;


    public SaveVideoAdapter(Activity activity, List<UserLikeVideoDataItem> muscles) {
        this.context = activity;
        this.muscles = muscles;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Myviewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_save_video, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final Myviewholder holder, final int position) {
        final UserLikeVideoDataItem bean = muscles.get(position);

        holder.Tv_Username.setText(bean.getAthlete().getName());
        holder.Tv_VideoDetails.setText(bean.getTitle());
        Glide.with(context).load(bean.getThumb()).into(holder.IvUserProfile);
        Glide.with(context).load(bean.getThumb()).into(holder.imgVideoView);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, VideoViewActivity.class)
                        .putExtra("fullScreenInd", "y")
                        .putExtra("VideoUrl", bean.getVideo()));

                Functions.animNext(context);
            }
        });


    }

    @Override
    public int getItemCount() {
        return muscles.size();
    }


    public class Myviewholder extends RecyclerView.ViewHolder {
        private TextView Tv_Username, Tv_VideoDetails;
        private CircleImageView IvUserProfile;
        private ImageView imgVideoView;


        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            Tv_Username = itemView.findViewById(R.id.Tv_Username);
            Tv_VideoDetails = itemView.findViewById(R.id.Tv_VideoDetails);
            IvUserProfile = itemView.findViewById(R.id.IvUserProfile);
            imgVideoView = itemView.findViewById(R.id.imgVideoView);

        }
    }
}
