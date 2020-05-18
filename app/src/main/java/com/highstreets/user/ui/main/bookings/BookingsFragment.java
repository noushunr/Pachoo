package com.highstreets.user.ui.main.bookings;

import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.highstreets.user.R;
import com.highstreets.user.adapters.BookingsAdapter;
import com.highstreets.user.app_pref.SharedPrefs;
import com.highstreets.user.common.CommonViewInterface;
import com.highstreets.user.common.OnFragmentInteractionListener;
import com.highstreets.user.models.BookedOffers;
import com.highstreets.user.ui.base.BaseFragment;
import com.highstreets.user.ui.dialog_fragment.ProgressDialogFragment;

import java.util.List;


public class BookingsFragment extends BaseFragment implements BookingsViewInterface {
    private View view;
    private RecyclerView mBookedRecyclerView;
    private BookingsAdapter mBookingsAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ProgressDialogFragment progressDialogFragment;
    private ActionBar toolbar;
    private TextView tvNoData;
    private BookingsPresenter bookingsPresenter;
    private String USER_ID;
    private OnFragmentInteractionListener mListener;
    private CommonViewInterface mCommonListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        bookingsPresenter = new BookingsPresenter(getActivity(), this);
        USER_ID = SharedPrefs.getString(SharedPrefs.Keys.USER_ID, "");
        progressDialogFragment = new ProgressDialogFragment();
        view = inflater.inflate(R.layout.fragment_booked, container, false);
        initView();
        getBookedOffer(USER_ID);
        return view;
    }

    private void getBookedOffer(String user_id) {
        bookingsPresenter.getBookedOffers(user_id);
    }

    private void initView() {
        mListener.setTitle("Bookings");
        mBookedRecyclerView = view.findViewById(R.id.booked_recyclerView);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mBookedRecyclerView.setLayoutManager(layoutManager);
        tvNoData = view.findViewById(R.id.tvNoData);
        mBookedRecyclerView.setHasFixedSize(false);
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
        mListener = null;
    }


    @Override
    public void onLoadingBookedOffersSuccess(final List<BookedOffers> booked_modelList) {
        mBookingsAdapter = new BookingsAdapter(getContext(), booked_modelList);
        mBookedRecyclerView.setAdapter(mBookingsAdapter);
        if (booked_modelList.size() <= 0) {
            tvNoData.setVisibility(View.VISIBLE);
        } else {
            tvNoData.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoadingBookedOffersFailed(String message) {

    }

    @Override
    public void onResponseFailed(String message) {

    }

    @Override
    public void onServerError(String message) {

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
    public void onRetry() {
        getBookedOffer(USER_ID);
    }
}
