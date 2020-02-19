package com.presentation.app.dealsnest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TopBanner {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("section")
    @Expose
    private String section;
    @SerializedName("positon")
    @Expose
    private String positon;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("category_id")
    @Expose
    private String categoryId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getPositon() {
        return positon;
    }

    public void setPositon(String positon) {
        this.positon = positon;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

}
