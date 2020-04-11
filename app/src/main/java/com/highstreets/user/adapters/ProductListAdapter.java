package com.highstreets.user.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.highstreets.user.R;
import com.highstreets.user.api.ApiClient;
import com.highstreets.user.common.OfferDetailAdapterCallback;
import com.highstreets.user.models.Offer;
import com.highstreets.user.ui.product.ProductDetailActivity;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.MyViewHolder> {

    private Context mContext;
    private List<Offer> offerList;
    private OfferDetailAdapterCallback listener;
    private String timing;

    public ProductListAdapter(Context mContext, List<Offer> offerList, String timing) {
        this.mContext = mContext;
        this.offerList = offerList;
        this.timing = timing.replaceAll(" ", "");
        if (mContext instanceof OfferDetailAdapterCallback) {
            this.listener = (OfferDetailAdapterCallback) mContext;
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.view_single_deal_item, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        final Offer offer = offerList.get(i);

        String DEAL_OFF = offer.getOfferPercentage();
        final String offer_percentage = DEAL_OFF + " % OFF ";

        myViewHolder.VALID_FOR = Integer.parseInt(offer.getValidFor().trim());
        myViewHolder.tvValidity.setText(offer.getOfferValidTo());
        myViewHolder.tvBoughtCount.setText(offer.getBought() + " bought");
        myViewHolder.tvDealName.setText(offer.getName());
        myViewHolder.tvValidFor.setText(offer.getValidFor() + " Person");
        myViewHolder.tvTiming.setText(timing);
        myViewHolder.tvMrpPrice.setText(mContext.getString(R.string.pound_symbol) + offer.getMrpPrice());
        myViewHolder.tvOfferPrice.setText(mContext.getString(R.string.pound_symbol) + offer.getOfferPrice());
        myViewHolder.tvOfferPercentage.setText(offer_percentage);
        if (offer.getOfferPercentage().equals("null")) {
            myViewHolder.tvOfferPercentage.setText("0" + "%");
        }
        Glide.with(mContext)
                .setDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.place_holder_small))
                .load(ApiClient.VIEW_ALL_BASE_URL + offer.getFeaturedImage()).into(myViewHolder.imDealThumbnail);

        myViewHolder.mDetails.setOnClickListener(v -> {
            Intent toDetailsIntent = new Intent(mContext, ProductDetailActivity.class);
            toDetailsIntent.putExtra("image", ApiClient.VIEW_ALL_BASE_URL + offer.getFeaturedImage());
            toDetailsIntent.putExtra("name", offer.getName());
            toDetailsIntent.putExtra("desc", offer.getDescription());
            toDetailsIntent.putExtra("validity_for", offer.getValidFor() + " Person");
            toDetailsIntent.putExtra("timing", timing);
            toDetailsIntent.putExtra("mrp", offer.getOfferPrice());
            toDetailsIntent.putExtra("offer_percentage", offer_percentage);
            toDetailsIntent.putExtra("valid_till", offer.getOfferValidTo());
            mContext.startActivity(toDetailsIntent);
        });

        myViewHolder.mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri IMAGE_URL = Uri.parse(ApiClient.VIEW_ALL_BASE_URL + offer.getFeaturedImage());
                final String shareLink = offer.getShare_url();

                Picasso.get().load(IMAGE_URL).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        Intent i = new Intent(Intent.ACTION_SEND);
                        i.setType("*/*");
                        i.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(bitmap));
                        i.putExtra(Intent.EXTRA_TEXT, shareLink);
                        i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        mContext.startActivity(Intent.createChooser(i, "Share Image"));
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                    }
                });


            }
        });

    }


    public Uri getLocalBitmapUri(Bitmap bmp) {
        Uri bmpUri = null;
        try {
            File file = new File(mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = FileProvider.getUriForFile(mContext, mContext.getApplicationContext().getPackageName() + ".provider", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }


    @Override
    public int getItemCount() {
        return offerList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mDetails, mShare, mQuantity, tvValidity, tvBoughtCount, tvDealName, tvTiming, tvValidFor, tvOfferPercentage, tvMrpPrice, tvOfferPrice;
        Button mAddButton, mPlus, mMinus;
        LinearLayout mAddingLayout;
        ImageView imDealThumbnail;
        int mCount = 0;
        int VALID_FOR;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvValidity = itemView.findViewById(R.id.txt_validity_value);
            tvBoughtCount = itemView.findViewById(R.id.booked_count);
            imDealThumbnail = itemView.findViewById(R.id.deals_thumbnail);
            tvDealName = itemView.findViewById(R.id.txt_deal_name);
            tvTiming = itemView.findViewById(R.id.txt_timing_value);
            tvValidFor = itemView.findViewById(R.id.txt_valid_for_value);
            tvOfferPercentage = itemView.findViewById(R.id.txt_off_percentage);
            tvMrpPrice = itemView.findViewById(R.id.txt_mrp_rate);
            tvOfferPrice = itemView.findViewById(R.id.txt_offer_rate);
            mDetails = itemView.findViewById(R.id.txt_detail_button);
            mShare = itemView.findViewById(R.id.txt_share_button);
            mAddButton = itemView.findViewById(R.id.txt_add_button);
            mAddingLayout = itemView.findViewById(R.id.adding_layout);
            mPlus = itemView.findViewById(R.id.txt_plus);
            mMinus = itemView.findViewById(R.id.txt_minus);
            mQuantity = itemView.findViewById(R.id.txt_quantity);

            mAddButton.setOnClickListener(this);
            mPlus.setOnClickListener(this);
            mMinus.setOnClickListener(this);

            mQuantity.setText("1");
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.txt_add_button:
                    setCount(++mCount, getAdapterPosition());

                    break;
                case R.id.txt_plus:
                    if (mCount <= VALID_FOR - 1) {
                        setCount(++mCount, getAdapterPosition());
                    }
                    break;
                case R.id.txt_minus:
                    setCount(--mCount, getAdapterPosition());
                    break;

            }
        }

        private void setCount(int count, int position) {
            if (count > 0) {
                mAddButton.setVisibility(View.GONE);
                mAddingLayout.setVisibility(View.VISIBLE);
            } else {
                mAddButton.setVisibility(View.VISIBLE);
                mAddingLayout.setVisibility(View.GONE);
            }
            mQuantity.setText(String.valueOf(count));
            offerList.get(position).setCount(count);
            listener.onClick(offerList);
        }
    }
}
