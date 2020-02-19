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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.presentation.app.dealsnest.R;
import com.presentation.app.dealsnest.api.ApiClient;
import com.presentation.app.dealsnest.models.BrandedShop;
import com.presentation.app.dealsnest.ui.shop_details.ShopActivity;
import com.presentation.app.dealsnest.utils.Constants;

import java.util.List;

public class BrandedShopAdapter extends RecyclerView.Adapter<BrandedShopAdapter.MyViewHolder> {
    private Context mContext;
    private List<BrandedShop> brandedShopList;
    private ActionBar toolbar;

    public BrandedShopAdapter(Context mContext, List<BrandedShop> brandedShops) {
        this.mContext = mContext;
        this.brandedShopList = brandedShops;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        toolbar = ((AppCompatActivity) mContext).getSupportActionBar();
        View ItemView = LayoutInflater.from(mContext).inflate(R.layout.best_offer_card, viewGroup, false);
        return new MyViewHolder(ItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        final BrandedShop brandedShop = brandedShopList.get(i);

        Glide.with(mContext)
                .setDefaultRequestOptions(new RequestOptions()
                        .placeholder(R.drawable.place_holder_square_large))
                .load(ApiClient.BRANDED_SHOP_BASE_URL + brandedShop.getFeaturedImage())
                .into(myViewHolder.BrandShopOffers);

        myViewHolder.BrandShopOffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shopDetailIntent = ShopActivity.getActivityIntent(mContext);
                shopDetailIntent.putExtra(Constants.MERCHANT_ID, brandedShop.getMerchantId());
                mContext.startActivity(shopDetailIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return brandedShopList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView BrandShopOffers;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            BrandShopOffers = itemView.findViewById(R.id.brand_shop_logo);
        }
    }
}
