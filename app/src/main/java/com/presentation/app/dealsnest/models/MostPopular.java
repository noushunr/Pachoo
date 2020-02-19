package com.presentation.app.dealsnest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MostPopular {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("merchant_id")
    @Expose
    private String merchantId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("offer_type_id")
    @Expose
    private String offerTypeId;
    @SerializedName("mrp_price")
    @Expose
    private String mrpPrice;
    @SerializedName("offer_percentage")
    @Expose
    private String offerPercentage;
    @SerializedName("offer_price")
    @Expose
    private String offerPrice;
    @SerializedName("qty")
    @Expose
    private String qty;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("sub_category")
    @Expose
    private String subCategory;
    @SerializedName("featured_image")
    @Expose
    private String featuredImage;
    @SerializedName("offer_valid_from")
    @Expose
    private String offerValidFrom;
    @SerializedName("offer_valid_to")
    @Expose
    private String offerValidTo;
    @SerializedName("valid_for")
    @Expose
    private String validFor;
    @SerializedName("deal_of_day")
    @Expose
    private String dealOfDay;
    @SerializedName("deal_date")
    @Expose
    private String dealDate;
    @SerializedName("like_count")
    @Expose
    private String likeCount;
    @SerializedName("booking_count")
    @Expose
    private String bookingCount;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("updated_date")
    @Expose
    private String updatedDate;
    @SerializedName("ofr_status")
    @Expose
    private String ofrStatus;
    @SerializedName("ofr_validity")
    @Expose
    private String ofrValidity;
    @SerializedName("ip")
    @Expose
    private String ip;
    @SerializedName("savings")
    @Expose
    private Double savings;

    public Double getSavings() {
        return savings;
    }

    public void setSavings(Double savings) {
        this.savings = savings;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
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

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
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

    public String getFeaturedImage() {
        return featuredImage;
    }

    public void setFeaturedImage(String featuredImage) {
        this.featuredImage = featuredImage;
    }

    public String getOfferValidFrom() {
        return offerValidFrom;
    }

    public void setOfferValidFrom(String offerValidFrom) {
        this.offerValidFrom = offerValidFrom;
    }

    public String getOfferValidTo() {
        return offerValidTo;
    }

    public void setOfferValidTo(String offerValidTo) {
        this.offerValidTo = offerValidTo;
    }

    public String getValidFor() {
        return validFor;
    }

    public void setValidFor(String validFor) {
        this.validFor = validFor;
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

    public String getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }

    public String getBookingCount() {
        return bookingCount;
    }

    public void setBookingCount(String bookingCount) {
        this.bookingCount = bookingCount;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getOfrStatus() {
        return ofrStatus;
    }

    public void setOfrStatus(String ofrStatus) {
        this.ofrStatus = ofrStatus;
    }

    public String getOfrValidity() {
        return ofrValidity;
    }

    public void setOfrValidity(String ofrValidity) {
        this.ofrValidity = ofrValidity;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }


}
