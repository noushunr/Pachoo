package com.highstreets.user.ui.main.home;

import com.highstreets.user.common.CommonViewInterface;
import com.highstreets.user.models.BottomBanner;
import com.highstreets.user.models.BrandedShop;
import com.highstreets.user.models.Category;
import com.highstreets.user.models.Deal;
import com.highstreets.user.models.MiddleBanner;
import com.highstreets.user.models.MostViewedShop;
import com.highstreets.user.models.ProductResult;
import com.highstreets.user.models.RecentlyBookedShop;
import com.highstreets.user.models.Slider;
import com.highstreets.user.models.Success;
import com.highstreets.user.models.TopBanner;
import com.highstreets.user.ui.main.categories.sub_categories.Data;

import java.util.List;

public interface HomeViewInterface extends CommonViewInterface {

    void setCategoryList(Data categoryList);

    void setSliderList(List<Slider> sliderList);

    void setShopList(List<Success> shopList);

    void setTopBannerList(List<TopBanner> topBannerList);

    void setBrandedShopList(List<BrandedShop> brandsModelsList);

    void setMiddleBannerList(List<MiddleBanner> middleBannerList);

    void setRecentlyBookedList(List<RecentlyBookedShop> recentlyBookedShopList);

    void setBottomBannersList(List<BottomBanner> bottomBannersList);

    void setMostViewedShops(List<MostViewedShop> mostViewedShopList);
}
