package com.presentation.app.dealsnest.ui.write_review;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.presentation.app.dealsnest.R;
import com.presentation.app.dealsnest.adapters.ReviewAllAdapter;
import com.presentation.app.dealsnest.models.Review;
import com.presentation.app.dealsnest.ui.BaseActivity;

import java.util.ArrayList;

public class ReadAllReviewsActivity extends BaseActivity {

    private TextView tvToolbar;
    private RecyclerView rvAllReviewRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ReviewAllAdapter reviewAllAdapter;
    private ArrayList<Review> reviewArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        reviewArrayList = getIntent().getParcelableArrayListExtra("allReviews");
        initView();
    }

    private void initView() {
        tvToolbar = findViewById(R.id.tvToolbarText);
        tvToolbar.setText(getString(R.string.dealsnest));

        rvAllReviewRecyclerView = findViewById(R.id.all_reviews);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvAllReviewRecyclerView.setLayoutManager(layoutManager);
        rvAllReviewRecyclerView.setHasFixedSize(false);
        reviewAllAdapter = new ReviewAllAdapter(this, reviewArrayList);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL);
        rvAllReviewRecyclerView.addItemDecoration(dividerItemDecoration);
        rvAllReviewRecyclerView.setAdapter(reviewAllAdapter);
    }

    @Override
    protected boolean setToolbar() {
        return true;
    }

    @Override
    public int setLayout() {
        return R.layout.activity_read_all_reviews;
    }

    @Override
    public void reloadPage() {

    }
}
