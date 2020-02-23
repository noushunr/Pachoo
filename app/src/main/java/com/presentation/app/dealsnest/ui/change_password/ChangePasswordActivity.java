package com.presentation.app.dealsnest.ui.change_password;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.presentation.app.dealsnest.R;
import com.presentation.app.dealsnest.app_pref.GlobalPreferManager;
import com.presentation.app.dealsnest.common.OnFragmentInteractionListener;
import com.presentation.app.dealsnest.ui.BaseActivity;
import com.presentation.app.dealsnest.ui.home.HomeMainActivity;
import com.presentation.app.dealsnest.utils.CommonUtils;

public class ChangePasswordActivity extends BaseActivity implements OnFragmentInteractionListener, View.OnClickListener, ChangePasswordViewInterface {

    private Button mSubmit;
    private ImageView mClose;
    private LinearLayout mLayout;
    private EditText edit_new_password, edit_confirm_password;
    private ChangePasswordPresenter passwordPresenter;
    private String REGISTER_ID_HOLDER, NEW_PASSWORD_HOLDER, CONFIRM_PASSWORD_HOLDER;

    private static Intent getActivityIntent(Context context) {
        return new Intent(context, ChangePasswordActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        passwordPresenter = new ChangePasswordPresenter(this);
        GlobalPreferManager.initializePreferenceManager(getApplicationContext());
        REGISTER_ID_HOLDER = GlobalPreferManager.getString(GlobalPreferManager.Keys.USER_ID, "");

        initView();
    }

    @Override
    protected boolean setToolbar() {
        return false;
    }

    @Override
    public int setLayout() {
        return R.layout.activity_change_password;
    }

    @Override
    public void reloadPage() {
        finish();
        startActivity(ChangePasswordActivity.getActivityIntent(this));
    }

    private void initView() {
        edit_new_password = findViewById(R.id.edit_new_password);
        edit_confirm_password = findViewById(R.id.edit_confirm_password);
        mSubmit = findViewById(R.id.btn_submit);
        mClose = findViewById(R.id.close_icon);
        mLayout = findViewById(R.id.password_layout);
        mSubmit.setOnClickListener(this);
        mClose.setOnClickListener(this);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void setTitle(String title) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                CHANGE_PASSWORD();

                break;
            case R.id.close_icon:
                finish();
                break;
        }
    }

    @Override
    public void failedToChangePassword(String message) {
        CommonUtils.showToast(this, message);
    }

    @Override
    public void onPasswordChangeSuccess(String message) {

        CommonUtils.showToast(this, message);
        Intent intent = new Intent(getApplicationContext(), HomeMainActivity.class);
        startActivity(intent);
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

    private boolean isValidated() {
        if (NEW_PASSWORD_HOLDER.isEmpty()) {
            edit_new_password.setError("New Password Field Is Empty");
            edit_new_password.requestFocus();
            return false;

        } else if (CONFIRM_PASSWORD_HOLDER.isEmpty()) {
            edit_confirm_password.setError("Confirm Password Field Is Empty");
            edit_confirm_password.requestFocus();
            return false;
        } else if (!NEW_PASSWORD_HOLDER.equals(CONFIRM_PASSWORD_HOLDER)) {
            edit_confirm_password.setError("Password Mismatched");
            edit_confirm_password.requestFocus();
            return false;
        }
        return true;

    }

    private void CHANGE_PASSWORD() {
        NEW_PASSWORD_HOLDER = edit_new_password.getText().toString();
        CONFIRM_PASSWORD_HOLDER = edit_confirm_password.getText().toString();

        if (isValidated()) {
            passwordPresenter.changePassword(REGISTER_ID_HOLDER, CONFIRM_PASSWORD_HOLDER);
        }
    }

}
