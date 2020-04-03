package com.highstreets.user.ui.profile.profile_edit;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.textfield.TextInputEditText;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.highstreets.user.R;
import com.highstreets.user.api.ApiClient;
import com.highstreets.user.app_pref.GlobalPreferManager;
import com.highstreets.user.models.ProfileData;
import com.highstreets.user.ui.base.BaseActivity;
import com.highstreets.user.ui.change_password.ChangePasswordActivity;
import com.highstreets.user.ui.dialog_fragment.ProgressDialogFragment;
import com.highstreets.user.ui.profile.ProfilePresenter;
import com.highstreets.user.ui.profile.ProfileViewInterface;
import com.highstreets.user.utils.CommonUtils;
import com.highstreets.user.utils.ImageUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileEditActivity extends BaseActivity implements View.OnClickListener, ProfileViewInterface {

    public static final int REQUEST_CODE = 1;
    public static final int STORAGE_PERMISSION_CODE = 123;
    private LinearLayout mLayout;
    private String REGISTER_ID_HOLDER, USERNAME_HOLDER, FIRST_NAME_HOLDER, LAST_NAME_HOLDER, EMAIL_ID_HOLDER, MOBILE_HOLDER, IMAGE_HOLDER;
    private Button mChangePassword, mUpdateProfile;
    private ImageView close_icon;
    private CircleImageView ProfilePic;
    private TextView mUsername, mMobile, mEmailID, tvToolbarTitle;
    private TextInputEditText edit_first_name, edit_last_name, edit_email, edit_number;
    private Button button_edit;
    private String image;
    private ProfilePresenter profilePresenter;
    private ProgressDialogFragment progressDialogFragment;


    public static Intent getActivityIntent(Context context) {
        return new Intent(context, ProfileEditActivity.class);
    }

    @Nullable
    public static String getImagePathFromResult(Context context, int requestCode, int resultCode,
                                                Intent imageReturnedIntent) {

        Uri selectedImage = null;
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            File imageFile = ImageUtils.getTemporalFile(context, String.valueOf(REQUEST_CODE));
            boolean isCamera = (imageReturnedIntent == null
                    || imageReturnedIntent.getData() == null
                    || imageReturnedIntent.getData().toString().contains(imageFile.toString()));
            if (isCamera) {
                return imageFile.getAbsolutePath();
            } else {
                selectedImage = imageReturnedIntent.getData();
            }

        }
        if (selectedImage == null) {
            return null;
        }
        return getFilePathFromUri(context, selectedImage);
    }

    private static String getFilePathFromUri(Context context, Uri uri) {
        InputStream is = null;
        if (uri.getAuthority() != null) {
            try {
                is = context.getContentResolver().openInputStream(uri);
                Bitmap bmp = BitmapFactory.decodeStream(is);
                return ImageUtils.savePicture(context, bmp, String.valueOf(uri.getPath().hashCode()));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        requestStoragePermission();
        profilePresenter = new ProfilePresenter(this, this);

        GlobalPreferManager.initializePreferenceManager(getApplicationContext());
        REGISTER_ID_HOLDER = GlobalPreferManager.getString(GlobalPreferManager.Keys.USER_ID, "");
        FIRST_NAME_HOLDER = GlobalPreferManager.getString(GlobalPreferManager.Keys.USER_FIRST_NAME, "");
        LAST_NAME_HOLDER = GlobalPreferManager.getString(GlobalPreferManager.Keys.USER_LAST_NAME, "");
        EMAIL_ID_HOLDER = GlobalPreferManager.getString(GlobalPreferManager.Keys.USER_EMAIL, "");
        MOBILE_HOLDER = GlobalPreferManager.getString(GlobalPreferManager.Keys.USER_MOBILE, "");
        IMAGE_HOLDER = GlobalPreferManager.getString(GlobalPreferManager.Keys.USER_IMAGE, "");

        USERNAME_HOLDER = FIRST_NAME_HOLDER + " " + LAST_NAME_HOLDER;
        initView();
        tvToolbarTitle.setText(getString(R.string.profile));

        mLayout.setVisibility(View.VISIBLE);
        button_edit.setVisibility(View.GONE);

        GetProfileDetails(REGISTER_ID_HOLDER);
    }

    @Override
    protected boolean setToolbar() {
        return true;
    }

    @Override
    public int setLayout() {
        return R.layout.activity_profile_edit;
    }

    @Override
    public void reloadPage() {

    }

    private void initView() {
        tvToolbarTitle = findViewById(R.id.tvToolbarText);
        mUsername = findViewById(R.id.txt_username);
        mEmailID = findViewById(R.id.txt_email);
        mMobile = findViewById(R.id.txt_number);
        edit_first_name = findViewById(R.id.edit_first_name);
        edit_last_name = findViewById(R.id.edit_last_name);
        edit_email = findViewById(R.id.edit_email);
        edit_number = findViewById(R.id.edit_number);
        ProfilePic = findViewById(R.id.profile_pic);
        button_edit = findViewById(R.id.button_edit);
        mLayout = findViewById(R.id.edit_layout);
        close_icon = findViewById(R.id.close_icon);
        mChangePassword = findViewById(R.id.button_change_password);
        mUpdateProfile = findViewById(R.id.update_button);

        ProfilePic.setOnClickListener(this);
        mChangePassword.setOnClickListener(this);
        mUpdateProfile.setOnClickListener(this);
        close_icon.setOnClickListener(this);
        button_edit.setOnClickListener(this);

        mUsername.setText(USERNAME_HOLDER);
        mEmailID.setText(EMAIL_ID_HOLDER);
        mMobile.setText(MOBILE_HOLDER);

        edit_first_name.setText(FIRST_NAME_HOLDER);
        edit_last_name.setText(LAST_NAME_HOLDER);
        edit_email.setText(EMAIL_ID_HOLDER);
        edit_number.setText(MOBILE_HOLDER);
    }

    private void GetProfileDetails(String register_id) {
        profilePresenter.loadProfileDetails(register_id);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profile_pic:
                SelectImage();
                break;
            case R.id.button_edit:
                mLayout.setVisibility(View.VISIBLE);
                button_edit.setVisibility(View.GONE);
                break;
            case R.id.close_icon:
                mLayout.setVisibility(View.GONE);
                button_edit.setVisibility(View.VISIBLE);
                break;
            case R.id.button_change_password:
                Intent intent = new Intent(getApplicationContext(), ChangePasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.update_button:
                checkFields();
                break;
        }
    }

    private void SelectImage() {
        Intent getIntent = new Intent();
        getIntent.setType("image/*");
        getIntent.setAction(Intent.ACTION_GET_CONTENT);

        Intent pickIntent = new Intent(Intent.ACTION_PICK);
        pickIntent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});
        startActivityForResult(chooserIntent, REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE) {

                image = getImagePathFromResult(this, requestCode, resultCode, data);

                ProfilePic.setImageURI(Uri.parse(image));

            }
        }
    }

    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void checkFields() {
        if (TextUtils.isEmpty(edit_first_name.getText())) {
            edit_first_name.setError("First Name Is Not Entered");
            edit_first_name.requestFocus();
        } else if (TextUtils.isEmpty(edit_last_name.getText())) {
            edit_last_name.setError("Last Name Is Not Entered");
            edit_last_name.requestFocus();
        } else if (TextUtils.isEmpty(edit_email.getText())) {
            edit_email.setError("Email ID Is Not Entered");
            edit_email.requestFocus();
        } else if (TextUtils.isEmpty(edit_number.getText())) {
            edit_number.setError("Mobile Number Is Not Entered");
            edit_number.requestFocus();
        } else {
            profilePresenter.updateProfile(REGISTER_ID_HOLDER, FIRST_NAME_HOLDER, LAST_NAME_HOLDER, EMAIL_ID_HOLDER, MOBILE_HOLDER, image);
        }
    }

    @Override
    public void failedToUpdateProfile(String message) {
        CommonUtils.showToast(this, message);
    }

    @Override
    public void onProfileUpdateSuccess(String message, String image) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onLoadingProfileSuccess(ProfileData ProfileData) {
        mUsername.setText(ProfileData.getFirstname() + " " + ProfileData.getLastname());
        mMobile.setText(ProfileData.getMobile());
        mEmailID.setText(ProfileData.getEmailId());
        Glide.with(this)
                .setDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.placeholder_circle))
                .load(ApiClient.PROFILE_BASE_URL + ProfileData.getImage())
                .apply(new RequestOptions().placeholder(R.drawable.ic_user).error(R.drawable.ic_user))
                .into(ProfilePic);
        GlobalPreferManager.setString(GlobalPreferManager.Keys.USER_IMAGE, ProfileData.getImage());
        edit_first_name.setText(ProfileData.getFirstname());
        edit_last_name.setText(ProfileData.getLastname());
        edit_email.setText(ProfileData.getEmailId());
        edit_number.setText(ProfileData.getMobile());

    }

    @Override
    public void onFailingToLoadProfile(String message) {
        CommonUtils.showToast(this, message);
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
        try {
            if (progressDialogFragment != null && !isFinishing())
                progressDialogFragment.dismiss();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void noInternet() {

    }

    @Override
    public void showProgressIndicator() {
        progressDialogFragment = ProgressDialogFragment.newInstance();
        progressDialogFragment.show(getSupportFragmentManager(), "progress_dialog");
    }
}
