
package com.highstreets.user.ui.place_order.model.payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MakePaymentResponse {

    @SerializedName("orderCode")
    @Expose
    private String orderCode;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("orderDescription")
    @Expose
    private String orderDescription;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("currencyCode")
    @Expose
    private String currencyCode;
    @SerializedName("paymentStatus")
    @Expose
    private String paymentStatus;
    @SerializedName("paymentResponse")
    @Expose
    private PaymentResponse paymentResponse;
    @SerializedName("is3DSOrder")
    @Expose
    private Boolean is3DSOrder;
    @SerializedName("authorizeOnly")
    @Expose
    private Boolean authorizeOnly;
    @SerializedName("customerOrderCode")
    @Expose
    private String customerOrderCode;
    @SerializedName("environment")
    @Expose
    private String environment;
    @SerializedName("riskScore")
    @Expose
    private RiskScore riskScore;
    @SerializedName("resultCodes")
    @Expose
    private ResultCodes resultCodes;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOrderDescription() {
        return orderDescription;
    }

    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public PaymentResponse getPaymentResponse() {
        return paymentResponse;
    }

    public void setPaymentResponse(PaymentResponse paymentResponse) {
        this.paymentResponse = paymentResponse;
    }

    public Boolean getIs3DSOrder() {
        return is3DSOrder;
    }

    public void setIs3DSOrder(Boolean is3DSOrder) {
        this.is3DSOrder = is3DSOrder;
    }

    public Boolean getAuthorizeOnly() {
        return authorizeOnly;
    }

    public void setAuthorizeOnly(Boolean authorizeOnly) {
        this.authorizeOnly = authorizeOnly;
    }

    public String getCustomerOrderCode() {
        return customerOrderCode;
    }

    public void setCustomerOrderCode(String customerOrderCode) {
        this.customerOrderCode = customerOrderCode;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public RiskScore getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(RiskScore riskScore) {
        this.riskScore = riskScore;
    }

    public ResultCodes getResultCodes() {
        return resultCodes;
    }

    public void setResultCodes(ResultCodes resultCodes) {
        this.resultCodes = resultCodes;
    }

}
