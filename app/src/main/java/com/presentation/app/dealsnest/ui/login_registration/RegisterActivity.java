package com.presentation.app.dealsnest.ui.login_registration;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

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
import com.presentation.app.dealsnest.R;
import com.presentation.app.dealsnest.app_pref.GlobalPreferManager;
import com.presentation.app.dealsnest.ui.dialog_fragment.ProgressDialogFragment;
import com.presentation.app.dealsnest.ui.home.HomeMainActivity;
import com.presentation.app.dealsnest.utils.CommonUtils;
import com.presentation.app.dealsnest.utils.Constants;

import org.json.JSONObject;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, LoginRegisterViewInterface, RadioGroup.OnCheckedChangeListener {

    private static final String TAG = RegisterActivity.class.getSimpleName();
    private LoginRegisterPresenterInterface loginRegisterPresenterInterface;
    private ProgressDialogFragment progressDialogFragment;
    private GoogleSignInOptions mGoogleSignInOptions;
    private GoogleSignInClient mGoogleSignInClient;
    private String fromStr;
    private int PasswordLength = 8;
    private CallbackManager mFBCallbackManager;
    private LoginManager mFBLoginManager;
    private String FirstNameHolder, LastNameHolder, EmailHolder, PasswordHolder, ConfirmPasswordHolder, MobileHolder, GenderHolder,profileImage;


    @BindView(R.id.edit_first_name)
    EditText  mFirstName;
    @BindView(R.id.edit_last_name)
    EditText  mLastName;
    @BindView(R.id.edit_email_id)
    EditText  mEmailID;
    @BindView(R.id.edit_password)
    EditText  mPassword;
    @BindView(R.id.edit_confirm_password)
    EditText  mConfirmPassword;
    @BindView(R.id.edit_mobile)
    EditText  mMobile;
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
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        loginRegisterPresenterInterface = new LoginRegisterPresenter(this);

        mFirstName = findViewById(R.id.edit_first_name);
        mLastName = findViewById(R.id.edit_last_name);
        mEmailID = findViewById(R.id.edit_email_id);
        mPassword = findViewById(R.id.edit_password);
        mConfirmPassword = findViewById(R.id.edit_confirm_password);
        mMobile = findViewById(R.id.edit_mobile);
        mGenderGroup = findViewById(R.id.radio_group);
        btnToLogin = findViewById(R.id.btnToLogin);
        mRegister = findViewById(R.id.btnRegister);
        btnFacebookSignIn = findViewById(R.id.btnFacebookSignIn);
        btnGoogleSignIn = findViewById(R.id.btnGoogleSignIn);

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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnToLogin: {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                if (fromStr != null) {
                    if (fromStr.equals(Constants.FROM_INSIDE)) {
                        intent.putExtra(Constants.FROM_INSIDE, Constants.FROM_INSIDE);
                    }
                }
                startActivity(intent);
                finish();
                break;
            }
            case R.id.btnRegister: {
                USER_REGISTRATION();
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

    private void USER_REGISTRATION() {
        FirstNameHolder = mFirstName.getText().toString();
        LastNameHolder = mLastName.getText().toString();
        EmailHolder = mEmailID.getText().toString();
        PasswordHolder = mPassword.getText().toString();
        ConfirmPasswordHolder = mConfirmPassword.getText().toString();
        MobileHolder = mMobile.getText().toString();
        RadioButton checkedGender = mGenderGroup.findViewById(mGenderGroup.getCheckedRadioButtonId());
        GenderHolder = checkedGender.getText().toString();

        if (isValidated()) {
            loginRegisterPresenterInterface.userRegister(FirstNameHolder,
                    LastNameHolder,
                    EmailHolder,
                    PasswordHolder,
                    ConfirmPasswordHolder,
                    MobileHolder,
                    GenderHolder);

        }

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

    private boolean isValidated() {

        if (FirstNameHolder.isEmpty()) {
            mFirstName.requestFocus();
            Toast.makeText(this, "First Name is not entered", Toast.LENGTH_SHORT).show();
            return false;
        } else if (LastNameHolder.isEmpty()) {
            mLastName.requestFocus();
            Toast.makeText(this, "Last Name is not entered", Toast.LENGTH_SHORT).show();
            return false;
        } else if (EmailHolder.isEmpty()) {
            mEmailID.requestFocus();
            Toast.makeText(this, "Email ID is not entered", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!EmailHolder.matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")) {
            mEmailID.requestFocus();
            Toast.makeText(this, "Email ID is not valid", Toast.LENGTH_SHORT).show();
            return false;

        } else if (PasswordHolder.isEmpty()) {
            mPassword.requestFocus();
            Toast.makeText(this, "Password is not entered", Toast.LENGTH_SHORT).show();
            return false;
        } else if (PasswordHolder.length() < PasswordLength) {
            mPassword.requestFocus();
            Toast.makeText(this, "Password is less than 8 Characters", Toast.LENGTH_SHORT).show();
            return false;
        } else if (ConfirmPasswordHolder.isEmpty()) {
            mConfirmPassword.requestFocus();
            Toast.makeText(this, "Confirm Password is not entered", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!PasswordHolder.equals(ConfirmPasswordHolder)) {
            mConfirmPassword.requestFocus();
            Toast.makeText(this, "Password Mismatched", Toast.LENGTH_SHORT).show();
            return false;
        } else if (MobileHolder.isEmpty()) {
            mMobile.requestFocus();
            Toast.makeText(this, "Mobile is not entered", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!MobileHolder.matches("^[6-9]\\d{9}$")) {
            mMobile.requestFocus();
            Toast.makeText(this, "Please Enter Valid 10 Digits Number", Toast.LENGTH_SHORT).show();
            return false;

        }

        return true;
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
