package com.highstreets.user.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ViewAllRecentlyBooked {

    @SerializedName("booking_id")
    @Expose
    private String bookingId;
    @SerializedName("merchant_id")
    @Expose
    private String merchantId;
    @SerializedName("business_name")
    @Expose
    private String businessName;
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
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<ViewAllRecentlyBooked> data = null;

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ViewAllRecentlyBooked> getData() {
        return data;
    }

    public void setData(List<ViewAllRecentlyBooked> data) {
        this.data = data;
    }
}
