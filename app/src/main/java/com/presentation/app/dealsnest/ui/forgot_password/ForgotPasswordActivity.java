package com.presentation.app.dealsnest.ui.forgot_password;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputEditText;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.presentation.app.dealsnest.R;
import com.presentation.app.dealsnest.ui.MyCustomDialogFragment;
import com.presentation.app.dealsnest.utils.CommonUtils;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener, ForgotPasswordViewInterface {

    private Button mReset;
    private TextInputEditText mEditEmailID;
    private ForgotPasswordPresenter forgotPasswordPresenter;
    private String fromStr;
    private String EMAIL_HOLDER;

    public static Intent getActivityIntent(Context context) {
        return new Intent(context, ForgotPasswordActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        forgotPasswordPresenter = new ForgotPasswordPresenter(this);
        mEditEmailID = findViewById(R.id.edit_email_id);
        mReset = findViewById(R.id.btn_reset);

        mEditEmailID.setOnClickListener(this);
        mReset.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_email_id:

                break;
            case R.id.btn_reset:
                RESET_PASSWORD();
                break;
        }
    }

    private void RESET_PASSWORD() {
        EMAIL_HOLDER = mEditEmailID.getText().toString();
        if (isValidated()) {
            forgotPasswordPresenter.forgotPassword(EMAIL_HOLDER);
        }
    }

    private boolean isValidated() {
        if (TextUtils.isEmpty(mEditEmailID.getText().toString())) {
            CommonUtils.showToast(this, "Please enter email");
            mEditEmailID.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public void failedToSignIn(String message) {
        CommonUtils.showToast(this, message);
    }

    @Override
    public void onResetLinkSent(String id, String message) {
        Bundle bundle = new Bundle();
        bundle.putString("email_id", EMAIL_HOLDER);
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
