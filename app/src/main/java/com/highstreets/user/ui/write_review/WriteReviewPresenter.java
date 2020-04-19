package com.highstreets.user.ui.write_review;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.highstreets.user.api.ApiClient;
import com.highstreets.user.app_pref.SharedPrefs;
import com.highstreets.user.models.ReviewGet;
import com.highstreets.user.utils.Constants;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WriteReviewPresenter implements WriteReviewPresenterInterface {

    private WriteReviewViewInterface writeReviewViewInterface;

    public WriteReviewPresenter(WriteReviewViewInterface writeReviewViewInterface) {
        this.writeReviewViewInterface = writeReviewViewInterface;
    }

    @Override
    public void write_review(String user_id, String merchant_id, String name, String review, String rating) {
        showProgressIndicator();
        ApiClient.getApiInterface().write_review(user_id, merchant_id, name, review, rating).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    try {
                        JsonObject jsonObject = response.body();
                        if (jsonObject.get(Constants.STATUS).getAsString().equals(Constants.SUCCESS)) {
                            SharedPrefs.setString(SharedPrefs.Keys.WRITE_SHOP_REVIEW_ID, jsonObject.get("review_id").getAsString());
                            writeReviewViewInterface.onReviewSubmitSuccess("Successfully Submitted");
                            dismissProgressIndicator();
                        } else {
                            writeReviewViewInterface.onFailedToSubmitReview("Failed to Submit Review");
                            dismissProgressIndicator();
                        }
                    } catch (JsonIOException e) {
                        e.printStackTrace();
                    }
                } else {
                    dismissProgressIndicator();
                    writeReviewViewInterface.onResponseFailed(Constants.MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                dismissProgressIndicator();
            }
        });
    }

    @Override
    public void write_offer_review(String user_id, String merchant_id, String offer_id, String name, String review, String rating) {
        showProgressIndicator();
        ApiClient.getApiInterface().write_offer_review(user_id, merchant_id, offer_id, name, review, rating).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    try {
                        JsonObject jsonObject = response.body();
                        if (jsonObject.get(Constants.STATUS).getAsString().equals(Constants.SUCCESS)) {
                            SharedPrefs.setString(SharedPrefs.Keys.WRITE_OFFER_REVIEW_ID, jsonObject.get("review_id").getAsString());
                            writeReviewViewInterface.onReviewSubmitSuccess("Successfully Submitted");
                            dismissProgressIndicator();
                        } else {
                            writeReviewViewInterface.onFailedToSubmitReview("Failed to Submit Review");
                            dismissProgressIndicator();
                        }
                    } catch (JsonIOException e) {
                        e.printStackTrace();
                    }
                } else {
                    dismissProgressIndicator();
                    writeReviewViewInterface.onResponseFailed(Constants.MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                dismissProgressIndicator();
            }
        });
    }

    @Override
    public void read_reviews(String user_id, String merchant_id, String section) {
        showProgressIndicator();
        ApiClient.getApiInterface().read_reviews(user_id, merchant_id, section).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    try {
                        JsonObject jsonObject = response.body();
                        if (jsonObject.get(Constants.STATUS).getAsString().equals(Constants.SUCCESS)) {
                            JsonObject data = jsonObject.get(Constants.DATA).getAsJsonObject();

                            writeReviewViewInterface.onSuccessReadReviews(getReviews(data));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    writeReviewViewInterface.onFailedToSubmitReview("Failed");

                }
                dismissProgressIndicator();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                writeReviewViewInterface.onServerError(Constants.ERROR_MESSAGE_SERVER);
                dismissProgressIndicator();

            }
        });
    }

    private ReviewGet getReviews(JsonObject data) {
        Gson gson = new Gson();
        Type listType = new TypeToken<ReviewGet>() {
        }.getType();

        return gson.fromJson(data, listType);
    }

    @Override
    public void showProgressIndicator() {
        writeReviewViewInterface.showProgressIndicator();
    }

    @Override
    public void dismissProgressIndicator() {
        writeReviewViewInterface.dismissProgressIndicator();
    }


}


