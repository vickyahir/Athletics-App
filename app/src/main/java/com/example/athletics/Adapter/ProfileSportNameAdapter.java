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
import com.example.athletics.Model.AthleteProfileStatItem;

import java.util.List;


public class ProfileSportNameAdapter extends RecyclerView.Adapter<ProfileSportNameAdapter.Myviewholder> {
    Context context;
    List<AthleteProfileStatItem> muscles;


    public ProfileSportNameAdapter(Activity activity, List<AthleteProfileStatItem> muscles) {
        this.context = activity;
        this.muscles = muscles;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Myviewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sportname_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final Myviewholder holder, final int position) {
        final AthleteProfileStatItem bean = muscles.get(position);

        holder.TvSportNameKey.setText(bean.getName() + " :");
        holder.TvSportNameValue.setText(bean.getValue());


    }

    @Override
    public int getItemCount() {
        return muscles.size();
    }


    public class Myviewholder extends RecyclerView.ViewHolder {
        private TextView TvSportNameKey, TvSportNameValue;


        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            TvSportNameKey = (TextView) itemView.findViewById(R.id.TvSportNameKey);
            TvSportNameValue = (TextView) itemView.findViewById(R.id.TvSportNameValue);

        }
    }
}
