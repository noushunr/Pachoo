package com.presentation.app.dealsnest.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
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
import com.presentation.app.dealsnest.models.notification_model;
import com.presentation.app.dealsnest.ui.notification.notification_details.NotificationDetailActivity;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {

    private Context mContext;
    private List<notification_model> modelList;

    public NotificationAdapter(Context mContext, List<notification_model> modelList) {
        this.mContext = mContext;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.notification_card, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        final notification_model items = modelList.get(i);
        myViewHolder.NotificationTitle.setText(items.getTittle());
        myViewHolder.NotificationDescription.setText(items.getDescription());
        Glide.with(mContext)
                .setDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.place_holder_small))
                .load(ApiClient.NOTIFICATION_IMAGE_URL + items.getImage()).into(myViewHolder.NotificationOfferImage);

        myViewHolder.ViewNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, NotificationDetailActivity.class);
                intent.putExtra("id", items.getId());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView NotificationTitle, NotificationDescription, ViewNow;
        private ImageView NotificationOfferImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            NotificationTitle = itemView.findViewById(R.id.notification_title);
            NotificationDescription = itemView.findViewById(R.id.notification_description);
            NotificationOfferImage = itemView.findViewById(R.id.notification_icon);
            ViewNow = itemView.findViewById(R.id.notification_view_now);
        }
    }
}
