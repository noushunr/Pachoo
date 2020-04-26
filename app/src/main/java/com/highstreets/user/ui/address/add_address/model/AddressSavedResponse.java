package com.highstreets.user.ui.address.add_address.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.highstreets.user.ui.address.model.Address;

public class AddressSavedResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("addres_id")
    @Expose
    private String addressId;

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

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }
}
