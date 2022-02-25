package com.example.athletics.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Athletics.R;

import java.util.List;


public class SubscribeDetailsAdapter extends RecyclerView.Adapter<SubscribeDetailsAdapter.Myviewholder> {
    Context context;
    List<String> detail;


    public SubscribeDetailsAdapter(Activity activity, List<String> muscles) {
        this.context = activity;
        this.detail = muscles;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Myviewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subscribe_details, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final Myviewholder holder, final int position) {
        final String bean = detail.get(position);

        holder.TvDetails.setText(bean);


    }

    @Override
    public int getItemCount() {
        return detail.size();
    }


    public class Myviewholder extends RecyclerView.ViewHolder {
        private TextView TvDetails;


        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            TvDetails = itemView.findViewById(R.id.TvDetails);


        }
    }
}
