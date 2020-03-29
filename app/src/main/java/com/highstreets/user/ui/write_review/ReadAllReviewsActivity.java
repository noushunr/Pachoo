package com.highstreets.user.ui.write_review;

import android.os.Bundle;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;

import com.highstreets.user.R;
import com.highstreets.user.adapters.ReviewAllAdapter;
import com.highstreets.user.models.Review;
import com.highstreets.user.ui.base.BaseActivity;

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
