package com.highstreets.user.ui.payment.stripe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.highstreets.user.R;

public class StripeCheckoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stripe_checkout);
    }
}
