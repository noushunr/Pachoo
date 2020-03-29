package com.highstreets.user.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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
import com.highstreets.user.models.Coupon;
import com.highstreets.user.utils.Constants;

import java.util.List;

public class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.MyViewHolder> {
    private Context mContext;
    private List<Coupon> couponList;
    private CouponAdapterCallback couponAdapterCallback;

    public CouponAdapter(Context mContext, Fragment fragment, List<Coupon> couponList) {
        this.mContext = mContext;
        this.couponList = couponList;
        if (fragment instanceof CouponAdapterCallback) {
            couponAdapterCallback = (CouponAdapterCallback) fragment;
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View ItemView = LayoutInflater.from(mContext).inflate(R.layout.coupon_card, viewGroup, false);
        return new MyViewHolder(ItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Coupon coupon = couponList.get(i);

        myViewHolder.txt_validity_value.setText(coupon.getValidTill());
        String imgUrl = ApiClient.IMAGE_URL_COUPONS + coupon.getImage();
        if (coupon.getOfferPrice().equals("")) {
            myViewHolder.txt_deal_price_value.setVisibility(View.GONE);
            myViewHolder.txt_deal_price.setVisibility(View.GONE);
        } else {
            myViewHolder.txt_deal_price_value.setText(mContext.getString(R.string.pound_symbol) + coupon.getOfferPrice());
        }
        if (coupon.getMrpPrice().equals("")) {
            myViewHolder.txt_mrp_value.setVisibility(View.GONE);
            myViewHolder.txt_deal_price.setVisibility(View.GONE);
        } else {
            myViewHolder.txt_mrp_value.setText(mContext.getString(R.string.pound_symbol) + coupon.getMrpPrice());
        }

        Glide.with(mContext)
                .setDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.place_holder_rectangle))
                .load(imgUrl)
                .into(myViewHolder.imgCoupon);


        if (coupon.getFavourited().equals("1")) {
            myViewHolder.favorite_icon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_heart_line));
        } else {
            myViewHolder.favorite_icon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_heart));
        }

        if (coupon.getCouponStatus().equals("1")) {
            myViewHolder.book_now.setText(Constants.BOOK_NOW);
        } else {
            myViewHolder.book_now.setText(Constants.BOOKED);
        }

        myViewHolder.setClicks(coupon);
    }


    @Override
    public int getItemCount() {
        return couponList.size();
    }

    public interface CouponAdapterCallback {
        void addToFavorites(String couponId);

        void bookNow(String couponId);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txt_validity_value;
        private ImageView imgCoupon;
        private TextView txt_deal_price_value;
        private TextView txt_mrp_value;
        private TextView txt_deal_price;
        private Button book_now;
        private ImageView favorite_icon;
        private Coupon coupon;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
            favorite_icon.setOnClickListener(this);
            book_now.setOnClickListener(this);
        }

        private void initView(View itemView) {
            txt_deal_price = itemView.findViewById(R.id.txt_deal_price);
            txt_validity_value = itemView.findViewById(R.id.txt_validity_value);
            imgCoupon = itemView.findViewById(R.id.imgCoupon);
            txt_deal_price_value = itemView.findViewById(R.id.txt_deal_price_value);
            txt_mrp_value = itemView.findViewById(R.id.txt_mrp_value);
            book_now = itemView.findViewById(R.id.book_now);
            favorite_icon = itemView.findViewById(R.id.favorite_icon);
        }

        public void setClicks(Coupon coupon) {
            this.coupon = coupon;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.favorite_icon: {
                    favorite_icon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_heart));
                    couponAdapterCallback.addToFavorites(coupon.getId());
                    break;
                }
                case R.id.book_now: {
                    couponAdapterCallback.bookNow(coupon.getId());
                    break;
                }
            }
        }
    }
}
