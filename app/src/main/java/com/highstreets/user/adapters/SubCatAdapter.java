package com.highstreets.user.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.highstreets.user.R;
import com.highstreets.user.api.ApiClient;
import com.highstreets.user.models.SubCategory;
import com.highstreets.user.models.Success;
import com.highstreets.user.ui.main.categories.sub_categories.SubCategoryActivity;
import com.highstreets.user.ui.product.ShopProductsActivity;
import com.highstreets.user.utils.Constants;

import java.util.List;


public class SubCatAdapter extends RecyclerView.Adapter<SubCatAdapter.MyViewHolder> {
    private Context mContext;
    private List<Success> subCategoryModelList;
    private SubCategoryAdapterCallback subCategoryAdapterCallback;
    private String merchantId;

    public SubCatAdapter(Context mContext, List<Success> subCategoryModelList, String merchantId) {
        this.mContext = mContext;
        this.subCategoryModelList = subCategoryModelList;
        this.merchantId = merchantId;
        if (mContext instanceof SubCategoryAdapterCallback) {
            this.subCategoryAdapterCallback = (SubCategoryAdapterCallback) mContext;
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View Item = LayoutInflater.from(mContext).inflate(R.layout.sub_category_item, viewGroup, false);
        return new MyViewHolder(Item);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        final Success subCategory = subCategoryModelList.get(i);
        myViewHolder.SubCategoryName.setText(subCategory.getCategoryName());

        Glide.with(mContext)
                .load(subCategory.getImage())
                .placeholder(R.drawable.shop)
                .into(myViewHolder.SubCategoryThumbnail);

        myViewHolder.bgThumbnail.setBackgroundColor(Color.TRANSPARENT);

        myViewHolder.SubCategoryThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                for (SubCategory sub : subCategoryModelList) {
//                    sub.setSelected(false);
//                }
//                subCategory.setSelected(true);
//                subCategoryAdapterCallback.getShops(subCategory.getId(), subCategory.getCategoryId());
//                notifyItemChanged(i);
//                notifyDataSetChanged();

                if (subCategory.isChild()){
                    Intent toSubCategoryIntent = SubCategoryActivity.getActivityIntent(mContext);
                    toSubCategoryIntent.putExtra(Constants.CATEGORY_ID, String.valueOf(subCategory.getCategoryId()));
                    toSubCategoryIntent.putExtra(Constants.CATEGORY_NAME, subCategory.getCategoryName());
                    toSubCategoryIntent.putExtra(Constants.MERCHANT_ID, merchantId);
                    mContext.startActivity(toSubCategoryIntent);
                }else {
                    Intent offerDetailIntent = ShopProductsActivity.getActivityIntent(mContext);
                    offerDetailIntent.putExtra(Constants.MERCHANT_ID, merchantId);
                    offerDetailIntent.putExtra(Constants.SUBCATEGORY_ID, String.valueOf(subCategory.getCategoryId()));
                    mContext.startActivity(offerDetailIntent);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return subCategoryModelList.size();
    }

    public interface SubCategoryAdapterCallback {
        void getShops(String id, String categoryId);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView SubCategoryThumbnail;
        private FrameLayout bgThumbnail;
        private TextView SubCategoryName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            SubCategoryThumbnail = itemView.findViewById(R.id.thumbnail);
            bgThumbnail = itemView.findViewById(R.id.bgThumbnail);
            SubCategoryName = itemView.findViewById(R.id.txt_sub_category_name);
        }
    }
}
