package com.highstreets.user.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class notification_model {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("merchant_id")
    @Expose
    private ArrayList<String> merchantId;


    @SerializedName("offer_id")
    @Expose
    private String offerId;
    @SerializedName("tittle")
    @Expose
    private String tittle;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("offer_image")
    @Expose
    private String offerImage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<String> getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(ArrayList<String> merchantId) {
        this.merchantId = merchantId;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOfferImage() {
        return offerImage;
    }

    public void setOfferImage(String offerImage) {
        this.offerImage = offerImage;
    }
}
