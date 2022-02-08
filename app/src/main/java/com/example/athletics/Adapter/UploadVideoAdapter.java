package com.example.athletics.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.Athletics.R;

import java.util.List;


public class UploadVideoAdapter extends RecyclerView.Adapter<UploadVideoAdapter.Myviewholder> {
    Context context;
    List<String> muscles;


    public UploadVideoAdapter(Activity activity, List<String> muscles) {
        this.context = activity;
        this.muscles = muscles;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Myviewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_upload_video, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final Myviewholder holder, final int position) {
        final String bean = muscles.get(position);

//        holder.Tv_ProductCategoryName.setText(bean);

//        if (position == 0) {
//            holder.iv_ProductCategory.setBorderWidth(8);
//        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                context.startActivity(new Intent(context, ProductListActivity.class).putExtra("productId", String.valueOf(bean.getId()))
//                        .putExtra("productTitle", bean.getName())
//                        .putExtra("from", "other")
//                );
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
        private ImageView IvUploadVideo;


        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            IvUploadVideo = itemView.findViewById(R.id.IvUploadVideo);

        }
    }
}
