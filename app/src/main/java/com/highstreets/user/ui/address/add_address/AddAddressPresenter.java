package com.highstreets.user.ui.address.add_address;

import com.google.gson.JsonObject;
import com.highstreets.user.api.ApiClient;
import com.highstreets.user.ui.address.add_address.model.AddressResponse;
import com.highstreets.user.ui.address.add_address.model.AddressSavedResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    public void addAddress(String userId,
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
        ApiClient.getApiInterface().addAddress(
                userId,
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
                    addAddressViewInterface.addressAddedResult(response.body());
                }
            }

            @Override
            public void onFailure(Call<AddressSavedResponse> call, Throwable t) {
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
                    addAddressViewInterface.addressAddedResult(response.body());
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
}