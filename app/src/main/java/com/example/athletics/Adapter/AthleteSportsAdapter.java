package com.example.athletics.Adapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Athletics.R;
import com.example.athletics.Activity.AthleteInformationActivity;
import com.example.athletics.Activity.CoachInformationActivity;
import com.example.athletics.Model.AthleteReqCategoriesItem;
import com.example.athletics.Utils.Functions;
import com.example.athletics.Utils.SessionManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class AthleteSportsAdapter extends RecyclerView.Adapter<AthleteSportsAdapter.Myviewholder> {
    Context context;
    List<AthleteReqCategoriesItem> detail;


    public AthleteSportsAdapter(Activity activity, List<AthleteReqCategoriesItem> muscles) {
        this.context = activity;
        this.detail = muscles;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Myviewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coach_category, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final Myviewholder holder, final int position) {
        final AthleteReqCategoriesItem bean = detail.get(position);

        holder.chkSports.setText(bean.getName());


        String result = "";
        if (!new SessionManager(context).getKeyAthleteSportsids().equalsIgnoreCase("")) {

            List<String> myList = new ArrayList<String>(Arrays.asList(new SessionManager(context).getKeyAthleteSportsids().split(",")));
            result = TextUtils.join(",", myList);

            for (int i = 0; i < myList.size(); i++) {
                if (String.valueOf(bean.getId()).equalsIgnoreCase(myList.get(i))) {
                    holder.chkSports.setChecked(true);
                    result = TextUtils.join(",", myList);
                    ((AthleteInformationActivity) context).CallAthleteSportsCategoryApiResponse(result);
                }
            }

        }

        holder.chkSports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> list = new ArrayList<String>(Arrays.asList(new SessionManager(context).getKeyAthleteSportsids().split(",")));
                String results = TextUtils.join(",", list);

                if (!holder.chkSports.isChecked()) {
                    holder.chkSports.setChecked(false);

                    List<String> myList = new ArrayList<String>(Arrays.asList(results.split(",")));

                    for (int i = 0; i < myList.size(); i++) {
                        if (myList.get(i).equalsIgnoreCase(String.valueOf(bean.getId()))) {
                            myList.remove(i);

                            results = TextUtils.join(",", myList);
                            new SessionManager(context).setKeyAthleteSportsids(results);
                            ((AthleteInformationActivity) context).CallAthleteSportsCategoryApiResponse(results);
                        }

                    }

                } else {
                    holder.chkSports.setChecked(true);

                    list.add(String.valueOf(bean.getId()));
                    results = TextUtils.join(",", list);
                    new SessionManager(context).setKeyAthleteSportsids(results);

                    ((AthleteInformationActivity) context).CallAthleteSportsCategoryApiResponse(results);

                }

            }
        });




    }

    @Override
    public int getItemCount() {
        return detail.size();
    }


    public class Myviewholder extends RecyclerView.ViewHolder {
        private CheckBox chkSports;


        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            chkSports = itemView.findViewById(R.id.chkSports);


        }
    }
}
