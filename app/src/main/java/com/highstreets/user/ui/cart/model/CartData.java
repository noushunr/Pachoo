package com.highstreets.user.ui.cart.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartData {
    @SerializedName("products")
    @Expose
    private List<Product> productList;
    @SerializedName("sub_total")
    @Expose
    private String subTotal;
    @SerializedName("total_offer")
    @Expose
    private String totalOffer;
    @SerializedName("grand_total")
    @Expose
    private String grandTotal;

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }

    public String getTotalOffer() {
        return totalOffer;
    }

    public void setTotalOffer(String totalOffer) {
        this.totalOffer = totalOffer;
    }

    public String getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(String grandTotal) {
        this.grandTotal = grandTotal;
    }
}
