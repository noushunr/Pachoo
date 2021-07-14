package com.highstreets.user.ui.view_all_deals;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.JsonIOException;
import com.highstreets.user.R;
import com.highstreets.user.adapters.ProductListAdapter;
import com.highstreets.user.adapters.ViewAllDealsAdapter;
import com.highstreets.user.api.ApiClient;
import com.highstreets.user.app_pref.SharedPrefs;
import com.highstreets.user.common.OfferDetailAdapterCallback;
import com.highstreets.user.models.ProductResult;
import com.highstreets.user.models.Success;
import com.highstreets.user.models.ViewAllDeals;
import com.highstreets.user.ui.auth.login_registration.LoginActivity;
import com.highstreets.user.ui.base.BaseActivity;
import com.highstreets.user.ui.cart.CartActivity;
import com.highstreets.user.ui.custom.CountDrawable;
import com.highstreets.user.ui.main.home.HomeFragment;
import com.highstreets.user.ui.orders.order_details.OrderDetailsActivity;
import com.highstreets.user.ui.product.ShopProductsActivity;
import com.highstreets.user.ui.product.ShopProductsPresenter;
import com.highstreets.user.ui.product.ShopProductsPresenterInterface;
import com.highstreets.user.ui.product.ShopProductsViewInterface;
import com.highstreets.user.ui.product.model.AddToCartResponse;
import com.highstreets.user.ui.review_booking.ReviewBookingActivity;
import com.highstreets.user.ui.search.SearchActivity;
import com.highstreets.user.ui.shop_details.ShopImagesDialogFragment;
import com.highstreets.user.utils.CommonUtils;
import com.highstreets.user.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import static com.highstreets.user.app_pref.SharedPrefs.Keys.TOKEN;
import static com.highstreets.user.utils.Constants.SUBCATEGORY_ID;

public class ViewAllDealsActivity extends BaseActivity implements
        View.OnClickListener,
        ShopProductsViewInterface,
        OfferDetailAdapterCallback {

    private RecyclerView rvOfferDetails;
    private TextView tvNoData;
    private LinearLayoutManager layoutManager;
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
    private Button btnBookOffer;
    private CardView cardBooking;
    private LinearLayout llShowImages, llSecondImage, llviewMore;
    private RelativeLayout rlThirdImage;
    private ShopImagesDialogFragment shopImagesDialogFragment;
    private ArrayList<Success> offersAddedToBuy;
    private String USER_ID, LATITUDE, LONGITUDE;
    private double mTotalPrice = 0;
    private String TOTAL;
    private String SHOP_CALL;
    private String mShopName;
    private ShopProductsPresenterInterface shopProductsPresenterInterface;
    private ProductListAdapter viewSingleDealAdapter;
    private Menu menu;
    private Menu defaultMenu;
    private boolean isToday;

    public static Intent getActivityIntent(Context context) {
        return new Intent(context, ShopProductsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isToday = getIntent().getBooleanExtra("is_today",false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();

        shopProductsPresenterInterface = new ShopProductsPresenter(this);
        USER_ID = SharedPrefs.getString(SharedPrefs.Keys.USER_ID, "");
        LATITUDE = SharedPrefs.getString(SharedPrefs.Keys.GET_CITY_LATITUDE, "");
        LONGITUDE = SharedPrefs.getString(SharedPrefs.Keys.GET_CITY_LONGITUDE, "");
        if (isToday)
            getTodaySpecial();
        else
            getOurSpecial();
//        shopProductsPresenterInterface.getAllProducts(SharedPrefs.getString(SharedPrefs.Keys.MERCHANT_ID, ""),categoryId);
    }

    private void getTodaySpecial(){
        showProgressIndicator();
        ApiClient.getApiInterface().todaySpecial("Bearer "+SharedPrefs.getString(TOKEN,"")).enqueue(new Callback<ProductResult>() {
            @Override
            public void onResponse(Call<ProductResult> call, Response<ProductResult> response) {
                if (response.isSuccessful()) {
                    try {
                        ProductResult jsonObject = response.body();
                        if (jsonObject.getSuccess()== 1 && jsonObject.getData()!=null) {
                            if (jsonObject.getData().getCart()!=null){
                                if (jsonObject.getData().getCart().getTotalItems()!=null){
                                    int count = Integer.parseInt(jsonObject.getData().getCart().getTotalItems());
                                    SharedPrefs.setInt(SharedPrefs.Keys.CART_COUNT, count);
                                }
                                int count = Integer.parseInt(jsonObject.getData().getCart().getTotalItems());
                                SharedPrefs.setInt(SharedPrefs.Keys.CART_COUNT, count);
                            }
                            if (jsonObject.getData().getProductList()!=null && jsonObject.getData().getProductList().size()>0){
                                tvNoData.setVisibility(View.GONE);
                                rvOfferDetails.setVisibility(View.VISIBLE);
                                viewSingleDealAdapter = new ProductListAdapter(ViewAllDealsActivity.this, jsonObject.getData().getProductList());
                                rvOfferDetails.setAdapter(viewSingleDealAdapter);
//            shopImagesDialogFragment = ShopImagesDialogFragment.newInstance(offerDetail.getImages());
                            }else {
                                tvNoData.setVisibility(View.VISIBLE);
                                rvOfferDetails.setVisibility(View.GONE);
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
            public void onFailure(Call<ProductResult> call, Throwable t) {
                dismissProgressIndicator();
            }
        });
    }

    private void getOurSpecial(){
        showProgressIndicator();
        ApiClient.getApiInterface().ourSpecial("Bearer "+SharedPrefs.getString(TOKEN,"")).enqueue(new Callback<ProductResult>() {
            @Override
            public void onResponse(Call<ProductResult> call, Response<ProductResult> response) {
                if (response.isSuccessful()) {
                    try {
                        ProductResult jsonObject = response.body();
                        if (jsonObject.getSuccess()== 1 && jsonObject.getData()!=null) {
                            if (jsonObject.getData().getCart()!=null){
                                if (jsonObject.getData().getCart().getTotalItems()!=null){
                                    int count = Integer.parseInt(jsonObject.getData().getCart().getTotalItems());
                                    SharedPrefs.setInt(SharedPrefs.Keys.CART_COUNT, count);
                                }
                                int count = Integer.parseInt(jsonObject.getData().getCart().getTotalItems());
                                SharedPrefs.setInt(SharedPrefs.Keys.CART_COUNT, count);
                            }
                            if (jsonObject.getData().getProductList()!=null && jsonObject.getData().getProductList().size()>0){
                                tvNoData.setVisibility(View.GONE);
                                rvOfferDetails.setVisibility(View.VISIBLE);
                                viewSingleDealAdapter = new ProductListAdapter(ViewAllDealsActivity.this, jsonObject.getData().getProductList());
                                rvOfferDetails.setAdapter(viewSingleDealAdapter);
//            shopImagesDialogFragment = ShopImagesDialogFragment.newInstance(offerDetail.getImages());
                            }else {
                                tvNoData.setVisibility(View.VISIBLE);
                                rvOfferDetails.setVisibility(View.GONE);
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
            public void onFailure(Call<ProductResult> call, Throwable t) {
                dismissProgressIndicator();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.navigation_searching);
        if (getSupportFragmentManager().findFragmentById(R.id.flContainer) instanceof HomeFragment)
            menuItem.setVisible(true);
        else
            menuItem.setVisible(false);
        defaultMenu = menu;
        if (SharedPrefs.getInt(SharedPrefs.Keys.CART_COUNT,0)>0){
            setCount(this, SharedPrefs.getInt(SharedPrefs.Keys.CART_COUNT,0), defaultMenu);
        }
        return true;
    }
    public void setCount(Context context, int count, Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.navigation_cart);
        LayerDrawable icon = (LayerDrawable) menuItem.getIcon();

        CountDrawable badge;

        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_group_count);
        if (reuse != null && reuse instanceof CountDrawable) {
            badge = (CountDrawable) reuse;
        } else {
            badge = new CountDrawable(context);
        }

        badge.setCount(String.valueOf(count));
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_group_count, badge);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_searching: {
                startActivity(new Intent(ViewAllDealsActivity.this, SearchActivity.class));
                break;
            }
            case R.id.navigation_cart: {
                startActivity(CartActivity.start(this));
                break;
            }
        }
        return super.onOptionsItemSelected(item);
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
        button_review_booking = findViewById(R.id.btnAddToCart);
        btnBookOffer = findViewById(R.id.btnBookOffer);

        rvOfferDetails = findViewById(R.id.rvOfferDetails);
        tvNoData = findViewById(R.id.txt_no_data);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvOfferDetails.setLayoutManager(layoutManager);
        rvOfferDetails.setHasFixedSize(false);

        button_review_booking.setOnClickListener(this);
        btnBookOffer.setOnClickListener(this);
        tvCall.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddToCart: {
                if (SharedPrefs.getString(SharedPrefs.Keys.USER_ID, "").equals("")) {
                    toLogin();
                } else {
//                    shopProductsPresenterInterface.addToCart(
//                            SharedPrefs.getString(SharedPrefs.Keys.USER_ID, ""),
//                            offersAddedToBuy);
                }
                break;
            }
            case R.id.btnBookOffer:{
                if (SharedPrefs.getString(SharedPrefs.Keys.USER_ID, "").equals("")) {
                    toLogin();
                } else {
                    Intent reviewBookingIntent = ReviewBookingActivity.start(this);
                    reviewBookingIntent.putExtra(Constants.SHOP_NAME, mShopName);
//                    reviewBookingIntent.putParcelableArrayListExtra(Constants.OFFERS_ADDED_TO_BUY, offersAddedToBuy);
                    startActivity(reviewBookingIntent);
                }
                break;
            }
            case R.id.llShowImages: {
                shopImagesDialogFragment.show(getSupportFragmentManager(), null);
                break;
            }
            case R.id.tvCall: {
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
    public void setProductList(List<Success> productList) {

        if (productList!=null && productList.size()>0){
            tvNoData.setVisibility(View.GONE);
            rvOfferDetails.setVisibility(View.VISIBLE);
            viewSingleDealAdapter = new ProductListAdapter(this, productList);
            rvOfferDetails.setAdapter(viewSingleDealAdapter);
//            shopImagesDialogFragment = ShopImagesDialogFragment.newInstance(offerDetail.getImages());
        }else {
            tvNoData.setVisibility(View.VISIBLE);
            rvOfferDetails.setVisibility(View.GONE);
        }

    }

    @Override
    public void setAddedToCartSuccess(ProductResult addToCartResponse) {
        CommonUtils.showToast(this, "Item Added to cart");

    }

    @Override
    public void cityChanged(AddToCartResponse addToCartResponse) {
        new AlertDialog.Builder(this)
                .setTitle("City Changed")
                .setMessage(getString(R.string.cart_clear_message))
                .setNegativeButton("no", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                })
                .setPositiveButton("yes", (dialogInterface, i) -> {
                    shopProductsPresenterInterface.clearCart(USER_ID);
                })
                .show();
    }

    @Override
    public void cartCleared(AddToCartResponse body) {
        if (body.getStatus().equals(Constants.SUCCESS)) {
            CommonUtils.showToast(this, body.getMessage());
        }
    }

    @Override
    public void onClick(Success offerList) {


        invalidateOptionsMenu();
        shopProductsPresenterInterface.addToCart(String.valueOf(offerList.getShopProductid()),String.valueOf(offerList.getProductId()),String.valueOf(offerList.getCount()));
//        offersAddedToBuy = new ArrayList<>();
//        for (Success success : offerList) {
//            if (success.getCount() > 0) {
//                itemsWithCount++;
//                offersAddedToBuy.add(success);
//            }
//        }
//        for (Success success : offersAddedToBuy) {
//            double noOf = success.getCount();
//            double price = Double.parseDouble("50.0");
//            double totalPrice = noOf * price;
//            mTotalPrice = mTotalPrice + totalPrice;
//            TOTAL = String.format("%.2f", mTotalPrice);
//        }
//        if (itemsWithCount > 0) {
//            cardBooking.setVisibility(View.VISIBLE);
//        } else {
//            cardBooking.setVisibility(View.GONE);
//        }
//        String totalStr = getString(R.string.pound_symbol) + TOTAL;
//        mCost.setText(totalStr);
//        String noStr = itemsWithCount + " Items in cart";
//        number_of_item.setText(noStr);
//        mTotalPrice = 0;
    }

}
