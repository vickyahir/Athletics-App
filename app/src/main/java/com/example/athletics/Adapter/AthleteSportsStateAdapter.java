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
    AthleteSportPositionStateAdapter.OnEdittextChanged OnEdittextChanged; //add this
    public EditText edtSportState;
    public TextView TvSportStateName;
    int pos = -1;


    public AthleteSportsStateAdapter(Activity activity, List<String> muscles, List<AthleteInformationStatItem> selectList, AthleteSportPositionStateAdapter.OnEdittextChanged OnEdittextChanged) {
        this.context = activity;
        this.detail = muscles;
        this.SelectedList = selectList;
        this.OnEdittextChanged = OnEdittextChanged;
        /*for (int i = 0; i < detail.size(); i++) {
            System.out.println("detail size is >>>"+detail.get(i));

        }*/
        System.out.println("detail size is >>>" + muscles.size());
        //  System.out.println("detailslist is >>>" + detailsList.size());

        // detailsList.addAll(muscles);
    }

    public AthleteSportsStateAdapter(AthleteInformationActivity context, List<String> athleteStatsList, List<String> s, AthleteSportPositionStateAdapter.OnEdittextChanged OnEdittextChanged) {
        this.context = context;
        this.detail = athleteStatsList;
        this.DummyString = s;
        this.OnEdittextChanged = OnEdittextChanged;


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
//                        OnEdittextChanged.getEditTextValue(SelectedList.get(i).getValue(), bean, position);
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
                // SelectedList.get(position).getValue() = s.toString();
                System.out.println("edt :::::" + edtSportState.getText().toString());
                // OnEdittextChanged.getEditTextValue(edtSportState.getText().toString(), bean, position);
                OnEdittextChanged.getEditTextValue(charSequence.toString(), bean, position);

            }

            @Override
            public void afterTextChanged(Editable editable) {

                // int position = (int) edtSportState.getTag();
                // System.out.println("position is :::::::::::" + position);

                // OnEdittextChanged.getEditTextValue(edtSportState.getText().toString(), bean, position);
//                pos = position;
//                if (SelectedList.size() > 0) {
//                    for (int i = 0; i < SelectedList.size(); i++) {
//                        if (pos == i) {
//                            System.out.println(">>>>"+SelectedList.get(pos).getName());
//                            System.out.println("::::"+TvSportStateName.getText().toString());
//                            if (SelectedList.get(pos).getName().equalsIgnoreCase(TvSportStateName.getText().toString())){
//                               // System.out.println("edt >>>"+edtSportState.getText().toString());
//                            }
//
//                           // if (SelectedList.get(pos).getValue().equalsIgnoreCase(edtSportState.getText().toString()))
//                          //  OnEdittextChanged.getEditTextValue(edtSportState.getText().toString(), bean, position);
//
//                        }
//                        /*if (SelectedList.get(i).getName().equalsIgnoreCase(bean)) {
//                            //edtSportState.setText(SelectedList.get(i).getValue());
//                            //OnEdittextChanged.getEditTextValue(edtSportState.getText().toString(),bean,position);
//
//
//                        }*/
//                    }
//                }

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
