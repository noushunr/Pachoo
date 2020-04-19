package com.highstreets.user.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.highstreets.user.R;
import com.highstreets.user.api.ApiClient;
import com.highstreets.user.ui.cart.model.Product;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHold> {

    private List<Product> productList;
    private Context context;
    private QuantityChange quantityChange;

    public CartAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        if (context instanceof QuantityChange){
            quantityChange = (QuantityChange) context;
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_cart_item, parent, false);
        return new ViewHold(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHold holder, int position) {
        Product product = productList.get(position);
        holder.tvItemName.setText(product.getTitle());
        String totalPrice = context.getString(R.string.pound_symbol)+ product.getTotalPrice();
        holder.tvPrice.setText(totalPrice);
        holder.tvQuantity.setText(product.getQty());
        Glide.with(context)
                .load(ApiClient.VIEW_ALL_BASE_URL + product.getImage())
                .into(holder.ivItemImage);

//        holder.btnMinus.setOnClickListener(view -> {
//            quantityChange.decrementCount(product);
//            holder.btnMinus.setEnabled(false);
//        });
//
//        holder.btnPlus.setOnClickListener(view -> {
//            quantityChange.incrementCount(product);
//            holder.btnPlus.setEnabled(false);
//        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHold extends RecyclerView.ViewHolder {
        @BindView(R.id.ivItemImage)
        ImageView ivItemImage;
        @BindView(R.id.tvItemName)
        TextView tvItemName;
        @BindView(R.id.tvPrice)
        TextView tvPrice;
        @BindView(R.id.btnMinus)
        Button btnMinus;
        @BindView(R.id.btnPlus)
        Button btnPlus;
        @BindView(R.id.tvQuantity)
        TextView tvQuantity;
        @BindView(R.id.tvCount)
        TextView tvCount;
        @BindView(R.id.btnRemove)
        Button btnRemove;
        @BindView(R.id.btnView)
        Button btnView;

        public ViewHold(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface QuantityChange{
        void incrementCount(Product product);
        void decrementCount(Product product);
    }
}
