package com.highstreets.user.adapters;

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
import com.highstreets.user.ui.cart.model.Product;

import java.util.List;

public class ReviewBookingAdapter extends RecyclerView.Adapter<ReviewBookingAdapter.MyViewHolder> {

    private Context mContext;
    private List<Product> productList;

    public ReviewBookingAdapter(Context mContext, List<Product> productList) {
        this.mContext = mContext;
        this.productList = productList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View ItemView = LayoutInflater.from(mContext).inflate(R.layout.review_booking_item, viewGroup, false);
        return new MyViewHolder(ItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Product product = productList.get(i);

//        int COUNT = product.getCount();
//        double price = Double.parseDouble(product.getOfferPrice());
//        double Total_Rate = COUNT * price;

        String TOTAL = mContext.getString(R.string.pound_symbol) + String.format("%.2f", product.getTotalPrice());
        myViewHolder.tvDealName.setText(product.getTitle());
        myViewHolder.tvDealDesc.setText(product.getDescription());
        myViewHolder.tvPrice.setText(TOTAL);
        Glide.with(mContext)
                .load(ApiClient.VIEW_ALL_BASE_URL + product.getImage())
                .into(myViewHolder.imThumbnail);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDealName, tvDealDesc, tvPrice;
        private ImageView imThumbnail;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imThumbnail = itemView.findViewById(R.id.deals_thumbnail);
            tvDealName = itemView.findViewById(R.id.txt_order_name);
            tvDealDesc = itemView.findViewById(R.id.order_desc);
            tvPrice = itemView.findViewById(R.id.txt_order_price);
        }
    }
}
