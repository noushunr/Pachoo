package com.presentation.app.dealsnest.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.presentation.app.dealsnest.R;
import com.presentation.app.dealsnest.api.ApiClient;
import com.presentation.app.dealsnest.models.Offer;

import java.util.List;

public class ReviewBookingAdapter extends RecyclerView.Adapter<ReviewBookingAdapter.MyViewHolder> {

    private Context mContext;
    private List<Offer> reviewBookings;

    public ReviewBookingAdapter(Context mContext, List<Offer> reviewBookings) {
        this.mContext = mContext;
        this.reviewBookings = reviewBookings;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View ItemView = LayoutInflater.from(mContext).inflate(R.layout.review_booking_item, viewGroup, false);
        return new MyViewHolder(ItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Offer offer = reviewBookings.get(i);

        int COUNT = offer.getCount();
        double price = Double.parseDouble(offer.getOfferPrice());
        double Total_Rate = COUNT * price;

        String TOTAL = "₹" + String.format("%.2f", Total_Rate);
        myViewHolder.tvDealName.setText(offer.getName());
        myViewHolder.tvDealDesc.setText(offer.getDescription());
        myViewHolder.tvPrice.setText(TOTAL);
        Glide.with(mContext)
                .setDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.place_holder_small))
                .load(ApiClient.VIEW_ALL_BASE_URL + offer.getFeaturedImage()).into(myViewHolder.imThumbnail);
    }

    @Override
    public int getItemCount() {
        return reviewBookings.size();
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
