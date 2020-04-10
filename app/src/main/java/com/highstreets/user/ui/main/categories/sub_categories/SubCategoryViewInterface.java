package com.highstreets.user.ui.main.categories.sub_categories;

import com.highstreets.user.common.CommonViewInterface;
import com.highstreets.user.models.FilterItemModel;
import com.highstreets.user.models.FilterPriceModel;
import com.highstreets.user.models.SubCategory;
import com.highstreets.user.models.shop.ShopBanner;

import java.util.List;

public interface SubCategoryViewInterface extends CommonViewInterface {

    void onFailedToLoadSubCategories(String message);

    void onLoadingSubCategoriesSuccess(List<SubCategory> subCategoryModelList);

    void onLoadingShopListSuccess(List<ShopBanner> shopList);

    void onLoadingFilteredShopListSuccess(List<ShopBanner> shopList);

    void onLoadingSortedShopListSuccess(List<ShopBanner> shopList);

    void onLoadingPriceItemsSuccess(FilterPriceModel priceList);

    void onLoadingBrandItemsSuccess(FilterItemModel brandItems);

}
