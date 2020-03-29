package com.highstreets.user.ui.home;

import com.highstreets.user.common.CommonViewInterface;
import com.highstreets.user.models.BottomBanner;
import com.highstreets.user.models.BrandedShop;
import com.highstreets.user.models.Category;
import com.highstreets.user.models.Deal;
import com.highstreets.user.models.MiddleBanner;
import com.highstreets.user.models.MostViewedShop;
import com.highstreets.user.models.RecentlyBookedShop;
import com.highstreets.user.models.Slider;
import com.highstreets.user.models.TopBanner;

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
