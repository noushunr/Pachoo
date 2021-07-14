package com.highstreets.user.ui.main.categories.sub_categories;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.highstreets.user.models.Cart;
import com.highstreets.user.models.Slider;
import com.highstreets.user.models.Success;

import java.io.Serializable;
import java.util.List;

public class Data implements Serializable {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("data")
    @Expose
    private List<Data_> data = null;
    @SerializedName("product_list")
    @Expose
    private List<Success> productList = null;
    @SerializedName("slider")
    @Expose
    private List<Slider> slider = null;
    @SerializedName("cart_items")
    @Expose
    private List<Success> cartItems = null;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("session_id")
    @Expose
    private String sessionId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("contact_no")
    @Expose
    private String contactNo;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("razorpay_order_id")
    @Expose
    private String razorpayOrderId;
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Data_> getData() {
        return data;
    }

    public void setData(List<Data_> data) {
        this.data = data;
    }

    public List<Success> getProductList() {
        return productList;
    }

    public void setProductList(List<Success> productList) {
        this.productList = productList;
    }

    public List<Success> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<Success> cartItems) {
        this.cartItems = cartItems;
    }

    public String getRazorpayOrderId() {
        return razorpayOrderId;
    }

    public void setRazorpayOrderId(String razorpayOrderId) {
        this.razorpayOrderId = razorpayOrderId;
    }

    @SerializedName("today_special")
    @Expose
    private List<Success> todaySpecial = null;
    @SerializedName("our_special")
    @Expose
    private List<Success> ourSpecial = null;
    @SerializedName("categories")
    @Expose
    private List<Success> categories = null;
    public List<Success> getTodaySpecial() {
        return todaySpecial;
    }

    public void setTodaySpecial(List<Success> todaySpecial) {
        this.todaySpecial = todaySpecial;
    }

    public List<Success> getOurSpecial() {
        return ourSpecial;
    }

    public void setOurSpecial(List<Success> ourSpecial) {
        this.ourSpecial = ourSpecial;
    }

    public List<Success> getCategories() {
        return categories;
    }

    public void setCategories(List<Success> categories) {
        this.categories = categories;
    }
    @SerializedName("cart")
    @Expose
    private Cart cart;

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public List<Slider> getSlider() {
        return slider;
    }

    public void setSlider(List<Slider> slider) {
        this.slider = slider;
    }
}
