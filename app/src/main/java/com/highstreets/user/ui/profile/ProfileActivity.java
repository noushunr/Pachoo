package com.highstreets.user.ui.profile;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.highstreets.user.BuildConfig;
import com.highstreets.user.R;
import com.highstreets.user.api.ApiClient;
import com.highstreets.user.app_pref.SharedPrefs;
import com.highstreets.user.models.ProductResult;
import com.highstreets.user.models.ProfileData;
import com.highstreets.user.ui.base.BaseActivity;
import com.highstreets.user.ui.dialog_fragment.LogoutDialogFragment;
import com.highstreets.user.ui.profile.profile_edit.ProfileEditActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends BaseActivity implements ProfileViewInterface {

    public static final int STORAGE_PERMISSION_CODE = 123;
    private LinearLayout mLayout;
    private Button mEditProfile, mLogout;
    private ImageView imShareWhatsapp, imShareFacebook, imSharetwitter, imMore;
    private TextView txt_username;
    private TextView txt_number;
    private TextView txt_email;
    private ImageView profile_pic;
    private ProfilePresenter profilePresenter;

    @BindView(R.id.tvToolbarText)
    TextView tvToolbarText;

    public static Intent start(Context context) {
        return new Intent(context, ProfileActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tvToolbarText.setText(getString(R.string.profile));

        requestStoragePermission();
        mLogout = findViewById(R.id.button_logout);
        mEditProfile = findViewById(R.id.button_edit);
        txt_username = findViewById(R.id.txt_username);
        txt_number = findViewById(R.id.txt_number);
        txt_email = findViewById(R.id.txt_email);
        profile_pic = findViewById(R.id.profile_pic);
        mLayout = findViewById(R.id.edit_layout);
        imShareWhatsapp = findViewById(R.id.icon_whatsapp);
        imShareFacebook = findViewById(R.id.icon_facebook);
        imSharetwitter = findViewById(R.id.icon_twitter);
        imMore = findViewById(R.id.icon_more);
        txt_username.setText(SharedPrefs.getString(SharedPrefs.Keys.USER_FIRST_NAME, ""));
        txt_email.setText(SharedPrefs.getString(SharedPrefs.Keys.USER_EMAIL, ""));
        profilePresenter = new ProfilePresenter(this, this);

        mEditProfile.setOnClickListener(v ->
                startActivity(new Intent(ProfileActivity.this, ProfileEditActivity.class)));
        mLogout.setOnClickListener(v ->
                new LogoutDialogFragment().show(ProfileActivity.this.getSupportFragmentManager(), "logout_fragment"));

        imShareWhatsapp.setOnClickListener(v -> {
            Intent whatsappIntent = new Intent();
            whatsappIntent.setAction(Intent.ACTION_SEND);
            whatsappIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            String shareMessage = "\nLet me recommend you this application\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
            whatsappIntent.setType("text/plain");
            whatsappIntent.setPackage("com.whatsapp");
            whatsappIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            try {
                startActivity(whatsappIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(ProfileActivity.this, "Whatsapp have not been installed.", Toast.LENGTH_LONG).show();

            }
        });

        imShareFacebook.setOnClickListener(v -> {
            Intent whatsappIntent = new Intent();
            whatsappIntent.setAction(Intent.ACTION_SEND);
            whatsappIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            String shareMessage = "\nLet me recommend you this application\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
            whatsappIntent.setType("text/plain");
            whatsappIntent.setPackage("com.facebook.katana");
            whatsappIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            try {
                startActivity(whatsappIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(ProfileActivity.this, "Facebook have not been installed.", Toast.LENGTH_LONG).show();
            }
        });

        imSharetwitter.setOnClickListener(v -> {
            Intent whatsappIntent = new Intent();
            whatsappIntent.setAction(Intent.ACTION_SEND);
            whatsappIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            String shareMessage = "\nLet me recommend you this application\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
            whatsappIntent.setType("text/plain");
            whatsappIntent.setPackage("com.twitter.android");
            whatsappIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            try {
                startActivity(whatsappIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(ProfileActivity.this, "Twitter have not been installed.", Toast.LENGTH_LONG).show();
            }
        });

        imMore.setOnClickListener(v -> {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

            String shareMessage = "\nLet me recommend you this application\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            shareIntent.setType("text/plain");
            startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.app_name)));
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        if (profilePresenter != null)
            profilePresenter.loadProfileDetails(SharedPrefs.getString(SharedPrefs.Keys.USER_ID, ""));

    }

    @Override
    protected boolean setToolbar() {
        return true;
    }

    @Override
    public int setLayout() {
        return R.layout.activity_profile;
    }

    @Override
    public void reloadPage() {

    }

    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(ProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(ProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
        }
        ActivityCompat.requestPermissions(ProfileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == STORAGE_PERMISSION_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(ProfileActivity.this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(ProfileActivity.this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public void failedToUpdateProfile(String message) {

    }

    @Override
    public void onProfileUpdateSuccess(String message, String img) {

    }

    @Override
    public void onLoadingProfileSuccess(ProductResult ProfileData) {

        if (ProfileData!=null && ProfileData.getData()!=null){
            txt_username.setText(ProfileData.getData().getName());
            txt_number.setText(ProfileData.getData().getContactNo());
            txt_email.setText(ProfileData.getData().getEmail());

            SharedPrefs.setString(SharedPrefs.Keys.USER_FIRST_NAME, ProfileData.getData().getName());
            SharedPrefs.setString(SharedPrefs.Keys.USER_LAST_NAME, "");
        }

    }

    @Override
    public void onFailingToLoadProfile(String message) {

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


}
