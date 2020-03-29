package com.highstreets.user.ui.favourites;

import com.highstreets.user.common.CommonViewInterface;
import com.highstreets.user.models.favorites_model;

import java.util.List;

public interface FavoritesFragmentViewInterface extends CommonViewInterface {

    void onLoadingFavouriteSuccess(List<favorites_model> favoritesModels);

    void onLoadingFavouriteFailed(String message);

    void onDeletingFavouriteSuccess(String message);

    void onDeletingFavouriteFailed(String message);
}

