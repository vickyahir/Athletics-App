package com.example.athletics.Adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Athletics.R;
import com.example.athletics.Activity.AthleteInformationActivity;
import com.example.athletics.Model.AthleteInformationStatItem;

import java.util.List;


public class AthleteSportsStateAdapter extends RecyclerView.Adapter<AthleteSportsStateAdapter.Myviewholder> {
    Context context;
    public List<String> detail;
    public List<String> DummyString;
    public List<AthleteInformationStatItem> SelectedList;
    AthleteSportPositionStateAdapter.OnEdittextChanged onItemClickListener; //add this
    public EditText edtSportState;
    public TextView TvSportStateName;


    public AthleteSportsStateAdapter(Activity activity, List<String> muscles, List<AthleteInformationStatItem> selectList, AthleteSportPositionStateAdapter.OnEdittextChanged onItemClickListener) {
        this.context = activity;
        this.detail = muscles;
        this.SelectedList = selectList;
        this.onItemClickListener = onItemClickListener;


    }

    public AthleteSportsStateAdapter(AthleteInformationActivity context, List<String> athleteStatsList, List<String> s, AthleteSportPositionStateAdapter.OnEdittextChanged onItemClickListener) {
        this.context = context;
        this.detail = athleteStatsList;
        this.DummyString = s;
        this.onItemClickListener = onItemClickListener;


    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Myviewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sports_state, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final Myviewholder holder, final int position) {
        final String bean = detail.get(position);

        TvSportStateName.setText(bean);

        try {
            if (SelectedList.size() > 0) {
                for (int i = 0; i < SelectedList.size(); i++) {
                    if (SelectedList.get(i).getName().equalsIgnoreCase(bean)) {
                        edtSportState.setText(SelectedList.get(i).getValue());


                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        edtSportState.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
//                onItemClickListener.OnEdittextValue(holder.TvSportStateName.getText().toString(), holder.edtSportState.getText().toString());

            }
        });


    }

    @Override
    public int getItemCount() {
        return detail.size();
    }


    public class Myviewholder extends RecyclerView.ViewHolder {


        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            edtSportState = itemView.findViewById(R.id.edtSportState);
            TvSportStateName = itemView.findViewById(R.id.TvSportStateName);


        }
    }
}
