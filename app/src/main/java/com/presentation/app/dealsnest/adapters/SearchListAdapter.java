package com.presentation.app.dealsnest.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.presentation.app.dealsnest.R;
import com.presentation.app.dealsnest.models.SearchItem;
import com.presentation.app.dealsnest.ui.shop_details.ShopActivity;
import com.presentation.app.dealsnest.utils.Constants;

import java.util.List;

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.MyViewHolder> {
    private Context mContext;
    private List<SearchItem> searchItemList;

    public SearchListAdapter(Context mContext, List<SearchItem> searchItemList) {
        this.mContext = mContext;
        this.searchItemList = searchItemList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View ItemView = LayoutInflater.from(mContext).inflate(R.layout.search_item, viewGroup, false);

        return new MyViewHolder(ItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        final SearchItem item = searchItemList.get(i);

        myViewHolder.tvSearch.setText(item.getName());
        myViewHolder.tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ShopActivity.getActivityIntent(mContext);
                intent.putExtra(Constants.MERCHANT_ID, item.getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchItemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvSearch;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSearch = itemView.findViewById(R.id.txt_item);
        }
    }
}
