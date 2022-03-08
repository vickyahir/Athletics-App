package com.example.athletics.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Athletics.R;
import com.example.athletics.Activity.SearchActivity;

import java.util.List;


public class ExclusiveSportsAdapter extends RecyclerView.Adapter<ExclusiveSportsAdapter.Myviewholder> {
    Context context;
    List<String> muscles;


    public ExclusiveSportsAdapter(Activity activity, List<String> muscles) {
        this.context = activity;
        this.muscles = muscles;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Myviewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sports, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final Myviewholder holder, final int position) {
        final String bean = muscles.get(position);

        holder.Tv_SportsName.setText(bean);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((SearchActivity)context).SearchFromBottomSheet(bean);
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return muscles.size();
    }


    public class Myviewholder extends RecyclerView.ViewHolder {
        private TextView Tv_SportsName;


        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            Tv_SportsName = (TextView) itemView.findViewById(R.id.Tv_SportsName);

        }
    }
}
