package com.highstreets.user.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.highstreets.user.R;
import com.highstreets.user.api.ApiClient;
import com.highstreets.user.models.shop.ShopBanner;

import java.util.List;

public class ShopListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int TYPE_BANNER = 0;
    private final int TYPE_SHOP_LIST = 1;
    private Context mContext;
    private List<ShopBanner> shopListModels;

    public ShopListAdapter(Context mContext, List<ShopBanner> shopListModels) {
        this.mContext = mContext;
        this.shopListModels = shopListModels;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case TYPE_BANNER:
                View view1 = LayoutInflater.from(mContext).inflate(R.layout.banner_item, viewGroup, false);
                viewHolder = new BannerViewHolder(view1);
                break;
            case TYPE_SHOP_LIST:
                View view2 = LayoutInflater.from(mContext).inflate(R.layout.recycle_six_items, viewGroup, false);
                viewHolder = new ShopViewHolder(view2);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final ShopBanner shopBanner = shopListModels.get(i);

        switch (viewHolder.getItemViewType()) {
            case TYPE_BANNER: {
                Glide.with(mContext)
                        .setDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.place_holder_rectangle))
                        .load(ApiClient.SHOP_LIST_BANNER_BASE_URL + shopBanner.getImage())
                        .into(((BannerViewHolder) viewHolder).ivBanner);
                break;
            }
            case TYPE_SHOP_LIST: {
                if (shopBanner.getData() != null) {
                    ShopDetailAdapter shopDetailAdapter = new ShopDetailAdapter(mContext, shopBanner.getData());
                    ((ShopViewHolder) viewHolder).rvSixShopItem.setAdapter(shopDetailAdapter);
                }
                break;
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        ShopBanner shopBanner = shopListModels.get(position);
        if (shopBanner.getType().equals("0"))
            return TYPE_BANNER;
        else if (shopBanner.getType().equals("1"))
            return TYPE_SHOP_LIST;

        return getItemViewType(position);
    }


    @Override
    public int getItemCount() {
        return shopListModels.size();
    }

    public class ShopViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView rvSixShopItem;

        public ShopViewHolder(@NonNull View itemView) {
            super(itemView);
            rvSixShopItem = itemView.findViewById(R.id.rvSixShopItem);
            rvSixShopItem.setLayoutManager(new GridLayoutManager(mContext, 2));
            rvSixShopItem.setNestedScrollingEnabled(false);
        }

    }

    public class BannerViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivBanner;

        public BannerViewHolder(@NonNull View itemView) {
            super(itemView);
            ivBanner = itemView.findViewById(R.id.ivBanner);
        }
    }
}
