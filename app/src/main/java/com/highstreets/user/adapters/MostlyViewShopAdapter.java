package com.highstreets.user.adapters;

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
import com.highstreets.user.R;
import com.highstreets.user.api.ApiClient;
import com.highstreets.user.models.MostViewedShop;
import com.highstreets.user.ui.shop_details.ShopActivity;
import com.highstreets.user.utils.Constants;

import java.util.List;

public class MostlyViewShopAdapter extends RecyclerView.Adapter<MostlyViewShopAdapter.MyViewHolder> {

    private Context mContext;
    private List<MostViewedShop> mostViewedShopList;

    public MostlyViewShopAdapter(Context mContext, List<MostViewedShop> mostViewedShopList) {
        this.mContext = mContext;
        this.mostViewedShopList = mostViewedShopList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(mContext).inflate(R.layout.most_viewed_shop_item, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        final MostViewedShop mostVisitedShops = mostViewedShopList.get(i);

        myViewHolder.TxtOfferName.setText(mostVisitedShops.getBusinessName());
        myViewHolder.TxtPercentage.setText(mostVisitedShops.getOfferPercentage() + "%");

        if (mostVisitedShops.getOfferPrice() != null) {
            myViewHolder.TxtOfferPrice.setText(mostVisitedShops.getOfferPrice());
        } else {
            myViewHolder.TxtOfferPrice.setText("0.00");
        }

        Glide.with(mContext)
                .setDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.place_holder_rectangle))
                .load(ApiClient.MERCHANTS_IMAGE_URL + mostVisitedShops.getImage()).into(myViewHolder.DealsThumbnails);

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
        return mostViewedShopList.size();
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
