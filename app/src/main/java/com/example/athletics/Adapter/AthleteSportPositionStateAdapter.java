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
    public List<AthleteCategoryPositionDataItem> detail;
    public List<AthleteInformationStateItem> SelectedList;
    public AthleteSportsStateAdapter athletePositionAdapter;
    public List<String> AthleteStatsList;
    public OnEdittextChanged OnEdittextChanged; //add this


    public AthleteSportPositionStateAdapter(Activity activity, List<AthleteCategoryPositionDataItem> muscles, List<AthleteInformationStateItem> select, AthleteSportPositionStateAdapter.OnEdittextChanged onEdittextChanged) {
        this.context = activity;
        this.detail = muscles;
        this.SelectedList = select;
        this.OnEdittextChanged = onEdittextChanged;
        System.out.println("muscles size is >>>" + muscles.size());

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
          //  detailsList.add(position, bean.getStats().get(position));
            holder.TvPositionName.setText("Stats for " + bean.getName());
            holder.rvAthleteSports.setLayoutManager(new GridLayoutManager(context, 3));
            System.out.println("data  >>>>>>" + bean.getStats());
            if (SelectedList.size() > position) {
                athletePositionAdapter = new AthleteSportsStateAdapter(((AthleteInformationActivity) context), AthleteStatsList, SelectedList.get(position).getStat(), OnEdittextChanged);
            } else {
                athletePositionAdapter = new AthleteSportsStateAdapter(((AthleteInformationActivity) context), AthleteStatsList, Collections.singletonList("Athletes"), OnEdittextChanged);
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

    public interface OnEdittextChanged {
        void OnEdittextValue(String Name, String Value);

        void getEditTextValue(String editValue, String title, int pos);
    }


}
