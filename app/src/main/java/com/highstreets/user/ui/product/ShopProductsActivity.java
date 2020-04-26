package com.highstreets.user.ui.product;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.highstreets.user.R;
import com.highstreets.user.adapters.ProductListAdapter;
import com.highstreets.user.api.ApiClient;
import com.highstreets.user.app_pref.SharedPrefs;
import com.highstreets.user.common.OfferDetailAdapterCallback;
import com.highstreets.user.models.Offer;
import com.highstreets.user.models.OfferDetail;
import com.highstreets.user.ui.auth.login_registration.LoginActivity;
import com.highstreets.user.ui.base.BaseActivity;
import com.highstreets.user.ui.main.HomeMainActivity;
import com.highstreets.user.ui.product.model.AddToCartResponse;
import com.highstreets.user.ui.shop_details.ShopImagesDialogFragment;
import com.highstreets.user.utils.CommonUtils;
import com.highstreets.user.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class ShopProductsActivity extends BaseActivity implements
        View.OnClickListener,
        ShopProductsViewInterface,
        OfferDetailAdapterCallback {

    private RecyclerView rvOfferDetails;
    private RecyclerView.LayoutManager layoutManager;
    private ImageView image_one;
    private ImageView image_second;
    private CardView cvImageSecond;
    private ImageView image_third;
    private ImageView ivFavorite;
    private TextView txt_shop_name;
    private TextView tvCall;
    private TextView txt_location;
    private TextView txt_rating;
    private TextView txt_distance;
    private TextView txt_fav_count;
    private TextView number_of_item;
    private TextView mCost;
    private TextView tvPhotoCount;
    private TextView tvToolbarText;
    private Button button_review_booking;
    private CardView cardBooking;
    private LinearLayout llShowImages, llSecondImage, llviewMore;
    private RelativeLayout rlThirdImage;
    private ShopImagesDialogFragment shopImagesDialogFragment;
    private ArrayList<Offer> offersAddedToBuy;
    private String USER_ID, LATITUDE, LONGITUDE;
    private double mTotalPrice = 0;
    private String TOTAL;
    private String SHOP_CALL;
    private String mShopName;
    private ShopProductsPresenterInterface shopProductsPresenterInterface;
    private ProductListAdapter viewSingleDealAdapter;
    private Menu menu;

    public static Intent getActivityIntent(Context context) {
        return new Intent(context, ShopProductsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();

        shopProductsPresenterInterface = new ShopProductsPresenter(this);
        USER_ID = SharedPrefs.getString(SharedPrefs.Keys.USER_ID, "");
        LATITUDE = SharedPrefs.getString(SharedPrefs.Keys.GET_CITY_LATITUDE, "");
        LONGITUDE = SharedPrefs.getString(SharedPrefs.Keys.GET_CITY_LONGITUDE, "");

        String type = getIntent().getStringExtra(Constants.OFFER_DETAIL_TYPE);
        if (type != null) {
            String merchantId = getIntent().getStringExtra(Constants.MERCHANT_ID);

            if (type.equals(Constants.OFFER_TYPE_SINGLE)) {
                String offerId = getIntent().getStringExtra(Constants.OFFER_ID);
                if (!LATITUDE.equals("") && !LONGITUDE.equals("")) {
                    shopProductsPresenterInterface.getOfferDetails(merchantId, offerId, USER_ID, LATITUDE, LONGITUDE);
                } else {
                    shopProductsPresenterInterface.getOfferDetails(merchantId, offerId, USER_ID, "-1", "-1");
                }
            } else {
                if (!LATITUDE.equals("") && !LONGITUDE.equals("")) {
                    shopProductsPresenterInterface.getAllOfferDetails(merchantId, USER_ID, LATITUDE, LONGITUDE);
                } else {
                    shopProductsPresenterInterface.getAllOfferDetails(merchantId, USER_ID, "-1", "-1");
                }
            }
        } else {
            Intent appLinkIntent = getIntent();
            Uri appLinkData = appLinkIntent.getData();
            String LINK = appLinkData.toString();
            String[] parts = LINK.split("=");
            String merchantID = parts[1];
            String[] merchant = merchantID.split("&");
            String merchantId = merchant[0];
            String offerId = parts[2];

            shopProductsPresenterInterface.getOfferDetails(merchantId, offerId, "-1", "-1", "-1");
        }
    }

    @Override
    protected boolean setToolbar() {
        return true;
    }

    @Override
    public int setLayout() {
        return R.layout.activity_offer_detail;
    }

    @Override
    public void reloadPage() {

    }

    private void initView() {
        image_one = findViewById(R.id.image_one);
        image_second = findViewById(R.id.image_second);
        image_third = findViewById(R.id.image_third);
        ivFavorite = findViewById(R.id.ivFavorite);
        cvImageSecond = findViewById(R.id.cvImageSecond);

        txt_shop_name = findViewById(R.id.txt_shop_name);
        tvCall = findViewById(R.id.tvCall);
        txt_location = findViewById(R.id.txt_location);
        txt_rating = findViewById(R.id.txt_rating);
        txt_distance = findViewById(R.id.txt_distance);
        txt_fav_count = findViewById(R.id.txt_fav_count);
        number_of_item = findViewById(R.id.number_of_item);
        mCost = findViewById(R.id.txt_cost);
        tvToolbarText = findViewById(R.id.tvToolbarText);
        llShowImages = findViewById(R.id.llShowImages);
        llSecondImage = findViewById(R.id.llSecondImage);
        rlThirdImage = findViewById(R.id.rl_image_third);
        tvPhotoCount = findViewById(R.id.tvPhotoCount);

        llviewMore = findViewById(R.id.viewMore);
        llShowImages.setOnClickListener(this);
        cardBooking = findViewById(R.id.cardBooking);
        button_review_booking = findViewById(R.id.button_review_booking);

        rvOfferDetails = findViewById(R.id.rvOfferDetails);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvOfferDetails.setLayoutManager(layoutManager);
        rvOfferDetails.setHasFixedSize(false);

        button_review_booking.setOnClickListener(this);
        tvCall.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_review_booking:
                if (SharedPrefs.getString(SharedPrefs.Keys.USER_ID, "").equals("")) {
                    toLogin();
                } else {
                    shopProductsPresenterInterface.addToCart(
                            SharedPrefs.getString(SharedPrefs.Keys.USER_ID, ""),
                            offersAddedToBuy);
//                    Intent reviewBookingIntent = ReviewBookingActivity.getActivityIntent(this);
//                    reviewBookingIntent.putExtra(Constants.SHOP_NAME, mShopName);
//                    reviewBookingIntent.putParcelableArrayListExtra(Constants.OFFERS_ADDED_TO_BUY, offersAddedToBuy);
//                    startActivity(reviewBookingIntent);
                }

                break;
            case R.id.llShowImages:
                shopImagesDialogFragment.show(getSupportFragmentManager(), null);
                break;

            case R.id.tvCall:
                tvCall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", SHOP_CALL, null));
                        startActivity(intent);
                    }
                });
                break;
        }

    }

    private void toLogin() {
        Intent toLoginIntent = LoginActivity.start(this);
        toLoginIntent.putExtra(Constants.FROM_INSIDE, Constants.FROM_INSIDE);
        startActivity(toLoginIntent);
        finishAffinity();
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
    public void setOfferDetail(OfferDetail offerDetail) {
        String km = offerDetail.getKms() + " miles";
        mShopName = offerDetail.getBusinessName();
        SHOP_CALL = offerDetail.getMobile();
        tvToolbarText.setText(offerDetail.getBusinessName());
        txt_shop_name.setText(offerDetail.getBusinessName());
        String location = offerDetail.getCityName() + "," + offerDetail.getDistrictName();
        txt_location.setText(location);
        txt_rating.setText(offerDetail.getRatings());
        txt_distance.setText(km);
        String favCount = offerDetail.getFavCount() + " Fav";
        txt_fav_count.setText(favCount);
        if (offerDetail.getImages().size() >= 1) {
            Glide.with(this)
                    .setDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.place_holder_square_large))
                    .load(ApiClient.SHOP_DETAILS_BASE_URL + offerDetail.getImages().get(0).getImages())
                    .into(image_one);
        }

        if (offerDetail.getImages().size() > 1) {
            cvImageSecond.setVisibility(View.VISIBLE);
            Glide.with(this)
                    .setDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.place_holder_square_large))
                    .load(ApiClient.SHOP_DETAILS_BASE_URL + offerDetail.getImages().get(1).getImages())
                    .into(image_second);
        } else {
            cvImageSecond.setVisibility(View.INVISIBLE);
        }
        if (offerDetail.getImages().size() > 2) {
            rlThirdImage.setVisibility(View.VISIBLE);
            Glide.with(this)
                    .setDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.place_holder_square_large))
                    .load(ApiClient.SHOP_DETAILS_BASE_URL + offerDetail.getImages().get(2).getImages())
                    .into(image_third);
        } else {
            rlThirdImage.setVisibility(View.INVISIBLE);
        }

        if (offerDetail.getImages().size() > 3) {
            int count = offerDetail.getImages().size() - 3;
            String countStr = "+" + count + " Photos";
            tvPhotoCount.setText(countStr);
        } else {
            llviewMore.setVisibility(View.GONE);
        }

        viewSingleDealAdapter = new ProductListAdapter(this, offerDetail.getOffers(), offerDetail.getTiming());
        rvOfferDetails.setAdapter(viewSingleDealAdapter);
        shopImagesDialogFragment = ShopImagesDialogFragment.newInstance(offerDetail.getImages());
    }

    @Override
    public void setAddedToCartSuccess(AddToCartResponse addToCartResponse) {
        SharedPrefs.setString(SharedPrefs.Keys.CART_COUNT, String.valueOf(addToCartResponse.getCartCount()));
        CommonUtils.showToast(this, addToCartResponse.getMessage());
        Intent toHomeIntent = HomeMainActivity.start(this);
        toHomeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(toHomeIntent);
    }

    @Override
    public void onClick(List<Offer> offerList) {
        int itemsWithCount = 0;
        offersAddedToBuy = new ArrayList<>();
        for (Offer offer : offerList) {
            if (offer.getCount() > 0) {
                itemsWithCount++;
                offersAddedToBuy.add(offer);
            }
        }
        for (Offer offer : offersAddedToBuy) {

            double noOf = offer.getCount();
            int validFor = Integer.parseInt(offer.getValidFor());
            if (noOf <= validFor) {
                double price = Double.parseDouble(offer.getOfferPrice());
                double totalPrice = noOf * price;
                mTotalPrice = mTotalPrice + totalPrice;

                TOTAL = String.format("%.2f", mTotalPrice);
            }


        }
        if (itemsWithCount > 0) {
            cardBooking.setVisibility(View.VISIBLE);
        } else {
            cardBooking.setVisibility(View.GONE);
        }
        String totalStr = getString(R.string.pound_symbol) + TOTAL;
        mCost.setText(totalStr);
        String noStr = itemsWithCount + " Items in cart";
        number_of_item.setText(noStr);
        mTotalPrice = 0;
    }

}