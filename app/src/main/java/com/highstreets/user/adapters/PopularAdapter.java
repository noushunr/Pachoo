package com.highstreets.user.adapters;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.highstreets.user.R;
import com.highstreets.user.api.ApiClient;
import com.highstreets.user.models.MostPopular;
import com.highstreets.user.ui.offer_details.OfferDetailActivity;
import com.highstreets.user.utils.Constants;

import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.MyViewHolder> {

    private Context mContext;
    private List<MostPopular> popularModels;

    public PopularAdapter(Context mContext, List<MostPopular> popularModels) {
        this.mContext = mContext;
        this.popularModels = popularModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View Item = LayoutInflater.from(mContext).inflate(R.layout.popular_card_item, viewGroup, false);
        return new MyViewHolder(Item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        final MostPopular mostPopular = popularModels.get(i);

        String SAVING = String.valueOf(mostPopular.getSavings());
        myViewHolder.tvPopularName.setText(mostPopular.getName());
        myViewHolder.tvPopularDesc.setText(mostPopular.getDescription());
        myViewHolder.tvValidityTo.setText(mostPopular.getOfferValidTo());
        String mrpStr = mContext.getString(R.string.pound_symbol) + mostPopular.getMrpPrice();
        String offerPriceStr = mContext.getString(R.string.pound_symbol) + mostPopular.getOfferPrice();
        String saveStr = mContext.getString(R.string.pound_symbol) + SAVING;
        myViewHolder.tvMrpPrice.setText(mrpStr);
        myViewHolder.tvOfferPrice.setText(offerPriceStr);
        myViewHolder.tvSavedPrice.setText(saveStr);

        Glide.with(mContext)
                .setDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.place_holder_small))
                .load(ApiClient.SHOP_MOST_POPULAR_BASE_URL + mostPopular.getFeaturedImage()).into(myViewHolder.imPopularThumbnail);

        myViewHolder.tvViewDeals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent offerDetailIntent = OfferDetailActivity.getActivityIntent(mContext);
                offerDetailIntent.putExtra(Constants.MERCHANT_ID, mostPopular.getMerchantId());
                offerDetailIntent.putExtra(Constants.OFFER_ID, mostPopular.getId());
                offerDetailIntent.putExtra(Constants.OFFER_DETAIL_TYPE, Constants.OFFER_TYPE_SINGLE);

                mContext.startActivity(offerDetailIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (popularModels.size() < 3) {
            return popularModels.size();
        } else {
            return 3;
        }
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imPopularThumbnail;
        private TextView tvPopularName, tvSavedPrice, tvPopularDesc, tvValidityTo, tvMrpPrice, tvOfferPrice, tvViewDeals;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imPopularThumbnail = itemView.findViewById(R.id.popular_card_thumbnail);
            tvPopularName = itemView.findViewById(R.id.popular_heading);
            tvPopularDesc = itemView.findViewById(R.id.popular_desc);
            tvValidityTo = itemView.findViewById(R.id.popular_validity_value);
            tvMrpPrice = itemView.findViewById(R.id.popular_mrp_value);
            tvOfferPrice = itemView.findViewById(R.id.popular_offer_price_value);
            tvViewDeals = itemView.findViewById(R.id.popular_view_deals);
            tvSavedPrice = itemView.findViewById(R.id.popular_saved_value);
        }
    }
}
