package com.highstreets.user.ui.review_booking;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.highstreets.user.R;
import com.highstreets.user.models.Offer;
import com.highstreets.user.ui.review_booking.adapter.OfferBookingAdapter;
import com.highstreets.user.ui.review_booking.adapter.ReviewBookingAdapter;
import com.highstreets.user.app_pref.SharedPrefs;
import com.highstreets.user.ui.SuccessDialogFragment;
import com.highstreets.user.ui.base.BaseActivity;
import com.highstreets.user.ui.cart.model.CartData;
import com.highstreets.user.ui.cart.model.Product;
import com.highstreets.user.utils.CommonUtils;
import com.highstreets.user.utils.Constants;

import java.util.ArrayList;
import java.util.List;


public class ReviewBookingActivity extends BaseActivity implements
        ReviewBookingViewInterface,
        View.OnClickListener {

    private Button mConfirmButton;
    private TextView tvTotalRate;
    private RecyclerView rvReviewBooking;
    private TextView tvToolbarText;
    private ReviewBookingPresenter reviewBookingPresenter;
    private List<Product> productList;
    private ArrayList<Offer> offerArrayList;
    private boolean isBooking;
    private String USER_ID, MERCHANT_ID;


    public static Intent start(Context context) {
        return new Intent(context, ReviewBookingActivity.class);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
        USER_ID = SharedPrefs.getString(SharedPrefs.Keys.USER_ID, "");
        MERCHANT_ID = SharedPrefs.getString(SharedPrefs.Keys.MERCHANT_ID, "");
        reviewBookingPresenter = new ReviewBookingPresenter(this);
        if (getIntent().getParcelableArrayListExtra(Constants.OFFERS_ADDED_TO_BUY) != null){
            isBooking = true;
            offerArrayList = getIntent().getParcelableArrayListExtra(Constants.OFFERS_ADDED_TO_BUY);
            rvReviewBooking.setAdapter(new OfferBookingAdapter(offerArrayList));
            double grandTotalDouble = 0;
            for (Offer offer : offerArrayList){
                int COUNT = offer.getCount();
                double price = Double.parseDouble(offer.getOfferPrice());
                double totalRate = COUNT * price;
                grandTotalDouble += totalRate;
            }
            String grandTotal = getString(R.string.pound_symbol) + grandTotalDouble;
            tvTotalRate.setText(grandTotal);
        } else {
            reviewBookingPresenter.getCartProducts(USER_ID);
        }
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
        tvToolbarText.setText(R.string.review_booking);

        rvReviewBooking = findViewById(R.id.review_booking_recycler_view);
        rvReviewBooking.setLayoutManager(new LinearLayoutManager(this));
        rvReviewBooking.setHasFixedSize(false);
        rvReviewBooking.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

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
    public void setCartData(CartData cartData) {
        productList = cartData.getProductList();
        rvReviewBooking.setAdapter(new ReviewBookingAdapter(this, productList));
        String grandTotal = getString(R.string.pound_symbol) +cartData.getGrandTotal();
        tvTotalRate.setText(grandTotal);
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
            case R.id.confirm_booking_button: {
                postData();
                break;
            }
        }
    }

    private void postData() {
        ArrayList<String> offerList = new ArrayList<>();
        ArrayList<String> priceList = new ArrayList<>();
        ArrayList<String> qtyList = new ArrayList<>();
        if (isBooking) {
            for (Offer offer : offerArrayList){
                offerList.add(offer.getId());
                priceList.add(offer.getOfferPrice());
                qtyList.add(String.valueOf(offer.getCount()));
            }
        } else {
            for (Product product : productList) {
                offerList.add(product.getProductId());
                priceList.add(product.getOfferPrice());
                qtyList.add(product.getQty());
            }
        }
        reviewBookingPresenter.getReviewBooking(USER_ID, MERCHANT_ID, offerList, qtyList, priceList);
    }
}
