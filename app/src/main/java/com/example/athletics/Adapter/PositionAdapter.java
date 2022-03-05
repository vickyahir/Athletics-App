package com.example.athletics.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Athletics.R;

import java.util.List;


public class PositionAdapter extends RecyclerView.Adapter<PositionAdapter.Myviewholder> {
    Context context;
    List<String> detail;
    List<String> SelectedList;


    public PositionAdapter(Activity activity, List<String> muscles, List<String> select) {
        this.context = activity;
        this.detail = muscles;
        this.SelectedList = select;
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

        if (SelectedList.size() > 0) {
            for (int i = 0; i < SelectedList.size(); i++) {
                if (SelectedList.get(i).equalsIgnoreCase(bean)) {
                    holder.chkSports.setChecked(true);
                }
            }
        }


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
