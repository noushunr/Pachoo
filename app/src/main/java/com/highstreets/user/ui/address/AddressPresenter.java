package com.highstreets.user.ui.address;

import com.highstreets.user.api.ApiClient;
import com.highstreets.user.app_pref.SharedPrefs;
import com.highstreets.user.models.Result;
import com.highstreets.user.models.ShopsList;
import com.highstreets.user.ui.address.model.AllAddressResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.highstreets.user.app_pref.SharedPrefs.Keys.TOKEN;

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
        ApiClient.getApiInterface().getAllAddresses("Bearer "+ SharedPrefs.getString(TOKEN,"")).enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                dismissProgressIndicator();
                if (response.isSuccessful()){
                    addressViewInterface.setAllAddress(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                dismissProgressIndicator();
            }
        });
    }
}
