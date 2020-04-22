package com.highstreets.user.ui.address.add_address.select_district;

import com.highstreets.user.api.ApiClient;
import com.highstreets.user.ui.address.add_address.select_district.model.DistrictResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DistrictPresenter implements DistrictPresenterInterface{

    private DistrictViewInterface districtViewInterface;

    public DistrictPresenter(DistrictViewInterface districtViewInterface) {
        this.districtViewInterface = districtViewInterface;
    }

    @Override
    public void getDistrict(String stateId) {
        ApiClient.getApiInterface().getDistrict(stateId).enqueue(new Callback<DistrictResponse>() {
            @Override
            public void onResponse(Call<DistrictResponse> call, Response<DistrictResponse> response) {
                if (response.isSuccessful()) {
                    districtViewInterface.setDistrictList(response.body().getDistrictList());
                }
            }

            @Override
            public void onFailure(Call<DistrictResponse> call, Throwable t) {

            }
        });
    }
}
