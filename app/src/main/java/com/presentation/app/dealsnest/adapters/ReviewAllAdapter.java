package com.presentation.app.dealsnest.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.presentation.app.dealsnest.R;
import com.presentation.app.dealsnest.models.Review;

import java.util.ArrayList;

public class ReviewAllAdapter extends RecyclerView.Adapter<ReviewAllAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<Review> reviewArrayList;

    public ReviewAllAdapter(Context mContext, ArrayList<Review> reviewArrayList) {
        this.mContext = mContext;
        this.reviewArrayList = reviewArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.all_review_item, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Review review = reviewArrayList.get(i);
        myViewHolder.ReviewName.setText(review.getName());
        myViewHolder.ReviewDescription.setText(review.getReview());
    }

    @Override
    public int getItemCount() {
        return reviewArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView ReviewName, ReviewDescription;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ReviewName = itemView.findViewById(R.id.txt_review_name);
            ReviewDescription = itemView.findViewById(R.id.txt_review_desc);
        }
    }
}
