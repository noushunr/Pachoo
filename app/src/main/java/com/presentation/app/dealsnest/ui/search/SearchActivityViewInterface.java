package com.presentation.app.dealsnest.ui.search;

import com.presentation.app.dealsnest.common.CommonViewInterface;
import com.presentation.app.dealsnest.models.SearchItem;

import java.util.List;

public interface SearchActivityViewInterface extends CommonViewInterface {

    void onSearchListFilterSuccess(List<SearchItem> searchListModelList);
}
