package com.highstreets.user.ui.payment.worldpay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.highstreets.user.R;
import com.highstreets.user.ui.base.BaseActivity;
import com.highstreets.user.utils.CommonUtils;
import com.highstreets.user.utils.Constants;
import com.worldpay.Card;
import com.worldpay.CardValidationError;
import com.worldpay.HttpServerResponse;
import com.worldpay.ResponseCard;
import com.worldpay.ResponseError;
import com.worldpay.WorldPay;
import com.worldpay.WorldPayError;
import com.worldpay.WorldPayResponse;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WorldPayActivity extends BaseActivity {

    private static final String TAG = WorldPayActivity.class.getSimpleName();
    private WorldPay worldpay;

    @BindView(R.id.tvToolbarText)
    TextView tvToolbarText;
    @BindView(R.id.etNameOnCard)
    EditText etNameOnCard;
    @BindView(R.id.etCardNo)
    EditText etCardNo;
    @BindView(R.id.etExpiryMonth)
    EditText etExpiryMonth;
    @BindView(R.id.etExpiryYear)
    EditText etExpiryYear;
    @BindView(R.id.etCVV)
    EditText etCVV;
    @BindView(R.id.btnContinue)
    Button btnContinue;

    public static Intent start(Context context) {
        return new Intent(context, WorldPayActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        tvToolbarText.setText(R.string.card_details);
        worldpay = WorldPay.getInstance();
        worldpay.setClientKey("T_C_34cf103b-ffb0-415d-a3fb-b7a57c743d09");
        // decide whether you want to charge this card multiple times or only once
        worldpay.setReusable(true);
        // set validation type advanced or basic
        Card.setValidationType(Card.VALIDATION_TYPE_ADVANCED);
        clickHandles();
    }

    private void clickHandles() {
        btnContinue.setOnClickListener(view -> {
            validateCard();
        });
    }

    private void validateCard(){
        Card card = new Card();

        CardValidationError validate = card.setHolderName(etNameOnCard.getText().toString())
                .setCardNumber(etCardNo.getText().toString())
                .setCvc(etCVV.getText().toString())
                .setExpiryMonth(etExpiryMonth.getText().toString())
                .setExpiryYear(etExpiryYear.getText().toString())
                .validate();

//        CardValidationError validate = card.setHolderName("Asgar Ahmed")
//                .setCardNumber("4462030000000000")
//                .setCvc("123")
//                .setExpiryMonth("09")
//                .setExpiryYear("2024")
//                .validate();

        if (validate != null) {
            //we got errors
            if (validate.hasError(CardValidationError.ERROR_CARD_EXPIRY)) {
                CommonUtils.showToast(this, "Card expiry error");
            }
            if (validate.hasError(CardValidationError.ERROR_HOLDER_NAME)) {
                CommonUtils.showToast(this, "Card Name error");
            }
            if (validate.hasError(CardValidationError.ERROR_CVC)) {
                CommonUtils.showToast(this, "cvv error");
            }
            if (validate.hasError(CardValidationError.ERROR_CARD_NUMBER)) {
                CommonUtils.showToast(this, "Card number error");
            }
        } else {
            callWorldPayAsync(card);
        }
    }

    private void callWorldPayAsync(Card card) {
        AsyncTask<Void, Void, HttpServerResponse> createTokenAsyncTask = worldpay.createTokenAsyncTask(this, card, new WorldPayResponse() {
            @Override
            public void onSuccess(ResponseCard response) {
                Log.e(TAG, "onSuccess: " + response.getToken());
                setToken(response.getToken());
            }

            @Override
            public void onResponseError(ResponseError responseError) {
                Log.e(TAG, "onResponseError: " + responseError.getMessage());
            }

            @Override
            public void onError(WorldPayError worldPayError) {
                Log.e(TAG, "onError: " + worldPayError.getMessage());
            }
        });

        if (createTokenAsyncTask != null) {
            createTokenAsyncTask.execute();
        }
    }

    private void setToken(String token) {
        getIntent().putExtra(Constants.WORLD_PAY_CARD_TOKEN, token);
        setResult(Activity.RESULT_OK, getIntent());
        finish();
    }

    @Override
    protected boolean setToolbar() {
        return true;
    }

    @Override
    public int setLayout() {
        return R.layout.activity_world_pay;
    }

    @Override
    public void reloadPage() {

    }
}
