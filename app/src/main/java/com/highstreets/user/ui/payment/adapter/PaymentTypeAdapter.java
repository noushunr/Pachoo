package com.highstreets.user.ui.payment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.highstreets.user.R;
import com.highstreets.user.ui.payment.model.PaymentType;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PaymentTypeAdapter extends RecyclerView.Adapter<PaymentTypeAdapter.ViewHold> {

    private List<PaymentType> paymentTypeList;
    private Context context;

    public PaymentTypeAdapter(List<PaymentType> paymentTypeList) {
        this.paymentTypeList = paymentTypeList;
    }

    @NonNull
    @Override
    public ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_payment_type_item, parent, false);
        return new ViewHold(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHold holder, int position) {
        PaymentType paymentType = paymentTypeList.get(position);
        holder.tvPaymentType.setText(paymentType.getType());
        Glide.with(context)
                .load(paymentType.getTypeImage())
                .into(holder.ivPaymentIcon);
        if (paymentType.isSelected()){
            holder.clPaymentType.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        } else {
            holder.clPaymentType.setBackgroundColor(context.getResources().getColor(R.color.transparent));
        }

        holder.clPaymentType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (paymentType.isSelected()){
                    paymentType.setSelected(false);
                } else {
                    clearSelection();
                    paymentType.setSelected(true);
                }
                notifyDataSetChanged();
            }
        });
    }

    private void clearSelection(){
        for (PaymentType paymentType : paymentTypeList){
            paymentType.setSelected(false);
        }
    }

    @Override
    public int getItemCount() {
        return paymentTypeList.size();
    }

    public class ViewHold extends RecyclerView.ViewHolder {
        @BindView(R.id.clPaymentType)
        ConstraintLayout clPaymentType;
        @BindView(R.id.ivPaymentIcon)
        ImageView ivPaymentIcon;
        @BindView(R.id.tvPaymentType)
        TextView tvPaymentType;

        public ViewHold(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
