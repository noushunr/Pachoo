package com.highstreets.user.api;

import com.highstreets.user.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static final String NOTIFICATION_IMAGE_URL = "https://shops.bluemooninfotech.com/uploads/admin/notification/";
    public static final String CATEGORIES_IMAGE_URL = "https://shops.bluemooninfotech.com/uploads/admin/categories/";
    public static final String MERCHANTS_IMAGE_URL = "https://shops.bluemooninfotech.com/uploads/admin/merchants/";
    public static final String SLIDERS_IMAGE_URL = "https://shops.bluemooninfotech.com/uploads/admin/sliders/";
    public static final String BANNERS_IMAGE_URL = "https://shops.bluemooninfotech.com/uploads/admin/banners/";
    public static final String USERS_IMAGE_URL = "https://shops.bluemooninfotech.com/uploads/admin/users/";
    public static final String COUPONS_IMAGE_URL = "https://shops.bluemooninfotech.com/uploads/admin/coupons/";
    public static final String SUB_CATEGORIES_IMAGE_URL = "https://shops.bluemooninfotech.com/uploads/admin/subcategories/";
    public static final String OFFERS_IMAGE_URL = "https://shops.bluemooninfotech.com/uploads/admin/offers/";
    private static final String BASE_URL = "https://www.admin.pachoo.in/";
//    private static final String BASE_URL = "https://www.pachoo.in/";
    private static final String BILLING_URL = "https://happy.bluemooninfotech.com/";

    private static Retrofit retrofit = null;
    private static OkHttpClient.Builder httpClient = null;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            if (BuildConfig.FLAVOR.equals("Billing")){
                retrofit = new Retrofit.Builder()
                        .baseUrl(BILLING_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(getHttpClient().build())
                        .build();
            }else {
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(getHttpClient().build())
                        .build();
            }

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
