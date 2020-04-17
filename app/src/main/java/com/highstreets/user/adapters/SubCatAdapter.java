package com.highstreets.user.adapters;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.highstreets.user.R;
import com.highstreets.user.api.ApiClient;
import com.highstreets.user.models.SubCategory;

import java.util.List;


public class SubCatAdapter extends RecyclerView.Adapter<SubCatAdapter.MyViewHolder> {
    private Context mContext;
    private List<SubCategory> subCategoryModelList;
    private SubCategoryAdapterCallback subCategoryAdapterCallback;

    public SubCatAdapter(Context mContext, List<SubCategory> subCategoryModelList) {
        this.mContext = mContext;
        this.subCategoryModelList = subCategoryModelList;
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
        final SubCategory subCategory = subCategoryModelList.get(i);
        myViewHolder.SubCategoryName.setText(subCategory.getSubCategory());

        Glide.with(mContext)
                .load(ApiClient.SUB_CAT_BASE_URL + subCategory.getSubcategoryImage())
                .apply(RequestOptions.circleCropTransform())
                .into(myViewHolder.SubCategoryThumbnail);

        if (subCategory.isSelected())
            myViewHolder.bgThumbnail.setBackgroundColor(mContext.getResources().getColor(R.color.white_transparent));
        else
            myViewHolder.bgThumbnail.setBackgroundColor(Color.TRANSPARENT);

        myViewHolder.SubCategoryThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (SubCategory sub : subCategoryModelList) {
                    sub.setSelected(false);
                }
                subCategory.setSelected(true);
                subCategoryAdapterCallback.getShops(subCategory.getId(), subCategory.getCategoryId());
                notifyItemChanged(i);
                notifyDataSetChanged();
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
