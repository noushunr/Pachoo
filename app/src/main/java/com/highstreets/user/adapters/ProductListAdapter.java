package com.highstreets.user.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
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
import com.highstreets.user.app_pref.SharedPrefs;
import com.highstreets.user.common.OfferDetailAdapterCallback;
import com.highstreets.user.models.Success;
import com.highstreets.user.ui.auth.login_registration.LoginActivity;
import com.highstreets.user.ui.product.OfferDetailActivity;
import com.highstreets.user.utils.Constants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.MyViewHolder> {

    private Context mContext;
    private List<Success> products;
    private OfferDetailAdapterCallback listener;

    public ProductListAdapter(Context mContext, List<Success> products) {
        this.mContext = mContext;
        this.products = products;
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
        final Success success = products.get(i);
        myViewHolder.tvDealName.setText(success.getProductName());
        myViewHolder.tvQuantityUnit.setText(success.getBoxQuantity() + " "+ success.getUnit());
        myViewHolder.tvOfferPrice.setText(mContext.getResources().getString(R.string.pound_symbol)+success.getSellingPrice());
        Glide.with(mContext)
                .setDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.shop))
                .load(success.getImage()).into(myViewHolder.imDealThumbnail);

        myViewHolder.imDealThumbnail.setOnClickListener(v -> {
            Intent toDetailsIntent = new Intent(mContext, OfferDetailActivity.class);
            Bundle args = new Bundle();
            args.putSerializable("product",success);
            toDetailsIntent.putExtras(args);
            mContext.startActivity(toDetailsIntent);
        });

        if (success.getQuantity()!=null){
            myViewHolder.setCount(Integer.parseInt(success.getQuantity()),i,false);
        }
        myViewHolder.mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Uri IMAGE_URL = Uri.parse(ApiClient.OFFERS_IMAGE_URL + offer.getFeaturedImage());
//                final String shareLink = offer.getShare_url();
//
//                Picasso.get().load(IMAGE_URL).into(new Target() {
//                    @Override
//                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//                        Intent i = new Intent(Intent.ACTION_SEND);
//                        i.setType("*/*");
//                        i.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(bitmap));
//                        i.putExtra(Intent.EXTRA_TEXT, shareLink);
//                        i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                        mContext.startActivity(Intent.createChooser(i, "Share Image"));
//                    }
//
//                    @Override
//                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
//
//                    }
//
//                    @Override
//                    public void onPrepareLoad(Drawable placeHolderDrawable) {
//                    }
//                });


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
        return products.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mDetails, mShare, mQuantity, tvValidity, tvBoughtCount, tvDealName, tvQuantityUnit, tvValidFor, tvOfferPercentage, tvMrpPrice, tvOfferPrice;
        Button mAddButton, mPlus, mMinus;
        LinearLayout mAddingLayout;
        ImageView imDealThumbnail;
        int mCount = 0;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvValidity = itemView.findViewById(R.id.txt_validity_value);
            tvBoughtCount = itemView.findViewById(R.id.booked_count);
            imDealThumbnail = itemView.findViewById(R.id.deals_thumbnail);
            tvDealName = itemView.findViewById(R.id.txt_deal_name);
            tvQuantityUnit = itemView.findViewById(R.id.txt_quantity_unit);
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

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.txt_add_button:
                case R.id.txt_plus:
                    mCount = products.get(getAdapterPosition()).getCount();
                    if (!SharedPrefs.getBoolean(SharedPrefs.Keys.IS_LOGIN,false)) {
                        toLogin();
                    }else {
                        if (mCount == 0){
                            int count = SharedPrefs.getInt(SharedPrefs.Keys.CART_COUNT,0);
                            count = count +1;
                            SharedPrefs.setInt(SharedPrefs.Keys.CART_COUNT, count);
                        }
                        setCount(++mCount, getAdapterPosition(), true);
                    }


                    break;
                case R.id.txt_minus:
                    if (!SharedPrefs.getBoolean(SharedPrefs.Keys.IS_LOGIN,false)) {
                        toLogin();
                    }else {
                        mCount = products.get(getAdapterPosition()).getCount();
                        if (mCount == 1){
                            int count = SharedPrefs.getInt(SharedPrefs.Keys.CART_COUNT,0);
                            count = count -1;
                            SharedPrefs.setInt(SharedPrefs.Keys.CART_COUNT, count);
                        }
//                        int count = SharedPrefs.getInt(SharedPrefs.Keys.CART_COUNT,0);
//                        count = count -1;
//                        SharedPrefs.setInt(SharedPrefs.Keys.CART_COUNT, count);
                        setCount(--mCount, getAdapterPosition(), true);
                    }
                    break;

            }
        }

        private void toLogin() {
            Intent toLoginIntent = LoginActivity.start(mContext);
            toLoginIntent.putExtra(Constants.FROM_INSIDE, Constants.FROM_INSIDE);
            mContext.startActivity(toLoginIntent);
        }
        private void setCount(int count, int position, boolean b) {
            if (count > 0) {
                mAddButton.setVisibility(View.GONE);
                mAddingLayout.setVisibility(View.VISIBLE);
            } else {
                mAddButton.setVisibility(View.VISIBLE);
                mAddingLayout.setVisibility(View.GONE);
            }
            mQuantity.setText(String.valueOf(count));
            products.get(position).setCount(count);
            if (b)
            listener.onClick(products.get(position));
        }
    }
}
