package com.presentation.app.dealsnest.models;

import com.google.gson.annotations.SerializedName;

public class brands_models {

    @SerializedName("image")
    private String brandICon;
    @SerializedName("category_name")
    private String brandName;
    @SerializedName("id")
    private String brandID;
    @SerializedName("created_date")
    private String createdDate;
    @SerializedName("updated_date")
    private String updatedDate;
    @SerializedName("status")
    private String Status;
    @SerializedName("ip")
    private String IP;

    public String getBrandICon() {
        return brandICon;
    }

    public void setBrandICon(String brandICon) {
        this.brandICon = brandICon;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandID() {
        return brandID;
    }

    public void setBrandID(String brandID) {
        this.brandID = brandID;
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

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }
}
