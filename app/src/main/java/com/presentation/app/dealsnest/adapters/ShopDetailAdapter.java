package com.presentation.app.dealsnest.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.presentation.app.dealsnest.R;
import com.presentation.app.dealsnest.api.ApiClient;
import com.presentation.app.dealsnest.models.shop.Shop;
import com.presentation.app.dealsnest.ui.shop_details.ShopActivity;
import com.presentation.app.dealsnest.utils.Constants;

import java.util.List;

public class ShopDetailAdapter extends RecyclerView.Adapter<ShopDetailAdapter.ViewHold> {
    private Context context;
    private List<Shop> shopList;

    public ShopDetailAdapter(Context context, List<Shop> shopList) {
        this.context = context;
        this.shopList = shopList;
    }

    @NonNull
    @Override
    public ViewHold onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.most_sold_item, viewGroup, false);
        return new ViewHold(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHold viewHold, int i) {
        final Shop shop = shopList.get(i);

        String offerPercentage = "Upto " + shop.getOfferPercentage() + " %off";
        viewHold.tvOffPercentage.setText(offerPercentage);
        viewHold.tvShopName.setText(shop.getBusinessName());
        viewHold.tvCost.setText(" ₹ " + shop.getOfferPrice());
        viewHold.tvDistance.setText(shop.getDistance() + " kms-");
        viewHold.tvPlace.setText(shop.getCity());
        viewHold.tvBroughtCount.setText(shop.getBought() + " bought");
        viewHold.ratingBar.setRating(!TextUtils.isEmpty(shop.getRatings()) ? Float.parseFloat(shop.getRatings()) : 0);

        Glide.with(context)
                .load(ApiClient.SHOP_LIST_SHOPS_BASE_URL + shop.getImage())
                .into(viewHold.ivShop);

        viewHold.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toShopDetailIntent = ShopActivity.getActivityIntent(context);
                toShopDetailIntent.putExtra(Constants.MERCHANT_ID, shop.getMerchantId());
                context.startActivity(toShopDetailIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return shopList.size();
    }

    public class ViewHold extends RecyclerView.ViewHolder {
        private TextView tvOffPercentage;
        private TextView tvShopName;
        private TextView tvDistance;
        private TextView tvPlace;
        private TextView tvCost;
        private TextView tvBroughtCount;
        private ImageView ivFavorite;
        private ImageView ivShop;
        private RatingBar ratingBar;

        public ViewHold(@NonNull View itemView) {
            super(itemView);
            tvOffPercentage = itemView.findViewById(R.id.tvOffPercentage);
            tvShopName = itemView.findViewById(R.id.tvShopName);
            tvDistance = itemView.findViewById(R.id.tvDistance);
            tvPlace = itemView.findViewById(R.id.tvPlace);
            tvCost = itemView.findViewById(R.id.tvCost);
            tvBroughtCount = itemView.findViewById(R.id.tvBroughtCount);
            ivFavorite = itemView.findViewById(R.id.ivFavorite);
            ivShop = itemView.findViewById(R.id.ivShop);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }
    }
}
