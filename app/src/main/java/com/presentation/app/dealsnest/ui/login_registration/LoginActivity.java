package com.presentation.app.dealsnest.ui.login_registration;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.presentation.app.dealsnest.R;
import com.presentation.app.dealsnest.app_pref.GlobalPreferManager;
import com.presentation.app.dealsnest.ui.BaseActivity;
import com.presentation.app.dealsnest.ui.dialog_fragment.ProgressDialogFragment;
import com.presentation.app.dealsnest.ui.forgot_password.ForgotPasswordActivity;
import com.presentation.app.dealsnest.ui.home.HomeMainActivity;
import com.presentation.app.dealsnest.utils.CommonUtils;
import com.presentation.app.dealsnest.utils.Constants;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity implements View.OnClickListener, LoginRegisterViewInterface {

    private String EmailHolder, PasswordHolder;
    private CallbackManager mFBCallbackManager;
    private String fromStr;
    private GoogleSignInClient mGoogleSignInClient;
    private LoginRegisterPresenterInterface loginRegisterPresenterInterface;
    private ProgressDialogFragment progressDialogFragment;

    @BindView(R.id.loginButton)
    LoginButton loginButton;
    @BindView(R.id.tiEtEmail)
    TextInputEditText tiEtEmail;
    @BindView(R.id.tiEtPassword)
    TextInputEditText tiEtPassword;
    @BindView(R.id.btnLogin)
    Button mLogin;
    @BindView(R.id.tvSignUp)
    TextView tvSignUp;
    @BindView(R.id.tvForgotPassword)
    TextView tvForgotPassword;
    @BindView(R.id.btnFacebookSignIn)
    Button btnFacebookSignIn;
    @BindView(R.id.btnGoogleSignIn)
    Button btnGoogleSignIn;
    @BindView(R.id.tvLoginAsGuest)
    TextView tvLoginAsGuest;

    public static Intent getActivityIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);

        btnFacebookSignIn.setOnClickListener(this);
        btnGoogleSignIn.setOnClickListener(this);
        mLogin.setOnClickListener(this);
        tvSignUp.setOnClickListener(this);
        tvForgotPassword.setOnClickListener(this);
        tvLoginAsGuest.setOnClickListener(this);

        GlobalPreferManager.initializePreferenceManager(getApplicationContext());
        loginRegisterPresenterInterface = new LoginRegisterPresenter(this);
        initView();

        GoogleSignInOptions mGoogleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestProfile()
                .requestEmail()
                .requestId()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, mGoogleSignInOptions);

        mFBCallbackManager = CallbackManager.Factory.create();
        handleFacebookSignInResult();
    }

    private void initView() {


    }

    private void facebookSignIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        if (isLoggedIn) {
            LoginManager.getInstance().logOut();
            AccessToken.setCurrentAccessToken(null);
        } else {
            loginButton.performClick();
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

            if (!handleGoogleSignInResult(task)) {
                mGoogleSignInClient.signOut();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        googleSignIn();

                    }
                }, 500);
            }
        }
    }

    @Override
    protected boolean setToolbar() {
        return false;
    }

    @Override
    public int setLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void reloadPage() {

    }

    private boolean handleGoogleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if (account == null || account.getDisplayName() == null)
                return false;

            String id = account.getId();
            String name = account.getDisplayName();
            String email = account.getEmail();
            String profileImage = account.getPhotoUrl() != null ? account.getPhotoUrl().toString() : "";
            loginRegisterPresenterInterface.userRegisterSocial(
                    Constants.TYPE_SOCIAL_GOOGLE,
                    id,
                    name,
                    email,
                    profileImage);

        } catch (ApiException e) {
            e.printStackTrace();
        }
        return true;
    }

    private void handleFacebookSignInResult() {
        loginButton.registerCallback(mFBCallbackManager, new FacebookCallback<LoginResult>() {
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
            public void onError(FacebookException exception) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin: {
                LOGIN_USER();
                break;
            }
            case R.id.tvSignUp: {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                if (fromStr != null) {
                    if (fromStr.equals(Constants.FROM_INSIDE)) {
                        intent.putExtra(Constants.FROM_INSIDE, Constants.FROM_INSIDE);
                    }
                }
                startActivity(intent);
                break;
            }
            case R.id.tvForgotPassword: {
                Intent i = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
                if (fromStr != null) {
                    if (fromStr.equals(Constants.FROM_INSIDE)) {
                        i.putExtra(Constants.FROM_INSIDE, Constants.FROM_INSIDE);
                    }
                }
                startActivity(i);
                break;
            }
            case R.id.btnGoogleSignIn: {
                googleSignIn();
                break;
            }
            case R.id.btnFacebookSignIn: {
                facebookSignIn();
                break;
            }
            case R.id.tvLoginAsGuest: {
                startActivity(new Intent(this, HomeMainActivity.class));
                finishAffinity();
                break;
            }
        }
    }

    private void LOGIN_USER() {
        EmailHolder = tiEtEmail.getText().toString();
        PasswordHolder = tiEtPassword.getText().toString();
        if (isValidated()) {
            loginRegisterPresenterInterface.userLogin(EmailHolder, PasswordHolder);
        }
    }

    private boolean isValidated() {
        if (EmailHolder.isEmpty()) {
            tiEtEmail.requestFocus();
            Toast.makeText(this, "Username is not entered", Toast.LENGTH_SHORT).show();
            return false;
        } else if (PasswordHolder.isEmpty()) {
            tiEtPassword.requestFocus();
            Toast.makeText(this, "Password is not entered", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
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


    @Override
    public void failedToSignIn(String message) {
        CommonUtils.showToast(this, message);
    }

    @Override
    public void onSighInSuccess(String message) {
        GlobalPreferManager.setBoolean(GlobalPreferManager.Keys.IS_REGISTERED, true);
        GlobalPreferManager.setBoolean(GlobalPreferManager.Keys.IS_LOGIN, true);
        startActivity(new Intent(this, HomeMainActivity.class));
        finishAffinity();
    }

}


