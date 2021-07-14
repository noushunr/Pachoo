package com.highstreets.user.api;

import com.google.gson.JsonObject;
import com.highstreets.user.models.Login;
import com.highstreets.user.models.ProductDetails;
import com.highstreets.user.models.ProductResult;
import com.highstreets.user.models.Result;
import com.highstreets.user.models.ShopsList;
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
import com.highstreets.user.ui.main.categories.sub_categories.Shop;
import com.highstreets.user.ui.main.categories.sub_categories.ShopList;
import com.highstreets.user.ui.orders.model.OrdersResponse;
import com.highstreets.user.ui.orders.order_details.model.OrderDetailsResponse;
import com.highstreets.user.ui.place_order.model.FinalBalanceItem;
import com.highstreets.user.ui.place_order.model.payment.MakePaymentResponse;
import com.highstreets.user.ui.product.model.AddToCartResponse;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("api/login")
    Call<ProductResult> login(@Field("contact_no") String email,
                              @Field("password") String password,
                              @Field("device_token") String token);

    @FormUrlEncoded
    @POST("api/register")
    Call<ProductResult> register(@Field("name") String firstname,
                                 @Field("email") String email,
                                 @Field("password") String password,
                                 @Field("c_password") String confirm_password,
                                 @Field("gender") String gender,
                                 @Field("contact_no") String mobile,
                                 @Field("device_token") String token);
    @FormUrlEncoded
    @POST("api/otp_verification")
    Call<ProductResult> otpVerification( @Field("contact_no") String mobile,
                                        @Field("otp") String otp,
                                        @Field("session_id") String sessionId);
    @FormUrlEncoded
    @POST("api/users/Users/loginwithsocialmedia/")
    Call<JsonObject> registerSocialMedia(@Field("type") String type,
                                         @Field("profile_id") String profileId,
                                         @Field("name") String name,
                                         @Field("email") String email,
                                         @Field("pic") String profileImage);

    @FormUrlEncoded
    @POST("api/forgot_password")
    Call<ProductResult> forgotpassword(@Field("contact_no") String email);

    @FormUrlEncoded
    @POST("api/reset_password")
    Call<ProductResult> resetPassword(@Field("otp") String otp,
                                   @Field("session_id") String sessionId,
                                   @Field("contact_no") String contactNo,
                                   @Field("new_password") String newPassword,
                                   @Field("c_password") String confirmPassword);
    @FormUrlEncoded
    @POST("api/users/Users/verifyPasswordResetKey/")
    Call<JsonObject> verify_password(@Field("password_reset_key") String reset_key,
                                     @Field("register_id") String register_id);

    @FormUrlEncoded
    @POST("api/users/Users/resetPassword/")
    Call<JsonObject> reset_password(@Field("register_id") String register_id,
                                    @Field("password") String password);


    @POST("api/viewProfile")
    Call<ProductResult> get_profile_details(@Header("Authorization") String token);

    @FormUrlEncoded
    @POST("api/editUserProfile")
    Call<JsonObject> update_profile(@Header("Authorization") String token,
                                    @Field("name") String firstname,
                                    @Field("email") String email,
                                    @Field("gender") String gender,
                                    @Field("contact_no") String mobile);

    @FormUrlEncoded
    @POST("api/users/Users/changePassword/")
    Call<JsonObject> change_password(@Field("register_id") String register_id,
                                     @Field("confirm_password") String confirm_password);

    @GET("api/users/Users/getNotifications/")
    Call<JsonObject> get_notification();

    @FormUrlEncoded
    @POST("api/categoriesList")
    Call<Result> getCategoryList(@Field("shop_id") String shopId);

    @FormUrlEncoded
    @POST("api/shopList")
    Call<Result> getShopList(@Field("latitude") String latitude,
                             @Field("longitude") String longitude);

    @POST("api/homeData")
    Call<ProductResult> getHomeData(@Header("Authorization") String token);

    @GET("api/users/Users/getCities/")
    Call<JsonObject> get_cities();

    @FormUrlEncoded
    @POST("api/categoriesList")
    Call<Result> get_sub_categories(@Field("category_id") String category_id,
                                    @Field("shop_id") String shopId);

    @FormUrlEncoded
    @POST("api/productListByShop")
    Call<ProductResult> getProducts(@Header("Authorization") String token,
                                    @Field("category_id") String category_id,
                                    @Field("shop_id") String shopId);

    @POST("api/todaySpecial")
    Call<ProductResult> todaySpecial(@Header("Authorization") String token);

    @POST("api/ourSpecial")
    Call<ProductResult> ourSpecial(@Header("Authorization") String token);

    @FormUrlEncoded
    @POST("api/add_to_cart")
    Call<ProductResult> addToCart(@Header("Authorization") String token,
                                  @Field("quantity") String quantity,
                                  @Field("shop_product_id") String shopId);

    @FormUrlEncoded
    @POST("api/add_wishes_to_cart")
    Call<JsonObject> addWish(@Header("Authorization") String token,
                                  @Field("wishes") String wish,
                                  @Field("cart_id") String cartId);

    @FormUrlEncoded
    @POST("api/shopProductShow")
    Call<ProductDetails> getProductDetails(@Header("Authorization") String token,
                                           @Field("shop_product_id") String shopId);

    @POST("api/cartShow")
    Call<ProductResult> getCartItems(@Header("Authorization") String token);

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
    Call<ShopList> get_shop_list(@Field("city") String city,
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
                                        @Field("longitude") String longitude,
                                        @Field("sub_category") String subCategoryId);

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
    Call<ShopList> get_sorted_list(@Field("option") String option,
                                   @Field("city") String city,
                                   @Field("category_id") String category_id,
                                   @Field("sub_category_id") String sub_category_id,
                                   @Field("latitude") String latitude,
                                   @Field("longitude") String longitude);

    @FormUrlEncoded
    @POST("api/users/Users/filterlist")
    Call<ShopList> get_filter_list(@Field("category_id") String category_id,
                                   @Field("sub_category_id") String sub_category_id,
                                   @Field("latitude") String latitude,
                                   @Field("longitude") String longitude);

    @FormUrlEncoded
    @POST("api/users/Users/getFilteringResult")
    Call<ShopList> get_filter_result(@Field("category_id") String category_id,
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

//    @FormUrlEncoded
//    @POST("api/users/Users/addToCart")
//    Call<AddToCartResponse> addToCart(@Field("customer_id") String userId,
//                                      @Field("product_id") String productId,
//                                      @Field("qty") String qty,
//                                      @Field("city") String city);


    @FormUrlEncoded
    @POST("api/users/Users/clearCart")
    Call<AddToCartResponse> clearCart(@Field("customer_id") String userId);


    @FormUrlEncoded
    @POST("api/users/Users/getCartProducts")
    Call<CartResponse> getCartProducts(@Field("customer_id") String userId);

    @FormUrlEncoded
    @POST("api/users/Users/deleteCart")
    Call<DeleteCartItemResponse> deleteCart(@Field("customer_id") String userId,
                                            @Field("cart_id") String cartId);

    @FormUrlEncoded
    @POST("api/users/Users/deleteBooking")
    Call<DeleteCartItemResponse> deleteBooking(@Field("user_id") String userId,
                                               @Field("booking_id") String bokingId);

    @FormUrlEncoded
    @POST("api/deliveryAddressStore")
    Call<Result> addAddress(@Header("Authorization") String token,
                            @Field("name") String firstName,
                            @Field("phone") String mobile,
                            @Field("zip") String postcode,
                            @Field("landmark") String address_1,
                            @Field("address") String address_2);

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

    @POST("api/deliveryAddressList")
    Call<Result> getAllAddresses(@Header("Authorization") String token);

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
    @POST("api/placeOrder")
    Call<ProductResult> placeOrder(@Header("Authorization") String token,
                                   @Field("delivery_address_id") String addressId,
                                   @Field("payment_mode") String paymentMethod);

    @FormUrlEncoded
    @POST("api/paymentApiResponse")
    Call<ProductResult> paymentApiResponse(@Header("Authorization") String token,
                                   @Field("razorpay_order_id") String order_id,
                                   @Field("status") String status);

    @POST("api/ordersList")
    Call<OrdersResponse> getOrders(@Header("Authorization") String token);

    @FormUrlEncoded
    @POST("api/ordersDetails")
    Call<OrderDetailsResponse> getOrder(@Header("Authorization") String token,
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

    @FormUrlEncoded
    @POST("api/placeOrder")
    Call<com.highstreets.user.models.CartResponse> makePayment(@Header("Authorization") String token,
                                                               @Field("delivery_address_id") String addressId);
}





