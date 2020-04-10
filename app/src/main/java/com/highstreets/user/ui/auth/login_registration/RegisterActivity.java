package com.highstreets.user.ui.auth.login_registration;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.highstreets.user.R;
import com.highstreets.user.app_pref.GlobalPreferManager;
import com.highstreets.user.ui.base.BaseActivity;
import com.highstreets.user.ui.dialog_fragment.ProgressDialogFragment;
import com.highstreets.user.ui.main.HomeMainActivity;
import com.highstreets.user.utils.CommonUtils;
import com.highstreets.user.utils.Constants;

import org.json.JSONObject;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RegisterActivity extends BaseActivity implements
        View.OnClickListener,
        LoginRegisterViewInterface,
        RadioGroup.OnCheckedChangeListener {

    private static final String TAG = RegisterActivity.class.getSimpleName();
    private LoginRegisterPresenterInterface loginRegisterPresenterInterface;
    private ProgressDialogFragment progressDialogFragment;
    private GoogleSignInOptions mGoogleSignInOptions;
    private GoogleSignInClient mGoogleSignInClient;
    private int PasswordLength = 8;
    private CallbackManager mFBCallbackManager;
    private LoginManager mFBLoginManager;
    private String  GenderHolder, profileImage;

    @BindView(R.id.itEtFirstName)
    TextInputEditText itEtFirstName;
    @BindView(R.id.tiEtLastName)
    TextInputEditText tiEtLastName;
    @BindView(R.id.tiEtEmailID)
    TextInputEditText tiEtEmailID;
    @BindView(R.id.tiEtPassword)
    TextInputEditText tiEtPassword;
    @BindView(R.id.tiEtConfirmPassword)
    TextInputEditText tiEtConfirmPassword;
    @BindView(R.id.tiEtMobile)
    TextInputEditText tiEtMobile;
    @BindView(R.id.radio_group)
    RadioGroup mGenderGroup;
    @BindView(R.id.btnToLogin)
    Button btnToLogin;
    @BindView(R.id.btnRegister)
    Button mRegister;
    @BindView(R.id.btnFacebookSignIn)
    Button btnFacebookSignIn;
    @BindView(R.id.btnGoogleSignIn)
    Button btnGoogleSignIn;

    public static Intent start(Context context){
        return new Intent(context, RegisterActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        loginRegisterPresenterInterface = new LoginRegisterPresenter(this);

        btnToLogin.setOnClickListener(this);
        mRegister.setOnClickListener(this);
        mGenderGroup.setOnCheckedChangeListener(this);
        mGenderGroup.getCheckedRadioButtonId();
        btnFacebookSignIn.setOnClickListener(this);
        btnGoogleSignIn.setOnClickListener(this);

        mGoogleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestProfile()
                .requestEmail()
                .requestId()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, mGoogleSignInOptions);

        mFBCallbackManager = CallbackManager.Factory.create();
        mFBLoginManager = LoginManager.getInstance();
        mFBLoginManager.registerCallback(mFBCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                loginResult.getAccessToken();
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        String email = object.optString("email");
                        String name = object.optString("name");
                        String id = object.optString("id");
                        String profileImage = String.format("http://graph.facebook.com/%s/picture?type=square", id);

                        loginRegisterPresenterInterface.userRegisterSocial(
                                Constants.TYPE_SOCIAL_FACEBOOK,
                                id,
                                name,
                                email,
                                profileImage);
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,picture");
                request.setParameters(parameters);
                request.executeAsync();
            }


            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    @Override
    protected boolean setToolbar() {
        return false;
    }

    @Override
    public int setLayout() {
        return R.layout.activity_register;
    }

    @Override
    public void reloadPage() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnToLogin: {
                startActivity(LoginActivity.start(this));
                finish();
                break;
            }
            case R.id.btnRegister: {
                validateFields();
                break;
            }
            case R.id.btnFacebookSignIn: {
                facebookSignIn();
                break;
            }
            case R.id.btnGoogleSignIn: {
                googleSignIn();
                break;
            }
        }
    }

    private void facebookSignIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        if (isLoggedIn) {
            LoginManager.getInstance().logOut();
            AccessToken.setCurrentAccessToken(null);
        } else {
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "public_profile"));
        }

    }

    private void googleSignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, Constants.GOOGLE_SIGN_IN_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        mFBCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.GOOGLE_SIGN_IN_CODE) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleGoogleSignInResult(task);
        }
    }


    private void handleGoogleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            String id = account.getId();
            String name = account.getDisplayName();
            String email = account.getEmail();
            if (account.getPhotoUrl()!=null) {
                profileImage = account.getPhotoUrl().toString();

            }

            loginRegisterPresenterInterface.userRegisterSocial(
                    Constants.TYPE_SOCIAL_GOOGLE,
                    id,
                    name,
                    email,
                    profileImage);

        } catch (ApiException e) {
        }
    }

    private void validateFields() {
        String emailId = tiEtEmailID.getText().toString();
        String password = tiEtPassword.getText().toString();
        String confirmPassword = tiEtConfirmPassword.getText().toString();
        String mobile = tiEtMobile.getText().toString();
        RadioButton checkedGender = mGenderGroup.findViewById(mGenderGroup.getCheckedRadioButtonId());

        if (TextUtils.isEmpty(itEtFirstName.getText())) {
            itEtFirstName.requestFocus();
            CommonUtils.showToast(this, "First Name is not entered");
        } else if (TextUtils.isEmpty(tiEtLastName.getText())) {
            tiEtLastName.requestFocus();
            CommonUtils.showToast(this, "Last Name is not entered");
        } else if (TextUtils.isEmpty(emailId)) {
            tiEtEmailID.requestFocus();
            CommonUtils.showToast(this, "Email ID is not entered");
        } else if (!emailId.matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")) {
            tiEtEmailID.requestFocus();
            CommonUtils.showToast(this, "Email ID is not valid");
        } else if (TextUtils.isEmpty(password)) {
            tiEtPassword.requestFocus();
            CommonUtils.showToast(this, "Password is not entered");
        } else if (password.length() < PasswordLength) {
            tiEtPassword.requestFocus();
            CommonUtils.showToast(this, "Password is less than 8 Characters");
        } else if (TextUtils.isEmpty(confirmPassword)) {
            tiEtConfirmPassword.requestFocus();
            CommonUtils.showToast(this, "Confirm Password is not entered");
        } else if (!password.equals(confirmPassword)) {
            tiEtConfirmPassword.requestFocus();
            CommonUtils.showToast(this, "Password Mismatched");
        } else if (TextUtils.isEmpty(mobile)) {
            tiEtMobile.requestFocus();
            CommonUtils.showToast(this, "Mobile is not entered");
        } else if (!mobile.matches("^[6-9]\\d{9}$")) {
            tiEtMobile.requestFocus();
            CommonUtils.showToast(this, "Please Enter Valid 10 Digits Number");
        } else {
            loginRegisterPresenterInterface.userRegister(tiEtLastName.getText().toString(),
                    tiEtLastName.getText().toString(),
                    emailId,
                    password,
                    confirmPassword,
                    mobile,
                    checkedGender.getText().toString());
        }
    }

    @Override
    public void failedToSignIn(String message) {
        CommonUtils.showToast(this, message);
    }

    @Override
    public void onSighInSuccess(String message) {
        CommonUtils.showToast(this, message);
        GlobalPreferManager.setBoolean(GlobalPreferManager.Keys.IS_LOGIN, true);
        startActivity(new Intent(this, HomeMainActivity.class));
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

    @SuppressLint("ResourceType")
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        RadioButton rb = group.findViewById(checkedId);
        if (null != rb && checkedId > -1) {
            GenderHolder = rb.getText().toString();
        }
    }
}
