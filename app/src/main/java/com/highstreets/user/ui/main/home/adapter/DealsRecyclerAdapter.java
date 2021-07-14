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
import com.highstreets.user.app_pref.SharedPrefs;
import com.highstreets.user.models.Category;
import com.highstreets.user.models.Deal;
import com.highstreets.user.models.Success;
import com.highstreets.user.ui.main.MoreCategoriesActivity;
import com.highstreets.user.ui.main.categories.sub_categories.SubCategoryActivity;
import com.highstreets.user.ui.shop_details.ShopActivity;
import com.highstreets.user.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class DealsRecyclerAdapter extends RecyclerView.Adapter<DealsRecyclerAdapter.MyViewHolder> {

    private Context mContext;
    private List<Success> list;
    private ActionBar toolbar;

    public DealsRecyclerAdapter(Context mContext, List<Success> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        toolbar = ((AppCompatActivity) mContext).getSupportActionBar();
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.deals_item, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        final Success success = list.get(i);

        myViewHolder.TxtOfferName.setText(success.getShopName());

        Glide.with(mContext)
                .setDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.shop))
                .load(success.getImage())
                .into(myViewHolder.DealsThumbnails);
        myViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toAllCategories = MoreCategoriesActivity.start(mContext);
                SharedPrefs.setString(SharedPrefs.Keys.MERCHANT_ID, String.valueOf(success.getShopId()));
                toAllCategories.putExtra(Constants.MERCHANT_ID, success.getShopId());
                mContext.startActivity(toAllCategories);
//                Intent toSubCategoryIntent = SubCategoryActivity.getActivityIntent(mContext);
//                toSubCategoryIntent.putExtra(Constants.CATEGORY_ID, "63");
//                toSubCategoryIntent.putExtra(Constants.CATEGORY_NAME, "Grocery");
//                mContext.startActivity(toSubCategoryIntent);
//                Intent shopDetailIntent = ShopActivity.getActivityIntent(mContext);
//                shopDetailIntent.putExtra(Constants.MERCHANT_ID, deal.getMerchantId());
//                mContext.startActivity(shopDetailIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView TxtOfferName, TxtOfferPrice, TxtPercentage;
        private ImageView DealsThumbnails;
        private LinearLayout linearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.card_layout);
            TxtOfferName = itemView.findViewById(R.id.txt_offer_name);
            TxtOfferPrice = itemView.findViewById(R.id.txt_offer_price);
            TxtPercentage = itemView.findViewById(R.id.txt_percentage);
            DealsThumbnails = itemView.findViewById(R.id.deals_thumbnail);

        }
    }
}
