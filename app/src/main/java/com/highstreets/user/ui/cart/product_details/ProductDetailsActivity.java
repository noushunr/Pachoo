package com.highstreets.user.ui.cart.product_details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.highstreets.user.R;
import com.highstreets.user.api.ApiClient;
import com.highstreets.user.ui.base.BaseActivity;
import com.highstreets.user.ui.cart.product_details.model.ProductDetails;
import com.highstreets.user.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductDetailsActivity extends BaseActivity implements ProductDetailsViewInterface{

    private ProductDetailsPresenterInterface productDetailsPresenterInterface;

    @BindView(R.id.ivImage)
    ImageView ivImage;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvOfferPercentage)
    TextView tvOfferPercentage;
    @BindView(R.id.tvValidTill)
    TextView tvValidTill;
    @BindView(R.id.tvTotal)
    TextView tvTotal;
    @BindView(R.id.tvMerchantName)
    TextView tvMerchantName;
    @BindView(R.id.tvDescription)
    TextView tvDescription;
    @BindView(R.id.tvToolbarText)
    TextView tvToolbarText;

    public static Intent start(Context context){
        return new Intent(context, ProductDetailsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tvToolbarText.setText(R.string.details);
        String productId = getIntent().getStringExtra(Constants.PRODUCT_ID);
        productDetailsPresenterInterface = new ProductDetailsPresenter(this);
        productDetailsPresenterInterface.getProduct(productId);
    }

    @Override
    protected boolean setToolbar() {
        return true;
    }

    @Override
    public int setLayout() {
        return R.layout.activity_product_details;
    }

    @Override
    public void reloadPage() {

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

    @Override
    public void setProductDetails(ProductDetails productDetails) {
        if (productDetails != null){
            Glide.with(this)
                    .load(ApiClient.OFFERS_IMAGE_URL + productDetails.getFeaturedImage())
                    .into(ivImage);
            tvName.setText(productDetails.getName());
            tvOfferPercentage.setText(productDetails.getOfferPercentage());
            tvTotal.setText(getString(R.string.pound_symbol) + productDetails.getOfferPrice());
            tvDescription.setText(productDetails.getDescription());
            tvValidTill.setText(productDetails.getOfferValidTo());
            tvMerchantName.setText(productDetails.getMerchantName());
        }
    }
}
