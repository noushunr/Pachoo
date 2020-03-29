package com.highstreets.user.ui.search;

import com.highstreets.user.common.CommonViewInterface;
import com.highstreets.user.models.SearchItem;

import java.util.List;

public interface SearchActivityViewInterface extends CommonViewInterface {

    void onSearchListFilterSuccess(List<SearchItem> searchListModelList);
}
