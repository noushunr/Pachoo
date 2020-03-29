package com.highstreets.user.ui.select_location;

import com.highstreets.user.common.CommonViewInterface;
import com.highstreets.user.models.location_model;

import java.util.List;

public interface SelectLocationViewInterface extends CommonViewInterface {

    void onLocationSelectionFailed(String message);

    void onLocationSelectionSuccess(List<location_model> locationModelList);
}
