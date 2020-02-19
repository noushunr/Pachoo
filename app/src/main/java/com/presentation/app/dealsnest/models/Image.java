package com.presentation.app.dealsnest.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Image implements Parcelable, Serializable {

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("merchant_id")
    @Expose
    private String merchantId;
    @SerializedName("images")
    @Expose
    private String images;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("updated_date")
    @Expose
    private String updatedDate;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("ip")
    @Expose
    private String ip;

    protected Image(Parcel in) {
        id = in.readString();
        merchantId = in.readString();
        images = in.readString();
        createdDate = in.readString();
        updatedDate = in.readString();
        status = in.readString();
        ip = in.readString();
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

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
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
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(merchantId);
        dest.writeString(images);
        dest.writeString(createdDate);
        dest.writeString(updatedDate);
        dest.writeString(status);
        dest.writeString(ip);
    }
}
