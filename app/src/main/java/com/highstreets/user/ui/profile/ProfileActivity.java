package com.highstreets.user.ui.profile;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.highstreets.user.R;
import com.highstreets.user.api.ApiClient;
import com.highstreets.user.app_pref.GlobalPreferManager;
import com.highstreets.user.models.ProfileData;
import com.highstreets.user.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends BaseActivity implements ProfileViewInterface {

    private ProfilePresenterInterface profilePresenterInterface;

    @BindView(R.id.ivProfilePic)
    CircleImageView ivProfilePic;
    @BindView(R.id.tvUsername)
    TextView tvUsername;
    @BindView(R.id.tvNumber)
    TextView tvNumber;
    @BindView(R.id.tvEmail)
    TextView tvEmail;
    @BindView(R.id.btnEdit)
    Button btnEdit;
    @BindView(R.id.btnLogout)
    Button btnLogout;
    @BindView(R.id.ivWhatsApp)
    ImageView ivWhatsApp;
    @BindView(R.id.ivFacebook)
    ImageView ivFacebook;
    @BindView(R.id.ivTwitter)
    ImageView ivTwitter;
    @BindView(R.id.ivMore)
    ImageView ivMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        profilePresenterInterface =  new ProfilePresenter(this, this);
        profilePresenterInterface.loadProfileDetails(GlobalPreferManager.getString(GlobalPreferManager.Keys.USER_ID, ""));
    }

    @Override
    protected boolean setToolbar() {
        return false;
    }

    @Override
    public int setLayout() {
        return R.layout.activity_profile;
    }

    @Override
    public void reloadPage() {

    }

    @Override
    public void failedToUpdateProfile(String message) {

    }

    @Override
    public void onProfileUpdateSuccess(String message, String image) {

    }

    @Override
    public void onLoadingProfileSuccess(ProfileData profile) {
        if (profile != null){
            Glide.with(this)
                    .setDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.placeholder_circle))
                    .load(ApiClient.PROFILE_BASE_URL + profile.getImage())
                    .apply(new RequestOptions().placeholder(R.drawable.ic_user).error(R.drawable.ic_user))
                    .into(ivProfilePic);
            String name = profile.getFirstname()+ " " + profile.getLastname();
            tvUsername.setText(name);
            tvNumber.setText(profile.getMobile());

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

    }

    @Override
    public void noInternet() {

    }

    @Override
    public void showProgressIndicator() {

    }
}
