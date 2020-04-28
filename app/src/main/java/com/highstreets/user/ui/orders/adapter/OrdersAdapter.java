package com.highstreets.user.ui.orders.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.ButterKnife;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHold> {

    @NonNull
    @Override
    public ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHold holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHold extends RecyclerView.ViewHolder {
        public ViewHold(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
