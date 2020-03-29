package com.highstreets.user.ui.write_review;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.highstreets.user.R;
import com.highstreets.user.app_pref.GlobalPreferManager;
import com.highstreets.user.models.ReviewGet;
import com.highstreets.user.ui.base.BaseActivity;

public class WriteReviewActivity extends BaseActivity implements View.OnClickListener, WriteReviewViewInterface {

    private ImageView mCloseIcon;
    private EditText edReview;
    private RatingBar ratingBar;
    private Button mSubmitReview;
    private TextView tvHomeTitle, edReviewerName;
    private WriteReviewPresenter writeReviewPresenter;
    private String USER_ID, MERCHANT_ID, OFFERS_MERCHANT_ID, SHOP_RATING, REVIEWER_NAME_HOLDER, REVIEW_HOLDER, OFFER_ID, REVIEW_TYPE;
    private Float RATING_HOLDER;

    public static Intent getActivityIntent(Context context) {
        return new Intent(context, WriteReviewActivity.class);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        USER_ID = GlobalPreferManager.getString(GlobalPreferManager.Keys.USER_ID, "");
        MERCHANT_ID = GlobalPreferManager.getString(GlobalPreferManager.Keys.MERCHANT_ID, "");
        REVIEWER_NAME_HOLDER = GlobalPreferManager.getString(GlobalPreferManager.Keys.USER_FIRST_NAME, "");
        tvHomeTitle = findViewById(R.id.tvToolbarText);
        tvHomeTitle.setText(getString(R.string.dealsnest));
        Intent intent = getIntent();
        OFFER_ID = intent.getStringExtra("offer_id");
        REVIEW_TYPE = intent.getStringExtra("review_type");
        OFFERS_MERCHANT_ID = intent.getStringExtra("merchant_id");

        writeReviewPresenter = new WriteReviewPresenter(this);
        ratingBar = findViewById(R.id.rating_bar);
        edReviewerName = findViewById(R.id.edit_critic_name);
        edReview = findViewById(R.id.edit_review);
        mCloseIcon = findViewById(R.id.close_icon);
        mSubmitReview = findViewById(R.id.submit_review);

        edReviewerName.setText(REVIEWER_NAME_HOLDER);
        if (REVIEW_TYPE.equals("0")) {
            writeReviewPresenter.read_reviews(USER_ID, OFFERS_MERCHANT_ID, "1");
        } else {
            writeReviewPresenter.read_reviews(USER_ID, MERCHANT_ID, "0");
        }
        mSubmitReview.setOnClickListener(this);
        mCloseIcon.setOnClickListener(this);
    }

    @Override
    public int setLayout() {
        return R.layout.activity_write_review;
    }

    @Override
    public void reloadPage() {

    }

    @Override
    public boolean setToolbar() {
        return true;
    }

    private boolean isValidated() {

        if (REVIEW_HOLDER.isEmpty()) {
            edReview.requestFocus();
            return true;
        }
        return true;
    }

    private void ShopReview() {
        REVIEW_HOLDER = edReview.getText().toString();
        RATING_HOLDER = ratingBar.getRating();

        if (REVIEW_HOLDER.equals("") && RATING_HOLDER != null) {
            SHOP_RATING = String.valueOf(RATING_HOLDER);
            USER_ID = GlobalPreferManager.getString(GlobalPreferManager.Keys.USER_ID, "");
            MERCHANT_ID = GlobalPreferManager.getString(GlobalPreferManager.Keys.MERCHANT_ID, "");
            if (isValidated()) {
                if (REVIEW_TYPE.equals("0")) {
                    writeReviewPresenter.write_offer_review(USER_ID, OFFERS_MERCHANT_ID, OFFER_ID, REVIEWER_NAME_HOLDER, REVIEW_HOLDER, SHOP_RATING);
                } else if (REVIEW_TYPE.equals("1")) {
                    writeReviewPresenter.write_review(USER_ID, MERCHANT_ID, REVIEWER_NAME_HOLDER, REVIEW_HOLDER, SHOP_RATING);
                }

            }
        } else {
            SHOP_RATING = String.valueOf(RATING_HOLDER);
            USER_ID = GlobalPreferManager.getString(GlobalPreferManager.Keys.USER_ID, "");
            MERCHANT_ID = GlobalPreferManager.getString(GlobalPreferManager.Keys.MERCHANT_ID, "");
            if (isValidated()) {
                if (REVIEW_TYPE.equals("0")) {
                    writeReviewPresenter.write_offer_review(USER_ID, OFFERS_MERCHANT_ID, OFFER_ID, REVIEWER_NAME_HOLDER, REVIEW_HOLDER, SHOP_RATING);
                } else if (REVIEW_TYPE.equals("1")) {
                    writeReviewPresenter.write_review(USER_ID, MERCHANT_ID, REVIEWER_NAME_HOLDER, REVIEW_HOLDER, SHOP_RATING);
                }

            }
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_review:
                ShopReview();
                break;
            case R.id.close_icon:
                finish();
                break;
        }
    }

    @Override
    public void onFailedToSubmitReview(String message) {

    }

    @Override
    public void onReviewSubmitSuccess(String message) {
        finish();
    }

    @Override
    public void onSuccessReadReviews(ReviewGet data) {
        REVIEWER_NAME_HOLDER = data.getName();
        REVIEW_HOLDER = data.getReview();

        edReviewerName.setText(REVIEWER_NAME_HOLDER);
        ratingBar.setRating(Float.parseFloat(data.getRating()));
        edReview.setText(REVIEW_HOLDER);
    }

    @Override
    public void onResponseFailed(String message) {

    }

    @Override
    public void onServerError(String message) {

    }

    @Override
    public void dismissProgressIndicator() {
        dismissProgress();
    }

    @Override
    public void noInternet() {

    }

    @Override
    public void showProgressIndicator() {
        showProgress();
    }
}
