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
import com.example.athletics.Activity.AthleteProfileActivity;

import java.util.List;


public class ProfileSportAdapter extends RecyclerView.Adapter<ProfileSportAdapter.Myviewholder> {
    Context context;
    List<String> muscles;
    int ModelSize = 0;
    private int selectedPosition = 0;


    public ProfileSportAdapter(Activity activity, List<String> muscles, int size) {
        this.context = activity;
        this.muscles = muscles;
        this.ModelSize = size;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Myviewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_athlet_sport_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final Myviewholder holder, final int position) {
        final String bean = muscles.get(position);

        holder.tvFollow.setText(bean);

        if (selectedPosition == position) {
            holder.tvFollow.setBackground(context.getResources().getDrawable(R.drawable.bg_button_dynamic_selected));
            holder.tvFollow.setTextColor(context.getResources().getColor(R.color.white));
            ((AthleteProfileActivity) context).sportNameDisplay(position);

        } else {
            holder.tvFollow.setBackground(context.getResources().getDrawable(R.drawable.bg_button_dynamic_unselected));
            holder.tvFollow.setTextColor(context.getResources().getColor(R.color.black));
        }


        holder.tvFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AthleteProfileActivity) context).sportNameDisplay(position);
                selectedPosition = holder.getAdapterPosition();
                notifyItemChanged(selectedPosition);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return ModelSize;
    }


    public class Myviewholder extends RecyclerView.ViewHolder {
        private TextView tvFollow;


        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            tvFollow = (TextView) itemView.findViewById(R.id.tvFollow);

        }
    }
}
