package com.presentation.app.dealsnest.adapters;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
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
import com.presentation.app.dealsnest.models.MostViewedShop;
import com.presentation.app.dealsnest.ui.shop_details.ShopActivity;
import com.presentation.app.dealsnest.utils.Constants;

import java.util.List;

public class MostVisitedShopAdapter extends RecyclerView.Adapter<MostVisitedShopAdapter.MyViewHolder> {

    private Context mContext;
    private List<MostViewedShop> mostVisitedShopsList;

    public MostVisitedShopAdapter(Context mContext, List<MostViewedShop> mostVisitedShopsList) {
        this.mContext = mContext;
        this.mostVisitedShopsList = mostVisitedShopsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.deals_item, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        final MostViewedShop mostVisitedShops = mostVisitedShopsList.get(i);

        myViewHolder.TxtOfferName.setText(mostVisitedShops.getBusinessName());
        myViewHolder.TxtOfferPrice.setText(mContext.getString(R.string.pound_symbol) + mostVisitedShops.getOfferPrice());
        myViewHolder.TxtPercentage.setText(mostVisitedShops.getOfferPercentage() + "%");

        Glide.with(mContext)
                .setDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.place_holder_rectangle))
                .load(ApiClient.DEALS_BASE_URL + mostVisitedShops.getImage()).into(myViewHolder.DealsThumbnails);

        myViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shopDetailIntent = ShopActivity.getActivityIntent(mContext);
                shopDetailIntent.putExtra(Constants.MERCHANT_ID, mostVisitedShops.getMerchantId());
                mContext.startActivity(shopDetailIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mostVisitedShopsList.size();
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
