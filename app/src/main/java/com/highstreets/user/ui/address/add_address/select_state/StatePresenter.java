package com.highstreets.user.ui.address.add_address.select_state;

import com.highstreets.user.api.ApiClient;
import com.highstreets.user.ui.address.add_address.select_state.model.StatesResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatePresenter implements StatePresenterInterface {

    private StateViewInterface stateViewInterface;

    public StatePresenter(StateViewInterface stateViewInterface) {
        this.stateViewInterface = stateViewInterface;
    }

    @Override
    public void getStates() {
        ApiClient.getApiInterface().getStates().enqueue(new Callback<StatesResponse>() {
            @Override
            public void onResponse(Call<StatesResponse> call, Response<StatesResponse> response) {
                if (response.isSuccessful()) {
                    stateViewInterface.setStateList(response.body().getState());
                }
            }

            @Override
            public void onFailure(Call<StatesResponse> call, Throwable t) {

            }
        });
    }
}
