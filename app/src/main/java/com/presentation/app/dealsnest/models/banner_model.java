package com.presentation.app.dealsnest.models;import com.google.gson.annotations.SerializedName;public class banner_model {    @SerializedName("id")    String BannerID;    @SerializedName("state_id")    String BannerStateID;    @SerializedName("district_id")    String BannerDistrictID;    @SerializedName("city_id")    String BannerCityID;    @SerializedName("image")    String BannerImage;    @SerializedName("section")    String BannerSection;    @SerializedName("created_date")    String BannerCreatedDate;    @SerializedName("updated_date")    String BannerUpdateDate;    @SerializedName("status")    String BannerStatus;    @SerializedName("ip")    String BannerIP;    @SerializedName("city")    String CityName;    public String getBannerID() {        return BannerID;    }    public void setBannerID(String bannerID) {        BannerID = bannerID;    }    public String getBannerStateID() {        return BannerStateID;    }    public void setBannerStateID(String bannerStateID) {        BannerStateID = bannerStateID;    }    public String getBannerDistrictID() {        return BannerDistrictID;    }    public void setBannerDistrictID(String bannerDistrictID) {        BannerDistrictID = bannerDistrictID;    }    public String getBannerCityID() {        return BannerCityID;    }    public void setBannerCityID(String bannerCityID) {        BannerCityID = bannerCityID;    }    public String getBannerImage() {        return BannerImage;    }    public void setBannerImage(String bannerImage) {        BannerImage = bannerImage;    }    public String getBannerSection() {        return BannerSection;    }    public void setBannerSection(String bannerSection) {        BannerSection = bannerSection;    }    public String getBannerCreatedDate() {        return BannerCreatedDate;    }    public void setBannerCreatedDate(String bannerCreatedDate) {        BannerCreatedDate = bannerCreatedDate;    }    public String getBannerUpdateDate() {        return BannerUpdateDate;    }    public void setBannerUpdateDate(String bannerUpdateDate) {        BannerUpdateDate = bannerUpdateDate;    }    public String getBannerStatus() {        return BannerStatus;    }    public void setBannerStatus(String bannerStatus) {        BannerStatus = bannerStatus;    }    public String getBannerIP() {        return BannerIP;    }    public void setBannerIP(String bannerIP) {        BannerIP = bannerIP;    }    public String getCityName() {        return CityName;    }    public void setCityName(String cityName) {        CityName = cityName;    }}