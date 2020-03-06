package com.presentation.app.dealsnest.ui.login_registration;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import com.presentation.app.dealsnest.R;
import com.presentation.app.dealsnest.app_pref.GlobalPreferManager;
import com.presentation.app.dealsnest.ui.dialog_fragment.ProgressDialogFragment;
import com.presentation.app.dealsnest.ui.forgot_password.ForgotPasswordActivity;
import com.presentation.app.dealsnest.ui.home.HomeMainActivity;
import com.presentation.app.dealsnest.utils.CommonUtils;
import com.presentation.app.dealsnest.utils.Constants;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, LoginRegisterViewInterface {

    private Button mLogin;
    private TextView mSignUp, mForgotPassword;
    private EditText mEmailID, mPassword;
    private String EmailHolder, PasswordHolder;
    private Button btnFacebookSignIn;
    private Button btnGoogleSignIn;
    private TextView tvLoginAsGuest;
    private LoginButton loginButton;
    private CallbackManager mFBCallbackManager;
    private String fromStr;
    private GoogleSignInOptions mGoogleSignInOptions;
    private GoogleSignInClient mGoogleSignInClient;
    private LoginRegisterPresenterInterface loginRegisterPresenterInterface;
    private ProgressDialogFragment progressDialogFragment;

    public static Intent getActivityIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        GlobalPreferManager.initializePreferenceManager(getApplicationContext());
        loginRegisterPresenterInterface = new LoginRegisterPresenter(this);
        initView();


        mGoogleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("777214460835-h0479v9gcod4q216io8d7oo6r8iv3hqi.apps.googleusercontent.com")
                .requestProfile()
                .requestEmail()
                .requestId()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, mGoogleSignInOptions);

        mFBCallbackManager = CallbackManager.Factory.create();
        handleFacebookSignInResult();
    }

    private void initView() {
        loginButton = findViewById(R.id.login_button);
        mEmailID = findViewById(R.id.edit_email);
        mPassword = findViewById(R.id.edit_password);
        mLogin = findViewById(R.id.btnLogin);
        mSignUp = findViewById(R.id.link_to_register);
        mForgotPassword = findViewById(R.id.forgot_password);
        btnFacebookSignIn = findViewById(R.id.btnFacebookSignIn);
        btnGoogleSignIn = findViewById(R.id.btnGoogleSignIn);
        tvLoginAsGuest = findViewById(R.id.tvLoginAsGuest);
        btnFacebookSignIn.setOnClickListener(this);
        btnGoogleSignIn.setOnClickListener(this);
        mLogin.setOnClickListener(this);
        mSignUp.setOnClickListener(this);
        mForgotPassword.setOnClickListener(this);
        tvLoginAsGuest.setOnClickListener(this);
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
            case R.id.link_to_register: {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                if (fromStr != null) {
                    if (fromStr.equals(Constants.FROM_INSIDE)) {
                        intent.putExtra(Constants.FROM_INSIDE, Constants.FROM_INSIDE);
                    }
                }
                startActivity(intent);
                break;
            }
            case R.id.forgot_password: {
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
        EmailHolder = mEmailID.getText().toString();
        PasswordHolder = mPassword.getText().toString();
        if (isValidated()) {
            loginRegisterPresenterInterface.userLogin(EmailHolder, PasswordHolder);
        }
    }

    private boolean isValidated() {
        if (EmailHolder.isEmpty()) {
            mEmailID.requestFocus();
            Toast.makeText(this, "Username is not entered", Toast.LENGTH_SHORT).show();
            return false;
        } else if (PasswordHolder.isEmpty()) {
            mPassword.requestFocus();
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


