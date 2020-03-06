package com.presentation.app.dealsnest.adapters;

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
import com.presentation.app.dealsnest.R;
import com.presentation.app.dealsnest.api.ApiClient;
import com.presentation.app.dealsnest.models.notification_merchant_view_model;
import com.presentation.app.dealsnest.ui.offer_details.OfferDetailActivity;
import com.presentation.app.dealsnest.utils.Constants;

import java.util.List;

public class MerchantsAdapter extends RecyclerView.Adapter<MerchantsAdapter.MyViewHolder> {

    private Context mContext;
    private List<notification_merchant_view_model> merchantViewModelList;

    public MerchantsAdapter(Context mContext, List<notification_merchant_view_model> merchantViewModelList) {
        this.mContext = mContext;
        this.merchantViewModelList = merchantViewModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View ItemView = LayoutInflater.from(mContext).inflate(R.layout.notification_details_item, viewGroup, false);

        return new MyViewHolder(ItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        final notification_merchant_view_model items = merchantViewModelList.get(i);

        myViewHolder.tvPopularName.setText(items.getName());
        myViewHolder.tvPopularDesc.setText(items.getDescription());
        myViewHolder.tvValidityTo.setText(items.getOfferValidTo());
        String mrpStr = "₹" + items.getMrpPrice();
        String offerPriceStr = "₹" + items.getOfferPrice();
        String SAVING = String.valueOf(items.getSavings());
        String saveStr = "₹" + SAVING;

        myViewHolder.tvMrpPrice.setText(mrpStr);
        myViewHolder.tvOfferPrice.setText(offerPriceStr);
        myViewHolder.tvSavedPrice.setText(saveStr);

        Glide.with(mContext)
                .setDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.place_holder_small))
                .load(ApiClient.SHOP_MOST_POPULAR_BASE_URL + items.getFeaturedImage()).into(myViewHolder.imPopularThumbnail);

        myViewHolder.tvViewDeals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent offerDetailIntent = OfferDetailActivity.getActivityIntent(mContext);
                offerDetailIntent.putExtra(Constants.MERCHANT_ID, items.getMerchantId());
                offerDetailIntent.putExtra(Constants.OFFER_ID, items.getId());
                offerDetailIntent.putExtra(Constants.OFFER_DETAIL_TYPE, Constants.OFFER_TYPE_SINGLE);
                mContext.startActivity(offerDetailIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return merchantViewModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imPopularThumbnail;
        private TextView tvPopularName;
        private TextView tvPopularDesc;
        private TextView tvValidityTo;
        private TextView tvMrpPrice;
        private TextView tvOfferPrice;
        private TextView tvViewDeals;
        private TextView tvSavedPrice;

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
