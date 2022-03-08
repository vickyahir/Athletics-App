package com.example.athletics.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Athletics.R;
import com.example.athletics.Activity.AthleteInformationActivity;
import com.example.athletics.Model.AthleteCategoryPositionDataItem;
import com.example.athletics.Model.AthleteInformationStateItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class AthleteSportPositionStateAdapter extends RecyclerView.Adapter<AthleteSportPositionStateAdapter.Myviewholder> {
    Context context;
    List<AthleteCategoryPositionDataItem> detail;
    List<AthleteInformationStateItem> SelectedList;
    public AthleteSportsStateAdapter athletePositionAdapter;
    private List<String> AthleteStatsList;


    public AthleteSportPositionStateAdapter(Activity activity, List<AthleteCategoryPositionDataItem> muscles, List<AthleteInformationStateItem> select) {
        this.context = activity;
        this.detail = muscles;
        this.SelectedList = select;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Myviewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_athlete_sports_state, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final Myviewholder holder, final int position) {
        final AthleteCategoryPositionDataItem bean = detail.get(position);


        try {
            AthleteStatsList = new ArrayList<>();
            AthleteStatsList.addAll(bean.getStats());
            holder.TvPositionName.setText("Stats for " + bean.getName());
            holder.rvAthleteSports.setLayoutManager(new GridLayoutManager(context, 3));

            if (SelectedList.size() > position) {
                athletePositionAdapter = new AthleteSportsStateAdapter(((AthleteInformationActivity) context), AthleteStatsList, SelectedList.get(position).getStat());
            } else {
                athletePositionAdapter = new AthleteSportsStateAdapter(((AthleteInformationActivity) context), AthleteStatsList, Collections.singletonList("Athletes"));
            }
            holder.rvAthleteSports.setAdapter(athletePositionAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return detail.size();
    }


    public class Myviewholder extends RecyclerView.ViewHolder {
        private TextView TvPositionName;
        private RecyclerView rvAthleteSports;


        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            TvPositionName = itemView.findViewById(R.id.TvPositionName);
            rvAthleteSports = itemView.findViewById(R.id.rvAthleteSports);


        }
    }
}
