package com.highstreets.user.ui.auth.forgot_password;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.highstreets.user.R;
import com.highstreets.user.api.ApiClient;
import com.highstreets.user.app_pref.SharedPrefs;
import com.highstreets.user.models.ProductResult;
import com.highstreets.user.ui.auth.login_registration.RegisterActivity;
import com.highstreets.user.ui.base.BaseActivity;
import com.highstreets.user.ui.MyCustomDialogFragment;
import com.highstreets.user.utils.CommonUtils;
import com.highstreets.user.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends BaseActivity implements
        ForgotPasswordViewInterface {

    private ForgotPasswordPresenter forgotPasswordPresenter;

    @BindView(R.id.etMobile)
    TextInputEditText tiEtEmail;
    @BindView(R.id.btnReset)
    Button btnReset;
    @BindView(R.id.ok)
    Button btnVerify;
    @BindView(R.id.etOtp)
    TextInputEditText etOtp;
    @BindView(R.id._ll_otp)
    LinearLayout llOtp;
    @BindView(R.id.ll_mobile)
    LinearLayout llMobile;
    @BindView(R.id._ll_change_password)
    LinearLayout llChangePassword;
    @BindView(R.id.btn_submit)
    Button mSubmit;
    @BindView(R.id.edit_new_password)
    EditText edit_new_password;
    @BindView(R.id.edit_confirm_password)
    EditText edit_confirm_password;
    String sessionId;
    private String REGISTER_ID_HOLDER, NEW_PASSWORD_HOLDER, CONFIRM_PASSWORD_HOLDER;

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
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etOtp.getText().toString().isEmpty()){
                    etOtp.requestFocus();
                    CommonUtils.showToast(ForgotPasswordActivity.this, "OTP is not entered");
                }else {
                    otpVerification();
                }
            }
        });
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CHANGE_PASSWORD();
            }
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
            CommonUtils.showToast(this, "Please enter mobile number");
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
        CommonUtils.showToast(this, message);
        sessionId = id;
        llMobile.setVisibility(View.GONE);
        llOtp.setVisibility(View.VISIBLE);
    }

    private void otpVerification(){
        showProgressIndicator();
        ApiClient.getApiInterface().otpVerification(tiEtEmail.getText().toString(),etOtp.getText().toString(), sessionId).enqueue(new Callback<ProductResult>() {
            @Override
            public void onResponse(Call<ProductResult> call, Response<ProductResult> response) {
                dismissProgressIndicator();
                if (response.isSuccessful()) {
                    ProductResult jsonObject = response.body();
                    if (jsonObject.getSuccess()==1) {
                        llMobile.setVisibility(View.GONE);
                        llOtp.setVisibility(View.GONE);
                        llChangePassword.setVisibility(View.VISIBLE);
                    } else {
                        CommonUtils.showToast(ForgotPasswordActivity.this, jsonObject.getMessage());
                    }

                } else {
                    CommonUtils.showToast(ForgotPasswordActivity.this, "Error");
                }
            }

            @Override
            public void onFailure(Call<ProductResult> call, Throwable t) {
                CommonUtils.showToast(ForgotPasswordActivity.this, "Error");
                dismissProgressIndicator();
            }
        });
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
            showProgressIndicator();
            ApiClient.getApiInterface().resetPassword(etOtp.getText().toString(), sessionId,tiEtEmail.getText().toString(),NEW_PASSWORD_HOLDER,CONFIRM_PASSWORD_HOLDER).enqueue(new Callback<ProductResult>() {
                @Override
                public void onResponse(Call<ProductResult> call, Response<ProductResult> response) {
                    dismissProgressIndicator();
                    if (response.isSuccessful()) {
                        ProductResult jsonObject = response.body();
                        if (jsonObject.getSuccess()==1) {
                            CommonUtils.showToast(ForgotPasswordActivity.this, jsonObject.getMessage());
                            finish();
                        } else {
                            CommonUtils.showToast(ForgotPasswordActivity.this, jsonObject.getMessage());
                        }

                    } else {
                        CommonUtils.showToast(ForgotPasswordActivity.this, "Error");
                    }
                }

                @Override
                public void onFailure(Call<ProductResult> call, Throwable t) {
                    CommonUtils.showToast(ForgotPasswordActivity.this, "Error");
                    dismissProgressIndicator();
                }
            });
        }
    }
}
