package com.highstreets.user.ui.address.add_address;

import com.highstreets.user.api.ApiClient;
import com.highstreets.user.app_pref.SharedPrefs;
import com.highstreets.user.models.Result;
import com.highstreets.user.ui.address.add_address.model.AddressResponse;
import com.highstreets.user.ui.address.add_address.model.AddressSavedResponse;
import com.highstreets.user.ui.address.add_address.model.PostResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.highstreets.user.app_pref.SharedPrefs.Keys.TOKEN;

public class AddAddressPresenter implements AddAddressPresenterInterface {

    private AddAddressViewInterface addAddressViewInterface;

    public AddAddressPresenter(AddAddressViewInterface addAddressViewInterface) {
        this.addAddressViewInterface = addAddressViewInterface;
    }

    @Override
    public void showProgressIndicator() {
        addAddressViewInterface.showProgressIndicator();
    }

    @Override
    public void dismissProgressIndicator() {
        addAddressViewInterface.dismissProgressIndicator();
    }

    @Override
    public void addAddress(
                           String firstName,
                           String mobile,
                           String postcode,
                           String address_1,
                           String address_2) {

        showProgressIndicator();
        ApiClient.getApiInterface().addAddress(
                "Bearer "+ SharedPrefs.getString(TOKEN,""),
                firstName,
                mobile,
                postcode,
                address_1,
                address_2).enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                dismissProgressIndicator();
                if (response.isSuccessful()){
                    addAddressViewInterface.addressAddedResult(response.body());
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                dismissProgressIndicator();
            }
        });

    }

    @Override
    public void editAddress(String userId,
                            String addressId,
                            String firstName,
                            String lastName,
                            String mobile,
                            String district,
                            String city,
                            String state,
                            String postcode,
                            String address_1,
                            String address_2,
                            String latitude,
                            String longitude) {

        showProgressIndicator();
        ApiClient.getApiInterface().editAddress(
                userId,
                addressId,
                firstName,
                lastName,
                mobile,
                district,
                city,
                state,
                postcode,
                address_1,
                address_2,
                latitude,
                longitude).enqueue(new Callback<AddressSavedResponse>() {
            @Override
            public void onResponse(Call<AddressSavedResponse> call, Response<AddressSavedResponse> response) {
                dismissProgressIndicator();
                if (response.isSuccessful()){
//                    addAddressViewInterface.addressAddedResult(response.body());
                }
            }

            @Override
            public void onFailure(Call<AddressSavedResponse> call, Throwable t) {
                dismissProgressIndicator();
            }
        });
    }

    @Override
    public void getAddress(String userId, String addressId) {
        showProgressIndicator();
        ApiClient.getApiInterface().getAddress(userId, addressId).enqueue(new Callback<AddressResponse>() {
            @Override
            public void onResponse(Call<AddressResponse> call, Response<AddressResponse> response) {
                dismissProgressIndicator();
                if (response.isSuccessful()){
                    addAddressViewInterface.setAddress(response.body().getAddress());
                }
            }

            @Override
            public void onFailure(Call<AddressResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void checkPostcode(String postcode) {
        showProgressIndicator();
        ApiClient.getApiInterface().checkPostcode(postcode).enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                dismissProgressIndicator();
                if (response.isSuccessful()){
                    addAddressViewInterface.checkPostcodeResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                dismissProgressIndicator();
            }
        });
    }
}
