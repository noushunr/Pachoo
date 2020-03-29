package com.highstreets.user.adapters;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.highstreets.user.R;
import com.highstreets.user.api.ApiClient;
import com.highstreets.user.common.CustomItemClickListener;
import com.highstreets.user.models.favorites_model;
import com.highstreets.user.ui.shop_details.ShopActivity;
import com.highstreets.user.utils.Constants;

import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.MyViewHolder> {

    private Context mContext;
    private List<favorites_model> favoritesModels;
    private CustomItemClickListener listener;

    public FavoritesAdapter(Context mContext, List<favorites_model> favoritesModels, CustomItemClickListener listener) {
        this.mContext = mContext;
        this.favoritesModels = favoritesModels;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View ItemView = LayoutInflater.from(mContext).inflate(R.layout.favorites_card, viewGroup, false);

        return new MyViewHolder(ItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        final favorites_model items = favoritesModels.get(i);

        String offer_percentage = items.getOfferPercentage();
        String OFFER_PERCENTAGE = "Upto " + offer_percentage + "% off on Open Voucher";
        myViewHolder.tvShopName.setText(items.getBusinessName());
        myViewHolder.tvBought.setText(items.getBought());
        String Place = items.getCityName() + "," + items.getDistrictName();
        myViewHolder.txt_places.setText(Place);

        if (items.getOfferPercentage() != null) {
            myViewHolder.tvOfferPercentage.setText(OFFER_PERCENTAGE);
            if (OFFER_PERCENTAGE.equals("null")) {
                myViewHolder.tvOfferPercentage.setText("0" + "%");
            }
        }
        Glide.with(mContext).load(ApiClient.GET_FAVOURITE_SHOP_BASE_URL + items.getImage())
                .into(myViewHolder.imFavouriteThumbnail);

        myViewHolder.imDeleteFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, i);
            }
        });
        myViewHolder.btnDeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ShopActivity.class);
                intent.putExtra(Constants.MERCHANT_ID, items.getMerchantId());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return favoritesModels.size();
    }

    public void clear() {
        favoritesModels.clear();
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imFavouriteThumbnail, imDeleteFav;
        private TextView tvShopName, tvOfferPercentage, tvBought, txt_places;
        private Button btnDeal;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imFavouriteThumbnail = itemView.findViewById(R.id.favorite_thumbnail);
            imDeleteFav = itemView.findViewById(R.id.im_fav_delete);
            tvShopName = itemView.findViewById(R.id.favorite_item_name);
            tvOfferPercentage = itemView.findViewById(R.id.fav_offer_percentage);
            tvBought = itemView.findViewById(R.id.bought_item_count);
            txt_places = itemView.findViewById(R.id.txt_places);
            btnDeal = itemView.findViewById(R.id.btnDeal);
        }
    }
}
