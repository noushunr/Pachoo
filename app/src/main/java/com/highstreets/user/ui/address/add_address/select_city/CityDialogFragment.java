package com.highstreets.user.ui.address.add_address.select_city;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.highstreets.user.R;
import com.highstreets.user.ui.address.add_address.select_city.adapter.CityAdapter;
import com.highstreets.user.ui.address.add_address.select_city.model.City;
import com.highstreets.user.ui.base.BaseDialogFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CityDialogFragment extends BaseDialogFragment implements
        CityViewInterface,
        CityAdapter.SelectCity {

    private static final String PARAM_1 = "param_1";
    private CityAdapter.SelectCity selectCity;

    @BindView(R.id.rvCites)
    RecyclerView rvCites;

    public static CityDialogFragment newInstance(String districtId) {
        Bundle args = new Bundle();
        args.putString(PARAM_1, districtId);
        CityDialogFragment fragment = new CityDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View setView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city_dialog, container, false);
        ButterKnife.bind(this, view);
        selectCity = (CityAdapter.SelectCity) context;
        rvCites.setLayoutManager(new LinearLayoutManager(context));
        if (getArguments() != null){
            String districtId = getArguments().getString(PARAM_1);
            CityPresenterInterface cityPresenterInterface = new CityPresenter(this);
            cityPresenterInterface.getCity(districtId);
        }
        return view;
    }

    @Override
    public void setCityList(List<City> cityList) {
        rvCites.setAdapter(new CityAdapter(cityList, this));
    }

    @Override
    public void setCity(City city) {
        selectCity.setCity(city);
        dismiss();
    }
}
