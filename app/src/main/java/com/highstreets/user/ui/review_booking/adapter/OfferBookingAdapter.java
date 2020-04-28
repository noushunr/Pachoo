package com.highstreets.user.ui.review_booking.adapter;

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
import com.highstreets.user.models.Offer;
import com.highstreets.user.ui.cart.model.Product;

import java.util.ArrayList;

public class OfferBookingAdapter extends RecyclerView.Adapter<OfferBookingAdapter.ViewHold> {

    private ArrayList<Offer> offerArrayList;
    private Context context;

    public OfferBookingAdapter(ArrayList<Offer> offerArrayList) {
        this.offerArrayList = offerArrayList;
    }

    @NonNull
    @Override
    public ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_booking_item, parent, false);
        return new ViewHold(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHold holder, int position) {
        Offer offer = offerArrayList.get(position);

        int COUNT = offer.getCount();
        double price = Double.parseDouble(offer.getOfferPrice());
        double Total_Rate = COUNT * price;

        String TOTAL = context.getString(R.string.pound_symbol) + String.format("%.2f", Total_Rate);
        holder.tvDealName.setText(offer.getName());
        holder.tvDealDesc.setText(offer.getDescription());
        holder.tvPrice.setText(TOTAL);
        Glide.with(context)
                .load(ApiClient.VIEW_ALL_BASE_URL + offer.getFeaturedImage())
                .into(holder.imThumbnail);
    }

    @Override
    public int getItemCount() {
        return offerArrayList.size();
    }

    public class ViewHold extends RecyclerView.ViewHolder {
        private TextView tvDealName, tvDealDesc, tvPrice;
        private ImageView imThumbnail;

        public ViewHold(@NonNull View itemView) {
            super(itemView);
            imThumbnail = itemView.findViewById(R.id.deals_thumbnail);
            tvDealName = itemView.findViewById(R.id.txt_order_name);
            tvDealDesc = itemView.findViewById(R.id.order_desc);
            tvPrice = itemView.findViewById(R.id.txt_order_price);
        }
    }
}
