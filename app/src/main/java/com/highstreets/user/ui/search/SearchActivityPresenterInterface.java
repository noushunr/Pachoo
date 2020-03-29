package com.highstreets.user.ui.search;

import com.highstreets.user.common.CommonPresenterInterface;

public interface SearchActivityPresenterInterface extends CommonPresenterInterface {

    void get_search_filter_list(String city_id, String search_text);

}
