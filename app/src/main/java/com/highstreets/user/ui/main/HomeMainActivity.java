package com.highstreets.user.ui.main;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.highstreets.user.R;
import com.highstreets.user.app_pref.GlobalPreferManager;
import com.highstreets.user.common.CommonViewInterface;
import com.highstreets.user.common.OnFragmentInteractionListener;
import com.highstreets.user.ui.ReferAFriendActivity;
import com.highstreets.user.ui.base.BaseActivity;
import com.highstreets.user.ui.dialog_fragment.LogoutDialogFragment;
import com.highstreets.user.ui.dialog_fragment.ProgressDialogFragment;
import com.highstreets.user.ui.help.HelpActivity;
import com.highstreets.user.ui.login_registration.LoginActivity;
import com.highstreets.user.ui.main.bookings.BookingsFragment;
import com.highstreets.user.ui.main.categories.CategoriesFragment;
import com.highstreets.user.ui.main.coupons.CouponsFragment;
import com.highstreets.user.ui.main.favorites.FavoritesFragment;
import com.highstreets.user.ui.main.home.HomeFragment;
import com.highstreets.user.ui.notification.NotificationActivity;
import com.highstreets.user.ui.profile.ProfileFragment;
import com.highstreets.user.ui.search.SearchActivity;
import com.highstreets.user.utils.CommonUtils;
import com.highstreets.user.utils.Constants;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeMainActivity extends BaseActivity
        implements OnFragmentInteractionListener,
        CommonViewInterface,
        View.OnClickListener,
        LocationListener,
        BottomNavigationView.OnNavigationItemSelectedListener {

    private ProgressDialogFragment progressDialogFragment;
    private LocationChange mLocationChange;
    private String tokenId;
    private String userId;
    private Geocoder geocoder;
    private String SELECTED_CITY;
    private HashMap<String, Fragment> mFragmentHashMap = new HashMap<>();
    private HomeMainPresenterInterface homeMainPresenterInterface;
    private static final int MY_UPDATE_REQUEST_CODE = 112;

    @BindView(R.id.tvToolbarText)
    TextView tvToolBarText;
    @BindView(R.id.tvNavFirstName)
    TextView tvNavFirstName;
    @BindView(R.id.llMyProfile)
    LinearLayout llMyProfile;
    @BindView(R.id.llMyBooking)
    LinearLayout llMyBooking;
    @BindView(R.id.llMyFavorites)
    LinearLayout llMyFavorites;
    @BindView(R.id.llReferAFriend)
    LinearLayout llReferAFriend;
    @BindView(R.id.llNotification)
    LinearLayout llNotification;
    @BindView(R.id.llRateApp)
    LinearLayout llRateApp;
    @BindView(R.id.llHelp)
    LinearLayout llHelp;
    @BindView(R.id.llLogout)
    LinearLayout llLogout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.bottomNavigation)
    BottomNavigationView bottomNavigation;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;


    public static Intent start(Context context) {
        return new Intent(context, HomeMainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        userId = GlobalPreferManager.getString(GlobalPreferManager.Keys.USER_ID, "");
        homeMainPresenterInterface = new HomeMainPresenter();
        bottomNavigation.setOnNavigationItemSelectedListener(this);
        tvToolBarText.setText(R.string.home);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
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
        getFirebaseToken();
        initiateFragments();
        loadFragment(mFragmentHashMap.get(Constants.TAG_HOME_FRAGMENT));
        bottomNavigation.setSelectedItemId(R.id.navigation_home);
    }

    private void getFirebaseToken() {
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                return;
            }
            tokenId = task.getResult().getToken();
            homeMainPresenterInterface.addTokens(userId, tokenId, "1");
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        checkNewBuildAvailable();
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
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.flContainer);
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

        llMyProfile.setOnClickListener(this);
        llMyBooking.setOnClickListener(this);
        llMyFavorites.setOnClickListener(this);
        llReferAFriend.setOnClickListener(this);
        llNotification.setOnClickListener(this);
        llRateApp.setOnClickListener(this);
        llHelp.setOnClickListener(this);
        llLogout.setOnClickListener(this);

        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                String drawerName = "Hello " + GlobalPreferManager.getString(GlobalPreferManager.Keys.USER_FIRST_NAME, "");
                tvNavFirstName.setText(drawerName);
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    @Override
    public int setLayout() {
        return R.layout.activity_home_main;
    }

    @Override
    public void reloadPage() {
        startActivity(HomeMainActivity.start(this));
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
        mFragmentHashMap.put(Constants.TAG_BOOKINGS_FRAGMENT, new BookingsFragment());
        mFragmentHashMap.put(Constants.TAG_PROFILE_FRAGMENT, new ProfileFragment());
    }

    private void toLogin() {
        Intent toLoginIntent = LoginActivity.start(this);
        toLoginIntent.putExtra(Constants.FROM_INSIDE, Constants.FROM_INSIDE);
        toLoginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(toLoginIntent);
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.flContainer, fragment);
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
        if (getSupportFragmentManager().findFragmentById(R.id.flContainer) instanceof HomeFragment)
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
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.flContainer);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (!(fragment instanceof HomeFragment)) {
            loadFragment(new HomeFragment());
            bottomNavigation.setSelectedItemId(R.id.navigation_home);
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
                    drawerLayout.closeDrawers();
                }
                break;
            }
            case R.id.llMyBooking: {
                if (GlobalPreferManager.getString(GlobalPreferManager.Keys.USER_ID, "").equals("")) {
                    toLogin();
                } else {
                    loadFragment(mFragmentHashMap.get(Constants.TAG_BOOKINGS_FRAGMENT));
                    bottomNavigation.setSelectedItemId(R.id.navigation_booked);
                    drawerLayout.closeDrawers();
                }

                break;
            }
            case R.id.llMyFavorites: {
                if (GlobalPreferManager.getString(GlobalPreferManager.Keys.USER_ID, "").equals("")) {
                    toLogin();
                } else {
                    loadFragment(mFragmentHashMap.get(Constants.TAG_FAVORITES_FRAGMENT));
                    bottomNavigation.setSelectedItemId(R.id.navigation_favorites);
                    drawerLayout.closeDrawers();
                }

                break;
            }
            case R.id.llReferAFriend: {
                startActivity(ReferAFriendActivity.getActivityIntent(this));
                drawerLayout.closeDrawers();
                break;
            }

            case R.id.llNotification: {
                startActivity(new Intent(HomeMainActivity.this, NotificationActivity.class));
                drawerLayout.closeDrawers();
                break;
            }
            case R.id.llRateApp: {
                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
                drawerLayout.closeDrawers();
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
                if (GlobalPreferManager.getString(GlobalPreferManager.Keys.USER_ID, "").equals("")) {
                    toLogin();
                } else {
                    loadFragment(mFragmentHashMap.get(Constants.TAG_COUPONS_FRAGMENT));
                }
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
                    loadFragment(mFragmentHashMap.get(Constants.TAG_BOOKINGS_FRAGMENT));
                }
                return true;
        }
        return false;
    }

    public interface LocationChange {
        void onLocationChanged(String place);
    }

    private void checkNewBuildAvailable() {
        AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(this);
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                try {
                    appUpdateManager.startUpdateFlowForResult(
                            appUpdateInfo,
                            AppUpdateType.IMMEDIATE,
                            this,
                            MY_UPDATE_REQUEST_CODE);
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_UPDATE_REQUEST_CODE) {
            if (resultCode != RESULT_OK) {
                CommonUtils.showToast(this, "Update failed");
            }
        }
    }

}
