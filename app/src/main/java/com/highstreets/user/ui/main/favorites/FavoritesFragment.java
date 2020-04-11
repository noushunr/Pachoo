package com.highstreets.user.ui.main.favorites;

import android.content.Context;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.highstreets.user.R;
import com.highstreets.user.adapters.FavoritesAdapter;
import com.highstreets.user.app_pref.GlobalPreferManager;
import com.highstreets.user.common.CommonViewInterface;
import com.highstreets.user.common.CustomItemClickListener;
import com.highstreets.user.common.OnFragmentInteractionListener;
import com.highstreets.user.models.favorites_model;
import com.highstreets.user.ui.base.BaseFragment;

import java.util.List;

public class FavoritesFragment extends BaseFragment implements FavoritesFragmentViewInterface {
    private View view;
    private RecyclerView mFavoritesRecyclerView;
    private FavoritesAdapter mFavoritesAdapter;
    private FavoritesFragmentPresenter favoritesFragmentPresenter;
    private Context mContext;
    private RecyclerView.LayoutManager layoutManager;
    private LottieAnimationView progressFavorites;
    private TextView tvNoData;
    private String USER_ID, FAV_ID;
    private OnFragmentInteractionListener mListener;
    private CommonViewInterface mCommonListener;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_favorites, container, false);

        favoritesFragmentPresenter = new FavoritesFragmentPresenter(mContext, this);
        setHasOptionsMenu(true);
        GlobalPreferManager.initializePreferenceManager(getContext());
        USER_ID = GlobalPreferManager.getString(GlobalPreferManager.Keys.USER_ID, "");

        initView();
        getFavourites(USER_ID);
        return view;
    }

    private void getFavourites(String user_id) {
        favoritesFragmentPresenter.getFavouriteShop(user_id);
    }

    private void initView() {
        mListener.setTitle("Favorites");
        mFavoritesRecyclerView = view.findViewById(R.id.favorite_recyclerView);
        progressFavorites = view.findViewById(R.id.progressFavorites);
        tvNoData = view.findViewById(R.id.tvNoData);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mFavoritesRecyclerView.setLayoutManager(layoutManager);
        mFavoritesRecyclerView.setHasFixedSize(false);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onLoadingFavouriteSuccess(final List<favorites_model> favoritesModels) {
        mFavoritesAdapter = new FavoritesAdapter(getContext(), favoritesModels, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                if (position != RecyclerView.NO_POSITION) {
                    favorites_model favoritesModel = favoritesModels.get(position);
                    FAV_ID = favoritesModel.getFavId();
                    favoritesFragmentPresenter.deleteFavouriteShop(FAV_ID);
                }
            }
        });
        mFavoritesRecyclerView.setAdapter(mFavoritesAdapter);

        if (favoritesModels.size() <= 0) {
            tvNoData.setVisibility(View.VISIBLE);
        } else {
            tvNoData.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoadingFavouriteFailed(String message) {
        tvNoData.setVisibility(View.VISIBLE);
        if (mFavoritesAdapter != null)
            mFavoritesAdapter.clear();
    }

    @Override
    public void onDeletingFavouriteSuccess(String message) {
        getFavourites(USER_ID);
    }

    @Override
    public void onDeletingFavouriteFailed(String message) {

    }

    @Override
    public void onResponseFailed(String message) {

    }

    @Override
    public void onServerError(String message) {

    }

    @Override
    public void dismissProgressIndicator() {
        progressFavorites.setVisibility(View.INVISIBLE);
    }

    @Override
    public void noInternet() {

    }

    @Override
    public void showProgressIndicator() {
        progressFavorites.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRetry() {
        getFavourites(USER_ID);
    }
}
