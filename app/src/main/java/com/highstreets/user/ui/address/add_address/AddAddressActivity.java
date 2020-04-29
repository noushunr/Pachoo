package com.highstreets.user.ui.address.add_address;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.highstreets.user.app_pref.SharedPrefs;
import com.highstreets.user.ui.address.add_address.model.AddressSavedResponse;
import com.highstreets.user.ui.address.add_address.model.PostResponse;
import com.highstreets.user.ui.address.add_address.select_city.CityDialogFragment;
import com.highstreets.user.ui.address.add_address.select_city.adapter.CityAdapter;
import com.highstreets.user.ui.address.add_address.select_city.model.City;
import com.highstreets.user.ui.address.add_address.select_district.DistrictDialogFragment;
import com.highstreets.user.ui.address.add_address.select_district.adapter.DistrictAdapter;
import com.highstreets.user.ui.address.add_address.select_district.model.District;
import com.highstreets.user.ui.address.add_address.select_state.StateDialogFragment;
import com.highstreets.user.ui.address.add_address.select_state.adapter.StatesAdapter;
import com.highstreets.user.ui.address.add_address.select_state.model.State;
import com.highstreets.user.ui.address.model.Address;
import com.highstreets.user.ui.base.BaseActivity;
import com.highstreets.user.utils.CommonUtils;
import com.highstreets.user.utils.Constants;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddAddressActivity extends BaseActivity implements
        View.OnClickListener,
        AddAddressViewInterface,
        StatesAdapter.SelectState,
        DistrictAdapter.SelectDistrict,
        CityAdapter.SelectCity {

    private static final int AUTOCOMPLETE_REQUEST_CODE = 100;
    private static final String TAG = AddAddressActivity.class.getSimpleName();
    private AddAddressPresenterInterface addAddressPresenterInterface;
    private LatLng latLng;
    private State state;
    private District district;
    private City city;
    private boolean isEditAddress;
    private String userId;
    private String addressId;
    private boolean validPostcode;

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
    @BindView(R.id.tvPostcodeResult)
    TextView tvPostcodeResult;
    @BindView(R.id.btnPostcodeCheck)
    Button btnPostcodeCheck;
    @BindView(R.id.tvSelectPlace)
    TextView tvSelectPlace;
    @BindView(R.id.etAddressesLine)
    EditText etAddressesLine;
    @BindView(R.id.btnAddAddress)
    Button btnAddAddress;


    public static Intent start(Context context) {
        return new Intent(context, AddAddressActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tvToolbarText.setText(getString(R.string.add_address_first_caps));
        userId = SharedPrefs.getString(SharedPrefs.Keys.USER_ID, "");
        Log.e(TAG, "onCreate: "+ userId);
        addAddressPresenterInterface = new AddAddressPresenter(this);

        if (getIntent().getStringExtra(Constants.EDIT_ADDRESS_ID) != null){
            addressId = getIntent().getStringExtra(Constants.EDIT_ADDRESS_ID);
            isEditAddress = true;
            addAddressPresenterInterface.getAddress(userId, addressId);
        }

        tvSelectPlace.setOnClickListener(this);
        tvState.setOnClickListener(this);
        tvDistrict.setOnClickListener(this);
        tvCity.setOnClickListener(this);
        btnAddAddress.setOnClickListener(this);
        btnPostcodeCheck.setOnClickListener(this);

        btnAddAddress.setText(isEditAddress ? getString(R.string.save_address) : getString(R.string.add_address));


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
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                latLng = place.getLatLng();
                tvSelectPlace.setText(place.getName());
                Log.e(TAG, "onActivityResult latitude: " + latLng.latitude);
                Log.e(TAG, "onActivityResult longitude: " + latLng.longitude);
            }
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnAddAddress:{
                checkFields();
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
            case R.id.tvSelectPlace:{
                startAutocompleteActivity();
                break;
            }
            case R.id.btnPostcodeCheck:{
                if (TextUtils.isEmpty(etPostcode.getText())){
                    etPostcode.setError("Enter Postcode");
                    etPostcode.findFocus();
                } else {
                    addAddressPresenterInterface.checkPostcode(etPostcode.getText().toString());
                }
                break;
            }
        }
    }

    private void checkFields() {
        if (TextUtils.isEmpty(etFirstName.getText())) {
            etFirstName.setError("This field is required");
            etFirstName.findFocus();
        } else if (TextUtils.isEmpty(etLastName.getText())) {
            etLastName.setError("This field is required");
            etLastName.findFocus();
        } else if (TextUtils.isEmpty(etMobile.getText())) {
            etMobile.setError("This field is required");
            etMobile.findFocus();
        } else if (TextUtils.isEmpty(tvState.getText())) {
            tvState.setError("This field is required");
            tvState.findFocus();
        } else if (TextUtils.isEmpty(tvDistrict.getText())) {
            tvDistrict.setError("This field is required");
            tvDistrict.findFocus();
        } else if (TextUtils.isEmpty(tvCity.getText())) {
            tvCity.setError("This field is required");
            tvCity.findFocus();
        } else if (TextUtils.isEmpty(etPostcode.getText())) {
            etPostcode.setError("This field is required");
            etPostcode.findFocus();
        } else if (!validPostcode){
            etPostcode.setError("Check postcode");
            etPostcode.findFocus();
        } else if (TextUtils.isEmpty(tvSelectPlace.getText()) || latLng == null) {
            tvSelectPlace.setError("This field is required");
            tvSelectPlace.findFocus();
        } else if (TextUtils.isEmpty(etAddressesLine.getText())) {
            etAddressesLine.setError("This field is required");
            etAddressesLine.findFocus();
        } else {
            postData();
        }
    }

    private void postData() {
        if (isEditAddress){
            addAddressPresenterInterface.editAddress(
                    userId,
                    addressId,
                    etFirstName.getText().toString(),
                    etLastName.getText().toString(),
                    etMobile.getText().toString(),
                    tvDistrict.getText().toString(),
                    tvCity.getText().toString(),
                    tvState.getText().toString(),
                    etPostcode.getText().toString(),
                    tvSelectPlace.getText().toString(),
                    etAddressesLine.getText().toString(),
                    String.valueOf(latLng.latitude),
                    String.valueOf(latLng.longitude));
        } else {
            addAddressPresenterInterface.addAddress(
                    userId,
                    etFirstName.getText().toString(),
                    etLastName.getText().toString(),
                    etMobile.getText().toString(),
                    tvDistrict.getText().toString(),
                    tvCity.getText().toString(),
                    tvState.getText().toString(),
                    etPostcode.getText().toString(),
                    tvSelectPlace.getText().toString(),
                    etAddressesLine.getText().toString(),
                    String.valueOf(latLng.latitude),
                    String.valueOf(latLng.longitude));
        }
    }

    @Override
    public void setState(State state) {
        this.state = state;
        tvState.setText(state.getName());
        district = null;
        city = null;
        tvDistrict.setText("");
        tvCity.setText("");
    }

    @Override
    public void setDistrict(District district) {
        this.district = district;
        tvDistrict.setText(district.getName());
        city = null;
        tvCity.setText("");
    }

    @Override
    public void setCity(City city) {
        this.city = city;
        tvCity.setText(city.getCity());
    }

    @Override
    public void onResponseFailed(String message) {

    }

    @Override
    public void onServerError(String message) {

    }

    @Override
    public void dismissProgressIndicator() {
        dismissProgress();
    }

    @Override
    public void noInternet() {

    }

    @Override
    public void showProgressIndicator() {
        showProgress();
    }

    @Override
    public void setAddress(Address address) {
        if (address != null){
            etFirstName.setText(address.getFirstname());
            etLastName.setText(address.getLastname());
            etMobile.setText(address.getMobile());
            tvState.setText(getString(R.string.select_state));
            tvDistrict.setText(getString(R.string.select_county));
            district = new District();
            district.setId(address.getDistrict());
            tvCity.setText(getString(R.string.select_city));
            city = new City();
            city.setId(address.getCity());
            etPostcode.setText(address.getPostcode());
            tvSelectPlace.setText(getString(R.string.select_place));
            etAddressesLine.setText(address.getAddress2());
        }
    }

    @Override
    public void addressAddedResult(AddressSavedResponse addressSavedResponse) {
        if (addressSavedResponse.getStatus().equals(Constants.SUCCESS)) {
            CommonUtils.showToast(this, addressSavedResponse.getMessage());
        }
    }

    @Override
    public void setPostcodeResult(PostResponse postResponse) {
        tvPostcodeResult.setVisibility(View.VISIBLE);
        if (postResponse.getStatus().equals(Constants.ERROR)){
            tvPostcodeResult.setText(postResponse.getMessage());
            tvPostcodeResult.setTextColor(getResources().getColor(R.color.red));
            validPostcode = false;
        } else {
            tvPostcodeResult.setText(postResponse.getMessage());
            tvPostcodeResult.setTextColor(getResources().getColor(R.color.green));
            validPostcode = true;
        }
    }
}
