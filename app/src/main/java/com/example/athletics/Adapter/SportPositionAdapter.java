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
import com.example.athletics.Model.AthleteInformationPositionItem;

import java.util.ArrayList;
import java.util.List;


public class SportPositionAdapter extends RecyclerView.Adapter<SportPositionAdapter.Myviewholder> {
    Context context;
    List<AthleteCategoryPositionDataItem> detail;
    List<AthleteInformationPositionItem> SelectedList;
    public PositionAdapter athletePositionAdapter;
    private List<String> AthleteSportsList;


    public SportPositionAdapter(Activity activity, List<AthleteCategoryPositionDataItem> muscles, List<AthleteInformationPositionItem> select) {
        this.context = activity;
        this.detail = muscles;
        this.SelectedList = select;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Myviewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_athlete_sports, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final Myviewholder holder, final int position) {
        final AthleteCategoryPositionDataItem bean = detail.get(position);


        try {
            AthleteSportsList = new ArrayList<>();
            AthleteSportsList.addAll(bean.getPositions());
            holder.TvPositionName.setText("Position(s) for " + bean.getName());
            holder.rvAthleteSports.setLayoutManager(new GridLayoutManager(context, 3));
            athletePositionAdapter = new PositionAdapter(((AthleteInformationActivity) context), AthleteSportsList, SelectedList.get(position).getPos());
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
