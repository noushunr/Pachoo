package com.highstreets.user.ui.booked;

import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.highstreets.user.R;
import com.highstreets.user.adapters.BookedAdapter;
import com.highstreets.user.app_pref.GlobalPreferManager;
import com.highstreets.user.common.CommonViewInterface;
import com.highstreets.user.common.CustomItemClickListener;
import com.highstreets.user.common.OnFragmentInteractionListener;
import com.highstreets.user.models.BookedOffers;
import com.highstreets.user.ui.BaseFragment;
import com.highstreets.user.ui.dialog_fragment.ProgressDialogFragment;

import java.util.List;


public class BookedFragment extends BaseFragment implements BookedFragmentViewInterface {
    private View view;
    private RecyclerView mBookedRecyclerView;
    private BookedAdapter mBookedAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ProgressDialogFragment progressDialogFragment;
    private ActionBar toolbar;
    private TextView tvNoData;
    private BookedFragmentPresenter bookedFragmentPresenter;
    private String USER_ID;
    private OnFragmentInteractionListener mListener;
    private CommonViewInterface mCommonListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        bookedFragmentPresenter = new BookedFragmentPresenter(getActivity(), this);
        USER_ID = GlobalPreferManager.getString(GlobalPreferManager.Keys.USER_ID, "");
        progressDialogFragment = new ProgressDialogFragment();
        view = inflater.inflate(R.layout.fragment_booked, container, false);
        initView();
        getBookedOffer(USER_ID);
        return view;
    }

    private void getBookedOffer(String user_id) {
        bookedFragmentPresenter.getBookedOffers(user_id);
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.home_search, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onLoadingBookedOffersSuccess(final List<BookedOffers> booked_modelList) {
        mBookedAdapter = new BookedAdapter(getContext(), booked_modelList, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
            }
        });
        mBookedRecyclerView.setAdapter(mBookedAdapter);
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