package com.presentation.app.dealsnest.ui.search;

import com.presentation.app.dealsnest.common.CommonPresenterInterface;

public interface SearchActivityPresenterInterface extends CommonPresenterInterface {

    void get_search_filter_list(String city_id, String search_text);

}
