package com.highstreets.user.ui.address;

import com.highstreets.user.api.ApiClient;
import com.highstreets.user.ui.address.model.AllAddressResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressPresenter implements AddressPresenterInterface {

    private AddressViewInterface addressViewInterface;

    public AddressPresenter(AddressViewInterface addressViewInterface) {
        this.addressViewInterface = addressViewInterface;
    }

    @Override
    public void showProgressIndicator() {
        addressViewInterface.showProgressIndicator();
    }

    @Override
    public void dismissProgressIndicator() {
        addressViewInterface.dismissProgressIndicator();
    }

    @Override
    public void getAllAddress(String userId) {
        showProgressIndicator();
        ApiClient.getApiInterface().getAllAddresses(userId).enqueue(new Callback<AllAddressResponse>() {
            @Override
            public void onResponse(Call<AllAddressResponse> call, Response<AllAddressResponse> response) {
                dismissProgressIndicator();
                if (response.isSuccessful()){
                    addressViewInterface.setAllAddress(response.body().getAddress());
                }
            }

            @Override
            public void onFailure(Call<AllAddressResponse> call, Throwable t) {
                dismissProgressIndicator();
            }
        });
    }
}
