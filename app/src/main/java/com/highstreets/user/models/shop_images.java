package com.highstreets.user.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class shop_images implements Parcelable {

    public static final Creator<shop_images> CREATOR = new Creator<shop_images>() {
        @Override
        public shop_images createFromParcel(Parcel in) {
            return new shop_images(in);
        }

        @Override
        public shop_images[] newArray(int size) {
            return new shop_images[size];
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

    protected shop_images(Parcel in) {
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
