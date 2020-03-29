package com.highstreets.user.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BrandedShop {

    @SerializedName("offer_id")
    @Expose
    private String offerId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("offer_type_id")
    @Expose
    private String offerTypeId;
    @SerializedName("featured_image")
    @Expose
    private String featuredImage;
    @SerializedName("mrp_price")
    @Expose
    private String mrpPrice;
    @SerializedName("offer_percentage")
    @Expose
    private String offerPercentage;
    @SerializedName("offer_price")
    @Expose
    private String offerPrice;
    @SerializedName("merchant_id")
    @Expose
    private String merchantId;

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOfferTypeId() {
        return offerTypeId;
    }

    public void setOfferTypeId(String offerTypeId) {
        this.offerTypeId = offerTypeId;
    }

    public String getFeaturedImage() {
        return featuredImage;
    }

    public void setFeaturedImage(String featuredImage) {
        this.featuredImage = featuredImage;
    }

    public String getMrpPrice() {
        return mrpPrice;
    }

    public void setMrpPrice(String mrpPrice) {
        this.mrpPrice = mrpPrice;
    }

    public String getOfferPercentage() {
        return offerPercentage;
    }

    public void setOfferPercentage(String offerPercentage) {
        this.offerPercentage = offerPercentage;
    }

    public String getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(String offerPrice) {
        this.offerPrice = offerPrice;
    }

}
