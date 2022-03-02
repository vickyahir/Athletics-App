package com.example.athletics.Adapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Athletics.R;
import com.example.athletics.Utils.SessionManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CoachPositionAdapter extends RecyclerView.Adapter<CoachPositionAdapter.Myviewholder> {
    Context context;
    List<String> detail;


    public CoachPositionAdapter(Activity activity, List<String> muscles) {
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
        final String bean = detail.get(position);

        holder.chkSports.setText(bean);

        String result = "";
        if (!new SessionManager(context).getKeyCoachPositionstrings().equalsIgnoreCase("")) {

            List<String> myList = new ArrayList<String>(Arrays.asList(new SessionManager(context).getKeyCoachPositionstrings().split(",")));
            for (int i = 0; i < myList.size(); i++) {
                if (String.valueOf(bean).equalsIgnoreCase(myList.get(i))) {
                    holder.chkSports.setChecked(true);
                    result = TextUtils.join(",", myList);
                }
            }

            new SessionManager(context).setKeyCoachPositionstrings(result);
        }


        holder.chkSports.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


                List<String> list = new ArrayList<>();
                list.add(new SessionManager(context).getKeyCoachPositionstrings());
                String result = TextUtils.join(",", list);

                if (compoundButton.isChecked()) {

                    list.add(String.valueOf(bean));
                    result = TextUtils.join(",", list);
                    new SessionManager(context).setKeyCoachPositionstrings(result);

                } else {

                    List<String> myList = new ArrayList<String>(Arrays.asList(result.split(",")));

                    for (int i = 0; i < myList.size(); i++) {
                        if (myList.get(i).equalsIgnoreCase(String.valueOf(bean))) {
                            myList.remove(i);

                            result = TextUtils.join(",", myList);
                            new SessionManager(context).setKeyCoachPositionstrings(result);
                        }

                    }
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
