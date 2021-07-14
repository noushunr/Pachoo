package com.highstreets.user.ui.orders.order_details.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
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
import com.highstreets.user.ui.cart.product_details.ProductDetailsActivity;
import com.highstreets.user.ui.orders.order_details.model.Product;
import com.highstreets.user.utils.Constants;

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
        holder.tvProductName.setText(String.valueOf(Html.fromHtml(product.getProductName())));
        if (product.getBoxQuantity()!=null && product.getUnit()!=null){
            holder.tvQuantity.setVisibility(View.VISIBLE);
            holder.tvQuantityText.setVisibility(View.VISIBLE);
            int quantity = (int) Double.parseDouble(product.getBoxQuantity());
            holder.tvQuantity.setText(String.valueOf(quantity)+" "+product.getUnit());
        }else {
            holder.tvQuantity.setVisibility(View.GONE);
            holder.tvQuantityText.setVisibility(View.GONE);
        }

        holder.tvPrice.setText(context.getString(R.string.pound_symbol) + product.getOfferPrice());
        Glide.with(context)
                .load(product.getImage())
                .into(holder.ivImage);

        holder.itemView.setOnClickListener(view -> {
//            Intent intent = ProductDetailsActivity.start(context);
//            intent.putExtra(Constants.PRODUCT_ID, product.getProductId());
//            context.startActivity(intent);
        });
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
        @BindView(R.id.tvQuantityText)
        TextView tvQuantityText;

        public ViewHold(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
