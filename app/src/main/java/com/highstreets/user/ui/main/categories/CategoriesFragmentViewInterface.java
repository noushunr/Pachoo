
package com.highstreets.user.ui.main.categories;

import com.highstreets.user.common.CommonViewInterface;
import com.highstreets.user.models.Category;

import java.util.List;

public interface CategoriesFragmentViewInterface extends CommonViewInterface {

    void setCategoryList(List<Category> categoryList);

    void onLoadingCategoriesFailed(String message);
}

