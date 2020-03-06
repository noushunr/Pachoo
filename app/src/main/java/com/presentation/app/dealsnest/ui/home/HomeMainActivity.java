package com.presentation.app.dealsnest.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.presentation.app.dealsnest.R;
import com.presentation.app.dealsnest.api.ApiClient;
import com.presentation.app.dealsnest.app_pref.GlobalPreferManager;
import com.presentation.app.dealsnest.common.CommonViewInterface;
import com.presentation.app.dealsnest.common.OnFragmentInteractionListener;
import com.presentation.app.dealsnest.ui.BaseActivity;
import com.presentation.app.dealsnest.ui.ReferAFriendActivity;
import com.presentation.app.dealsnest.ui.booked.BookedFragment;
import com.presentation.app.dealsnest.ui.categories.CategoriesFragment;
import com.presentation.app.dealsnest.ui.coupons.CouponsFragment;
import com.presentation.app.dealsnest.ui.dialog_fragment.LogoutDialogFragment;
import com.presentation.app.dealsnest.ui.dialog_fragment.PlayStoreUpdateDialogFragment;
import com.presentation.app.dealsnest.ui.dialog_fragment.ProgressDialogFragment;
import com.presentation.app.dealsnest.ui.favourites.FavoritesFragment;
import com.presentation.app.dealsnest.ui.help.HelpActivity;
import com.presentation.app.dealsnest.ui.login_registration.LoginActivity;
import com.presentation.app.dealsnest.ui.notification.NotificationActivity;
import com.presentation.app.dealsnest.ui.profile.ProfileFragment;
import com.presentation.app.dealsnest.ui.search.SearchActivity;
import com.presentation.app.dealsnest.utils.Constants;
import com.presentation.app.dealsnest.utils.GooglePlayAppVersion;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeMainActivity extends BaseActivity
        implements OnFragmentInteractionListener,
        CommonViewInterface,
        View.OnClickListener,
        LocationListener {

    private Toolbar toolbar;
    private BottomNavigationView mBottomNavigationView;
    private ProgressDialogFragment progressDialogFragment;
    private DrawerLayout mDrawer;
    private TextView tvToolBarText;
    private LinearLayout llMyProfile;
    private LinearLayout llMyBooking;
    private LinearLayout llMyFavorites;
    private LinearLayout llReferAFriend;
    private LinearLayout llNotification;
    private LinearLayout llRateApp;
    private LinearLayout llHelp;
    private LinearLayout llLogout;
    private TextView tvNavFirstName;
    private LocationChange mLocationChange;
    private String tokenId;
    private String mUserID;
    private Geocoder geocoder;
    private String SELECTED_CITY;

    private HashMap<String, Fragment> mFragmentHashMap = new HashMap<>();
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            invalidateOptionsMenu();
            switch (menuItem.getItemId()) {
                case R.id.navigation_home:
                    loadFragment(mFragmentHashMap.get(Constants.TAG_HOME_FRAGMENT));
                    return true;
                case R.id.navigation_categories:
                    loadFragment(mFragmentHashMap.get(Constants.TAG_CATEGORIES_FRAGMENT));
                    return true;
                case R.id.navigation_coupons:
                    loadFragment(mFragmentHashMap.get(Constants.TAG_COUPONS_FRAGMENT));
                    return true;
                case R.id.navigation_favorites:
                    if (GlobalPreferManager.getString(GlobalPreferManager.Keys.USER_ID, "").equals("")) {
                        toLogin();
                    } else {
                        loadFragment(mFragmentHashMap.get(Constants.TAG_FAVORITES_FRAGMENT));
                    }
                    return true;
                case R.id.navigation_booked:
                    if (GlobalPreferManager.getString(GlobalPreferManager.Keys.USER_ID, "").equals("")) {
                        toLogin();
                    } else {
                        loadFragment(mFragmentHashMap.get(Constants.TAG_BOOKED_FRAGMENT));
                    }
                    return true;
            }
            return false;
        }
    };

    public static Intent getActivityIntent(Context context) {
        return new Intent(context, HomeMainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        toolbar = findViewById(R.id.toolbar);
        mUserID = GlobalPreferManager.getString(GlobalPreferManager.Keys.USER_ID, "");
        try {
            tokenId = FirebaseInstanceId.getInstance().getToken();
        } catch (Exception e) {
            e.printStackTrace();
        }


        addDeviceToken(mUserID, tokenId, "1");
        initiateFragments();
        mBottomNavigationView = findViewById(R.id.navigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//        mBottomNavigationView.setItemIconTintList(null);
        tvToolBarText = findViewById(R.id.tvToolbarText);
        tvToolBarText.setText("Home");
        mDrawer = findViewById(R.id.drawer_layout);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();
        initViews();

        String fromProfile = getIntent().getStringExtra(Constants.FROM_PROFILE);
        if (fromProfile != null) {
            if (fromProfile.equals(Constants.OPEN_PROFILE)) {
                loadFragment(mFragmentHashMap.get(Constants.TAG_PROFILE_FRAGMENT));
            }
        }

        if (!GlobalPreferManager.getBoolean(GlobalPreferManager.Keys.IS_LOGIN, false)) {
            llLogout.setVisibility(View.GONE);
        }

        mLocationChange = (LocationChange) mFragmentHashMap.get(Constants.TAG_HOME_FRAGMENT);

        loadFragment(mFragmentHashMap.get(Constants.TAG_HOME_FRAGMENT));
        mBottomNavigationView.setSelectedItemId(R.id.navigation_home);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkNewBuildAvailable();
    }


    public void addDeviceToken(String user_id, String token, String type) {
        ApiClient.getApiInterface().addTokens(user_id, token, type).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.isSuccessful()) {
                    try {
                        JsonObject jsonObject = response.body();
                        if (jsonObject.get(Constants.STATUS).getAsString().equals(Constants.SUCCESS)) {
                            Log.e("Status", jsonObject.get("message").getAsString());
                        } else {
                            Log.e("Status", jsonObject.get("message").getAsString());
                        }
                    } catch (JsonIOException e) {
                        e.printStackTrace();
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {
        if (getApplicationContext() == null)
            return;
        geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        List<Address> addresses;
        try {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            SELECTED_CITY = addresses.get(0).getLocality();
            GlobalPreferManager.setBoolean(GlobalPreferManager.Keys.IS_LOCATED, true);
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame_container);
            if (fragment instanceof HomeFragment) {
                mLocationChange.onLocationChanged(SELECTED_CITY);
            }

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

    private void initViews() {
        llMyProfile = findViewById(R.id.llMyProfile);
        llMyBooking = findViewById(R.id.llMyBooking);
        llMyFavorites = findViewById(R.id.llMyFavorites);
        llReferAFriend = findViewById(R.id.llReferAFriend);
        llNotification = findViewById(R.id.llNotification);
        llRateApp = findViewById(R.id.llRateApp);
        llHelp = findViewById(R.id.llHelp);
        llLogout = findViewById(R.id.llLogout);
        tvNavFirstName = findViewById(R.id.tvNavFirstName);

        String drawerName = "Hello " + GlobalPreferManager.getString(GlobalPreferManager.Keys.USER_FIRST_NAME, "");

        tvNavFirstName.setText(drawerName);
        llMyProfile.setOnClickListener(this);
        llMyBooking.setOnClickListener(this);
        llMyFavorites.setOnClickListener(this);
        llReferAFriend.setOnClickListener(this);
        llNotification.setOnClickListener(this);
        llRateApp.setOnClickListener(this);
        llHelp.setOnClickListener(this);
        llLogout.setOnClickListener(this);
    }

    @Override
    public int setLayout() {
        return R.layout.activity_home_main;
    }

    @Override
    public void reloadPage() {
        startActivity(HomeMainActivity.getActivityIntent(this));
        finish();
    }

    @Override
    public boolean setToolbar() {
        return true;
    }

    private void initiateFragments() {
        mFragmentHashMap.put(Constants.TAG_HOME_FRAGMENT, new HomeFragment());
        mFragmentHashMap.put(Constants.TAG_CATEGORIES_FRAGMENT, new CategoriesFragment());
        mFragmentHashMap.put(Constants.TAG_COUPONS_FRAGMENT, new CouponsFragment());
        mFragmentHashMap.put(Constants.TAG_FAVORITES_FRAGMENT, new FavoritesFragment());
        mFragmentHashMap.put(Constants.TAG_BOOKED_FRAGMENT, new BookedFragment());
        mFragmentHashMap.put(Constants.TAG_PROFILE_FRAGMENT, new ProfileFragment());
    }

    private void toLogin() {
        Intent toLoginIntent = LoginActivity.getActivityIntent(this);
        toLoginIntent.putExtra(Constants.FROM_INSIDE, Constants.FROM_INSIDE);
//        toLoginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(toLoginIntent);

    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        try {
            transaction.commit();
        } catch (IllegalStateException e) {
            transaction.commitAllowingStateLoss();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_search, menu);
        MenuItem menuItem = menu.findItem(R.id.navigation_searching);
        if (getSupportFragmentManager().findFragmentById(R.id.frame_container) instanceof HomeFragment)
            menuItem.setVisible(true);
        else
            menuItem.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_searching:
                startActivity(new Intent(HomeMainActivity.this, SearchActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame_container);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (!(fragment instanceof HomeFragment)) {
            loadFragment(new HomeFragment());
            mBottomNavigationView.setSelectedItemId(R.id.navigation_home);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void setTitle(String title) {
        tvToolBarText.setText(title);
    }

    @Override
    public void onResponseFailed(String message) {

    }

    @Override
    public void onServerError(String message) {

    }

    @Override
    public void dismissProgressIndicator() {
        if (progressDialogFragment != null) {
            progressDialogFragment.dismiss();
        }
    }

    @Override
    public void noInternet() {

    }

    @Override
    public void showProgressIndicator() {

        if (getApplicationContext() != null) {

            findViewById(android.R.id.content).post(new Runnable() {
                @Override
                public void run() {
                    progressDialogFragment = ProgressDialogFragment.newInstance();
                    progressDialogFragment.show(getSupportFragmentManager(), "progress_dialog");
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llMyProfile: {
                if (GlobalPreferManager.getString(GlobalPreferManager.Keys.USER_ID, "").equals("")) {
                    toLogin();
                } else {
                    loadFragment(mFragmentHashMap.get(Constants.TAG_PROFILE_FRAGMENT));
                    mDrawer.closeDrawers();
                }

                break;
            }
            case R.id.llMyBooking: {
                if (GlobalPreferManager.getString(GlobalPreferManager.Keys.USER_ID, "").equals("")) {
                    toLogin();
                } else {
                    loadFragment(mFragmentHashMap.get(Constants.TAG_BOOKED_FRAGMENT));
                    mBottomNavigationView.setSelectedItemId(R.id.navigation_booked);
                    mDrawer.closeDrawers();
                }

                break;
            }
            case R.id.llMyFavorites: {
                if (GlobalPreferManager.getString(GlobalPreferManager.Keys.USER_ID, "").equals("")) {
                    toLogin();
                } else {
                    loadFragment(mFragmentHashMap.get(Constants.TAG_FAVORITES_FRAGMENT));
                    mBottomNavigationView.setSelectedItemId(R.id.navigation_favorites);
                    mDrawer.closeDrawers();
                }

                break;
            }
            case R.id.llReferAFriend: {
                startActivity(ReferAFriendActivity.getActivityIntent(this));
                mDrawer.closeDrawers();
                break;
            }

            case R.id.llNotification: {
                startActivity(new Intent(HomeMainActivity.this, NotificationActivity.class));
                mDrawer.closeDrawers();
                break;
            }
            case R.id.llRateApp: {
                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
                mDrawer.closeDrawers();
                break;
            }

            case R.id.llHelp: {
                startActivity(new Intent(HomeMainActivity.this, HelpActivity.class));
                break;
            }
            case R.id.llLogout: {
                new LogoutDialogFragment().show(getSupportFragmentManager(), "logout_fragment");
                break;
            }
        }
    }

    public interface LocationChange {
        void onLocationChanged(String place);
    }

    private void checkNewBuildAvailable() {
        new GooglePlayAppVersion(getPackageName(), new GooglePlayAppVersion.Listener() {
            @Override
            public void result(String version) {
                PackageManager manager = getPackageManager();
                PackageInfo info = null;
                try {
                    info = manager.getPackageInfo(getPackageName(), 0);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                if (version != null && !version.isEmpty()) {
                    if (Float.valueOf(info.versionName) < Float.valueOf(version)) {
                        DialogFragment dialogFragment = PlayStoreUpdateDialogFragment.newInstance();
                        dialogFragment.setCancelable(false);
                        dialogFragment.show(getSupportFragmentManager(), "playStore_fragment");

                    }

                }
            }
        }
        ).execute();
    }

}
