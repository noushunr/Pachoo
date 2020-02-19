package com.presentation.app.dealsnest.ui.sub_categories;

import com.presentation.app.dealsnest.common.CommonViewInterface;
import com.presentation.app.dealsnest.models.FilterItemModel;
import com.presentation.app.dealsnest.models.FilterPriceModel;
import com.presentation.app.dealsnest.models.SubCategory;
import com.presentation.app.dealsnest.models.shop.ShopBanner;

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
