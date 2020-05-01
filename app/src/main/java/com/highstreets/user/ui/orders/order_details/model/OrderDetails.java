
package com.highstreets.user.ui.orders.order_details.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderDetails {

    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("telephone")
    @Expose
    private String telephone;
    @SerializedName("qr_code")
    @Expose
    private String qrCode;
    @SerializedName("custom_field")
    @Expose
    private String customField;
    @SerializedName("time_slot")
    @Expose
    private String timeSlot;
    @SerializedName("payment_firstname")
    @Expose
    private String paymentFirstname;
    @SerializedName("payment_lastname")
    @Expose
    private String paymentLastname;
    @SerializedName("payment_company")
    @Expose
    private String paymentCompany;
    @SerializedName("payment_address_1")
    @Expose
    private String paymentAddress1;
    @SerializedName("payment_address_2")
    @Expose
    private String paymentAddress2;
    @SerializedName("payment_city")
    @Expose
    private String paymentCity;
    @SerializedName("payment_postcode")
    @Expose
    private String paymentPostcode;
    @SerializedName("payment_country")
    @Expose
    private String paymentCountry;
    @SerializedName("payment_country_id")
    @Expose
    private String paymentCountryId;
    @SerializedName("payment_zone")
    @Expose
    private String paymentZone;
    @SerializedName("payment_zone_id")
    @Expose
    private String paymentZoneId;
    @SerializedName("payment_phone")
    @Expose
    private String paymentPhone;
    @SerializedName("payment_district")
    @Expose
    private String paymentDistrict;
    @SerializedName("payment_method")
    @Expose
    private String paymentMethod;
    @SerializedName("payment_lattitude")
    @Expose
    private String paymentLattitude;
    @SerializedName("payment_longitude")
    @Expose
    private String paymentLongitude;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("order_status_id")
    @Expose
    private String orderStatusId;
    @SerializedName("date_added")
    @Expose
    private String dateAdded;
    @SerializedName("date_modified")
    @Expose
    private String dateModified;
    @SerializedName("shipping_charge")
    @Expose
    private String shippingCharge;
    @SerializedName("subtotal")
    @Expose
    private String subtotal;
    @SerializedName("service_charge")
    @Expose
    private String serviceCharge;
    @SerializedName("seen_status")
    @Expose
    private String seenStatus;
    @SerializedName("rating_status")
    @Expose
    private String ratingStatus;
    @SerializedName("qr_code_image")
    @Expose
    private String qrCodeImage;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getCustomField() {
        return customField;
    }

    public void setCustomField(String customField) {
        this.customField = customField;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String getPaymentFirstname() {
        return paymentFirstname;
    }

    public void setPaymentFirstname(String paymentFirstname) {
        this.paymentFirstname = paymentFirstname;
    }

    public String getPaymentLastname() {
        return paymentLastname;
    }

    public void setPaymentLastname(String paymentLastname) {
        this.paymentLastname = paymentLastname;
    }

    public String getPaymentCompany() {
        return paymentCompany;
    }

    public void setPaymentCompany(String paymentCompany) {
        this.paymentCompany = paymentCompany;
    }

    public String getPaymentAddress1() {
        return paymentAddress1;
    }

    public void setPaymentAddress1(String paymentAddress1) {
        this.paymentAddress1 = paymentAddress1;
    }

    public String getPaymentAddress2() {
        return paymentAddress2;
    }

    public void setPaymentAddress2(String paymentAddress2) {
        this.paymentAddress2 = paymentAddress2;
    }

    public String getPaymentCity() {
        return paymentCity;
    }

    public void setPaymentCity(String paymentCity) {
        this.paymentCity = paymentCity;
    }

    public String getPaymentPostcode() {
        return paymentPostcode;
    }

    public void setPaymentPostcode(String paymentPostcode) {
        this.paymentPostcode = paymentPostcode;
    }

    public String getPaymentCountry() {
        return paymentCountry;
    }

    public void setPaymentCountry(String paymentCountry) {
        this.paymentCountry = paymentCountry;
    }

    public String getPaymentCountryId() {
        return paymentCountryId;
    }

    public void setPaymentCountryId(String paymentCountryId) {
        this.paymentCountryId = paymentCountryId;
    }

    public String getPaymentZone() {
        return paymentZone;
    }

    public void setPaymentZone(String paymentZone) {
        this.paymentZone = paymentZone;
    }

    public String getPaymentZoneId() {
        return paymentZoneId;
    }

    public void setPaymentZoneId(String paymentZoneId) {
        this.paymentZoneId = paymentZoneId;
    }

    public String getPaymentPhone() {
        return paymentPhone;
    }

    public void setPaymentPhone(String paymentPhone) {
        this.paymentPhone = paymentPhone;
    }

    public String getPaymentDistrict() {
        return paymentDistrict;
    }

    public void setPaymentDistrict(String paymentDistrict) {
        this.paymentDistrict = paymentDistrict;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentLattitude() {
        return paymentLattitude;
    }

    public void setPaymentLattitude(String paymentLattitude) {
        this.paymentLattitude = paymentLattitude;
    }

    public String getPaymentLongitude() {
        return paymentLongitude;
    }

    public void setPaymentLongitude(String paymentLongitude) {
        this.paymentLongitude = paymentLongitude;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getOrderStatusId() {
        return orderStatusId;
    }

    public void setOrderStatusId(String orderStatusId) {
        this.orderStatusId = orderStatusId;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public String getShippingCharge() {
        return shippingCharge;
    }

    public void setShippingCharge(String shippingCharge) {
        this.shippingCharge = shippingCharge;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(String serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public String getSeenStatus() {
        return seenStatus;
    }

    public void setSeenStatus(String seenStatus) {
        this.seenStatus = seenStatus;
    }

    public String getRatingStatus() {
        return ratingStatus;
    }

    public void setRatingStatus(String ratingStatus) {
        this.ratingStatus = ratingStatus;
    }

    public String getQrCodeImage() {
        return qrCodeImage;
    }

    public void setQrCodeImage(String qrCodeImage) {
        this.qrCodeImage = qrCodeImage;
    }

}
