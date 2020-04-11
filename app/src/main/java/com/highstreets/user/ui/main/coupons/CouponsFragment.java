package com.highstreets.user.ui.main.coupons;

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

import com.highstreets.user.R;
import com.highstreets.user.adapters.CouponAdapter;
import com.highstreets.user.app_pref.GlobalPreferManager;
import com.highstreets.user.common.CommonViewInterface;
import com.highstreets.user.common.OnFragmentInteractionListener;
import com.highstreets.user.models.Coupon;
import com.highstreets.user.models.FavoriteCouponBooking;
import com.highstreets.user.ui.base.BaseFragment;
import com.highstreets.user.ui.SuccessDialogFragment;
import com.highstreets.user.ui.dialog_fragment.ProgressDialogFragment;
import com.highstreets.user.utils.CommonUtils;
import com.highstreets.user.utils.Constants;

import java.util.List;

public class CouponsFragment extends BaseFragment implements CouponsViewInterface, CouponAdapter.CouponAdapterCallback {

    private View view;
    private RecyclerView mCouponRecyclerView;
    private CouponAdapter mCouponAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private CouponsPresenterInterface couponsPresenterInterface;
    private ProgressDialogFragment progressDialogFragment = new ProgressDialogFragment();
    private String mUserId;
    private String mCity;
    private OnFragmentInteractionListener mListener;
    private CommonViewInterface mCommonListener;
    private TextView tvNoData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_coupons, container, false);
        mListener.setTitle("Super Deals");
        setHasOptionsMenu(true);
        mCouponRecyclerView = view.findViewById(R.id.coupon_recycler_view);
        tvNoData = view.findViewById(R.id.tvNoData);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mCouponRecyclerView.setLayoutManager(layoutManager);
        mCouponRecyclerView.setHasFixedSize(false);

        couponsPresenterInterface = new CouponsPresenter(this, getActivity());
        mUserId = GlobalPreferManager.getString(GlobalPreferManager.Keys.USER_ID, "");
        mCity = GlobalPreferManager.getString(GlobalPreferManager.Keys.GET_CITY_NAME, "");
        couponsPresenterInterface.getCoupons(mCity, mUserId);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }

        if (context instanceof CommonViewInterface){
            mCommonListener = (CommonViewInterface) context;
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResponseFailed(String message) {

    }

    @Override
    public void onServerError(String message) {
        tvNoData.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissProgressIndicator() {
        if (mCommonListener!=null) {
            mCommonListener.dismissProgressIndicator();
        }
    }

    @Override
    public void noInternet() {

    }

    @Override
    public void showProgressIndicator() {
        if (mCommonListener!=null){
            mCommonListener.showProgressIndicator();
        }
    }

    @Override
    public void setCoupons(List<Coupon> couponList) {
        mCouponAdapter = new CouponAdapter(getContext(), this, couponList);
        mCouponRecyclerView.setAdapter(mCouponAdapter);
        if (couponList.size() <= 0) {
            tvNoData.setVisibility(View.VISIBLE);
        } else {
            tvNoData.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFavoritesAdded(FavoriteCouponBooking favoriteCouponBooking) {
        if (favoriteCouponBooking.getStatus().equals(Constants.ERROR)) {
            CommonUtils.showToast(getActivity(), favoriteCouponBooking.getMessage());
        } else {
            couponsPresenterInterface.getCoupons(mCity, mUserId);
        }
    }

    @Override
    public void onBooked(FavoriteCouponBooking favoriteCouponBooking) {
        if (favoriteCouponBooking.getStatus().equals(Constants.ERROR)) {
            CommonUtils.showToast(getActivity(), favoriteCouponBooking.getMessage());
        } else {
            couponsPresenterInterface.getCoupons(mCity, mUserId);
            Bundle bundle = new Bundle();
            bundle.putString("message", "Your Coupon has been booked");
            SuccessDialogFragment dialogFragment = new SuccessDialogFragment();
            dialogFragment.setArguments(bundle);
            dialogFragment.show(getFragmentManager(), null);
        }
    }

    @Override
    public void addToFavorites(String couponId) {
        String userId = GlobalPreferManager.getString(GlobalPreferManager.Keys.USER_ID, "");
        couponsPresenterInterface.addFavoriteCoupon(userId, couponId);
    }

    @Override
    public void bookNow(String couponId) {
        String userId = GlobalPreferManager.getString(GlobalPreferManager.Keys.USER_ID, "");
        couponsPresenterInterface.couponBooking(userId, couponId);
    }

    @Override
    public void onRetry() {

    }
}
