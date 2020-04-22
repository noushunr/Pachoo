package com.highstreets.user.ui.address.add_address.select_district;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.highstreets.user.R;
import com.highstreets.user.ui.address.add_address.select_district.adapter.DistrictAdapter;
import com.highstreets.user.ui.address.add_address.select_district.model.District;
import com.highstreets.user.ui.base.BaseDialogFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DistrictDialogFragment extends BaseDialogFragment implements
        DistrictViewInterface,
        DistrictAdapter.SelectDistrict {

    private static final String PARAM_1 = "param_1";
    private DistrictAdapter.SelectDistrict selectDistrict;

    @BindView(R.id.rvDistricts)
    RecyclerView rvDistricts;

    public static DistrictDialogFragment newInstance(String stateId) {
        Bundle args = new Bundle();
        args.putString(PARAM_1, stateId);
        DistrictDialogFragment fragment = new DistrictDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View setView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_district_dialog, container, false);
        ButterKnife.bind(this, view);
        selectDistrict = (DistrictAdapter.SelectDistrict) context;
        rvDistricts.setLayoutManager(new LinearLayoutManager(context));
        if (getArguments() != null) {
            String stateId = getArguments().getString(PARAM_1);
            DistrictPresenterInterface districtPresenterInterface = new DistrictPresenter(this);
            districtPresenterInterface.getDistrict(stateId);
        }
        return view;
    }

    @Override
    public void setDistrictList(List<District> districtList) {
        rvDistricts.setAdapter(new DistrictAdapter(districtList, this));
    }

    @Override
    public void setDistrict(District district) {
        selectDistrict.setDistrict(district);
        dismiss();
    }
}
