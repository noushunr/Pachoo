package com.highstreets.user.ui.main.categories.sub_categories;

import com.highstreets.user.common.CommonPresenterInterface;

public interface SubCategoryPresenterInterface extends CommonPresenterInterface {

    void getSubCategories(String category_id, String shopId);

    void getShopLists(String city, String category_id, String sub_category_id, String latitude, String longitude);

    void getSortedShopList(String option, String city, String category_id, String sub_category_id, String latitude, String longitude);

    void getFilterList(String category_id, String sub_category_id, String latitude, String longitude);

    void getFilterResult(String category_id, String sub_category_id, String latitude, String longitude, String brand, String price, String city);
}
