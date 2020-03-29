package com.highstreets.user.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomePageDetails {

    @SerializedName("categories")
    @Expose
    private List<Category> categories = null;
    @SerializedName("sliders")
    @Expose
    private List<Slider> sliders = null;
    @SerializedName("deals")
    @Expose
    private List<Deal> deals = null;
    @SerializedName("top_banners")
    @Expose
    private List<TopBanner> topBanners = null;
    @SerializedName("branded_shops")
    @Expose
    private List<BrandedShop> brandedShops = null;
    @SerializedName("middle_banners")
    @Expose
    private List<MiddleBanner> middleBanners = null;
    @SerializedName("recently_booked_shops")
    @Expose
    private List<RecentlyBookedShop> recentlyBookedShops = null;
    @SerializedName("bottom_banners")
    @Expose
    private List<BottomBanner> bottomBanners = null;
    @SerializedName("most_viewed_shops")
    @Expose
    private List<MostViewedShop> mostViewedShops = null;

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Slider> getSliders() {
        return sliders;
    }

    public void setSliders(List<Slider> sliders) {
        this.sliders = sliders;
    }

    public List<Deal> getDeals() {
        return deals;
    }

    public void setDeals(List<Deal> deals) {
        this.deals = deals;
    }

    public List<TopBanner> getTopBanners() {
        return topBanners;
    }

    public void setTopBanners(List<TopBanner> topBanners) {
        this.topBanners = topBanners;
    }

    public List<BrandedShop> getBrandedShops() {
        return brandedShops;
    }

    public void setBrandedShops(List<BrandedShop> brandedShops) {
        this.brandedShops = brandedShops;
    }

    public List<MiddleBanner> getMiddleBanners() {
        return middleBanners;
    }

    public void setMiddleBanners(List<MiddleBanner> middleBanners) {
        this.middleBanners = middleBanners;
    }

    public List<RecentlyBookedShop> getRecentlyBookedShops() {
        return recentlyBookedShops;
    }

    public void setRecentlyBookedShops(List<RecentlyBookedShop> recentlyBookedShops) {
        this.recentlyBookedShops = recentlyBookedShops;
    }

    public List<BottomBanner> getBottomBanners() {
        return bottomBanners;
    }

    public void setBottomBanners(List<BottomBanner> bottomBanners) {
        this.bottomBanners = bottomBanners;
    }

    public List<MostViewedShop> getMostViewedShops() {
        return mostViewedShops;
    }

    public void setMostViewedShops(List<MostViewedShop> mostViewedShops) {
        this.mostViewedShops = mostViewedShops;
    }

}
