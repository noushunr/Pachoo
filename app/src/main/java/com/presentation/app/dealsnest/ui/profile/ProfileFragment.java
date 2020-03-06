package com.presentation.app.dealsnest.ui.profile;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.presentation.app.dealsnest.BuildConfig;
import com.presentation.app.dealsnest.R;
import com.presentation.app.dealsnest.app_pref.GlobalPreferManager;
import com.presentation.app.dealsnest.common.CommonViewInterface;
import com.presentation.app.dealsnest.common.OnFragmentInteractionListener;
import com.presentation.app.dealsnest.models.ProfileData;
import com.presentation.app.dealsnest.ui.BaseFragment;
import com.presentation.app.dealsnest.ui.change_password.ChangePasswordActivity;
import com.presentation.app.dealsnest.ui.dialog_fragment.LogoutDialogFragment;
import com.presentation.app.dealsnest.ui.dialog_fragment.ProgressDialogFragment;


public class ProfileFragment extends BaseFragment implements ProfileActivityViewInterface {
    public static final int STORAGE_PERMISSION_CODE = 123;
    private LinearLayout mLayout;
    private View view;
    private Button mEditProfile, mChangePassword, mUpdateProfile, mLogout;
    private ImageView imShareWhatsapp, imShareFacebook, imSharetwitter, imMore;
    private TextView txt_username;
    private TextView txt_number;
    private TextView txt_email;
    private ImageView profile_pic;
    private ProfileActivityPresenter profileActivityPresenter;
    private OnFragmentInteractionListener mListener;
    private ProgressDialogFragment progressDialogFragment;
    private CommonViewInterface mCommonListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        requestStoragePermission();

        view = inflater.inflate(R.layout.fragment_profile, container, false);
        mListener.setTitle("Profile");
        mLogout = view.findViewById(R.id.button_logout);
        mEditProfile = view.findViewById(R.id.button_edit);
        txt_username = view.findViewById(R.id.txt_username);
        txt_number = view.findViewById(R.id.txt_number);
        txt_email = view.findViewById(R.id.txt_email);
        profile_pic = view.findViewById(R.id.profile_pic);
        mLayout = view.findViewById(R.id.edit_layout);
        imShareWhatsapp = view.findViewById(R.id.icon_whatsapp);
        imShareFacebook = view.findViewById(R.id.icon_facebook);
        imSharetwitter = view.findViewById(R.id.icon_twitter);
        imMore = view.findViewById(R.id.icon_more);
        mChangePassword = view.findViewById(R.id.button_change_password);
        mUpdateProfile = view.findViewById(R.id.update_button);
        txt_username.setText(GlobalPreferManager.getString(GlobalPreferManager.Keys.USER_FIRST_NAME, ""));
        txt_email.setText(GlobalPreferManager.getString(GlobalPreferManager.Keys.USER_EMAIL, ""));
        profileActivityPresenter = new ProfileActivityPresenter(getContext(), this);

        mEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ProfileActivity.class));
            }
        });

        mChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
                startActivity(intent);
            }
        });

        mUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLayout.setVisibility(View.GONE);
            }
        });

        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LogoutDialogFragment().show(getActivity().getSupportFragmentManager(), "logout_fragment");
            }
        });

        imShareWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent whatsappIntent = new Intent();
                whatsappIntent.setAction(Intent.ACTION_SEND);
                whatsappIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                String shareMessage = "\nLet me recommend you this application\n\n";
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("com.whatsapp");
                whatsappIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                try {
                    getActivity().startActivity(whatsappIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getActivity(), "Whatsapp have not been installed.", Toast.LENGTH_LONG).show();
                }
            }
        });
        imShareFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent whatsappIntent = new Intent();
                whatsappIntent.setAction(Intent.ACTION_SEND);
                whatsappIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                String shareMessage = "\nLet me recommend you this application\n\n";
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("com.facebook.katana");
                whatsappIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                try {
                    getActivity().startActivity(whatsappIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getActivity(), "Facebook have not been installed.", Toast.LENGTH_LONG).show();
                }
            }
        });
        imSharetwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent whatsappIntent = new Intent();
                whatsappIntent.setAction(Intent.ACTION_SEND);
                whatsappIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                String shareMessage = "\nLet me recommend you this application\n\n";
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("com.twitter.android");
                whatsappIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                try {
                    getActivity().startActivity(whatsappIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getActivity(), "Twitter have not been installed.", Toast.LENGTH_LONG).show();
                }
            }
        });
        imMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

                String shareMessage = "\nLet me recommend you this application\n\n";
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                shareIntent.setType("text/plain");
                startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.app_name)));
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (profileActivityPresenter != null)
            profileActivityPresenter.loadProfileDetails(GlobalPreferManager.getString(GlobalPreferManager.Keys.USER_ID, ""));

    }

    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
        }
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == STORAGE_PERMISSION_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity(), "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }

        if (context instanceof CommonViewInterface){
            mCommonListener = (CommonViewInterface) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void failedToUpdateProfile(String message) {

    }

    @Override
    public void onProfileUpdateSuccess(String message, String img) {

    }

    @Override
    public void onLoadingProfileSuccess(ProfileData ProfileData) {

        if (getActivity() == null)
            return;
        txt_username.setText(ProfileData.getFirstname() + " " + ProfileData.getLastname());
        txt_number.setText(ProfileData.getMobile());
        txt_email.setText(ProfileData.getEmailId());
        Glide.with(this)
                .setDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.placeholder_circle))
                .load(/*ApiClient.PROFILE_BASE_URL + ProfileData.getImage()*/"")
                .apply(new RequestOptions().placeholder(R.drawable.ic_user).error(R.drawable.ic_user))
                .into(profile_pic);
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
        if (mCommonListener!=null) {
            mCommonListener.dismissProgressIndicator();
        }
    }

    @Override
    public void noInternet() {

    }

    @Override
    public void showProgressIndicator() {
        if (mCommonListener!=null){
            mCommonListener.showProgressIndicator();
        }
    }

    @Override
    public void onRetry() {
        profileActivityPresenter.loadProfileDetails(GlobalPreferManager.getString(GlobalPreferManager.Keys.USER_ID, ""));
    }

}
