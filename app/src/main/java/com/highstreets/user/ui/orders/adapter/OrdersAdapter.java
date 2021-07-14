package com.highstreets.user.ui.orders.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
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
        String name = "";
        holder.tvName.setText(String.valueOf(Html.fromHtml(name)));
        holder.tvTotal.setText(context.getString(R.string.pound_symbol) + order.getAmount());
        holder.tvOrderId.setText(order.getOrderId()+"");
        String status = order.getOrderStatus();
        switch (order.getOrderStatus()){
            case "1":
                status = "Placed";
                holder.tvCancel.setVisibility(View.VISIBLE);
                holder.tvNote.setVisibility(View.VISIBLE);
                break;
            case "2":
                status = "Item prepared";
                holder.tvCancel.setVisibility(View.VISIBLE);
                holder.tvNote.setVisibility(View.VISIBLE);
                break;
            case "3":
                status = "Packed";
                holder.tvCancel.setVisibility(View.VISIBLE);
                holder.tvNote.setVisibility(View.VISIBLE);
                break;
            case "4":
                status = "Picked";
                holder.tvCancel.setVisibility(View.GONE);
                holder.tvNote.setVisibility(View.GONE);
                break;
            case "5":
                status = "Delivered";
                holder.tvCancel.setVisibility(View.GONE);
                holder.tvNote.setVisibility(View.GONE);
                break;
            case "0":
                status = "Cancelled";
                holder.tvCancel.setVisibility(View.GONE);
                holder.tvNote.setVisibility(View.GONE);
                break;

        }
        holder.tvStatus.setText(status);
//        Glide.with(context)
//                .load(order.getQrCodeImage())
//                .into(holder.ivQRCodeImage);

//        holder.ivQRCodeImage.setOnClickListener(view -> {
//            ShowQRCodeDialogFragment.newInstance(order.getQrCodeImage())
//                    .show(((AppCompatActivity)context).getSupportFragmentManager(), null);
//        });

        holder.btnView.setOnClickListener(view -> {
            Intent orderDetailsIntent = OrderDetailsActivity.start(context);
            orderDetailsIntent.putExtra(Constants.ORDER_ID, String.valueOf(order.getOrderId()));
            context.startActivity(orderDetailsIntent);
        });
        holder.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+918589800002"));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHold extends RecyclerView.ViewHolder {
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.btnCancel)
        TextView tvCancel;
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
        @BindView(R.id.tvNote)
        TextView tvNote;

        public ViewHold(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
