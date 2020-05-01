package com.highstreets.user.ui.cart.model;

import android.content.Intent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeleteCartItemResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("cart_count")
    @Expose
    private Integer cartCount;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCartCount() {
        return cartCount;
    }

    public void setCartCount(Integer cartCount) {
        this.cartCount = cartCount;
    }
}
