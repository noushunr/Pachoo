package com.highstreets.user.ui.review_booking;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.highstreets.user.R;
import com.highstreets.user.adapters.ReviewBookingAdapter;
import com.highstreets.user.app_pref.GlobalPreferManager;
import com.highstreets.user.models.Offer;
import com.highstreets.user.ui.BaseActivity;
import com.highstreets.user.ui.SuccessDialogFragment;
import com.highstreets.user.utils.CommonUtils;
import com.highstreets.user.utils.Constants;

import java.util.ArrayList;


public class ReviewBookingActivity extends BaseActivity implements ReviewBookingViewInterface, View.OnClickListener {

    private Button mConfirmButton;
    private TextView tvTotalRate;
    private RecyclerView rvReviewBooking;
    private RecyclerView.LayoutManager layoutManager;
    private ReviewBookingAdapter reviewBookingAdapter;
    private TextView tvToolbarText;
    private TextView tvShopName;
    private String mShopName;
    private ReviewBookingPresenter reviewBookingPresenter;
    private ArrayList<Offer> offerArrayList = new ArrayList<>();
    private String USER_ID, MERCHANT_ID;
    private ArrayList<String> offerList = new ArrayList<>();
    private ArrayList<String> priceList = new ArrayList<>();
    private ArrayList<String> QtyList = new ArrayList<>();

    public static Intent getActivityIntent(Context context) {
        return new Intent(context, ReviewBookingActivity.class);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        USER_ID = GlobalPreferManager.getString(GlobalPreferManager.Keys.USER_ID, "");
        MERCHANT_ID = GlobalPreferManager.getString(GlobalPreferManager.Keys.MERCHANT_ID, "");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        offerArrayList = getIntent().getParcelableArrayListExtra(Constants.OFFERS_ADDED_TO_BUY);
        mShopName = getIntent().getStringExtra(Constants.SHOP_NAME);

        reviewBookingPresenter = new ReviewBookingPresenter(this);
        initView();
    }

    @Override
    protected boolean setToolbar() {
        return true;
    }

    @Override
    public int setLayout() {
        return R.layout.activity_review_booking;
    }

    @Override
    public void reloadPage() {

    }

    private void initView() {

        tvTotalRate = findViewById(R.id.txt_total_amount_value);
        tvToolbarText = findViewById(R.id.tvToolbarText);
        tvToolbarText.setText("Review Booking");
        tvShopName = findViewById(R.id.tvShopName);
        tvShopName.setText(mShopName);
        rvReviewBooking = findViewById(R.id.review_booking_recycler_view);
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvReviewBooking.setLayoutManager(layoutManager);
        rvReviewBooking.setHasFixedSize(false);
        rvReviewBooking.addItemDecoration(decoration);
        reviewBookingAdapter = new ReviewBookingAdapter(this, offerArrayList);
        rvReviewBooking.setAdapter(reviewBookingAdapter);
        double allTotal = 0;
        for (Offer offer : offerArrayList) {
            double oneOfferAmount = ((double) offer.getCount()) * (Double.parseDouble(offer.getOfferPrice()));
            allTotal = allTotal + oneOfferAmount;
        }

        String totalStr = getString(R.string.pound_symbol) + String.format("%.2f", allTotal);
        tvTotalRate.setText(totalStr);

        mConfirmButton = findViewById(R.id.confirm_booking_button);
        mConfirmButton.setOnClickListener(this);
    }

    @Override
    public void onLoadingReviewBookingSuccess(String message) {
        DialogFragment dialogFragment = new SuccessDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("message", message);
        dialogFragment.setArguments(bundle);
        dialogFragment.setCancelable(false);
        dialogFragment.show(getSupportFragmentManager(), "Success Dialog");
    }

    @Override
    public void onLoadingReviewBookingFailed(String message) {
        CommonUtils.showToast(this, message);
    }

    @Override
    public void onResponseFailed(String message) {
        CommonUtils.showToast(this, message);
    }

    @Override
    public void onServerError(String message) {
        CommonUtils.showToast(this, message);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm_booking_button:
                for (Offer model : offerArrayList) {
                    model.getOfferTypeId();
                    offerList.add(model.getId());
                    priceList.add(model.getOfferPrice());
                    QtyList.add(String.valueOf(model.getCount()));
                }
                reviewBookingPresenter.getReviewBooking(USER_ID, MERCHANT_ID, offerList, QtyList, priceList);
                break;
        }
    }
}
