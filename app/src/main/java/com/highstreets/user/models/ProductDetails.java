package com.highstreets.user.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.highstreets.user.ui.main.categories.sub_categories.Data;

import java.io.Serializable;
import java.util.List;

public class ProductDetails implements Serializable {

    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("message")
    @Expose
    private List<Success>  message;


    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public List<Success>  getMessage() {
        return message;
    }

    public void setMessage(List<Success> message) {
        this.message = message;
    }

}

