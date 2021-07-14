
package com.highstreets.user.ui.main.categories;

import com.highstreets.user.common.CommonViewInterface;
import com.highstreets.user.models.Category;
import com.highstreets.user.models.Success;

import java.util.List;

public interface CategoriesFragmentViewInterface extends CommonViewInterface {

    void setCategoryList(List<Success> categoryList);

    void onLoadingCategoriesFailed(String message);
}

