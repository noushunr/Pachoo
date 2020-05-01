package com.highstreets.user.ui.orders.order_details.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.highstreets.user.R;
import com.highstreets.user.api.ApiClient;
import com.highstreets.user.ui.orders.order_details.model.Product;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderProductsAdapter extends RecyclerView.Adapter<OrderProductsAdapter.ViewHold> {

    private List<Product> productList;
    private Context context;

    public OrderProductsAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_order_product_item, parent, false);
        return new ViewHold(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHold holder, int position) {
        Product product = productList.get(position);
        holder.tvProductName.setText(product.getName());
        holder.tvQuantity.setText(product.getQuantity());
        holder.tvPrice.setText(context.getString(R.string.pound_symbol) + product.getTotal());
        Glide.with(context)
                .load(ApiClient.VIEW_ALL_BASE_URL + product.getFeaturedImage())
                .into(holder.ivImage);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHold extends RecyclerView.ViewHolder {
        @BindView(R.id.ivImage)
        ImageView ivImage;
        @BindView(R.id.tvProductName)
        TextView tvProductName;
        @BindView(R.id.tvQuantity)
        TextView tvQuantity;
        @BindView(R.id.tvPrice)
        TextView tvPrice;

        public ViewHold(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
