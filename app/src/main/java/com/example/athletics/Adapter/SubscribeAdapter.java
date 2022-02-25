package com.example.athletics.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Athletics.R;
import com.example.athletics.Activity.PaymentActivity;
import com.example.athletics.Activity.SubscribePackageActivity;
import com.example.athletics.Model.SuitablePlanDataItem;
import com.example.athletics.Utils.Functions;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class SubscribeAdapter extends RecyclerView.Adapter<SubscribeAdapter.Myviewholder> {
    Context context;
    List<SuitablePlanDataItem> muscles;
    List<String> detail;


    public SubscribeAdapter(Activity activity, List<SuitablePlanDataItem> muscles) {
        this.context = activity;
        this.muscles = muscles;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Myviewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subscribe, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final Myviewholder holder, final int position) {
        final SuitablePlanDataItem bean = muscles.get(position);

        holder.TvPlanName.setText(bean.getName());

        if (bean.getPrice().equalsIgnoreCase("0")) {
            if (!String.valueOf(bean.getFree_enable()).equalsIgnoreCase("") && String.valueOf(bean.getFree_enable()) != null) {
                if (String.valueOf(bean.getFree_enable()).equalsIgnoreCase("0")) {
                    holder.TvBuyNow.setBackground(context.getResources().getDrawable(R.drawable.bg_button));
                    holder.TvBuyNow.setTextColor(context.getResources().getColor(R.color.white));
                    holder.TvBuyNow.setClickable(true);
                    holder.TvBuyNow.setEnabled(false);
                } else {
                    holder.TvBuyNow.setBackground(context.getResources().getDrawable(R.drawable.bg_button_dynamic_unselected));
                    holder.TvBuyNow.setTextColor(context.getResources().getColor(R.color.black));
                    holder.TvBuyNow.setClickable(false);
                    holder.TvBuyNow.setEnabled(false);
                }
            }
        } else {
            holder.TvBuyNow.setBackground(context.getResources().getDrawable(R.drawable.bg_button));
            holder.TvBuyNow.setTextColor(context.getResources().getColor(R.color.white));
            holder.TvBuyNow.setClickable(true);
            holder.TvBuyNow.setEnabled(true);
        }

//        if (!String.valueOf(bean.getFree_enable()).equalsIgnoreCase("") && String.valueOf(bean.getFree_enable()) != null) {
//            if (String.valueOf(bean.getFree_enable()).equalsIgnoreCase("0")) {
//
//            } else {
//                holder.TvLogin.setBackground(context.getResources().getDrawable(R.drawable.bg_button_dynamic_unselected));
//                holder.TvLogin.setTextColor(context.getResources().getColor(R.color.black));
//                holder.TvLogin.setClickable(false);
//            }
//        }


        detail = new ArrayList<>();
        detail.addAll(bean.getDetail());
        holder.rvPackageDetails.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        holder.rvPackageDetails.setAdapter(new SubscribeDetailsAdapter(((SubscribePackageActivity) context), detail));

        if (!bean.getPrice().equalsIgnoreCase("") && !bean.getDay().equalsIgnoreCase("")) {
            try {
                if (!bean.getPrice().equalsIgnoreCase("0")) {
                    String Price = String.valueOf(PriceCalculation(bean.getPrice(), bean.getDay()));
                    holder.Tv_Price.setText(Price);
                } else {
                    holder.Tv_Price.setText("Free");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        holder.TvBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, PaymentActivity.class)
                        .putExtra("Id", bean.getId())
                        .putExtra("authLink", "www.google.com"));
                Functions.animNext(context);
            }
        });


    }

    @Override
    public int getItemCount() {
        return muscles.size();
    }


    public class Myviewholder extends RecyclerView.ViewHolder {
        private TextView Tv_Title, Tv_Price, TvPlanName, TvBuyNow;
        private RecyclerView rvPackageDetails;


        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            Tv_Title = itemView.findViewById(R.id.Tv_Title);
            Tv_Price = itemView.findViewById(R.id.Tv_Price);
            TvPlanName = itemView.findViewById(R.id.TvPlanName);
            rvPackageDetails = itemView.findViewById(R.id.rvPackageDetails);
            TvBuyNow = itemView.findViewById(R.id.TvBuyNow);

        }
    }

    private double PriceCalculation(String price, String days) {

        float calc = Float.parseFloat(days) / 30;
        long count = (long) Math.round(Double.parseDouble(String.valueOf(calc)));
        Double FinalValue = (double) Float.parseFloat(price) / count;
        DecimalFormat newFormat = new DecimalFormat("#.##");
        return Double.parseDouble(newFormat.format(FinalValue));

    }

}
