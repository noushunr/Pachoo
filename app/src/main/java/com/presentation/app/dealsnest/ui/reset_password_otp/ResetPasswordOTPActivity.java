package com.presentation.app.dealsnest.ui.reset_password_otp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.presentation.app.dealsnest.R;
import com.presentation.app.dealsnest.app_pref.GlobalPreferManager;
import com.presentation.app.dealsnest.ui.dialog_fragment.ProgressDialogFragment;
import com.presentation.app.dealsnest.ui.reset_password.ResetPasswordActivity;
import com.presentation.app.dealsnest.utils.CommonUtils;

public class ResetPasswordOTPActivity extends AppCompatActivity implements View.OnClickListener, ResetPasswordViewInterface {

    private EditText mCodeOne, mCodeTwo, mCodeThree, mCodeFour;
    private Button mNext;
    private ResetPasswordPresenter resetPasswordPresenter;
    private ProgressDialogFragment progressDialogFragment;
    private String mId;
    private String PASSWORD_KEY, CODE_ONE, CODE_TWO, CODE_THREE, CODE_FOUR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password_otp);

        GlobalPreferManager.initializePreferenceManager(getApplicationContext());
        resetPasswordPresenter = new ResetPasswordPresenter(this);

        mId = getIntent().getStringExtra("id");

        initView();
    }

    private void initView() {
        mCodeOne = findViewById(R.id.code_one);
        mCodeTwo = findViewById(R.id.code_two);
        mCodeThree = findViewById(R.id.code_three);
        mCodeFour = findViewById(R.id.code_four);
        mNext = findViewById(R.id.button_next);

        mCodeOne.addTextChangedListener(new OtpManager(mCodeOne));
        mCodeTwo.addTextChangedListener(new OtpManager(mCodeTwo));
        mCodeThree.addTextChangedListener(new OtpManager(mCodeThree));
        mCodeFour.addTextChangedListener(new OtpManager(mCodeFour));

        mNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_next:
                VERIFY_PASSWORD();
                break;
        }
    }

    private void VERIFY_PASSWORD() {

        CODE_ONE = mCodeOne.getText().toString();
        CODE_TWO = mCodeTwo.getText().toString();
        CODE_THREE = mCodeThree.getText().toString();
        CODE_FOUR = mCodeFour.getText().toString();
        PASSWORD_KEY = CODE_ONE + CODE_TWO + CODE_THREE + CODE_FOUR;

        if (isValidated()) {
            resetPasswordPresenter.verify_password(PASSWORD_KEY, mId);
        }
    }

    private boolean isValidated() {
        if (CODE_ONE.isEmpty()) {
            mCodeOne.setError("Enter Code Please");
            mCodeOne.requestFocus();
        } else if (CODE_TWO.isEmpty()) {
            mCodeTwo.setError("Enter Code Please");
            mCodeTwo.requestFocus();

        } else if (CODE_THREE.isEmpty()) {
            mCodeThree.setError("Enter Code Please");
            mCodeThree.requestFocus();

        } else if (CODE_FOUR.isEmpty()) {
            mCodeFour.setError("Enter Code Please");
            mCodeFour.requestFocus();

        }
        return true;
    }

    @Override
    public void failedToReset(String message) {
        CommonUtils.showToast(this, message);
    }

    @Override
    public void onResetSuccess(String message) {
        CommonUtils.showToast(this, message);
        GlobalPreferManager.setBoolean(GlobalPreferManager.Keys.IS_REGISTERED, true);
        Intent intent = new Intent(ResetPasswordOTPActivity.this, ResetPasswordActivity.class);
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

    private class OtpManager implements android.text.TextWatcher {
        private View view;

        public OtpManager(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.code_one: {
                    if (!mCodeOne.getText().toString().equals("")) {
                        mCodeTwo.requestFocus();
                    }
                    break;
                }
                case R.id.code_two: {
                    if (mCodeTwo.getText().toString().equals("")) {
                        mCodeOne.requestFocus();
                    } else {
                        mCodeThree.requestFocus();
                    }
                    break;
                }
                case R.id.code_three: {
                    if (mCodeThree.getText().toString().equals("")) {
                        mCodeTwo.requestFocus();
                    } else {
                        mCodeFour.requestFocus();
                    }
                    break;
                }
                case R.id.code_four: {
                    if (mCodeFour.getText().toString().equals("")) {
                        mCodeThree.requestFocus();
                    }
                    break;
                }
            }
        }
    }
}
