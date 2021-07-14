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
import com.highstreets.user.R;
import com.highstreets.user.api.ApiClient;
import com.highstreets.user.models.Category;
import com.highstreets.user.models.Success;
import com.highstreets.user.ui.main.categories.sub_categories.SubCategoryActivity;
import com.highstreets.user.ui.product.ShopProductsActivity;
import com.highstreets.user.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class AllCategoriesAdapter extends RecyclerView.Adapter<AllCategoriesAdapter.ViewHolder> {

    private Context mContext;
    private List<Success> categoryArrayList;
    private String merchantId;

    public AllCategoriesAdapter(Context mContext, List<Success> categoryArrayList, String merchantId) {
        this.mContext = mContext;
        this.categoryArrayList = categoryArrayList;
        this.merchantId = merchantId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View Item = LayoutInflater.from(mContext).inflate(R.layout.more_cat_item, viewGroup, false);
        return new ViewHolder(Item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Success category = categoryArrayList.get(i);
        viewHolder.mBrandName.setText(category.getCategoryName());
        Glide.with(mContext)
                .load(category.getImage())
                .placeholder(R.drawable.shop)
                .into(viewHolder.mThumbnail);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (category.isChild()){
                    Intent toSubCategoryIntent = SubCategoryActivity.getActivityIntent(mContext);
                    toSubCategoryIntent.putExtra(Constants.CATEGORY_ID, String.valueOf(category.getCategoryId()));
                    toSubCategoryIntent.putExtra(Constants.CATEGORY_NAME, category.getCategoryName());
                    toSubCategoryIntent.putExtra(Constants.MERCHANT_ID, merchantId);
                    mContext.startActivity(toSubCategoryIntent);
                }else {
                    Intent offerDetailIntent = ShopProductsActivity.getActivityIntent(mContext);
                    offerDetailIntent.putExtra(Constants.MERCHANT_ID, merchantId);
                    offerDetailIntent.putExtra(Constants.SUBCATEGORY_ID, String.valueOf(category.getCategoryId()));
                    mContext.startActivity(offerDetailIntent);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mThumbnail;
        private TextView mBrandName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mThumbnail = itemView.findViewById(R.id.thumbnail);
            mBrandName = itemView.findViewById(R.id.txt_brands_name);
        }
    }
}
