package com.example.athletics.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Athletics.R;
import com.example.athletics.Activity.AthleteProfileActivity;
import com.example.athletics.Utils.Functions;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.Myviewholder> {
    Context context;
    List<String> muscles;


    public NotificationAdapter(Activity activity, List<String> muscles) {
        this.context = activity;
        this.muscles = muscles;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Myviewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notifcation, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final Myviewholder holder, final int position) {
        final String bean = muscles.get(position);

        holder.Tv_Username.setText(bean);

        if (position % 2 == 0) {
            holder.FrmVideo.setVisibility(View.GONE);
            holder.tvFollow.setVisibility(View.VISIBLE);
        } else {
            holder.FrmVideo.setVisibility(View.VISIBLE);
            holder.tvFollow.setVisibility(View.GONE);
        }

        holder.tvFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.tvFollow.getText().toString().equalsIgnoreCase(context.getResources().getString(R.string.follow))) {
                    holder.tvFollow.setText(context.getResources().getString(R.string.following));
                } else {
                    holder.tvFollow.setText(context.getResources().getString(R.string.follow));
                }
            }
        });


        holder.Tv_Username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                context.startActivity(new Intent(context, AthleteProfileActivity.class));
//                Functions.animNext(context);
            }
        });

        holder.Tv_UserType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                context.startActivity(new Intent(context, AthleteProfileActivity.class));
//                Functions.animNext(context);
            }
        });

        holder.iv_UserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                context.startActivity(new Intent(context, AthleteProfileActivity.class));
//                Functions.animNext(context);
            }
        });

//        Glide.with(context).load(bean.getBanner()).into(holder.iv_ProductCategory);


    }

    @Override
    public int getItemCount() {
        return muscles.size();
    }


    public class Myviewholder extends RecyclerView.ViewHolder {
        private TextView Tv_Username, tvFollow, Tv_UserType;
        private FrameLayout FrmUserProfile, FrmVideo;
        private CircleImageView iv_UserProfile;
        private LinearLayout LLUserProfile;


        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            Tv_Username = itemView.findViewById(R.id.Tv_Username);
            Tv_UserType = itemView.findViewById(R.id.Tv_UserType);
            FrmUserProfile = itemView.findViewById(R.id.FrmUserProfile);
            FrmVideo = itemView.findViewById(R.id.FrmVideo);
            tvFollow = itemView.findViewById(R.id.tvFollow);
            iv_UserProfile = itemView.findViewById(R.id.iv_UserProfile);
            LLUserProfile = itemView.findViewById(R.id.LLUserProfile);

        }
    }
}
