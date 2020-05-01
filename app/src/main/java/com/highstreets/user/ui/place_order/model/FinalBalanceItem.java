package com.highstreets.user.ui.place_order.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FinalBalanceItem {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("subtotal")
    @Expose
    private String subtotal;
    @SerializedName("delivery_charge")
    @Expose
    private double deliveryCharge;
    @SerializedName("service_charge")
    @Expose
    private String serviceCharge;
    @SerializedName("grand_total")
    @Expose
    private String grandTotal;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public double getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(double deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public String getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(String serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public String getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(String grandTotal) {
        this.grandTotal = grandTotal;
    }
}
