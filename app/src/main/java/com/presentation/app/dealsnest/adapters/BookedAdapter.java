package com.presentation.app.dealsnest.adapters;

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
import com.bumptech.glide.request.RequestOptions;
import com.presentation.app.dealsnest.R;
import com.presentation.app.dealsnest.api.ApiClient;
import com.presentation.app.dealsnest.common.CustomItemClickListener;
import com.presentation.app.dealsnest.models.BookedOffers;
import com.presentation.app.dealsnest.ui.booked.ViewDealActivity;
import com.presentation.app.dealsnest.ui.write_review.WriteReviewActivity;
import com.presentation.app.dealsnest.utils.Constants;

import java.util.List;

public class BookedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<BookedOffers> bookedModels;

    public BookedAdapter(Context mContext, List<BookedOffers> bookedModels, CustomItemClickListener listener) {
        this.mContext = mContext;
        this.bookedModels = bookedModels;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        View v = null;
        switch (viewType) {
            case 0:
                v = inflater.inflate(R.layout.booked_coupon_item, viewGroup, false);
                break;

            case 1:
                v = inflater.inflate(R.layout.booked_card, viewGroup, false);
                break;
        }

        viewHolder = new OffersViewHolder(v);
        return viewHolder;

    }

    @Override
    public int getItemViewType(int position) {
        return Integer.parseInt(bookedModels.get(position).getType());
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        final BookedOffers bookedOffers = bookedModels.get(i);
        OffersViewHolder offersViewHolder = (OffersViewHolder) viewHolder;

        String imgUrl = ApiClient.IMAGE_URL_COUPONS + bookedOffers.getImage();
        offersViewHolder.tvValidTill.setText(bookedOffers.getValidTill());
        offersViewHolder.tvBookedTitle.setText(bookedOffers.getTitle());
        offersViewHolder.tvBookedCount.setText(bookedOffers.getUsed() + " Used");

        Glide.with(mContext)
                .setDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.place_holder_rectangle))
                .load(imgUrl)
                .into(offersViewHolder.imgCoupon);

        Glide.with(mContext)
                .load(ApiClient.VIEW_ALL_BASE_URL + bookedOffers.getImage())
                .into(offersViewHolder.imBookedThumbnail);

        try {
            if (bookedOffers.getDescription() != null) {
                offersViewHolder.tvBookedDesc.setText(bookedOffers.getDescription());
            } else {
                offersViewHolder.tvBookedDesc.setText("Description Not Provided for this offer");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (bookedOffers.getBookedStatus().equals("2")) {
            offersViewHolder.tvBookedStatus.setText(Constants.OFFER_USED);
            offersViewHolder.btnWriteOfferReview.setVisibility(View.VISIBLE);
            offersViewHolder.btnWriteOfferReview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, WriteReviewActivity.class);
                    intent.putExtra("offer_id", bookedOffers.getId());
                    intent.putExtra("merchant_id", bookedOffers.getMerchantId());
                    intent.putExtra("review_type", "0");
                    mContext.startActivity(intent);
                }
            });

        } else {
            offersViewHolder.tvBookedStatus.setText(Constants.OFFER_BOOKED);
            offersViewHolder.btnWriteOfferReview.setVisibility(View.GONE);
        }

        if (bookedOffers.getBookedStatus() != null) {
            if (bookedOffers.getBookedStatus().equals("1")) {
                offersViewHolder.book_now.setText(Constants.BOOKED);
            } else {
                offersViewHolder.book_now.setText(Constants.BOOK_NOW);
            }
        }


        final String percentOff;
        if (bookedOffers.getOfferPrice() != null) {
            if (bookedOffers.getOfferPrice().equals("")) {
                offersViewHolder.tvOfferPrice.setVisibility(View.INVISIBLE);
                offersViewHolder.txt_deal_price.setVisibility(View.INVISIBLE);
                percentOff = "0 % OFF";
            } else {
                offersViewHolder.tvOfferPrice.setText(mContext.getString(R.string.pound_symbol) + bookedOffers.getOfferPrice());
                float offerPrice = Float.parseFloat(bookedOffers.getOfferPrice());
                float mrpPrice = Float.parseFloat(bookedOffers.getMrpPrice());
                float offerPercentage = 100 - ((offerPrice * 100) / mrpPrice);
//                String.format("%.2f", offerPercentage);
                percentOff =  String.format("%.2f", offerPercentage) + " % OFF";
            }
        } else {
            percentOff = "0 % OFF";
        }

        if (bookedOffers.getMrpPrice() != null) {
            if (bookedOffers.getMrpPrice().equals("")) {
                offersViewHolder.tvMrpPrice.setVisibility(View.INVISIBLE);
                offersViewHolder.txt_deal_price.setVisibility(View.INVISIBLE);
            } else {
                offersViewHolder.tvMrpPrice.setText(mContext.getString(R.string.pound_symbol) + bookedOffers.getMrpPrice());
            }
        }

        offersViewHolder.btnViewDeals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toViewDeail = ViewDealActivity.getActivityIntent(mContext);
                if (bookedOffers.getType().equals("0")) {
                    toViewDeail.putExtra("image", ApiClient.VIEW_ALL_COUPONS_BASE_URL + bookedOffers.getImage());
                    toViewDeail.putExtra("name", bookedOffers.getTitle());
                    toViewDeail.putExtra("desc", bookedOffers.getDescription());
                    toViewDeail.putExtra("validity_till", bookedOffers.getValidTill());
                    toViewDeail.putExtra("total", bookedOffers.getOfferPrice());
                    toViewDeail.putExtra("merchant", bookedOffers.getMerchantName());
                    toViewDeail.putExtra("offerPercentage", bookedOffers.getOfferPercentage()+ " % OFF");
                } else {
                    toViewDeail.putExtra("image", ApiClient.VIEW_ALL_BASE_URL + bookedOffers.getImage());
                    toViewDeail.putExtra("name", bookedOffers.getTitle());
                    toViewDeail.putExtra("desc", bookedOffers.getDescription());
                    toViewDeail.putExtra("validity_till", bookedOffers.getValidTill());
                    toViewDeail.putExtra("total", bookedOffers.getOfferPrice());
                    toViewDeail.putExtra("merchant", bookedOffers.getMerchantName());
                    toViewDeail.putExtra("offerPercentage",bookedOffers.getOfferPercentage()+ " % OFF");
                }
                mContext.startActivity(toViewDeail);

            }
        });
    }


    @Override
    public int getItemCount() {
        return bookedModels.size();
    }


    private class OffersViewHolder extends RecyclerView.ViewHolder {

        private TextView tvBookedTitle, tvBookedDesc, tvValidTill, tvBookedCount, tvBookedStatus, tvOfferPrice, tvMrpPrice;
        private ImageView imBookedThumbnail;
        private TextView txt_deal_price;
        private Button btnViewDeals, btnWriteOfferReview;
        private Button book_now;
        private ImageView imgCoupon;

        public OffersViewHolder(View v2) {
            super(v2);
            txt_deal_price = v2.findViewById(R.id.txt_deal_price);
            imBookedThumbnail = v2.findViewById(R.id.booked_thumbnail);
            tvBookedTitle = v2.findViewById(R.id.booked_offer_name);
            tvBookedDesc = v2.findViewById(R.id.booked_desc);
            tvValidTill = v2.findViewById(R.id.txt_validity_value);
            tvBookedCount = v2.findViewById(R.id.booked_count);
            tvBookedStatus = v2.findViewById(R.id.txt_status_value);
            tvOfferPrice = v2.findViewById(R.id.txt_deal_price_value);
            tvMrpPrice = v2.findViewById(R.id.txt_mrp_value);
            btnViewDeals = v2.findViewById(R.id.view_deals);
            btnWriteOfferReview = v2.findViewById(R.id.write_review);
            imgCoupon = v2.findViewById(R.id.imgCoupon);
            book_now = v2.findViewById(R.id.book_now);
        }
    }
}
