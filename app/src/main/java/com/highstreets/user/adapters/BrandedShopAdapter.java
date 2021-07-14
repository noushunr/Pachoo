package com.highstreets.user.adapters;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.highstreets.user.R;
import com.highstreets.user.api.ApiClient;
import com.highstreets.user.models.BrandedShop;
import com.highstreets.user.models.Success;
import com.highstreets.user.ui.product.OfferDetailActivity;
import com.highstreets.user.ui.shop_details.ShopActivity;
import com.highstreets.user.utils.Constants;

import java.util.List;

public class BrandedShopAdapter extends RecyclerView.Adapter<BrandedShopAdapter.MyViewHolder> {
    private Context mContext;
    private List<Success> brandedShopList;
    private ActionBar toolbar;

    public BrandedShopAdapter(Context mContext, List<Success> brandedShops) {
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

        final Success brandedShop = brandedShopList.get(i);
        myViewHolder.mBrandName.setText(brandedShop.getProductName());
        Glide.with(mContext)
                .setDefaultRequestOptions(new RequestOptions()
                        .placeholder(R.drawable.place_holder_square_large))
                .load(brandedShop.getImage())
                .into(myViewHolder.BrandShopOffers);

        myViewHolder.BrandShopOffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toDetailsIntent = new Intent(mContext, OfferDetailActivity.class);
                Bundle args = new Bundle();
                args.putSerializable("product",brandedShop);
                toDetailsIntent.putExtras(args);
                mContext.startActivity(toDetailsIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return brandedShopList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView BrandShopOffers;
        private TextView mBrandName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            BrandShopOffers = itemView.findViewById(R.id.brand_shop_logo);
            mBrandName = itemView.findViewById(R.id.txt_brands_name);
        }
    }
}
