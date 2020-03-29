package com.highstreets.user.ui.help;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.highstreets.user.api.ApiClient;
import com.highstreets.user.models.Help;
import com.highstreets.user.utils.Constants;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HelpActivityPresenter implements HelpActivityPresenterInterface {

    private HelpActivityViewInterface helpActivityViewInterface;

    public HelpActivityPresenter(HelpActivityViewInterface helpActivityViewInterface) {
        this.helpActivityViewInterface = helpActivityViewInterface;
    }

    @Override
    public void getHelp() {
        showProgressIndicator();
        ApiClient.getApiInterface().get_help().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    try {
                        JsonObject jsonObject = response.body();
                        if (jsonObject.get(Constants.STATUS).getAsString().equals(Constants.SUCCESS)) {
                            JsonArray data = jsonObject.get(Constants.DATA).getAsJsonArray();
                            ArrayList<Help> getHelp = new Gson().fromJson(data, new TypeToken<ArrayList<Help>>() {
                            }.getType());
                            helpActivityViewInterface.onLoadingHelpSuccess(getHelp);
                            dismissProgressIndicator();
                        } else {
                            helpActivityViewInterface.onLoadingHelpFailed(jsonObject.get(Constants.MESSAGE).getAsString());
                            dismissProgressIndicator();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    dismissProgressIndicator();
                    helpActivityViewInterface.onResponseFailed("Failed");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                dismissProgressIndicator();
                helpActivityViewInterface.onServerError(Constants.ERROR_MESSAGE_SERVER);
            }
        });
    }

    @Override
    public void showProgressIndicator() {
        helpActivityViewInterface.showProgressIndicator();
    }

    @Override
    public void dismissProgressIndicator() {
        helpActivityViewInterface.dismissProgressIndicator();
    }
}
