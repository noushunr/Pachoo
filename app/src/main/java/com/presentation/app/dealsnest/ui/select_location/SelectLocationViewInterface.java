package com.presentation.app.dealsnest.ui.select_location;

import com.presentation.app.dealsnest.common.CommonViewInterface;
import com.presentation.app.dealsnest.models.location_model;

import java.util.List;

public interface SelectLocationViewInterface extends CommonViewInterface {

    void onLocationSelectionFailed(String message);

    void onLocationSelectionSuccess(List<location_model> locationModelList);
}
