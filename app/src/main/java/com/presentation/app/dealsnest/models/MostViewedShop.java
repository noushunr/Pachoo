package com.presentation.app.dealsnest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MostViewedShop {

    @SerializedName("viewing_id")
    @Expose
    private String viewingId;
    @SerializedName("merchant_id")
    @Expose
    private String merchantId;
    @SerializedName("business_name")
    @Expose
    private String businessName;
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
    @SerializedName("total_count")
    @Expose
    private Integer totalCount;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public String getViewingId() {
        return viewingId;
    }

    public void setViewingId(String viewingId) {
        this.viewingId = viewingId;
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

}
