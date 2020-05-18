package com.highstreets.user.app_pref;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPrefs {
    private static SharedPreferences preferences;

    public static void initializePreferenceManager(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return preferences.getBoolean(key, defaultValue);
    }

    public static void setBoolean(String key, boolean defaultValue) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, defaultValue);
        editor.apply();
    }


    public static String getString(String key, String defaultValue) {
        return preferences.getString(key, defaultValue);
    }

    public static void setString(String key, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static int getInt(String key, int defaultValue) {
        return preferences.getInt(key, defaultValue);
    }

    public static void setInt(String key, int value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static void setFloat(String key, float value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public static float getFloat(String key, float defaultValue) {
        return preferences.getFloat(key, defaultValue);
    }

    public static void clear() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }

    public static void remove(String key) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key);
        editor.apply();
    }

    public static class Keys {

        public static final String TOKEN = "token";

        public static final String IS_LOGIN = "is_login";
        public static final String IS_REGISTERED = "is_registered";
        public static final String USER_ID = "user_id";
        public static final String IS_LOCATED = "is_located";
        public static final String USER_FIRST_NAME = "first_name";
        public static final String USER_LAST_NAME = "last_name";
        public static final String USER_EMAIL = "email";
        public static final String USER_MOBILE = "mobile";
        public static final String USER_GENDER = "gender";
        public static final String USER_CURRENT_PASSWORD = "password";

        public static final String USER_IMAGE = "image";
        public static final String USER_ORGANIZATION = "user_organization";
        public static final String USER_EMP_CODE = "user_emp_code";


        public static final String SELECTED_BRAND = "selected_brand";
        public static final String SELECTED_PRICE = "selected_price";

        // Cities
        public static final String GET_CITY_ID = "city";
        public static final String GET_CITY_LATITUDE = "latitude";
        public static final String GET_CITY_LONGITUDE = "longitude";
        public static final String GET_CITY_NAME = "city_name";

        // Notification
        public static final String NOTIFICATION_ID = "id";
        public static final String MERCHANT_ID = "merchant_id";
        public static final String OFFER_ID = "offer_id";
        public static final String NOTIFICATION_TITLE = "tittle";
        public static final String NOTIFICATION_DESCRIPTION = "description";
        public static final String OFFER_IMAGE = "offer_image";

        //  public static final String USER_CHANGED_PASSWORD = "user_password_change";

        //Subcategories
        public static final String SUB_CATEGORY_ID = "id";
        public static final String SUB_CATEGORY_NAME = "sub_category";
        public static final String SUB_CATEGORY_CREATED_DATE = "created_date";
        public static final String SUB_CATEGORY_UPDATED_DATE = "updated_date";
        public static final String SUB_CATEGORY_IMAGE = "subcategory_image";
        public static final String SUB_CATEGORY_STATUS = "status";
        public static final String SUB_CATEGORY_IP = "ip";

        //Shoplist
        public static final String SHOP_LIST_MERCHANT_ID = "merchant_id";
        public static final String SHOP_LIST_BUSINESS_NAME = "business_name";
        public static final String SHOP_LIST_CATEGORY = "category";
        public static final String SHOP_LIST_SUB_CATEGORY = "sub_category";
        public static final String SHOP_LIST_CITY = "city";
        public static final String SHOP_LIST_DEAL_OF_DAY = "deal_of_day";
        public static final String SHOP_LIST_DEAL_DATE = "deal_date";
        public static final String SHOP_LIST_OFFER_ID = "ofr_id";
        public static final String SHOP_LIST_IMAGE = "image";
        public static final String SHOP_LIST_OFFER_PERCENTAGE = "offer_percentage";
        public static final String SHOP_LIST_MRP_PRICE = "mrp_price";
        public static final String SHOP_LIST_OFFER_PRICE = "offer_price";
        public static final String SHOP_LIST_BOUGHT = "bought";
        public static final String SHOP_LIST_RATING = "ratings";

        //Shop Details
        public static final String SHOP_DETAILS_ID = "id";
        public static final String SHOP_DETAILS_BUSINESS_NAME = "business_name";
        public static final String SHOP_DETAILS_MERCHANT_ID = "merchant_id";
        public static final String SHOP_DETAILS_CONTACT_NAME = "contact_name";
        public static final String SHOP_DETAILS_EMAIL = "email";
        public static final String SHOP_DETAILS_MOBILE = "mobile";
        public static final String SHOP_DETAILS_STATE_ID = "state";
        public static final String SHOP_DETAILS_DISTRICT_ID = "district";
        public static final String SHOP_DETAILS_CITY = "city";
        public static final String SHOP_DETAILS_PINCODE = "pincode";
        public static final String SHOP_DETAILS_TIMING = "timing";
        public static final String SHOP_DETAILS_CATEGORY = "category";
        public static final String SHOP_DETAILS_LATITUDE = "latitude";
        public static final String SHOP_DETAILS_LONGITUDE = "longitude";
        public static final String SHOP_DETAILS_DESCRIPTION = "description";
        public static final String SHOP_DETAILS_RATING = "ratings";
        public static final String SHOP_DETAILS_FAVORITE_COUNT = "fav_count";
        public static final String SHOP_DETAILS_FAVORITED = "favourited";
        public static final String SHOP_DETAILS_KMS = "kms";
        public static final String SHOP_DETAILS_STATE_NAME = "state_name";
        public static final String SHOP_DETAILS_DISTRICT_NAME = "district_name";
        public static final String SHOP_DETAILS_CITY_NAME = "city_name";
        public static final String SHOP_DETAILS_OFFER_PERCENTAGE = "offer_percentage";
        public static final String SHOP_DETAILS_DEALS_COUNT = "deals_count";

        //View All Deals
        public static final String VIEW_ALL_DEALS_ID = "id";
        public static final String VIEW_ALL_DEALS_MERCHANT_ID = "merchant_id";
        public static final String VIEW_ALL_DEALS_BUSINESS_NAME = "business_name";
        public static final String VIEW_ALL_DEALS_CONTACT_NAME = "contact_name";
        public static final String VIEW_ALL_DEALS_EMAIL_ID = "email";
        public static final String VIEW_ALL_DEALS_MOBILE = "mobile";
        public static final String VIEW_ALL_DEALS_STATE = "state";
        public static final String VIEW_ALL_DEALS_DISTRICT = "district";
        public static final String VIEW_ALL_DEALS_CITY = "city";
        public static final String VIEW_ALL_DEALS_PINCODE = "pincode";
        public static final String VIEW_ALL_DEALS_TIMING = "timing";
        public static final String VIEW_ALL_DEALS_CATEGORY = "category";
        public static final String VIEW_ALL_DEALS_RATING = "ratings";
        public static final String VIEW_ALL_DEALS_FAV_COUNT = "fav_count";
        public static final String VIEW_ALL_DEALS_STATE_NAME = "state_name";
        public static final String VIEW_ALL_DEALS_DISTRICT_NAME = "district_name";
        public static final String VIEW_ALL_DEALS_CITY_NAME = "city_name";


        public static final String SHOP_DETAILS_MOST_POPULAR_ID = "id";
        public static final String SHOP_DETAILS_MOST_POPULAR_MERCHANT_ID = "merchant_id";
        public static final String SHOP_DETAILS_MOST_POPULAR_NAME = "name";
        public static final String SHOP_DETAILS_MOST_POPULAR_DESCRIPTION = "description";
        public static final String SHOP_DETAILS_MOST_POPULAR_OFFER_TYPE = "offer_type_id";
        public static final String SHOP_DETAILS_MOST_POPULAR_MRP_PRICE = "mrp_price";
        public static final String SHOP_DETAILS_MOST_POPULAR_OFFER_PERCENTAGE = "offer_percentage";
        public static final String SHOP_DETAILS_MOST_POPULAR_OFFER_PRICE = "offer_price";
        public static final String SHOP_DETAILS_MOST_POPULAR_QUANTITY = "qty";
        public static final String SHOP_DETAILS_MOST_POPULAR_FEATURED_IMAGE = "featured_image";
        public static final String SHOP_DETAILS_MOST_POPULAR_OFFER_VALIDITY_FROM = "offer_valid_from";
        public static final String SHOP_DETAILS_MOST_POPULAR_OFFER_VALIDITY_TO = "offer_valid_to";
        public static final String SHOP_DETAILS_MOST_POPULAR_OFFER_VALID_FOR = "valid_for";


        public static final String WRITE_SHOP_REVIEW_ID = "review_id";
        public static final String WRITE_OFFER_REVIEW_ID = "review_id";


        public static final String BOOKED_COUPON_ID = "id";
        public static final String BOOKED_OFFERS_ID = "id";
        public static final String BOOKING_ID = "booking_id";

        public static final String PROFILE_IMAGE_REAL_PATH = "profile_image_real_path";

        public static final String NOTIFICATION_COUNT = "notification_count";
        public static final String USER_SOCIAL_TYPE = "user_social_type";
        public static final String USER_PROFILE_ID = "user_profile_id";
        public static final String CHECKED_ITEM_ID = "checked_item_id";
        public static final String CART_COUNT = "cart_count";
    }


}
