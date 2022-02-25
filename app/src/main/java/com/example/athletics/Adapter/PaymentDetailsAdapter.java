package com.example.athletics.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Athletics.R;
import com.example.athletics.Model.PaymentDataItem;

import java.util.List;


public class PaymentDetailsAdapter extends RecyclerView.Adapter<PaymentDetailsAdapter.Myviewholder> {
    Context context;
    List<PaymentDataItem> muscles;


    public PaymentDetailsAdapter(Activity activity, List<PaymentDataItem> muscles) {
        this.context = activity;
        this.muscles = muscles;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Myviewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_payment_details, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final Myviewholder holder, final int position) {
        final PaymentDataItem bean = muscles.get(position);

        holder.TvPaymentType.setText(bean.getPaymentMethod());
        holder.TvReferralCode.setText(String.valueOf(bean.getUniqueCode()));
        holder.TvDate.setText(String.valueOf(bean.getUniqueCode()));
        holder.TvStatus.setText(bean.getStatus());
        holder.TvDatAmount.setText("$ " + bean.getAmount() + " for " + bean.getDays() + " Days");


    }

    @Override
    public int getItemCount() {
        return muscles.size();
    }


    public class Myviewholder extends RecyclerView.ViewHolder {
        private TextView TvPaymentType, TvReferralCode, TvDate, TvStatus, TvDatAmount;


        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            TvPaymentType = itemView.findViewById(R.id.TvPaymentType);
            TvReferralCode = itemView.findViewById(R.id.TvReferralCode);
            TvDate = itemView.findViewById(R.id.TvDate);
            TvStatus = itemView.findViewById(R.id.TvStatus);
            TvDatAmount = itemView.findViewById(R.id.TvDatAmount);

        }
    }
}
