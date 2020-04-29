package com.highstreets.user.ui.orders.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.highstreets.user.R;
import com.highstreets.user.ui.orders.model.Order;

import java.util.List;

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
        holder.tvNumber.setText(order.getTelephone());
        holder.tvEmail.setText(order.getEmail());
        holder.tvTotal.setText(context.getString(R.string.pound_symbol) + order.getTotal());
        holder.tvOrderId.setText(order.getOrderId());

        holder.itemView.setOnClickListener(view -> {

        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHold extends RecyclerView.ViewHolder {
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvNumber)
        TextView tvNumber;
        @BindView(R.id.tvEmail)
        TextView tvEmail;
        @BindView(R.id.tvTotal)
        TextView tvTotal;
        @BindView(R.id.tvOrderId)
        TextView tvOrderId;

        public ViewHold(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
