package com.example.athletics.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Athletics.R;
import com.example.athletics.Model.AthleteInformationStatItem;

import java.util.List;


public class AthleteSportsStateAdapter extends RecyclerView.Adapter<AthleteSportsStateAdapter.Myviewholder> {
    Context context;
    List<String> detail;
    List<AthleteInformationStatItem> SelectedList;


    public AthleteSportsStateAdapter(Activity activity, List<String> muscles, List<AthleteInformationStatItem> selectList) {
        this.context = activity;
        this.detail = muscles;
        this.SelectedList = selectList;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Myviewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sports_state, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final Myviewholder holder, final int position) {
        final String bean = detail.get(position);

        holder.TvSportStateName.setText(bean);

        if (SelectedList.size() > 0) {
            for (int i = 0; i < SelectedList.size(); i++) {
                if (SelectedList.get(i).getName().equalsIgnoreCase(bean)) {
                    holder.edtSportState.setText(SelectedList.get(i).getValue());
                }
            }
        }


    }

    @Override
    public int getItemCount() {
        return detail.size();
    }


    public class Myviewholder extends RecyclerView.ViewHolder {
        private EditText edtSportState;
        private TextView TvSportStateName;


        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            edtSportState = itemView.findViewById(R.id.edtSportState);
            TvSportStateName = itemView.findViewById(R.id.TvSportStateName);


        }
    }
}
