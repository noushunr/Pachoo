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
import com.bumptech.glide.request.RequestOptions;
import com.presentation.app.dealsnest.R;
import com.presentation.app.dealsnest.api.ApiClient;
import com.presentation.app.dealsnest.models.ViewAllDeals;
import com.presentation.app.dealsnest.ui.shop_details.ShopActivity;
import com.presentation.app.dealsnest.utils.Constants;

import java.util.List;

public class ViewAllDealsAdapter extends RecyclerView.Adapter<ViewAllDealsAdapter.MyViewHolder> {
    private List<ViewAllDeals> viewAllDealsList;
    private Context mContext;

    public ViewAllDealsAdapter(Context mContext, List<ViewAllDeals> viewAllDealsList) {
        this.viewAllDealsList = viewAllDealsList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View ItemView = LayoutInflater.from(mContext).inflate(R.layout.deals_item, viewGroup, false);
        return new MyViewHolder(ItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        final ViewAllDeals deal = viewAllDealsList.get(i);

        myViewHolder.TxtOfferName.setText(deal.getBusinessName());
        myViewHolder.TxtOfferPrice.setText(deal.getOfferPrice());
        myViewHolder.TxtPercentage.setText(deal.getOfferPercentage() + "%");

        Glide.with(mContext)
                .setDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.place_holder_rectangle))
                .load(ApiClient.DEALS_BASE_URL + deal.getImage())
                .into(myViewHolder.DealsThumbnails);

        myViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shopDetailIntent = ShopActivity.getActivityIntent(mContext);
                shopDetailIntent.putExtra(Constants.MERCHANT_ID, deal.getMerchantId());
                mContext.startActivity(shopDetailIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return viewAllDealsList.size();
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
