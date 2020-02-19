package com.presentation.app.dealsnest.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static final String IMAGE_BASE_URL = "https://shoffz.com/uploads/admin/offers/";
    public static final String NOTIFICATION_IMAGE_URL = "https://shoffz.com/uploads/admin/notification/";
    public static final String CATEGORY_BASE_URL = "https://shoffz.com/uploads/admin/categories/";
    public static final String DEALS_BASE_URL = "https://shoffz.com/uploads/admin/merchants/";
    public static final String SLIDERS_BASE_URL = "https://shoffz.com/uploads/admin/sliders/";
    public static final String TOP_BANNER_BASE_URL = "https://shoffz.com/uploads/admin/banners/";
    public static final String BRANDED_SHOP_BASE_URL = "https://shoffz.com/uploads/admin/merchants/";
    public static final String MIDDLE_BANNER_BASE_URL = "https://shoffz.com/uploads/admin/banners/";
    public static final String RECENTLY_BOOKED = "https://shoffz.com/uploads/admin/merchants/";
    public static final String PROFILE_BASE_URL = "https://shoffz.com/uploads/admin/users/";
    public static final String IMAGE_URL_COUPONS = "https://shoffz.com/uploads/admin/coupons/";
    public static final String SUB_CAT_BASE_URL = "https://shoffz.com/uploads/admin/subcategories/";
    public static final String SHOP_LIST_BANNER_BASE_URL = "https://shoffz.com/uploads/admin/banners/";
    public static final String SHOP_LIST_SHOPS_BASE_URL = "https://shoffz.com/uploads/admin/merchants/";
    public static final String SHOP_DETAILS_BASE_URL = "https://shoffz.com/uploads/admin/merchants/";
    public static final String SHOP_MOST_POPULAR_BASE_URL = "https://shoffz.com/uploads/admin/offers/";
    public static final String VIEW_ALL_BASE_URL = "https://shoffz.com/uploads/admin/offers/";
    public static final String VIEW_ALL_COUPONS_BASE_URL = "https://shoffz.com/uploads/admin/coupons/";
    public static final String GET_FAVOURITE_SHOP_BASE_URL = "https://shoffz.com/uploads/admin/merchants/";
    private static final String BASE_URL = "https://shoffz.com/";
    private static Retrofit retrofit = null;
    private static OkHttpClient.Builder httpClient = null;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getHttpClient().build())
                    .build();
        }
        return retrofit;
    }

    public static OkHttpClient.Builder getHttpClient() {
        if (httpClient == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient = new OkHttpClient.Builder();
            httpClient.connectTimeout(30000, TimeUnit.SECONDS);
            httpClient.readTimeout(30000, TimeUnit.SECONDS).build();
            httpClient.addInterceptor(logging);

        }
        return httpClient;
    }

    public static ApiInterface getApiInterface() {
        return getRetrofit().create(ApiInterface.class);
    }
}
