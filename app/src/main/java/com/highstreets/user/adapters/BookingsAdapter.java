package com.highstreets.user.adapters;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.highstreets.user.R;
import com.highstreets.user.api.ApiClient;
import com.highstreets.user.models.BookedOffers;
import com.highstreets.user.ui.main.bookings.ViewDealActivity;
import com.highstreets.user.ui.orders.adapter.ShowQRCodeDialogFragment;
import com.highstreets.user.ui.write_review.WriteReviewActivity;
import com.highstreets.user.utils.Constants;

import java.util.List;

public class BookingsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<BookedOffers> bookedModels;
    private RemoveBookingItem removeBookingItem;
    public BookingsAdapter(Context mContext, List<BookedOffers> bookedModels, RemoveBookingItem removeBookingItem) {
        this.mContext = mContext;
        this.bookedModels = bookedModels;
        this.removeBookingItem = removeBookingItem;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
//        if (mContext instanceof RemoveBookingItem){
//            removeBookingItem = (RemoveBookingItem) mContext;
//        }
        View v = null;
        switch (viewType) {
            case 0: {
                v = inflater.inflate(R.layout.recycle_booked_coupon_item, viewGroup, false);
                break;
            }
            case 1: {
                v = inflater.inflate(R.layout.recycle_booked_card, viewGroup, false);
                break;
            }
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

        String imgUrl = ApiClient.OFFERS_IMAGE_URL + bookedOffers.getImage();
        offersViewHolder.tvValidTill.setText(bookedOffers.getValidTill());
        offersViewHolder.tvBookedTitle.setText(bookedOffers.getTitle());
        offersViewHolder.tvBookedCount.setText(bookedOffers.getUsed() + " Used");

        Glide.with(mContext)
                .setDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.place_holder_rectangle))
                .load(imgUrl)
                .into(offersViewHolder.imgCoupon);

        Glide.with(mContext)
                .load(bookedOffers.getQrCodeImage())
                .into(offersViewHolder.ivQRCode);

        try {
            if (bookedOffers.getDescription() != null) {
                offersViewHolder.tvBookedDesc.setText(bookedOffers.getDescription());
            } else {
                offersViewHolder.tvBookedDesc.setText("Description Not Provided for this offer");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        offersViewHolder.ivRemove.setOnClickListener(view -> {
            new AlertDialog.Builder(mContext)
                    .setTitle("Remove?")
                    .setMessage("Are you sure to remove this item")
                    .setPositiveButton("yes", (dialogInterface, i1) -> {
                        removeBookingItem.remove(bookedOffers.getBookingId());
                    })
                    .setNegativeButton("no", (dialogInterface, i1) -> {
                        dialogInterface.dismiss();
                    })
                    .show();
        });
        if (bookedOffers.getBookedStatus().equals("2")) {
            offersViewHolder.tvBookedStatus.setText(Constants.OFFER_USED);
            offersViewHolder.btnWriteOfferReview.setVisibility(View.GONE);
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

        offersViewHolder.btnViewDeals.setOnClickListener(v -> {
            Intent toViewDeail = ViewDealActivity.getActivityIntent(mContext);
            if (bookedOffers.getType().equals("0")) {
                toViewDeail.putExtra("image", ApiClient.COUPONS_IMAGE_URL + bookedOffers.getImage());
                toViewDeail.putExtra("name", bookedOffers.getTitle());
                toViewDeail.putExtra("desc", bookedOffers.getDescription());
                toViewDeail.putExtra("validity_till", bookedOffers.getValidTill());
                toViewDeail.putExtra("total", bookedOffers.getOfferPrice());
                toViewDeail.putExtra("merchant", bookedOffers.getMerchantName());
                toViewDeail.putExtra("offerPercentage", bookedOffers.getOfferPercentage()+ " % OFF");
            } else {
                toViewDeail.putExtra("image", ApiClient.OFFERS_IMAGE_URL + bookedOffers.getImage());
                toViewDeail.putExtra("name", bookedOffers.getTitle());
                toViewDeail.putExtra("desc", bookedOffers.getDescription());
                toViewDeail.putExtra("validity_till", bookedOffers.getValidTill());
                toViewDeail.putExtra("total", bookedOffers.getOfferPrice());
                toViewDeail.putExtra("merchant", bookedOffers.getMerchantName());
                toViewDeail.putExtra("offerPercentage",bookedOffers.getOfferPercentage()+ " % OFF");
            }
            mContext.startActivity(toViewDeail);
        });

        offersViewHolder.ivQRCode.setOnClickListener(view -> {
            ShowQRCodeDialogFragment.newInstance(bookedOffers.getQrCodeImage())
                    .show(((AppCompatActivity)mContext).getSupportFragmentManager(), null);
        });

    }


    @Override
    public int getItemCount() {
        return bookedModels.size();
    }


    private class OffersViewHolder extends RecyclerView.ViewHolder {

        private TextView tvBookedTitle, tvBookedDesc, tvValidTill, tvBookedCount, tvBookedStatus, tvOfferPrice, tvMrpPrice;
        private TextView txt_deal_price;
        private Button btnViewDeals, btnWriteOfferReview;
        private ImageView imgCoupon;
        private ImageView ivQRCode;
        ImageView ivRemove;

        public OffersViewHolder(View v2) {
            super(v2);
            txt_deal_price = v2.findViewById(R.id.txt_deal_price);
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
            ivQRCode = v2.findViewById(R.id.ivQRCode);
            ivRemove = v2.findViewById(R.id.ivRemove);
        }
    }
    public interface RemoveBookingItem{
        void remove(String bookingId);
    }
}
