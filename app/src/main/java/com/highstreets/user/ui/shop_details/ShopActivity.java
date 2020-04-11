package com.highstreets.user.ui.shop_details;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.highstreets.user.R;
import com.highstreets.user.adapters.PopularAdapter;
import com.highstreets.user.api.ApiClient;
import com.highstreets.user.app_pref.GlobalPreferManager;
import com.highstreets.user.models.Image;
import com.highstreets.user.models.MostPopular;
import com.highstreets.user.models.Review;
import com.highstreets.user.models.ShopDetail;
import com.highstreets.user.models.shop_images;
import com.highstreets.user.models.shops_most_popular;
import com.highstreets.user.ui.base.BaseActivity;
import com.highstreets.user.ui.auth.login_registration.LoginActivity;
import com.highstreets.user.ui.product.ShopProductsActivity;
import com.highstreets.user.ui.write_review.ReadAllReviewsActivity;
import com.highstreets.user.ui.write_review.WriteReviewActivity;
import com.highstreets.user.utils.CommonUtils;
import com.highstreets.user.utils.Constants;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShopActivity extends BaseActivity implements ShopViewInterface, View.OnClickListener, OnMapReadyCallback {
    boolean isFavorite = true;
    private TextView tvViewDeal, mWriteReview;
    private RecyclerView mPopularRecyclerView;
    private PopularAdapter mPopularAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private String mMerchantId;
    private Resources res;
    private TextView tvToolbarText;
    private TextView tvTime;
    private TextView txt_review;
    private TextView tvPhotoCount;
    private TextView txt_address_value;
    private TextView tvNoPopularText;
    private TextView tvReviewAll;
    private LinearLayout llShowImages, llSecondImage, llViewMore, llMostPopular;
    private RelativeLayout rlDealLayout;
    private ShopImagesDialogFragment shopImagesDialogFragment;
    private String mLatitude;
    private String mLongitude;
    private Button btnViewRoute;
    private Uri IMAGE_URL = null;
    private GoogleMap mMap;
    private ShopPresenter shopFragmentPresenter;
    private TextView tvShopName, tvCall, tvLocation, tvRating, tvDistance, tvCategory, tvMostPopular, tvFavCount, tvOfferPercentage, tvDealsCount, tvReviewerName, tvReview, tvDescription;
    private ImageView image_third, image_one, image_second, ivFavourite;
    private String MERCHANT_ID, USER_ID, CITY_NAME, LATITUDE, LONGITUDE, SHOP_NAME, SHOP_CALL, FAV_STATUS, shareLink, SHOP_RATING;
    private double SHOP_LAT, SHOP_LONG;

    public static Intent getActivityIntent(Context context) {
        return new Intent(context, ShopActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mMerchantId = getIntent().getStringExtra(Constants.MERCHANT_ID);
        USER_ID = GlobalPreferManager.getString(GlobalPreferManager.Keys.USER_ID, "");
        MERCHANT_ID = GlobalPreferManager.getString(GlobalPreferManager.Keys.SHOP_LIST_MERCHANT_ID, "");

        CITY_NAME = GlobalPreferManager.getString(GlobalPreferManager.Keys.GET_CITY_NAME, "");
        LATITUDE = GlobalPreferManager.getString(GlobalPreferManager.Keys.GET_CITY_LATITUDE, "");
        LONGITUDE = GlobalPreferManager.getString(GlobalPreferManager.Keys.GET_CITY_LONGITUDE, "");
        GlobalPreferManager.setString(GlobalPreferManager.Keys.MERCHANT_ID, mMerchantId);
        shopFragmentPresenter = new ShopPresenter(this);
        res = getResources();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mMerchantId != null) {
            if (!LONGITUDE.equals("") && !LATITUDE.equals("") && !CITY_NAME.equals("")) {
                shopFragmentPresenter.getShopDetails(mMerchantId, USER_ID, LONGITUDE, LATITUDE, CITY_NAME);
            } else {
                shopFragmentPresenter.getShopDetails(mMerchantId, USER_ID, "-1", "-1", "-1");
            }
        } else {
            Intent appLinkIntent = getIntent();
            Uri appLinkData = appLinkIntent.getData();
            String LINK = appLinkData.toString();
            String[] parts = LINK.split("=");
            mMerchantId = parts[1];

            if (!LONGITUDE.equals("") && !LATITUDE.equals("") && !CITY_NAME.equals("")) {
                shopFragmentPresenter.getShopDetails(mMerchantId, USER_ID, LONGITUDE, LATITUDE, CITY_NAME);
            } else {
                shopFragmentPresenter.getShopDetails(mMerchantId, USER_ID, "-1", "-1", "-1");
            }
        }

    }

    private void initView() {

        tvShopName = findViewById(R.id.txt_shop_name);
        tvLocation = findViewById(R.id.txt_location);
        tvCall = findViewById(R.id.tvCall);
        tvCategory = findViewById(R.id.offer_heading);
        tvRating = findViewById(R.id.txt_rating);
        tvDistance = findViewById(R.id.txt_distance);
        tvFavCount = findViewById(R.id.txt_fav_count);
        tvOfferPercentage = findViewById(R.id.txt_offer);
        tvDealsCount = findViewById(R.id.deals_count);
        tvToolbarText = findViewById(R.id.tvToolbarText);
        tvTime = findViewById(R.id.tvTime);
        txt_review = findViewById(R.id.txt_review);
        llShowImages = findViewById(R.id.llShowImages);
        llSecondImage = findViewById(R.id.llSecondImage);
        llViewMore = findViewById(R.id.viewMore);
        txt_address_value = findViewById(R.id.txt_address_value);
        ivFavourite = findViewById(R.id.ivFavorite);
        tvPhotoCount = findViewById(R.id.tvPhotoCount);
        tvNoPopularText = findViewById(R.id.tvNoPopularText);
        tvMostPopular = findViewById(R.id.tvMostPopular);

        tvReviewerName = findViewById(R.id.txt_username);
        tvReview = findViewById(R.id.txt_review_details);
        tvReviewAll = findViewById(R.id.txt_read_all_review);

        tvDescription = findViewById(R.id.txt_desc_value);
        rlDealLayout = findViewById(R.id.RLDealLayout);
        llMostPopular = findViewById(R.id.LLMostPopularLayout);

        image_third = findViewById(R.id.image_third);
        image_second = findViewById(R.id.image_second);
        image_one = findViewById(R.id.image_one);

        tvViewDeal = findViewById(R.id.view_deals);
        mWriteReview = findViewById(R.id.write_review);
        btnViewRoute = findViewById(R.id.btnViewRoute);

        mPopularRecyclerView = findViewById(R.id.popular_recyclerView);
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mPopularRecyclerView.setLayoutManager(layoutManager);
        mPopularRecyclerView.addItemDecoration(decoration);
        mPopularRecyclerView.setHasFixedSize(false);
        mPopularRecyclerView.setNestedScrollingEnabled(false);
        btnViewRoute.setOnClickListener(this);

        tvViewDeal.setOnClickListener(this);
        mWriteReview.setOnClickListener(this);
        llShowImages.setOnClickListener(this);
        ivFavourite.setOnClickListener(this);
    }

    @Override
    public int setLayout() {
        return R.layout.activity_shop;
    }

    @Override
    public void reloadPage() {

    }

    @Override
    public boolean setToolbar() {
        return true;
    }

    @Override
    public void onLoadingShopImages(List<shop_images> shopImages) {

    }

    @Override
    public void onLoadingShopImagesFailed(String message) {

    }

    @Override
    public void onLoadingShopDetails(String message) {

    }

    @Override
    public void onFailedToLoadShopDetails(String message) {

    }

    @Override
    public void onLoadingShopDetailsMostPopular(List<shops_most_popular> mostPopularList) {

    }

    @Override
    public void onFailedToLoadShopDetailsMostPopular(String message) {

    }

    @Override
    public void setShopDetail(final ShopDetail shopDetail) {

        SHOP_NAME = shopDetail.getBusinessName();
        mMerchantId = shopDetail.getId();
        FAV_STATUS = shopDetail.getFavourited();
        mLatitude = shopDetail.getLatitude();
        mLongitude = shopDetail.getLongitude();

        if (FAV_STATUS.equals("1")) {
            ivFavourite.setBackgroundResource(R.drawable.ic_heart);
            isFavorite = true;
        } else if (FAV_STATUS.equals("0")) {
            ivFavourite.setBackgroundResource(R.drawable.ic_heart_line);
            isFavorite = false;
        }

        String offer_percentage = "Upto " + shopDetail.getOfferPercentage() + "% off";
        String miles = shopDetail.getKms() + " miles";

        if (shopDetail.getImages().size() > 0)
            IMAGE_URL = Uri.parse(ApiClient.SHOP_DETAILS_BASE_URL + shopDetail.getImages().get(0).getImages());
        shareLink = shopDetail.getShare_url();

        SHOP_LAT = Double.parseDouble(shopDetail.getLatitude());
        SHOP_LONG = Double.parseDouble(shopDetail.getLongitude());
        tvToolbarText.setText(shopDetail.getBusinessName());
        tvShopName.setText(shopDetail.getBusinessName());
        String location = shopDetail.getCityName() + "," + shopDetail.getDistrictName();
        tvLocation.setText(location);
        tvCategory.setText(shopDetail.getCategory());
        tvRating.setText(shopDetail.getRatings());
        tvDistance.setText(miles);
        String favCount = shopDetail.getFavCount() + " Fav";
        tvFavCount.setText(favCount);
        SHOP_CALL = shopDetail.getMobile();
        tvOfferPercentage.setText(offer_percentage);
        if (shopDetail.getOfferPercentage() == null) {
            tvOfferPercentage.setText("Upto 0" + "%");
        }
        String dealCount = "+" + shopDetail.getDealsCount() + " more offers";
        tvDealsCount.setText(dealCount);
        tvTime.setText(shopDetail.getTiming());
        String Address = shopDetail.getCityName() + "," + shopDetail.getDistrictName() + "," + shopDetail.getStateName();
        txt_address_value.setText(Address);
        String reviewCount = String.format("Reviews(%s)", shopDetail.getReviewCount());
        txt_review.setText(reviewCount);
        if (shopDetail.getDescription() != null && !TextUtils.isEmpty(shopDetail.getDescription())) {
            tvDescription.setText(shopDetail.getDescription());
        } else {
            tvDescription.setText("No Description");
        }

        final ArrayList<Review> reviewList = shopDetail.getReviews();
        if (reviewList.size() > 0) {
            tvReviewerName.setText(reviewList.get(0).getName());
            tvReview.setText(reviewList.get(0).getReview());
        } else {
            tvReview.setText("No reviews yet");
        }
        if (reviewList.size() > 1) {
            tvReviewAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ShopActivity.this, ReadAllReviewsActivity.class);
                    intent.putParcelableArrayListExtra("allReviews", reviewList);
                    startActivity(intent);
                }
            });
        } else {
            tvReviewAll.setVisibility(View.GONE);

        }

        tvCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", SHOP_CALL, null));
                startActivity(intent);
            }
        });
        if (shopDetail.getImages().size() > 0) {
            Glide.with(this)
                    .setDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.place_holder_small))
                    .load(ApiClient.SHOP_DETAILS_BASE_URL + shopDetail.getImages().get(0).getImages())
                    .into(image_one);
        }
        if (shopDetail.getImages().size() > 1) {
            llSecondImage.setVisibility(View.VISIBLE);
            Glide.with(this)
                    .setDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.place_holder_small))
                    .load(ApiClient.SHOP_DETAILS_BASE_URL + shopDetail.getImages().get(1).getImages())
                    .into(image_second);
        }
        if (shopDetail.getImages().size() > 2) {
            Glide.with(this)
                    .setDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.place_holder_small))
                    .load(ApiClient.SHOP_DETAILS_BASE_URL + shopDetail.getImages().get(2).getImages())
                    .into(image_third);
        }
        if (shopDetail.getImages().size() == 2) {
            image_third.setVisibility(View.GONE);
        }
        if (shopDetail.getImages().size() > 3) {
            int count = shopDetail.getImages().size() - 3;
            String countStr = "+" + count + " Photos";
            tvPhotoCount.setText(countStr);
        } else {
            llViewMore.setVisibility(View.GONE);
        }

        List<MostPopular> mostPopularList = shopDetail.getMostPopular();
        if (mostPopularList.size() > 0) {
            mPopularAdapter = new PopularAdapter(this, mostPopularList);
            mPopularRecyclerView.setAdapter(mPopularAdapter);
        } else {
            mPopularRecyclerView.setVisibility(View.GONE);
            tvMostPopular.setVisibility(View.GONE);
            tvNoPopularText.setVisibility(View.VISIBLE);
            tvViewDeal.setVisibility(View.GONE);
        }
        List<Image> imageList = shopDetail.getImages();
        shopImagesDialogFragment = ShopImagesDialogFragment.newInstance(imageList);
//        onMapReady(mMap);
    }

    @Override
    public void onFailedToAddFav(String message) {
        CommonUtils.showToast(this, "Failed To Favourite Shop");
    }

    @Override
    public void onSuccessToAddFav(String message) {
//        shopFragmentPresenter.getShopDetails(mMerchantId, USER_ID, LONGITUDE, LATITUDE, CITY_NAME);
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
            case R.id.llShowImages:
                shopImagesDialogFragment.show(getSupportFragmentManager(), null);
                break;
            case R.id.write_review:
                if (GlobalPreferManager.getString(GlobalPreferManager.Keys.USER_ID, "").equals("")) {
                    toLogin();
                } else {
                    Intent toWriteReviewIntent = WriteReviewActivity.getActivityIntent(this);
                    toWriteReviewIntent.putExtra("user_id", USER_ID);
                    toWriteReviewIntent.putExtra("merchant_id", MERCHANT_ID);
                    toWriteReviewIntent.putExtra("rating", SHOP_RATING);
                    toWriteReviewIntent.putExtra("review_type", "1");
                    startActivity(toWriteReviewIntent);
                }
                break;
            case R.id.view_deals:
                Intent offerDetailIntent = ShopProductsActivity.getActivityIntent(this);
                offerDetailIntent.putExtra(Constants.MERCHANT_ID, mMerchantId);
                offerDetailIntent.putExtra(Constants.OFFER_DETAIL_TYPE, Constants.OFFER_TYPE_ALL);
                startActivity(offerDetailIntent);
                break;

            case R.id.ivFavorite:
                if (GlobalPreferManager.getString(GlobalPreferManager.Keys.USER_ID, "").equals("")) {
                    toLogin();
                } else {
                    if (isFavorite) {
                        shopFragmentPresenter.addFavouriteShop(mMerchantId, USER_ID, "1");
                        ivFavourite.setBackgroundResource(R.drawable.ic_heart_line);
                        isFavorite = false;
                    } else {
                        shopFragmentPresenter.addFavouriteShop(mMerchantId, USER_ID, "0");
                        ivFavourite.setBackgroundResource(R.drawable.ic_heart);
                        isFavorite = true;
                    }
                }
                break;
            case R.id.btnViewRoute: {
                getDirectionInGMap();
                break;
            }
        }
    }

    private void toLogin() {
        Intent toLoginIntent = LoginActivity.start(this);
        toLoginIntent.putExtra(Constants.FROM_INSIDE, Constants.FROM_INSIDE);
        startActivity(toLoginIntent);
        finishAffinity();
    }


    public void getDirectionInGMap() {
        Uri gmmIntentUri = Uri.parse("geo:" + mLatitude + "," + mLongitude +
                "?q=" + mLatitude + "," + mLongitude);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {
            CommonUtils.showToast(this, "Please install Google Maps");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.shop_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_share:
                SHARE_IN_APP();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void SHARE_IN_APP() {

        Picasso.get().load(IMAGE_URL).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("*/*");
                i.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(bitmap));
                i.putExtra(Intent.EXTRA_TEXT, shareLink);
                i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(Intent.createChooser(i, getResources().getText(R.string.app_name)));
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        });

    }

    public Uri getLocalBitmapUri(Bitmap bmp) {
        Uri bmpUri = null;
        try {
            File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = FileProvider.getUriForFile(getApplicationContext(), getPackageName() + ".provider", file);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng shopLatLon = new LatLng(SHOP_LAT, SHOP_LONG);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(shopLatLon));
        googleMap.addMarker(new MarkerOptions()
                .position(shopLatLon)
                .title(SHOP_NAME));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(20));
    }
}
