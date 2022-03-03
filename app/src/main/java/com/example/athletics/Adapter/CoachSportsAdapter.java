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
import com.example.athletics.Activity.CoachInformationActivity;
import com.example.athletics.Model.CoachCategoryDataItem;
import com.example.athletics.Utils.Functions;
import com.example.athletics.Utils.SessionManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CoachSportsAdapter extends RecyclerView.Adapter<CoachSportsAdapter.Myviewholder> {
    Context context;
    List<CoachCategoryDataItem> detail;


    public CoachSportsAdapter(Activity activity, List<CoachCategoryDataItem> muscles) {
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
        final CoachCategoryDataItem bean = detail.get(position);

        holder.chkSports.setText(bean.getName());
        String result = "";
        if (!new SessionManager(context).getKeyCoachSportsids().equalsIgnoreCase("")) {

//            List<String> listresult = new ArrayList<>();
//            listresult.add(new SessionManager(context).getKeyCoachSportsids());
//            List<String> myList = new ArrayList<String>(Arrays.asList(new SessionManager(context).getKeyCoachSportsids().split(",")));
//            result = TextUtils.join(",", listresult);


            List<String> myList = new ArrayList<String>(Arrays.asList(new SessionManager(context).getKeyCoachSportsids().split(",")));
            result = TextUtils.join(",", myList);

            for (int i = 0; i < myList.size(); i++) {
                if (String.valueOf(bean.getId()).equalsIgnoreCase(myList.get(i))) {
                    holder.chkSports.setChecked(true);

                    result = TextUtils.join(",", myList);
//                    Functions.dialogShow(context);
                    ((CoachInformationActivity) context).CallCoachCategoryPositionResponse(result);
                }
            }

        }

        holder.chkSports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> list = new ArrayList<String>(Arrays.asList(new SessionManager(context).getKeyCoachSportsids().split(",")));
                String results = TextUtils.join(",", list);

                if (!holder.chkSports.isChecked()) {
                    holder.chkSports.setChecked(false);

                    List<String> myList = new ArrayList<String>(Arrays.asList(results.split(",")));

                    for (int i = 0; i < myList.size(); i++) {
                        if (myList.get(i).equalsIgnoreCase(String.valueOf(bean.getId()))) {
                            myList.remove(i);

                            results = TextUtils.join(",", myList);
                            new SessionManager(context).setKeyCoachSportsids(results);
                            Functions.dialogShow(context);
                            ((CoachInformationActivity) context).CallCoachCategoryPositionResponse(results);
                        }

                    }

                } else {
                    holder.chkSports.setChecked(true);

                    list.add(String.valueOf(bean.getId()));
                    results = TextUtils.join(",", list);
                    new SessionManager(context).setKeyCoachSportsids(results);

                    Functions.dialogShow(context);
                    ((CoachInformationActivity) context).CallCoachCategoryPositionResponse(results);

                }

            }
        });


//        holder.chkSports.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//
//
////                List<String> list = new ArrayList<>();
////                list.add(new SessionManager(context).getKeyCoachSportsids());
////                String results = TextUtils.join(",", list);
//
//                List<String> list = new ArrayList<String>(Arrays.asList(new SessionManager(context).getKeyCoachSportsids().split(",")));
//                String results = TextUtils.join(",", list);
//
//                if (compoundButton.isChecked()) {
//                    list.add(String.valueOf(detail.get(position).getId()));
//                    results = TextUtils.join(",", list);
//                    new SessionManager(context).setKeyCoachSportsids(results);
//
////                    Functions.dialogShow(context);
//                    ((CoachInformationActivity) context).CallCoachCategoryPositionResponse(results);
//
//                } else {
//                    List<String> myList = new ArrayList<String>(Arrays.asList(results.split(",")));
//
//                    for (int i = 0; i < myList.size(); i++) {
//                        if (myList.get(i).equalsIgnoreCase(String.valueOf(bean.getId()))) {
//                            myList.remove(i);
//
//                            results = TextUtils.join(",", myList);
//                            new SessionManager(context).setKeyCoachSportsids(results);
////                            Functions.dialogShow(context);
//                            ((CoachInformationActivity) context).CallCoachCategoryPositionResponse(results);
//                        }
//
//                    }
//                }
//            }
//        });

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
