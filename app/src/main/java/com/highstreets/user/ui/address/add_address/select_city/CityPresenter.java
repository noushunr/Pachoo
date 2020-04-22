package com.highstreets.user.ui.address.add_address.select_city;

import com.highstreets.user.api.ApiClient;
import com.highstreets.user.ui.address.add_address.select_city.model.CityResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CityPresenter implements CityPresenterInterface{

    private CityViewInterface cityViewInterface;

    public CityPresenter(CityViewInterface cityViewInterface) {
        this.cityViewInterface = cityViewInterface;
    }

    @Override
    public void getCity(String districtId) {
        ApiClient.getApiInterface().getCity(districtId).enqueue(new Callback<CityResponse>() {
            @Override
            public void onResponse(Call<CityResponse> call, Response<CityResponse> response) {
                if (response.isSuccessful()) {
                    cityViewInterface.setCityList(response.body().getCityList());
                }
            }

            @Override
            public void onFailure(Call<CityResponse> call, Throwable t) {

            }
        });
    }
}
