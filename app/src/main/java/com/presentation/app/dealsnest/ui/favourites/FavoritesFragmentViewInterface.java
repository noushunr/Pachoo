package com.presentation.app.dealsnest.ui.favourites;

import com.presentation.app.dealsnest.common.CommonViewInterface;
import com.presentation.app.dealsnest.models.favorites_model;

import java.util.List;

public interface FavoritesFragmentViewInterface extends CommonViewInterface {

    void onLoadingFavouriteSuccess(List<favorites_model> favoritesModels);

    void onLoadingFavouriteFailed(String message);

    void onDeletingFavouriteSuccess(String message);

    void onDeletingFavouriteFailed(String message);
}

