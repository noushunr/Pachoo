package com.highstreets.user.api;

import com.google.gson.JsonObject;
import com.highstreets.user.ui.address.add_address.model.AddressResponse;
import com.highstreets.user.ui.address.add_address.model.AddressSavedResponse;
import com.highstreets.user.ui.address.add_address.model.PostResponse;
import com.highstreets.user.ui.address.add_address.select_city.model.CityResponse;
import com.highstreets.user.ui.address.add_address.select_district.model.DistrictResponse;
import com.highstreets.user.ui.address.add_address.select_state.model.StatesResponse;
import com.highstreets.user.ui.address.model.AllAddressResponse;
import com.highstreets.user.ui.cart.model.CartResponse;
import com.highstreets.user.ui.cart.model.DeleteCartItemResponse;
import com.highstreets.user.ui.cart.product_details.model.ProductDetailsResponse;
import com.highstreets.user.ui.orders.model.OrdersResponse;
import com.highstreets.user.ui.orders.order_details.model.OrderDetailsResponse;
import com.highstreets.user.ui.place_order.model.FinalBalanceItem;
import com.highstreets.user.ui.product.model.AddToCartResponse;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("api/users/Users/loginByEmail/")
    Call<JsonObject> login(@Field("email_id") String email,
                           @Field("password") String password);

    @FormUrlEncoded
    @POST("api/users/Users/signUp/")
    Call<JsonObject> register(@Field("firstname") String firstname,
                              @Field("lastname") String lastname,
                              @Field("email_id") String email,
                              @Field("password") String password,
                              @Field("password") String confirm_password,
                              @Field("mobile") String mobile,
                              @Field("gender") String gender);

    @FormUrlEncoded
    @POST("api/users/Users/loginwithsocialmedia/")
    Call<JsonObject> registerSocialMedia(@Field("type") String type,
                                         @Field("profile_id") String profileId,
                                         @Field("name") String name,
                                         @Field("email") String email,
                                         @Field("pic") String profileImage);

    @FormUrlEncoded
    @POST("api/users/Users/forgotPassword")
    Call<JsonObject> forgotpassword(@Field("email_id") String email);

    @FormUrlEncoded
    @POST("api/users/Users/verifyPasswordResetKey/")
    Call<JsonObject> verify_password(@Field("password_reset_key") String reset_key,
                                     @Field("register_id") String register_id);

    @FormUrlEncoded
    @POST("api/users/Users/resetPassword/")
    Call<JsonObject> reset_password(@Field("register_id") String register_id,
                                    @Field("password") String password);

    @FormUrlEncoded
    @POST("api/users/Users/getProfileDetails/")
    Call<JsonObject> get_profile_details(@Field("register_id") String register_id);

    @Multipart
    @POST("api/users/Users/updateProfileDetails")
    Call<JsonObject> update_profile(@PartMap Map<String, RequestBody> params,
                                    @Part MultipartBody.Part images);

    @FormUrlEncoded
    @POST("api/users/Users/changePassword/")
    Call<JsonObject> change_password(@Field("register_id") String register_id,
                                     @Field("confirm_password") String confirm_password);

    @GET("api/users/Users/getNotifications/")
    Call<JsonObject> get_notification();

    @FormUrlEncoded
    @POST("api/users/Users/getHomePageDetails")
    Call<JsonObject> get_home_details(@Field("city") String city_name);


    @GET("api/users/Users/getCities/")
    Call<JsonObject> get_cities();

    @FormUrlEncoded
    @POST("api/users/Users/getSubcategoriesList/")
    Call<JsonObject> get_sub_categories(@Field("category_id") String category_id);

    @FormUrlEncoded
    @POST("api/users/Users/getCoupons")
    Call<JsonObject> getCoupons(@Field("city") String city,
                                @Field("user_id") String userId);

    @FormUrlEncoded
    @POST("api/users/Users/getFavouriteShops")
    Call<JsonObject> get_favourite_shops(@Field("user_id") String userId);

//    @FormUrlEncoded
//    @POST("api/users/Users/getBookedOffers")
//    Call<JsonObject> get_booked_offer(@Field("user_id") String userId);

    @FormUrlEncoded
    @POST("api/users/Users/addFavouriteCoupons")
    Call<JsonObject> addFavoriteCoupons(@Field("user_id") String userId,
                                        @Field("coupon_id") String couponId);

    @FormUrlEncoded
    @POST("api/users/Users/couponBooking")
    Call<JsonObject> couponBooking(@Field("user_id") String userId,
                                   @Field("coupon_id") String couponId);

    @FormUrlEncoded
    @POST("api/users/Users/getListShops")
    Call<JsonObject> get_shop_list(@Field("city") String city,
                                   @Field("category_id") String category_id,
                                   @Field("sub_category_id") String sub_category_id,
                                   @Field("latitude") String latitude,
                                   @Field("longitude") String longitude);

    @FormUrlEncoded
    @POST("api/users/Users/getShopsDetails")
    Call<JsonObject> get_shop_details(@Field("merchant_id") String merchant_id,
                                      @Field("user_id") String user_id,
                                      @Field("longitude") String longitude,
                                      @Field("latitude") String latitude,
                                      @Field("city") String city);

    @FormUrlEncoded
    @POST("api/users/Users/writeShopReview/")
    Call<JsonObject> write_review(@Field("user_id") String user_id,
                                  @Field("merchant_id") String merchant_id,
                                  @Field("name") String name,
                                  @Field("review") String review,
                                  @Field("rating") String rating);

    @FormUrlEncoded
    @POST("api/users/Users/writeOfferReview/")
    Call<JsonObject> write_offer_review(@Field("user_id") String user_id,
                                        @Field("merchant_id") String merchant_id,
                                        @Field("offer_id") String offer_id,
                                        @Field("name") String name,
                                        @Field("review") String review,
                                        @Field("rating") String rating);


    @FormUrlEncoded
    @POST("api/users/Users/getReview")
    Call<JsonObject> read_reviews(@Field("user_id") String user_id,
                                  @Field("merchant_id") String merchant_id,
                                  @Field("section") String section);

    @FormUrlEncoded
    @POST("api/users/Users/getViewAllOfferDetails/")
    Call<JsonObject> getAllOfferDetails(@Field("merchant_id") String merchant_id,
                                        @Field("user_id") String user_id,
                                        @Field("latitude") String latitude,
                                        @Field("longitude") String longitude);

    @FormUrlEncoded
    @POST("api/users/Users/getViewOfferDetails/")
    Call<JsonObject> getOfferDetails(@Field("merchant_id") String merchant_id,
                                     @Field("offer_id") String offer_id,
                                     @Field("user_id") String user_id,
                                     @Field("latitude") String latitude,
                                     @Field("longitude") String longitude);

    @FormUrlEncoded
    @POST("api/users/Users/confirmBooking")
    Call<JsonObject> confirmBooking(@Field("user_id") String user_id,
                                    @Field("merchant_id") String merchant_id,
                                    @Field("offer_id[]") String[] offer_id,
                                    @Field("qty[]") String[] quantity,
                                    @Field("price[]") String[] price);

    @FormUrlEncoded
    @POST("api/users/Users/addFavouriteShop")
    Call<JsonObject> add_fav_shop(@Field("merchant_id") String merchant_id,
                                  @Field("user_id") String user_id,
                                  @Field("fav_status") String fav_status);

    @FormUrlEncoded
    @POST("api/users/Users/deleteFavouriteShop")
    Call<JsonObject> delete_fav_shop(@Field("fav_id") String fav_id);

    @FormUrlEncoded
    @POST("api/users/Users/getAllDealOfDays")
    Call<JsonObject> view_all_deals(@Field("city") String city);

    @FormUrlEncoded
    @POST("api/users/Users/getAllRecentlyBookedShops")
    Call<JsonObject> view_all_recently_booked(@Field("city") String city);

    @FormUrlEncoded
    @POST("api/users/Users/getAllMostViewedShops")
    Call<JsonObject> view_all_mostly_view_shop(@Field("city") String city);

    @FormUrlEncoded
    @POST("api/users/Users/getBookedOffer")
    Call<JsonObject> get_booked_offer(@Field("user_id") String userId);

    @FormUrlEncoded
    @POST("api/users/Users/sortingData")
    Call<JsonObject> get_sorted_list(@Field("option") String option,
                                     @Field("city") String city,
                                     @Field("category_id") String category_id,
                                     @Field("sub_category_id") String sub_category_id,
                                     @Field("latitude") String latitude,
                                     @Field("longitude") String longitude);

    @FormUrlEncoded
    @POST("api/users/Users/filterlist")
    Call<JsonObject> get_filter_list(@Field("category_id") String category_id,
                                     @Field("sub_category_id") String sub_category_id,
                                     @Field("latitude") String latitude,
                                     @Field("longitude") String longitude);

    @FormUrlEncoded
    @POST("api/users/Users/getFilteringResult")
    Call<JsonObject> get_filter_result(@Field("category_id") String category_id,
                                       @Field("sub_category_id") String sub_category_id,
                                       @Field("latitude") String latitude,
                                       @Field("longitude") String longitude,
                                       @Field("brand") String brand,
                                       @Field("price") String price,
                                       @Field("city") String city);

    @FormUrlEncoded
    @POST("api/users/Users/searchListss")
    Call<JsonObject> get_search_filter_list(@Field("city") String city_id,
                                            @Field("text") String search_text);

    @FormUrlEncoded
    @POST("api/users/Users/addDeviceToken")
    Call<JsonObject> addTokens(@Field("user_id") String user_id,
                               @Field("device_token") String token,
                               @Field("type") String type);

    @GET("api/users/Users/getHelp")
    Call<JsonObject> get_help();

    @FormUrlEncoded
    @POST("api/users/Users/getNotificationDetails")
    Call<JsonObject> getNotificationDetails(@Field("notification_id") String notification_id);

    @FormUrlEncoded
    @POST("api/users/Users/addToCart")
    Call<AddToCartResponse> addToCart(@Field("customer_id") String userId,
                                      @Field("product_id") String productId,
                                      @Field("qty") String qty);

    @FormUrlEncoded
    @POST("api/users/Users/getCartProducts")
    Call<CartResponse> getCartProducts(@Field("customer_id") String userId);

    @FormUrlEncoded
    @POST("api/users/Users/deleteCart")
    Call<DeleteCartItemResponse> deleteCart(@Field("customer_id") String userId,
                                            @Field("cart_id") String cartId);

    @FormUrlEncoded
    @POST("api/users/Users/saveAddress")
    Call<AddressSavedResponse> addAddress(@Field("customer_id")String userId,
                                          @Field("firstname") String firstName,
                                          @Field("lastname") String lastName,
                                          @Field("mobile") String mobile,
                                          @Field("district") String district,
                                          @Field("city") String city,
                                          @Field("state") String state,
                                          @Field("postcode") String postcode,
                                          @Field("address_1") String address_1,
                                          @Field("address_2") String address_2,
                                          @Field("lattitude") String latitude,
                                          @Field("longitude") String longitude);

    @FormUrlEncoded
    @POST("api/users/Users/saveAddress")
    Call<AddressSavedResponse> editAddress(@Field("customer_id") String userId,
                                 @Field("address_id") String addressId,
                                 @Field("firstname") String firstName,
                                 @Field("lastname") String lastName,
                                 @Field("mobile") String mobile,
                                 @Field("district") String district,
                                 @Field("city") String city,
                                 @Field("state") String state,
                                 @Field("postcode") String postcode,
                                 @Field("address_1") String address_1,
                                 @Field("address_2") String address_2,
                                 @Field("lattitude") String latitude,
                                 @Field("longitude") String longitude);

    @FormUrlEncoded
    @POST("api/users/Users/getAddress")
    Call<AllAddressResponse> getAllAddresses(@Field("customer_id") String userId);

    @FormUrlEncoded
    @POST("api/users/Users/getAddress")
    Call<AddressResponse> getAddress(@Field("customer_id") String userId,
                                     @Field("address_id") String addressId);

    @GET("api/users/Users/getStates")
    Call<StatesResponse> getStates();

    @FormUrlEncoded
    @POST("api/users/Users/getDistrict")
    Call<DistrictResponse> getDistrict(@Field("state_id") String stateId);

    @FormUrlEncoded
    @POST("api/users/Users/getCity")
    Call<CityResponse> getCity(@Field("district_id") String districtId);


    @FormUrlEncoded
    @POST("api/users/Users/placeOrder")
    Call<PostResponse> placeOrder(@Field("customer_id") String userId,
                                @Field("address_id") String addressId,
                                @Field("payment_method") String paymentMethod);

    @FormUrlEncoded
    @POST("api/users/Users/getOrders")
    Call<OrdersResponse> getOrders(@Field("customer_id") String userId);

    @FormUrlEncoded
    @POST("api/users/Users/getOrder")
    Call<OrderDetailsResponse> getOrder(@Field("customer_id") String userId,
                                        @Field("order_id") String orderId);

    @FormUrlEncoded
    @POST("api/users/Users/getFinalBalance")
    Call<FinalBalanceItem> getFinalBalance(@Field("customer_id") String userId,
                                           @Field("address_id") String addressId);

    @FormUrlEncoded
    @POST("api/users/Users/checkPostcode")
    Call<PostResponse> checkPostcode(@Field("postcode") String postcode);

    @FormUrlEncoded
    @POST("api/users/Users/getProduct")
    Call<ProductDetailsResponse> getProduct(@Field("product_id") String productId);
}





