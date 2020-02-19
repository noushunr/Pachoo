package com.presentation.app.dealsnest.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.presentation.app.dealsnest.R;
import com.presentation.app.dealsnest.api.ApiClient;
import com.presentation.app.dealsnest.models.Category;
import com.presentation.app.dealsnest.ui.sub_categories.SubCategoryActivity;
import com.presentation.app.dealsnest.utils.Constants;

import java.util.List;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.MyViewHolder> {

    private Context mContext;
    private List<Category> brandsModels;
    private ToAllCategories toAllCategories;

    public CategoryRecyclerAdapter(Context mContext, List<Category> brandsModels, Fragment fragment) {
        this.mContext = mContext;
        this.brandsModels = brandsModels;
        if (fragment instanceof ToAllCategories) {
            this.toAllCategories = (ToAllCategories) fragment;
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, int i) {
        final View Item = LayoutInflater.from(mContext).inflate(R.layout.category_item, viewGroup, false);
        return new MyViewHolder(Item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        final Category category = brandsModels.get(i);

        myViewHolder.mBrandName.setText(category.getCategoryName());
        Glide.with(mContext)
                .setDefaultRequestOptions(new RequestOptions()
                        .placeholder(R.drawable.placeholder_circle))
                .load(ApiClient.CATEGORY_BASE_URL + category.getImage())
                .into(myViewHolder.mThumbnail);

        myViewHolder.mThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (category.getCategoryName().equalsIgnoreCase("more")) {
                    toAllCategories.toAll();
                } else {
                    Intent toSubCategoryIntent = SubCategoryActivity.getActivityIntent(mContext);
                    toSubCategoryIntent.putExtra(Constants.CATEGORY_ID, category.getId());
                    toSubCategoryIntent.putExtra(Constants.CATEGORY_NAME, category.getCategoryName());
                    mContext.startActivity(toSubCategoryIntent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return brandsModels.size();
    }

    public interface ToAllCategories {
        void toAll();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView mThumbnail;
        private TextView mBrandName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mThumbnail = itemView.findViewById(R.id.thumbnail);
            mBrandName = itemView.findViewById(R.id.txt_brands_name);
        }
    }
}
