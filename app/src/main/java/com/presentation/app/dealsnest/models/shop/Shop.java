package com.presentation.app.dealsnest.models.shop;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Shop {

    @SerializedName("merchant_id")
    @Expose
    private String merchantId;
    @SerializedName("business_name")
    @Expose
    private String businessName;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("sub_category")
    @Expose
    private String subCategory;
    @SerializedName("deal_of_day")
    @Expose
    private String dealOfDay;
    @SerializedName("deal_date")
    @Expose
    private String dealDate;
    @SerializedName("ofr_id")
    @Expose
    private String ofrId;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("offer_percentage")
    @Expose
    private String offerPercentage;
    @SerializedName("mrp_price")
    @Expose
    private String mrpPrice;
    @SerializedName("offer_price")
    @Expose
    private String offerPrice;
    @SerializedName("bought")
    @Expose
    private String bought;
    @SerializedName("ratings")
    @Expose
    private String ratings;
    @SerializedName("distance")
    @Expose
    private String distance;

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getDealOfDay() {
        return dealOfDay;
    }

    public void setDealOfDay(String dealOfDay) {
        this.dealOfDay = dealOfDay;
    }

    public String getDealDate() {
        return dealDate;
    }

    public void setDealDate(String dealDate) {
        this.dealDate = dealDate;
    }

    public String getOfrId() {
        return ofrId;
    }

    public void setOfrId(String ofrId) {
        this.ofrId = ofrId;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOfferPercentage() {
        return offerPercentage;
    }

    public void setOfferPercentage(String offerPercentage) {
        this.offerPercentage = offerPercentage;
    }

    public String getMrpPrice() {
        return mrpPrice;
    }

    public void setMrpPrice(String mrpPrice) {
        this.mrpPrice = mrpPrice;
    }

    public String getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(String offerPrice) {
        this.offerPrice = offerPrice;
    }

    public String getBought() {
        return bought;
    }

    public void setBought(String bought) {
        this.bought = bought;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

}
