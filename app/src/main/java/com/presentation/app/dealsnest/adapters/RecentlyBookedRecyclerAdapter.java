package com.presentation.app.dealsnest.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.presentation.app.dealsnest.R;
import com.presentation.app.dealsnest.api.ApiClient;
import com.presentation.app.dealsnest.models.RecentlyBookedShop;
import com.presentation.app.dealsnest.ui.shop_details.ShopActivity;
import com.presentation.app.dealsnest.utils.Constants;

import java.util.List;

public class RecentlyBookedRecyclerAdapter extends RecyclerView.Adapter<RecentlyBookedRecyclerAdapter.MyViewHolder> {

    private Context mContext;
    private List<RecentlyBookedShop> list;
    private ActionBar toolbar;

    public RecentlyBookedRecyclerAdapter(Context mContext, List<RecentlyBookedShop> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        toolbar = ((AppCompatActivity) mContext).getSupportActionBar();
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.recently_booked_item, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        final RecentlyBookedShop recentlyBookedShop = list.get(i);
        myViewHolder.mOfferName.setText(recentlyBookedShop.getBusinessName());
        myViewHolder.mOfferPrice.setText("₹" + recentlyBookedShop.getOfferPrice());
        myViewHolder.mOfferPercentage.setText(recentlyBookedShop.getOfferPercentage() + "%");

        Glide.with(mContext)
                .setDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.place_holder_rectangle))
                .load(ApiClient.RECENTLY_BOOKED + recentlyBookedShop.getImage()).into(myViewHolder.ImageThumbnail);

        myViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toShopDetailIntent = ShopActivity.getActivityIntent(mContext);
                toShopDetailIntent.putExtra(Constants.MERCHANT_ID, recentlyBookedShop.getMerchantId());
                mContext.startActivity(toShopDetailIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView mOfferName, mOfferPrice, mOfferPercentage;
        private ImageView ImageThumbnail;
        private LinearLayout linearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.card_layout);
            ImageThumbnail = itemView.findViewById(R.id.offer_thumbnail);
            mOfferName = itemView.findViewById(R.id.txt_offer_name);
            mOfferPrice = itemView.findViewById(R.id.txt_offer_price);
            mOfferPercentage = itemView.findViewById(R.id.txt_percentage);
        }
    }
}
