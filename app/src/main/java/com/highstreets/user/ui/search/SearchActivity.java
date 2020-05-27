package com.highstreets.user.ui.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.highstreets.user.R;
import com.highstreets.user.adapters.SearchListAdapter;
import com.highstreets.user.app_pref.SharedPrefs;
import com.highstreets.user.models.SearchItem;
import com.highstreets.user.ui.base.BaseActivity;
import com.highstreets.user.utils.CommonUtils;

import java.util.Arrays;
import java.util.List;

import static com.highstreets.user.ui.select_location.SelectLocationActivity.AUTOCOMPLETE_REQUEST_CODE;

public class SearchActivity extends BaseActivity implements View.OnClickListener, SearchActivityViewInterface {

    private static final String TAG = SearchActivity.class.getSimpleName();
    private EditText edSearchItem;
    private TextView edit_near_me;
    private TextView tvToolbar;
    private RecyclerView rvSearchList;
    private RecyclerView.LayoutManager layoutManager;
    private SearchActivityPresenterInterface searchActivityPresenterInterface;
    private SearchListAdapter searchListAdapter;
    private String NEAR_ME_HOLDER, SEARCH_LOCALITIES_HOLDER, CITY_NAME, LATITUDE, LONGITUDE;

    public static Intent getActivityIntent(Context context) {
        return new Intent(context, SearchActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchActivityPresenterInterface = new SearchActivityPresenter(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();

        String apiKey = getString(R.string.google_android_key);

        if (apiKey.equals("")) {
            Toast.makeText(this, getString(R.string.error_api_key), Toast.LENGTH_LONG).show();
            return;
        }

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }
    }


    private void startAutocompleteActivity() {
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);
        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.FULLSCREEN, fields)
                .build(this);
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
    }

    private void initView() {

        NEAR_ME_HOLDER = SharedPrefs.getString(SharedPrefs.Keys.GET_CITY_NAME, "");
        edit_near_me = findViewById(R.id.edit_near_me);
        edSearchItem = findViewById(R.id.edit_search_localities);
        tvToolbar = findViewById(R.id.tvToolbarText);
        tvToolbar.setText("Search");

        rvSearchList = findViewById(R.id.search_filter);
        layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rvSearchList.setLayoutManager(layoutManager);
        rvSearchList.setHasFixedSize(false);

        edit_near_me.setOnClickListener(this);
        edSearchItem.setOnClickListener(this);
        edSearchItem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchActivityPresenterInterface.get_search_filter_list(CITY_NAME, s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    @Override
    protected boolean setToolbar() {
        return true;
    }

    @Override
    public int setLayout() {
        return R.layout.activity_search;
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
        switch (item.getItemId()) {
            case R.id.search_close:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_near_me:
                startAutocompleteActivity();
                break;
            case R.id.edit_search_localities:
                if (TextUtils.isEmpty(edit_near_me.getText())) {
                    CommonUtils.showToast(this,"please select a city or area");
                    edSearchItem.setEnabled(false);
                    edSearchItem.setFocusable(false);
                } else {
                    edSearchItem.setEnabled(true);
                    SEARCH_LOCALITIES_HOLDER = edSearchItem.getText().toString();
                    if (SEARCH_LOCALITIES_HOLDER.isEmpty()) {
                        edSearchItem.setError("Enter something you want to search");
                        edSearchItem.setFocusable(true);
                    }
                }

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.e(TAG, "Place: " + place.getName() + ", " + place.getId());
                CITY_NAME = place.getName();
                LATITUDE = String.valueOf(place.getLatLng().latitude);
                LONGITUDE = String.valueOf(place.getLatLng().longitude);
                edit_near_me.setText(place.getName());
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.e(TAG, status.getStatusMessage());
            }
        }
    }

    @Override
    public void onResponseFailed(String message) {

    }

    @Override
    public void onServerError(String message) {

    }

    @Override
    public void dismissProgressIndicator() {

    }

    @Override
    public void noInternet() {

    }

    @Override
    public void showProgressIndicator() {

    }

    @Override
    public void onSearchListFilterSuccess(List<SearchItem> searchListModelList) {
        searchListAdapter = new SearchListAdapter(this, searchListModelList);
        rvSearchList.setAdapter(searchListAdapter);
    }
}
