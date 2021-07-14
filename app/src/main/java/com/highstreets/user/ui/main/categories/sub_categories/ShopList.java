package com.highstreets.user.ui.main.categories.sub_categories;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.highstreets.user.models.shop.ShopBanner;

import java.io.Serializable;
import java.util.List;

public class ShopList implements Serializable {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<ShopBanner> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ShopBanner> getData() {
        return data;
    }

    public void setData(List<ShopBanner> data) {
        this.data = data;
    }

}
