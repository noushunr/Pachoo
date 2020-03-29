package com.highstreets.user.models;

import com.google.gson.annotations.SerializedName;

public class shop_banner_model {
    @SerializedName("id")
    String ShopID;
    @SerializedName("section")
    String ShopSection;
    @SerializedName("positon")
    String ShopPosition;
    @SerializedName("image")
    String ShopBannerImage;
    @SerializedName("status")
    String ShopStatus;

    public String getShopID() {
        return ShopID;
    }

    public void setShopID(String shopID) {
        ShopID = shopID;
    }

    public String getShopSection() {
        return ShopSection;
    }

    public void setShopSection(String shopSection) {
        ShopSection = shopSection;
    }

    public String getShopPosition() {
        return ShopPosition;
    }

    public void setShopPosition(String shopPosition) {
        ShopPosition = shopPosition;
    }

    public String getShopBannerImage() {
        return ShopBannerImage;
    }

    public void setShopBannerImage(String shopBannerImage) {
        ShopBannerImage = shopBannerImage;
    }

    public String getShopStatus() {
        return ShopStatus;
    }

    public void setShopStatus(String shopStatus) {
        ShopStatus = shopStatus;
    }
}
