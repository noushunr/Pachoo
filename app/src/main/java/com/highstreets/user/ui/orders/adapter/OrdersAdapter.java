package com.highstreets.user.ui.orders.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.highstreets.user.R;
import com.highstreets.user.ui.orders.model.Order;
import com.highstreets.user.ui.orders.order_details.OrderDetailsActivity;
import com.highstreets.user.utils.Constants;

import java.util.List;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHold> {

    private List<Order> orderList;
    private Context context;

    public OrdersAdapter(List<Order> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_order_item, parent, false);
        return new ViewHold(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHold holder, int position) {
        Order order = orderList.get(position);
        String name = order.getFirstname() + " " + order.getLastname();
        holder.tvName.setText(name);
        holder.tvTotal.setText(context.getString(R.string.pound_symbol) + order.getTotal());
        holder.tvOrderId.setText(order.getOrderId());
        holder.tvStatus.setText(order.getOrderStatus());
        Glide.with(context)
                .load(order.getQrCodeImage())
                .into(holder.ivQRCodeImage);

        holder.ivQRCodeImage.setOnClickListener(view -> {
            ShowQRCodeDialogFragment.newInstance(order.getQrCodeImage())
                    .show(((AppCompatActivity)context).getSupportFragmentManager(), null);
        });

        holder.btnView.setOnClickListener(view -> {
            Intent orderDetailsIntent = OrderDetailsActivity.start(context);
            orderDetailsIntent.putExtra(Constants.ORDER_ID, order.getOrderId());
            context.startActivity(orderDetailsIntent);
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHold extends RecyclerView.ViewHolder {
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvTotal)
        TextView tvTotal;
        @BindView(R.id.tvOrderId)
        TextView tvOrderId;
        @BindView(R.id.ivQRCodeImage)
        ImageView ivQRCodeImage;
        @BindView(R.id.tvStatus)
        TextView tvStatus;
        @BindView(R.id.btnView)
        Button btnView;

        public ViewHold(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
