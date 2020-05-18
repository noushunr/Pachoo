package com.highstreets.user.ui.cart.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.highstreets.user.R;
import com.highstreets.user.api.ApiClient;
import com.highstreets.user.ui.cart.model.Product;
import com.highstreets.user.ui.cart.product_details.ProductDetailsActivity;
import com.highstreets.user.utils.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHold> {

    private List<Product> productList;
    private Context context;
    private QuantityChange quantityChange;
    private RemoveCartItem removeCartItem;

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
        if (context instanceof RemoveCartItem){
            removeCartItem = (RemoveCartItem) context;
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
                .load(ApiClient.OFFERS_IMAGE_URL + product.getImage())
                .into(holder.ivItemImage);

        holder.ivRemove.setOnClickListener(view -> {
            new AlertDialog.Builder(context)
                    .setTitle("Remove?")
                    .setMessage("Are you sure to remove this item")
                    .setPositiveButton("yes", (dialogInterface, i) -> {
                        removeCartItem.remove(product.getCartId());
                    })
                    .setNegativeButton("no", (dialogInterface, i) -> {
                        dialogInterface.dismiss();
                    })
                    .show();
        });

        holder.itemView.setOnClickListener(view -> {
            Intent toDetailsIntent = ProductDetailsActivity.start(context);
            toDetailsIntent.putExtra(Constants.PRODUCT_ID, product.getProductId());
            context.startActivity(toDetailsIntent);
        });

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
        @BindView(R.id.ivRemove)
        ImageView ivRemove;

        public ViewHold(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface QuantityChange{
        void incrementCount(Product product);
        void decrementCount(Product product);
    }

    public interface RemoveCartItem{
        void remove(String cartId);
    }

}
