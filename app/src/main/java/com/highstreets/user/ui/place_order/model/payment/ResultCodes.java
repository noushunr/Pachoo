
package com.highstreets.user.ui.place_order.model.payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultCodes {

    @SerializedName("avsResultCode")
    @Expose
    private String avsResultCode;
    @SerializedName("cvcResultCode")
    @Expose
    private String cvcResultCode;

    public String getAvsResultCode() {
        return avsResultCode;
    }

    public void setAvsResultCode(String avsResultCode) {
        this.avsResultCode = avsResultCode;
    }

    public String getCvcResultCode() {
        return cvcResultCode;
    }

    public void setCvcResultCode(String cvcResultCode) {
        this.cvcResultCode = cvcResultCode;
    }

}
