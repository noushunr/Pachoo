
package com.highstreets.user.ui.place_order.model.payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentResponse {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("expiryMonth")
    @Expose
    private Integer expiryMonth;
    @SerializedName("expiryYear")
    @Expose
    private Integer expiryYear;
    @SerializedName("cardType")
    @Expose
    private String cardType;
    @SerializedName("maskedCardNumber")
    @Expose
    private String maskedCardNumber;
    @SerializedName("cardSchemeType")
    @Expose
    private String cardSchemeType;
    @SerializedName("cardSchemeName")
    @Expose
    private String cardSchemeName;
    @SerializedName("cardIssuer")
    @Expose
    private String cardIssuer;
    @SerializedName("countryCode")
    @Expose
    private String countryCode;
    @SerializedName("cardClass")
    @Expose
    private String cardClass;
    @SerializedName("cardProductTypeDescNonContactless")
    @Expose
    private String cardProductTypeDescNonContactless;
    @SerializedName("cardProductTypeDescContactless")
    @Expose
    private String cardProductTypeDescContactless;
    @SerializedName("prepaid")
    @Expose
    private String prepaid;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getExpiryMonth() {
        return expiryMonth;
    }

    public void setExpiryMonth(Integer expiryMonth) {
        this.expiryMonth = expiryMonth;
    }

    public Integer getExpiryYear() {
        return expiryYear;
    }

    public void setExpiryYear(Integer expiryYear) {
        this.expiryYear = expiryYear;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getMaskedCardNumber() {
        return maskedCardNumber;
    }

    public void setMaskedCardNumber(String maskedCardNumber) {
        this.maskedCardNumber = maskedCardNumber;
    }

    public String getCardSchemeType() {
        return cardSchemeType;
    }

    public void setCardSchemeType(String cardSchemeType) {
        this.cardSchemeType = cardSchemeType;
    }

    public String getCardSchemeName() {
        return cardSchemeName;
    }

    public void setCardSchemeName(String cardSchemeName) {
        this.cardSchemeName = cardSchemeName;
    }

    public String getCardIssuer() {
        return cardIssuer;
    }

    public void setCardIssuer(String cardIssuer) {
        this.cardIssuer = cardIssuer;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCardClass() {
        return cardClass;
    }

    public void setCardClass(String cardClass) {
        this.cardClass = cardClass;
    }

    public String getCardProductTypeDescNonContactless() {
        return cardProductTypeDescNonContactless;
    }

    public void setCardProductTypeDescNonContactless(String cardProductTypeDescNonContactless) {
        this.cardProductTypeDescNonContactless = cardProductTypeDescNonContactless;
    }

    public String getCardProductTypeDescContactless() {
        return cardProductTypeDescContactless;
    }

    public void setCardProductTypeDescContactless(String cardProductTypeDescContactless) {
        this.cardProductTypeDescContactless = cardProductTypeDescContactless;
    }

    public String getPrepaid() {
        return prepaid;
    }

    public void setPrepaid(String prepaid) {
        this.prepaid = prepaid;
    }

}
