package com.example.athletics.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.Athletics.R;
import com.example.athletics.Activity.AthleteProfileActivity;
import com.example.athletics.Model.HomeAthleteDataItem;
import com.example.athletics.Utils.Functions;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class AthleteSearchedAdapter extends RecyclerView.Adapter<AthleteSearchedAdapter.Myviewholder> {
    Context context;
    List<HomeAthleteDataItem> muscles;


    public AthleteSearchedAdapter(Activity activity, List<HomeAthleteDataItem> muscles) {
        this.context = activity;
        this.muscles = muscles;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Myviewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final Myviewholder holder, final int position) {
        final HomeAthleteDataItem bean = muscles.get(position);


        holder.Tv_Username.setText(bean.getName());
        holder.LLAthlete.setVisibility(View.VISIBLE);
        holder.Tv_UserType.setVisibility(View.GONE);

        holder.Tv_UserLocation.setText(bean.getAthlete().getCountry());

//
//        if (bean.getRole() == 1) {
//            holder.Tv_UserType.setText(context.getResources().getString(R.string.visitor));
//        } else if (bean.getRole() == 2) {
//            holder.Tv_UserType.setText(context.getResources().getString(R.string.athlete));
//        } else if (bean.getRole() == 3) {
//            holder.Tv_UserType.setText(context.getResources().getString(R.string.school_club));
//        } else if (bean.getRole() == 4) {
//            holder.Tv_UserType.setText(context.getResources().getString(R.string.coach));
//        }
        Glide.with(context).load(bean.getImage()).into(holder.iv_UserProfile);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                context.startActivity(new Intent(context, AthleteProfileActivity.class).putExtra("Id", String.valueOf(bean.getId())));
                Functions.animNext(context);

            }
        });

    }

    @Override
    public int getItemCount() {
        return muscles.size();
    }


    public class Myviewholder extends RecyclerView.ViewHolder {
        private TextView Tv_Username, Tv_UserType, Tv_UserLocation;
        private CircleImageView iv_UserProfile;
        private LinearLayout LLAthlete;


        public Myviewholder(@NonNull View itemView) {
            super(itemView);

            Tv_Username = (TextView) itemView.findViewById(R.id.Tv_Username);
            Tv_UserType = (TextView) itemView.findViewById(R.id.Tv_UserType);
            Tv_UserLocation = (TextView) itemView.findViewById(R.id.Tv_UserLocation);
            iv_UserProfile = (CircleImageView) itemView.findViewById(R.id.iv_UserProfile);
            LLAthlete = (LinearLayout) itemView.findViewById(R.id.LLAthlete);
        }
    }
}
