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
import com.example.athletics.Activity.LikeVideoActivity;
import com.example.athletics.Model.AthleteProfileVideosItem;
import com.example.athletics.Utils.Functions;

import java.util.List;


public class AthleteProfileVideoAdapter extends RecyclerView.Adapter<AthleteProfileVideoAdapter.Myviewholder> {
    Context context;
    List<AthleteProfileVideosItem> muscles;


    public AthleteProfileVideoAdapter(Activity activity, List<AthleteProfileVideosItem> muscles) {
        this.context = activity;
        this.muscles = muscles;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Myviewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_profile_home, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final Myviewholder holder, final int position) {
        final AthleteProfileVideosItem bean = muscles.get(position);

        holder.Tv_Title.setText(bean.getTitle());
        holder.Tv_Details.setText(bean.getTitle());

//        if (position == 0) {
//            holder.iv_ProductCategory.setBorderWidth(8);
//        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                context.startActivity(new Intent(context, VideoViewActivity.class).putExtra("fullScreenInd", "y")
//                        .putExtra("VideoUrl", ""));
//                Functions.animNext(context);

                context.startActivity(new Intent(context, LikeVideoActivity.class)
                        .putExtra("Id", String.valueOf(bean.getUserId()))
                        .putExtra("position", String.valueOf(position))
                );
                Functions.animNext(context);

            }
        });

        Glide.with(context).load(bean.getThumb()).into(holder.ImgAthleteVideo);


    }

    @Override
    public int getItemCount() {
        return muscles.size();
    }


    public class Myviewholder extends RecyclerView.ViewHolder {
        private TextView Tv_Title, Tv_Details;
        private ImageView ImgAthleteVideo;


        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            Tv_Title = itemView.findViewById(R.id.Tv_Title);
            Tv_Details = itemView.findViewById(R.id.Tv_Details);
            ImgAthleteVideo = itemView.findViewById(R.id.ImgAthleteVideo);

        }
    }
}
