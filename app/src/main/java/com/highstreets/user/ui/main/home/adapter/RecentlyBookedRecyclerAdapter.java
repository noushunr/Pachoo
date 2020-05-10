package com.highstreets.user.ui.main.home.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
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
import com.highstreets.user.models.RecentlyBookedShop;
import com.highstreets.user.ui.shop_details.ShopActivity;
import com.highstreets.user.utils.Constants;

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
        String priceWithSymbol = mContext.getString(R.string.pound_symbol) + recentlyBookedShop.getOfferPrice();
        myViewHolder.mOfferPrice.setText(priceWithSymbol);
        String offerPercentageWithSymbol = recentlyBookedShop.getOfferPercentage() + mContext.getString(R.string.percentage_symbol);
        myViewHolder.mOfferPercentage.setText(offerPercentageWithSymbol);

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
