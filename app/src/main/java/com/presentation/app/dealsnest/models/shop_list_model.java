package com.presentation.app.dealsnest.models;

import com.google.gson.annotations.SerializedName;

public class shop_list_model {

    @SerializedName("merchant_id")
    String MerchantID;
    @SerializedName("business_name")
    String BusinessName;
    @SerializedName("category")
    String CategoryID;
    @SerializedName("sub_category")
    String SubCategoryID;
    @SerializedName("city")
    String CityName;
    @SerializedName("deal_of_day")
    String DealOfDay;
    @SerializedName("deal_date")
    String DealDate;
    @SerializedName("ofr_id")
    String OfferID;
    @SerializedName("image")
    String ShopImage;
    @SerializedName("offer_percentage")
    String OfferPercentage;
    @SerializedName("mrp_price")
    String MrpPrice;
    @SerializedName("offer_price")
    String OfferPrice;
    @SerializedName("bought")
    String Bought;
    @SerializedName("ratings")
    Float Rating;

    public String getMerchantID() {
        return MerchantID;
    }

    public void setMerchantID(String merchantID) {
        MerchantID = merchantID;
    }

    public String getBusinessName() {
        return BusinessName;
    }

    public void setBusinessName(String businessName) {
        BusinessName = businessName;
    }

    public String getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(String categoryID) {
        CategoryID = categoryID;
    }

    public String getSubCategoryID() {
        return SubCategoryID;
    }

    public void setSubCategoryID(String subCategoryID) {
        SubCategoryID = subCategoryID;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

    public String getDealOfDay() {
        return DealOfDay;
    }

    public void setDealOfDay(String dealOfDay) {
        DealOfDay = dealOfDay;
    }

    public String getDealDate() {
        return DealDate;
    }

    public void setDealDate(String dealDate) {
        DealDate = dealDate;
    }

    public String getOfferID() {
        return OfferID;
    }

    public void setOfferID(String offerID) {
        OfferID = offerID;
    }

    public String getShopImage() {
        return ShopImage;
    }

    public void setShopImage(String shopImage) {
        ShopImage = shopImage;
    }

    public String getOfferPercentage() {
        return OfferPercentage;
    }

    public void setOfferPercentage(String offerPercentage) {
        OfferPercentage = offerPercentage;
    }

    public String getMrpPrice() {
        return MrpPrice;
    }

    public void setMrpPrice(String mrpPrice) {
        MrpPrice = mrpPrice;
    }

    public String getOfferPrice() {
        return OfferPrice;
    }

    public void setOfferPrice(String offerPrice) {
        OfferPrice = offerPrice;
    }

    public String getBought() {
        return Bought;
    }

    public void setBought(String bought) {
        Bought = bought;
    }

    public Float getRating() {
        return Rating;
    }

    public void setRating(Float rating) {
        Rating = rating;
    }

}
