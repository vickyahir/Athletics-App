package com.example.athletics.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.Athletics.R;
import com.example.athletics.Activity.UserProfileActivity;
import com.example.athletics.Model.FollowingDataItem;
import com.example.athletics.Utils.Functions;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class MyFollowingAdapter extends RecyclerView.Adapter<MyFollowingAdapter.Myviewholder> {
    Context context;
    List<FollowingDataItem> muscles;


    public MyFollowingAdapter(Activity activity, List<FollowingDataItem> muscles) {
        this.context = activity;
        this.muscles = muscles;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Myviewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_find_user, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final Myviewholder holder, final int position) {
        final FollowingDataItem bean = muscles.get(position);


        holder.Tv_Username.setText(bean.getName());
        holder.Tv_UserType.setText(bean.getName());

//        holder.TvProductAverageRating.setText(String.valueOf(bean.getRating()));
//        holder.RecentlyRatingBar.setNumStars(5);
//        holder.RecentlyRatingBar.setRating(Float.valueOf(bean.getRating()));
//
        Glide.with(context).load(bean.getImage()).into(holder.iv_UserProfile);


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
                context.startActivity(new Intent(context, UserProfileActivity.class));
                Functions.animNext(context);

            }
        });

        holder.Tv_UserType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, UserProfileActivity.class));
                Functions.animNext(context);

            }
        });

        holder.iv_UserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, UserProfileActivity.class));
                Functions.animNext(context);

            }
        });


    }

    @Override
    public int getItemCount() {
        return muscles.size();
    }


    public class Myviewholder extends RecyclerView.ViewHolder {
        private CircleImageView iv_UserProfile;
        private TextView Tv_Username, Tv_UserType, tvFollow;


        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            iv_UserProfile = (CircleImageView) itemView.findViewById(R.id.iv_UserProfile);
            Tv_Username = (TextView) itemView.findViewById(R.id.Tv_Username);
            Tv_UserType = (TextView) itemView.findViewById(R.id.Tv_UserType);
            tvFollow = (TextView) itemView.findViewById(R.id.tvFollow);

        }
    }
}
