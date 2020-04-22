package com.highstreets.user.ui.address.add_address;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.highstreets.user.R;
import com.highstreets.user.ui.address.add_address.select_city.CityDialogFragment;
import com.highstreets.user.ui.address.add_address.select_city.adapter.CityAdapter;
import com.highstreets.user.ui.address.add_address.select_city.model.City;
import com.highstreets.user.ui.address.add_address.select_district.DistrictDialogFragment;
import com.highstreets.user.ui.address.add_address.select_district.adapter.DistrictAdapter;
import com.highstreets.user.ui.address.add_address.select_district.model.District;
import com.highstreets.user.ui.address.add_address.select_state.StateDialogFragment;
import com.highstreets.user.ui.address.add_address.select_state.adapter.StatesAdapter;
import com.highstreets.user.ui.address.add_address.select_state.model.State;
import com.highstreets.user.ui.base.BaseActivity;
import com.highstreets.user.utils.CommonUtils;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddAddressActivity extends BaseActivity implements
        View.OnClickListener,
        StatesAdapter.SelectState,
        DistrictAdapter.SelectDistrict,
        CityAdapter.SelectCity {

    private static final int AUTOCOMPLETE_REQUEST_CODE = 100;
    private LatLng latLng;
    private State state;
    private District district;
    private City city;

    @BindView(R.id.tvToolbarText)
    TextView tvToolbarText;
    @BindView(R.id.etFirstName)
    EditText etFirstName;
    @BindView(R.id.etLastName)
    EditText etLastName;
    @BindView(R.id.etMobile)
    EditText etMobile;
    @BindView(R.id.tvDistrict)
    TextView tvDistrict;
    @BindView(R.id.tvCity)
    TextView tvCity;
    @BindView(R.id.tvState)
    TextView tvState;
    @BindView(R.id.etPostcode)
    EditText etPostcode;
    @BindView(R.id.tvSelectPlace)
    TextView tvSelectPlace;
    @BindView(R.id.etAddressesLine)
    EditText etAddressesLine;
    @BindView(R.id.btnAddAddress)
    Button btnAddAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        tvToolbarText.setText(getString(R.string.add_address_first_caps));

        tvSelectPlace.setOnClickListener(this);
        tvState.setOnClickListener(this);
        tvDistrict.setOnClickListener(this);
        tvCity.setOnClickListener(this);

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getString(R.string.google_android_key));
        }


    }

    @Override
    protected boolean setToolbar() {
        return true;
    }

    @Override
    public int setLayout() {
        return R.layout.activity_add_address;
    }

    @Override
    public void reloadPage() {

    }

    private void startAutocompleteActivity() {
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);
        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.FULLSCREEN, fields)
                .build(this);
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                latLng = place.getLatLng();
                tvSelectPlace.setText(place.getName());
            }
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnAddAddress:{
                break;
            }
            case R.id.tvState:{
                StateDialogFragment.newInstance().show(getSupportFragmentManager(), null);
                break;
            }
            case R.id.tvDistrict:{
                if (state != null){
                    DistrictDialogFragment.newInstance(state.getId()).show(getSupportFragmentManager(), null);
                } else {
                    CommonUtils.showToast(this, "Select a state");
                }
                break;
            }
            case R.id.tvCity:{
                if (district != null){
                    CityDialogFragment.newInstance(district.getId()).show(getSupportFragmentManager(), null);
                } else {
                    CommonUtils.showToast(this, "Select a county");
                }
                break;
            }
        }
    }

    @Override
    public void setState(State state) {
        this.state = state;
        tvState.setText(state.getName());
    }

    @Override
    public void setDistrict(District district) {
        this.district = district;
        tvDistrict.setText(district.getName());
    }

    @Override
    public void setCity(City city) {
        this.city = city;
        tvCity.setText(city.getCity());
    }
}
