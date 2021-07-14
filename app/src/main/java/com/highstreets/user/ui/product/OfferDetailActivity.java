package com.highstreets.user.ui.product;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.JsonIOException;
import com.highstreets.user.R;
import com.highstreets.user.api.ApiClient;
import com.highstreets.user.app_pref.SharedPrefs;
import com.highstreets.user.models.Image;
import com.highstreets.user.models.Offer;
import com.highstreets.user.models.ProductDetails;
import com.highstreets.user.models.ProductResult;
import com.highstreets.user.models.Success;
import com.highstreets.user.ui.CarouselView.ViewPagerCarouselView;
import com.highstreets.user.ui.auth.login_registration.LoginActivity;
import com.highstreets.user.ui.base.BaseActivity;
import com.highstreets.user.ui.product.model.AddToCartResponse;
import com.highstreets.user.utils.CommonUtils;
import com.highstreets.user.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.highstreets.user.app_pref.SharedPrefs.Keys.TOKEN;

public class OfferDetailActivity extends BaseActivity implements View.OnClickListener,ShopProductsViewInterface{

    private ImageView ivImage;
    private TextView tvName;
    private TextView tvOfferPercentage;
    private TextView tvValidTill;
    private TextView tvTotal;
    private TextView tvValidFor;
    private TextView tvDescription;
    private TextView tvToolbarText;
    private String productId;
    TextView mQuantity;
    Button mAddButton, mPlus, mMinus;
    LinearLayout mAddingLayout;
    ImageView imDealThumbnail;
    int mCount = 0;
    private ViewPagerCarouselView mViewPagerCarouselView;
    private ArrayList<Offer> images;
    private Success success;
    private ShopProductsPresenterInterface shopProductsPresenterInterface;
    public static Intent start(Context context) {
        return new Intent(context, OfferDetailActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ivImage = findViewById(R.id.ivImage);
        mViewPagerCarouselView = findViewById(R.id.carousel_view);
        tvOfferPercentage = findViewById(R.id.tvOfferPercentage);
        tvName = findViewById(R.id.tvName);
        tvValidTill = findViewById(R.id.tvValidTill);
        tvTotal = findViewById(R.id.tvTotal);
        tvValidFor = findViewById(R.id.tvValidFor);
        tvDescription = findViewById(R.id.tvDescription);
        tvToolbarText = findViewById(R.id.tvToolbarText);
        mAddButton = findViewById(R.id.txt_add_button);
        mAddingLayout = findViewById(R.id.adding_layout);
        mPlus = findViewById(R.id.txt_plus);
        mMinus = findViewById(R.id.txt_minus);
        mQuantity = findViewById(R.id.txt_quantity);
        shopProductsPresenterInterface = new ShopProductsPresenter(this);
        mAddButton.setOnClickListener(this);
        mPlus.setOnClickListener(this);
        mMinus.setOnClickListener(this);
        mViewPagerCarouselView.setFilterTouchesWhenObscured(true);

        tvToolbarText.setText("Details");
        success = (Success) getIntent().getSerializableExtra("product");
        if (success != null) {
            productId = success.getShopProductid();
            getProductDetails();

        }

//        if (images!=null && images.size()>0){
//            images.clear();
//            Offer image = new Offer();
//            image.setFeaturedImage(getIntent().getStringExtra("image"));
//            images.add(image);
//            ivImage.setVisibility(View.GONE);
//            mViewPagerCarouselView.setVisibility(View.VISIBLE);
//            setUpCarousel();
//
//        }else {
//            ivImage.setVisibility(View.VISIBLE);
//            mViewPagerCarouselView.setVisibility(View.GONE);
//        }

    }

    private void setUpCarousel() {
        long carouselSlideInterval = 5000;
        mViewPagerCarouselView.onDestroy();
        mViewPagerCarouselView.setData(getSupportFragmentManager(), images, carouselSlideInterval);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.search_close) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected boolean setToolbar() {
        return true;
    }

    @Override
    public int setLayout() {
        return R.layout.activity_detail;
    }

    @Override
    public void reloadPage() {

    }

    private void getProductDetails(){
        showProgress();
        ApiClient.getApiInterface().getProductDetails("Bearer "+SharedPrefs.getString(TOKEN,""),
                 productId).enqueue(new Callback<ProductDetails>() {
            @Override
            public void onResponse(Call<ProductDetails> call, Response<ProductDetails> response) {
                if (response.isSuccessful()) {
                    try {
                        ProductDetails jsonObject = response.body();
                        if (jsonObject.getSuccess()== 1 && jsonObject.getMessage()!=null) {
                            if (jsonObject.getMessage().get(0)!=null){
                                success = jsonObject.getMessage().get(0);
                                if (jsonObject.getMessage().get(0).getQuantity()!=null)
                                    mCount = Integer.parseInt(jsonObject.getMessage().get(0).getQuantity());
                                if (mCount > 0) {
                                    mAddButton.setVisibility(View.GONE);
                                    mAddingLayout.setVisibility(View.VISIBLE);
                                } else {
                                    mAddButton.setVisibility(View.VISIBLE);
                                    mAddingLayout.setVisibility(View.GONE);
                                }
                                mQuantity.setText(String.valueOf(mCount));
                                Glide.with(OfferDetailActivity.this)
                                        .load(success.getImage())
                                        .into(ivImage);
                                tvDescription.setText(success.getDescription());
                                tvName.setText(success.getProductName());
                                String mrpStr = getString(R.string.pound_symbol) + success.getSellingPrice();
                                tvTotal.setText(mrpStr);
                            }
                            dismissProgressIndicator();
                        } else {
                            dismissProgressIndicator();
                        }
                    } catch (JsonIOException e) {
                        e.printStackTrace();
                    }
                } else {
                    dismissProgressIndicator();
                }
            }

            @Override
            public void onFailure(Call<ProductDetails> call, Throwable t) {
                dismissProgressIndicator();
            }
        });
    }

    @Override
    public void onClick(View v) {

            switch (v.getId()) {
                case R.id.txt_add_button:
                case R.id.txt_plus:
                    if (!SharedPrefs.getBoolean(SharedPrefs.Keys.IS_LOGIN,false)) {
                        toLogin();
                    }else {
                        if (mCount == 0){
                            int count = SharedPrefs.getInt(SharedPrefs.Keys.CART_COUNT,0);
                            count = count +1;
                            SharedPrefs.setInt(SharedPrefs.Keys.CART_COUNT, count);
                        }
                        setCount(++mCount);
                    }


                    break;
                case R.id.txt_minus:
                    if (!SharedPrefs.getBoolean(SharedPrefs.Keys.IS_LOGIN,false)) {
                        toLogin();
                    }else {
                        if (mCount == 1){
                            int count = SharedPrefs.getInt(SharedPrefs.Keys.CART_COUNT,0);
                            count = count -1;
                            SharedPrefs.setInt(SharedPrefs.Keys.CART_COUNT, count);
                        }
//                        int count = SharedPrefs.getInt(SharedPrefs.Keys.CART_COUNT,0);
//                        count = count -1;
//                        SharedPrefs.setInt(SharedPrefs.Keys.CART_COUNT, count);
                        setCount(--mCount);
                    }
                    break;

            }

    }

    private void toLogin() {
        Intent toLoginIntent = LoginActivity.start(this);
        toLoginIntent.putExtra(Constants.FROM_INSIDE, Constants.FROM_INSIDE);
        startActivity(toLoginIntent);
    }
    private void setCount(int count) {
        if (count > 0) {
            mAddButton.setVisibility(View.GONE);
            mAddingLayout.setVisibility(View.VISIBLE);
        } else {
            mAddButton.setVisibility(View.VISIBLE);
            mAddingLayout.setVisibility(View.GONE);
        }
        mQuantity.setText(String.valueOf(count));
        shopProductsPresenterInterface.addToCart(String.valueOf(success.getShopProductid()),String.valueOf(success.getProductId()),String.valueOf(mCount));

    }

    @Override
    public void setProductList(List<Success> productList) {

    }

    @Override
    public void setAddedToCartSuccess(ProductResult addToCartResponse) {
        CommonUtils.showToast(this, "Item Added to cart");
    }

    @Override
    public void cityChanged(AddToCartResponse addToCartResponse) {

    }

    @Override
    public void cartCleared(AddToCartResponse body) {

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
