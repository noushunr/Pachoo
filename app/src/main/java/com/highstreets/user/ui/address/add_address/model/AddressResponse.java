package com.highstreets.user.ui.address.add_address.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.highstreets.user.ui.address.model.Address;

import java.util.List;

public class AddressResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("result")
    @Expose
    private Address address;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
