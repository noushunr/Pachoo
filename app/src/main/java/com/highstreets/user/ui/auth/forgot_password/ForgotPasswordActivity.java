package com.highstreets.user.ui.auth.forgot_password;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;

import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.highstreets.user.R;
import com.highstreets.user.ui.base.BaseActivity;
import com.highstreets.user.ui.MyCustomDialogFragment;
import com.highstreets.user.utils.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForgotPasswordActivity extends BaseActivity implements
        ForgotPasswordViewInterface {

    private ForgotPasswordPresenter forgotPasswordPresenter;

    @BindView(R.id.tiEtEmail)
    TextInputEditText tiEtEmail;
    @BindView(R.id.btnReset)
    Button btnReset;

    public static Intent start(Context context) {
        return new Intent(context, ForgotPasswordActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        forgotPasswordPresenter = new ForgotPasswordPresenter(this);

        btnReset.setOnClickListener(view -> {
            validateField();
        });
    }

    @Override
    protected boolean setToolbar() {
        return false;
    }

    @Override
    public int setLayout() {
        return R.layout.activity_forgot_password;
    }

    @Override
    public void reloadPage() {

    }

    private void validateField() {
        if (TextUtils.isEmpty(tiEtEmail.getText())) {
            CommonUtils.showToast(this, "Please enter email");
            tiEtEmail.requestFocus();
        } else {
            forgotPasswordPresenter.forgotPassword(tiEtEmail.getText().toString());
        }
    }

    @Override
    public void failedToSignIn(String message) {
        CommonUtils.showToast(this, message);
    }

    @Override
    public void onResetLinkSent(String id, String message) {
        Bundle bundle = new Bundle();
        bundle.putString("email_id", tiEtEmail.getText().toString());
        bundle.putString("id", id);
        bundle.putString("message", message);
        DialogFragment dialogFragment = new MyCustomDialogFragment();
        dialogFragment.setArguments(bundle);
        dialogFragment.show(getSupportFragmentManager(), "dialog");
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

    }

    @Override
    public void noInternet() {

    }

    @Override
    public void showProgressIndicator() {

    }

}
