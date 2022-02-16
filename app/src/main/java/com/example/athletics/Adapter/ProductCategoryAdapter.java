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
import com.example.athletics.Activity.UserProfileActivity;
import com.example.athletics.Model.HomeAthleteDataItem;
import com.example.athletics.Utils.Functions;

import java.util.List;


public class ProductCategoryAdapter extends RecyclerView.Adapter<ProductCategoryAdapter.Myviewholder> {
    Context context;
    List<HomeAthleteDataItem> muscles;


    public ProductCategoryAdapter(Activity activity, List<HomeAthleteDataItem> muscles) {
        this.context = activity;
        this.muscles = muscles;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Myviewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final Myviewholder holder, final int position) {
        final HomeAthleteDataItem bean = muscles.get(position);

        holder.Tv_AthleteName.setText(bean.getName());
        Glide.with(context).load(bean.getImage()).into(holder.iv_AthleticProfile);


//        if (position == 0) {
//            holder.iv_ProductCategory.setBorderWidth(8);
//        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
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
        private ImageView iv_AthleticProfile;
        private TextView Tv_AthleteName;


        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            iv_AthleticProfile = itemView.findViewById(R.id.iv_AthleticProfile);
            Tv_AthleteName = itemView.findViewById(R.id.Tv_AthleteName);

        }
    }
}
