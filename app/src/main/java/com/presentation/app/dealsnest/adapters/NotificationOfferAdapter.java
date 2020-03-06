package com.presentation.app.dealsnest.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.presentation.app.dealsnest.models.NotificationDetails;
import com.presentation.app.dealsnest.models.notification_merchant_view_model;

import java.util.ArrayList;
import java.util.List;

public class NotificationOfferAdapter extends RecyclerView.Adapter<NotificationOfferAdapter.MyViewHolder> {

    private Context mContext;
    private List<NotificationDetails> notificationDetailsArrayList;
    private MerchantsAdapter merchantsAdapter;
    private List<notification_merchant_view_model> offerlist = new ArrayList<>();

    public NotificationOfferAdapter(Context mContext, List<NotificationDetails> notificationDetailsArrayList) {
        this.mContext = mContext;
        this.notificationDetailsArrayList = notificationDetailsArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.notification_item, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        NotificationDetails items = notificationDetailsArrayList.get(i);

        myViewHolder.tvTitle.setText(items.getTittle());
        myViewHolder.tvDescription.setText(items.getDescription());

        Glide.with(mContext)
                .setDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.place_holder_small))
                .load(ApiClient.NOTIFICATION_IMAGE_URL + items.getImage()).into(myViewHolder.ivThumbnail);

        myViewHolder.rvOffers.setNestedScrollingEnabled(false);
        myViewHolder.rvOffers.setHasFixedSize(false);
        myViewHolder.layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        myViewHolder.rvOffers.setLayoutManager(myViewHolder.layoutManager);
        offerlist = items.getOffers();
        merchantsAdapter = new MerchantsAdapter(mContext, offerlist);
        myViewHolder.rvOffers.setAdapter(merchantsAdapter);
    }

    @Override
    public int getItemCount() {
        return notificationDetailsArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private TextView tvDescription;
        private ImageView ivThumbnail;
        private RecyclerView rvOffers;
        private RecyclerView.LayoutManager layoutManager;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            ivThumbnail = itemView.findViewById(R.id.ivThumbnail);
            rvOffers = itemView.findViewById(R.id.rvOffers);
        }
    }
}
