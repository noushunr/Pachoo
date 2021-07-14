package com.highstreets.user.ui.payment;

import android.app.Activity;
import android.widget.Toast;

import com.highstreets.user.R;
import com.razorpay.Checkout;

import org.json.JSONObject;

public class RazorPayHelper {
    private Activity activity;
    private Double amount;
    private String orderId;
    private String apiKey;

    public RazorPayHelper(Activity activity, Double amount, String orderId, String apiKey) {
        this.activity = activity;
        this.amount = amount;
        this.orderId = orderId;
        this.apiKey = apiKey;
    }

    public void startPayment() {
        double mTotalPrice = amount;
        double priceInDecimals = Math.round(mTotalPrice * 100);
        final Checkout co = new Checkout();
        try {
            if(priceInDecimals< 100)
            {
                priceInDecimals = 100;
            }
            int price = (int) priceInDecimals;

            co.setPublicKey(apiKey);
            JSONObject options = new JSONObject();
            options.put("name", activity.getString(R.string.app_name));
            options.put("description", activity.getString(R.string.app_name));
            //You can omit the image option to fetch the image from dashboard
            // options.put("image", "https://rzp-mobile.s3.amazonaws.com/images/rzp.png");
            options.put("currency", "USD");
            options.put("amount", Double.toString(priceInDecimals));
            options.put("order_id",orderId);
            JSONObject preFill = new JSONObject();
            options.put("prefill", preFill);
            co.open(activity, options);


        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }
}
