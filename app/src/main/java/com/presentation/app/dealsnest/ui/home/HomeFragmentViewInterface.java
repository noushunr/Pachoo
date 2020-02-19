package com.presentation.app.dealsnest.ui.home;

import com.presentation.app.dealsnest.common.CommonViewInterface;
import com.presentation.app.dealsnest.models.BottomBanner;
import com.presentation.app.dealsnest.models.BrandedShop;
import com.presentation.app.dealsnest.models.Category;
import com.presentation.app.dealsnest.models.Deal;
import com.presentation.app.dealsnest.models.MiddleBanner;
import com.presentation.app.dealsnest.models.MostViewedShop;
import com.presentation.app.dealsnest.models.RecentlyBookedShop;
import com.presentation.app.dealsnest.models.Slider;
import com.presentation.app.dealsnest.models.TopBanner;

import java.util.List;

public interface HomeFragmentViewInterface extends CommonViewInterface {

    void setCategoryList(List<Category> categoryList);

    void setSliderList(List<Slider> sliderList);

    void setDealList(List<Deal> dealList);

    void setTopBannerList(List<TopBanner> topBannerList);

    void setBrandedShopList(List<BrandedShop> brandsModelsList);

    void setMiddleBannerList(List<MiddleBanner> middleBannerList);

    void setRecentlyBookedList(List<RecentlyBookedShop> recentlyBookedShopList);

    void setBottomBannersList(List<BottomBanner> bottomBannersList);

    void setMostViewedShops(List<MostViewedShop> mostViewedShopList);
}
