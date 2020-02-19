
package com.presentation.app.dealsnest.ui.categories;

import com.presentation.app.dealsnest.common.CommonViewInterface;
import com.presentation.app.dealsnest.models.Category;

import java.util.List;

public interface CategoriesFragmentViewInterface extends CommonViewInterface {

    void setCategoryList(List<Category> categoryList);

    void onLoadingCategoriesFailed(String message);
}

