package com.presentation.app.dealsnest.ui.select_location;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.presentation.app.dealsnest.R;
import com.presentation.app.dealsnest.adapters.PopularCitiesAdapter;
import com.presentation.app.dealsnest.app_pref.GlobalPreferManager;
import com.presentation.app.dealsnest.models.location_model;
import com.presentation.app.dealsnest.ui.BaseActivity;
import com.presentation.app.dealsnest.utils.CommonUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class SelectLocationActivity extends BaseActivity implements PopularCitiesAdapter.OnItemClickListener,
        SelectLocationViewInterface,
        View.OnClickListener,
        LocationListener {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public static final int AUTOCOMPLETE_REQUEST_CODE = 1001;
    public static final int LOCATION_REQUEST_CODE = 100;
    private static final String TAG = SelectLocationActivity.class.getSimpleName();
    private RecyclerView mListPopularCities;
    private Button btnCurrentLocation;
    private AutoCompleteTextView mAutocompleteTextView;
    private LocationManager mLocationManager;
    private Geocoder geocoder;
    private PopularCitiesAdapter popularCitiesAdapter;
    private TextView tvToolbarTitle;
    private SelectLocationPresenter selectLocationPresenter;
    private double lat, lng;
    private String SELECTED_CITY, CITY_LATITUDE, CITY_LONGITUDE, district;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tvToolbarTitle = findViewById(R.id.tvToolbarText);
        tvToolbarTitle.setText("Select Location");
        String apiKey = getString(R.string.google_android_key);

        if (apiKey.equals("")) {
            Toast.makeText(this, getString(R.string.error_api_key), Toast.LENGTH_LONG).show();
            return;
        }

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }

        GlobalPreferManager.initializePreferenceManager(getApplicationContext());
        selectLocationPresenter = new SelectLocationPresenter(this);
        getPopularCities();
        geocoder = new Geocoder(this, Locale.getDefault());

        btnCurrentLocation = findViewById(R.id.button_current_location);
        mAutocompleteTextView = findViewById(R.id.edit_location_name);
        btnCurrentLocation.setOnClickListener(this);
        mListPopularCities = findViewById(R.id.location_list_recyclerView);
        mListPopularCities.setLayoutManager(new LinearLayoutManager(this));

        mAutocompleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAutocompleteActivity();
            }
        });
    }

    @Override
    protected boolean setToolbar() {
        return true;
    }

    @Override
    public int setLayout() {
        return R.layout.activity_select_location;
    }

    @Override
    public void reloadPage() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.search_close) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void startAutocompleteActivity() {
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);
        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.FULLSCREEN, fields)
                .build(this);
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
    }

    private void getPopularCities() {
        selectLocationPresenter.cities();
    }


    @Override
    public void onLocationSelectionFailed(String message) {
        CommonUtils.showToast(this, message);
    }

    @Override
    public void onLocationSelectionSuccess(List<location_model> locationModelList) {
        popularCitiesAdapter = new PopularCitiesAdapter(this, locationModelList);
        mListPopularCities.setAdapter(popularCitiesAdapter);
    }

    @Override
    public void onResponseFailed(String message) {
        CommonUtils.showToast(this, message);
    }

    @Override
    public void onServerError(String message) {
        CommonUtils.showToast(this, message);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_current_location:
                requestLocationPermission();
                break;
        }
    }

    private void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(SelectLocationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            EnableGPSAutomatically();
            return;
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(SelectLocationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)) {

        }
        ActivityCompat.requestPermissions(SelectLocationActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EnableGPSAutomatically();
    }

    private void EnableGPSAutomatically() {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        builder.setAlwaysShow(true);
        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi
                .checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result
                        .getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        getYourLocation();
                        showProgress();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            status.startResolutionForResult(SelectLocationActivity.this, LOCATION_REQUEST_CODE);
                        } catch (IntentSender.SendIntentException e) {
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:

                        break;
                }
            }
        });
    }

    private void getYourLocation() {
        try {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 5, this);
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 5, this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        List<Address> addresses;
        try {
            dismissProgress();
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            SELECTED_CITY = addresses.get(0).getSubAdminArea();
            CITY_LATITUDE = String.valueOf(latitude);
            CITY_LONGITUDE = String.valueOf(longitude);
            mLocationManager.removeUpdates(this);
            Intent intent = new Intent();
            intent.putExtra("city", SELECTED_CITY);
            intent.putExtra("lat", CITY_LATITUDE);
            intent.putExtra("lon", CITY_LONGITUDE);
            setResult(RESULT_OK, intent);
            finish();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onItemClick(String city_id, String city_name, String lat, String lon) {
        callBackResult(city_id, city_name, lat, lon);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                lat = place.getLatLng().latitude;
                lng = place.getLatLng().longitude;
                CITY_LATITUDE = String.valueOf(lat);
                CITY_LONGITUDE = String.valueOf(lng);
                try {
                    List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
                    if (addresses.get(0).getSubAdminArea() != null) {
                        district = addresses.get(0).getSubAdminArea();
                    } else {
                        district = place.getName();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                callBackResult(place.getId(), district, String.valueOf(place.getLatLng().latitude), String.valueOf(place.getLatLng().longitude));
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.e(TAG, status.getStatusMessage());
            }
        } else if (requestCode == LOCATION_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                getYourLocation();
            }
        }
    }

    private void callBackResult(String cityId, String cityName, String lat, String lng) {
        Intent i = new Intent();
        i.putExtra("city", cityName);
        i.putExtra("city_id", cityId);
        i.putExtra("lat", lat);
        i.putExtra("lon", lng);
        setResult(Activity.RESULT_OK, i);
        finish();
    }
}
