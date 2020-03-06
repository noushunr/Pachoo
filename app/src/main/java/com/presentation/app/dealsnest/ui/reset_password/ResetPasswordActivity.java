package com.presentation.app.dealsnest.ui.reset_password;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputEditText;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.presentation.app.dealsnest.R;
import com.presentation.app.dealsnest.app_pref.GlobalPreferManager;
import com.presentation.app.dealsnest.ui.dialog_fragment.ProgressDialogFragment;
import com.presentation.app.dealsnest.ui.login_registration.LoginActivity;
import com.presentation.app.dealsnest.utils.CommonUtils;

public class ResetPasswordActivity extends AppCompatActivity implements View.OnClickListener, ResetPasswordViewInterface {

    private Button mSavePassword;
    private TextInputEditText EditNewPassword, EditConfirmPassword;
    private ProgressDialogFragment progressDialogFragment;
    private ResetPasswordPresenter resetPasswordPresenter;
    private String NEW_PASSWORD_HOLDER, CONFIRM_PASSWORD_HOLDER, REGISTER_ID_HOLDER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        GlobalPreferManager.initializePreferenceManager(getApplicationContext());
        resetPasswordPresenter = new ResetPasswordPresenter(this);

        REGISTER_ID_HOLDER = GlobalPreferManager.getString(GlobalPreferManager.Keys.USER_ID, "");

        EditNewPassword = findViewById(R.id.edit_password);
        EditConfirmPassword = findViewById(R.id.edit_confirm_password);
        mSavePassword = findViewById(R.id.btnSavePassword);
        mSavePassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSavePassword:
                RESET_PASSWORD();
                break;
        }
    }

    private void RESET_PASSWORD() {
        NEW_PASSWORD_HOLDER = EditNewPassword.getText().toString();
        CONFIRM_PASSWORD_HOLDER = EditConfirmPassword.getText().toString();
        if (isValidated()) {
            resetPasswordPresenter.resetPassword(REGISTER_ID_HOLDER, NEW_PASSWORD_HOLDER);
        }
    }

    private boolean isValidated() {
        if (NEW_PASSWORD_HOLDER.isEmpty()) {
            EditNewPassword.setError("Please Enter A New Password");
            EditNewPassword.requestFocus();
            return false;
        } else if (CONFIRM_PASSWORD_HOLDER.isEmpty()) {
            EditConfirmPassword.setError("Please Enter The Password");
            EditConfirmPassword.requestFocus();
            return false;
        } else if (!NEW_PASSWORD_HOLDER.equals(CONFIRM_PASSWORD_HOLDER)) {
            EditConfirmPassword.setError("Password Does Not Match");
            EditConfirmPassword.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public void failedToResetNewPassword(String message) {
        CommonUtils.showToast(this, message);
    }

    @Override
    public void onResetPasswordSuccess(String message) {
        CommonUtils.showToast(this, message);
        GlobalPreferManager.setBoolean(GlobalPreferManager.Keys.IS_REGISTERED, true);
        Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finishAffinity();
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
