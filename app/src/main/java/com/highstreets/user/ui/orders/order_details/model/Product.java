
package com.highstreets.user.ui.orders.order_details.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import butterknife.BindView;

public class Product {

    @SerializedName("order_product_id")
    @Expose
    private String orderProductId;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("merchant_id")
    @Expose
    private String merchantId;
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("model")
    @Expose
    private String model;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("discount_price")
    @Expose
    private String discountPrice;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("tax")
    @Expose
    private String tax;
    @SerializedName("reward")
    @Expose
    private String reward;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("business_name")
    @Expose
    private String businessName;
    @SerializedName("featured_image")
    @Expose
    private String featuredImage;

    public String getOrderProductId() {
        return orderProductId;
    }

    public void setOrderProductId(String orderProductId) {
        this.orderProductId = orderProductId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getFeaturedImage() {
        return featuredImage;
    }

    public void setFeaturedImage(String featuredImage) {
        this.featuredImage = featuredImage;
    }
}
