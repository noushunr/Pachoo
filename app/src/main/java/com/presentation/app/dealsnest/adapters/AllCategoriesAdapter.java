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
import com.presentation.app.dealsnest.models.Category;
import com.presentation.app.dealsnest.ui.sub_categories.SubCategoryActivity;
import com.presentation.app.dealsnest.utils.Constants;

import java.util.ArrayList;

public class AllCategoriesAdapter extends RecyclerView.Adapter<AllCategoriesAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Category> categoryArrayList;

    public AllCategoriesAdapter(Context mContext, ArrayList<Category> categoryArrayList) {
        this.mContext = mContext;
        this.categoryArrayList = categoryArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View Item = LayoutInflater.from(mContext).inflate(R.layout.more_cat_item, viewGroup, false);
        return new ViewHolder(Item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Category category = categoryArrayList.get(i);
        viewHolder.mBrandName.setText(category.getCategoryName());
        if (category.getCategoryName().equals("More")) {
            viewHolder.mBrandName.setText(category.getCategoryName());
            categoryArrayList.remove(categoryArrayList.size() - 1);
        }
        Glide.with(mContext)
                .setDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.placeholder_circle))
                .load(ApiClient.CATEGORY_BASE_URL + category.getImage())
                .into(viewHolder.mThumbnail);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toSubCategoryIntent = SubCategoryActivity.getActivityIntent(mContext);
                toSubCategoryIntent.putExtra(Constants.CATEGORY_ID, category.getId());
                toSubCategoryIntent.putExtra(Constants.CATEGORY_NAME, category.getCategoryName());
                mContext.startActivity(toSubCategoryIntent);
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
