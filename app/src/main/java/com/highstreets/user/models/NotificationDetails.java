package com.highstreets.user.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class NotificationDetails {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("tittle")
    @Expose
    private String tittle;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("offers")
    @Expose
    private ArrayList<notification_merchant_view_model> offers = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public ArrayList<notification_merchant_view_model> getOffers() {
        return offers;
    }

    public void setOffers(ArrayList<notification_merchant_view_model> offers) {
        this.offers = offers;
    }
}
