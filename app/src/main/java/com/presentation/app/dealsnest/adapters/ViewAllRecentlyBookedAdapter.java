package com.presentation.app.dealsnest.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.presentation.app.dealsnest.R;
import com.presentation.app.dealsnest.api.ApiClient;
import com.presentation.app.dealsnest.models.ViewAllRecentlyBooked;
import com.presentation.app.dealsnest.ui.shop_details.ShopActivity;
import com.presentation.app.dealsnest.utils.Constants;

import java.util.List;

public class ViewAllRecentlyBookedAdapter extends RecyclerView.Adapter<ViewAllRecentlyBookedAdapter.MyViewHolder> {
    private Context mContext;
    private List<ViewAllRecentlyBooked> viewAllRecentlyBookedList;

    public ViewAllRecentlyBookedAdapter(Context mContext, List<ViewAllRecentlyBooked> viewAllRecentlyBookedList) {
        this.mContext = mContext;
        this.viewAllRecentlyBookedList = viewAllRecentlyBookedList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.view_all_deals_item, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        final ViewAllRecentlyBooked recentlyBooked = viewAllRecentlyBookedList.get(i);

        myViewHolder.TxtOfferName.setText(recentlyBooked.getBusinessName());
        myViewHolder.TxtOfferPrice.setText(recentlyBooked.getOfferPrice());
        myViewHolder.TxtPercentage.setText(recentlyBooked.getOfferPercentage() + "%");

        Glide.with(mContext).load(ApiClient.DEALS_BASE_URL + recentlyBooked.getImage())
                .into(myViewHolder.DealsThumbnails);

        myViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shopDetailIntent = ShopActivity.getActivityIntent(mContext);
                shopDetailIntent.putExtra(Constants.MERCHANT_ID, recentlyBooked.getMerchantId());
                mContext.startActivity(shopDetailIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return viewAllRecentlyBookedList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView TxtOfferName, TxtOfferPrice, TxtPercentage;
        private ImageView DealsThumbnails;
        private LinearLayout linearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.card_layout);
            TxtOfferName = itemView.findViewById(R.id.txt_offer_name);
            TxtOfferPrice = itemView.findViewById(R.id.txt_offer_price);
            TxtPercentage = itemView.findViewById(R.id.txt_percentage);
            DealsThumbnails = itemView.findViewById(R.id.deals_thumbnail);
        }
    }
}
