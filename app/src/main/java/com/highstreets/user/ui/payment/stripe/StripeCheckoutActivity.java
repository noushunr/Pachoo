package com.highstreets.user.ui.payment.stripe;

import butterknife.BindView;
import butterknife.ButterKnife;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.highstreets.user.R;
import com.highstreets.user.ui.base.BaseActivity;
import com.highstreets.user.utils.Constants;
//import com.stripe.android.Stripe;
//import com.stripe.android.model.Card;
//import com.stripe.android.model.PaymentMethodCreateParams;
//import com.stripe.android.view.CardMultilineWidget;
//import com.stripe.model.Token;

import java.util.HashMap;
import java.util.Map;

public class StripeCheckoutActivity extends BaseActivity {

    private String paymentIntentClientSecret;
//    private Stripe stripe;
    @BindView(R.id.tvToolbarText)
    TextView tvToolbarText;
    @BindView(R.id.btnContinue)
    Button payButton;
//    @BindView(R.id.cardInputWidget)
//    CardMultilineWidget cardMultilineWidget;

    public static Intent start(Context context) {
        return new Intent(context, StripeCheckoutActivity.class);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_stripe_checkout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        tvToolbarText.setText(R.string.card_details);
//        stripe = new Stripe(
//                getApplicationContext(),
//                PaymentConfiguration.getInstance(getApplicationContext()).getPublishableKey()
//        );

//        com.stripe.Stripe.apiKey = "sk_test_51Gs1WzCkc2UppVTDWLbXKL4ruwmb3yRs0X3plzytX6uOYLZNnmG7hdDAyACdryOGsZfO8z8PopO6jgCBXcjqY9jQ00XFxKPckG";


//        PaymentIntentCreateParams params =
//                PaymentIntentCreateParams.builder()
//                        .setAmount(1099L)
//                        .setCurrency("usd")
//                        .build();
//
//        PaymentIntent intent = null;
//        try {
//            intent = PaymentIntent.create(params);
//            paymentIntentClientSecret = intent.getClientSecret();
//        startCheckout();
//        } catch (StripeException e) {
//            e.printStackTrace();
//            startCheckout();
//        }

    }


    @Override
    protected boolean setToolbar() {
        return true;
    }

    @Override
    public int setLayout() {
        return R.layout.activity_stripe_checkout;
    }

    @Override
    public void reloadPage() {

    }

//    private void startCheckout() {
//        // ...
//
//        // Hook up the pay button to the card widget and stripe instance
//        payButton.setOnClickListener((View view) -> {
//            PaymentMethodCreateParams params = cardMultilineWidget.getPaymentMethodCreateParams();
//            Card cardDetails = cardMultilineWidget.getCard();
//            if (cardDetails != null) {
//                Map<String, Object> card = new HashMap<>();
//                card.put("number", cardDetails.getNumber());
//                card.put("exp_month", cardDetails.getExpMonth());
//                card.put("exp_year", cardDetails.getExpYear());
//                card.put("cvc", "314");
////                Map<String, Object> parametrs = new HashMap<>();
////                parametrs.put("card", card);
//                Bundle bundle1 = new Bundle();
//                bundle1.putParcelable(Constants.STRIPE_PAY_CARD, cardDetails);
//                Intent intent = new Intent();
//                intent.putExtras(bundle1);
////                getIntent().putExtra(Constants.STRIPE_PAY_CARD, cardDetails);
//                setResult(Activity.RESULT_OK, intent);
//                finish();
//
////                try {
////                    new GetToken().execute(parametrs);
////                } catch (Exception e) {
////                    e.printStackTrace();
////                }
//            }
//
////            if (params != null) {
////                ConfirmPaymentIntentParams confirmParams = ConfirmPaymentIntentParams
////                        .createWithPaymentMethodCreateParams(params, "sk_test_51Gs1WzCkc2UppVTDWLbXKL4ruwmb3yRs0X3plzytX6uOYLZNnmG7hdDAyACdryOGsZfO8z8PopO6jgCBXcjqY9jQ00XFxKPckG");
////                final Context context = getApplicationContext();
////
////                stripe.confirmPayment(this, confirmParams);
////            }
//        });
//    }
//
//    private class GetToken extends AsyncTask<Map<String, Object>, Void, Token> {
//
//        @Override
//        protected Token doInBackground(Map<String, Object>... params) {
//            try {
//                Token token = Token.create(params[0]);
//                return token;
//            } catch (Exception e) {
//                e.printStackTrace();
//                return null;
//            }
//        }
//
//        @Override
//        protected void onPostExecute(Token result) {
//
//        }
//    }
}
