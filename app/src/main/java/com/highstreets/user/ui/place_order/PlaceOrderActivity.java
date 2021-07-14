package com.highstreets.user.ui.place_order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.highstreets.user.R;
import com.highstreets.user.app_pref.SharedPrefs;
import com.highstreets.user.models.ProductResult;
import com.highstreets.user.models.Result;
import com.highstreets.user.models.Success;
import com.highstreets.user.ui.SplashActivity;
import com.highstreets.user.ui.address.add_address.model.PostResponse;
import com.highstreets.user.ui.base.BaseActivity;
import com.highstreets.user.ui.cart.model.CartData;
import com.highstreets.user.ui.main.HomeMainActivity;
import com.highstreets.user.ui.main.MoreCategoriesActivity;
import com.highstreets.user.ui.payment.RazorPayHelper;
import com.highstreets.user.ui.payment.stripe.StripeCheckoutActivity;
import com.highstreets.user.ui.place_order.model.FinalBalanceItem;
import com.highstreets.user.ui.place_order.model.payment.MakePaymentResponse;
import com.highstreets.user.ui.review_booking.adapter.ReviewBookingAdapter;
import com.highstreets.user.utils.CommonUtils;
import com.highstreets.user.utils.Constants;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;
//import com.stripe.android.model.Card;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlaceOrderActivity extends BaseActivity implements PlaceOrderViewInterface, PaymentResultWithDataListener {

    private static final String TAG = PlaceOrderActivity.class.getSimpleName();
    private static int STRIPE_PAY_ACTIVITY_CODE = 123;
    private String userId;
    private String addressId;
    private PlaceOrderPresenterInterface placeOrderPresenterInterface;
    private String grandTotal;

    @BindView(R.id.tvToolbarText)
    TextView tvToolbarText;
    @BindView(R.id.tvSubTotal)
    TextView tvSubTotal;
    @BindView(R.id.tvDeliveryCharge)
    TextView tvDeliveryCharge;
    @BindView(R.id.tvServiceCharge)
    TextView tvServiceCharge;
    @BindView(R.id.tvGrandTotal)
    TextView tvGrandTotal;
    @BindView(R.id.btnPlaceOrder)
    Button btnPlaceOrder;
    @BindView(R.id.rvOrderItems)
    RecyclerView rvOrderItems;
    double totalPrice;
    public static Intent start(Context context){
        return new Intent(context, PlaceOrderActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tvToolbarText.setText(R.string.confirm_orders);
        userId = SharedPrefs.getString(SharedPrefs.Keys.USER_ID, "");
        addressId = String.valueOf(getIntent().getIntExtra(Constants.ADDRESS_ID,0));
        rvOrderItems.setLayoutManager(new LinearLayoutManager(this));
        rvOrderItems.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        placeOrderPresenterInterface = new PlaceOrderPresenter(this);
        placeOrderPresenterInterface.getCartProducts(userId);
        btnPlaceOrder.setEnabled(true);
        clickHandles();
    }

    private void clickHandles() {
        btnPlaceOrder.setOnClickListener(view -> {

            placeOrderPresenterInterface.placeOrder("",addressId,"C");
//            startActivityForResult(StripeCheckoutActivity.start(this), STRIPE_PAY_ACTIVITY_CODE);
//            startActivity(new Intent(PlaceOrderActivity.this, StripeCheckoutActivity.class));
        });
    }

    @Override
    protected boolean setToolbar() {
        return true;
    }

    @Override
    public int setLayout() {
        return R.layout.activity_place_order;
    }

    @Override
    public void reloadPage() {

    }

    @Override
    public void onResponseFailed(String message) {

    }

    @Override
    public void onServerError(String message) {

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


    @Override
    public void setFinalBalance(FinalBalanceItem finalBalanceItem) {
        if (finalBalanceItem != null) {
            this.grandTotal = finalBalanceItem.getGrandTotal();
            String subTotal = getString(R.string.pound_symbol) + finalBalanceItem.getSubtotal();
            String deliveryCharge = getString(R.string.pound_symbol) + finalBalanceItem.getDeliveryCharge();
            String serviceCharge = getString(R.string.pound_symbol) + finalBalanceItem.getServiceCharge();
            String grandTotal = getString(R.string.pound_symbol) + finalBalanceItem.getGrandTotal();
            tvSubTotal.setText(subTotal);
            tvDeliveryCharge.setText(deliveryCharge);
            tvServiceCharge.setText(serviceCharge);
            tvGrandTotal.setText(grandTotal);
            btnPlaceOrder.setEnabled(true);
        }
    }

    @Override
    public void setPlaceOrderResponse(ProductResult postResponse) {
        if (postResponse != null){
            if (postResponse.getSuccess() == 0){
                CommonUtils.showToast(this, postResponse.getMessage());
            } else {
                CommonUtils.showToast(this, "Successfully place the order");
                SharedPrefs.setInt(SharedPrefs.Keys.CART_COUNT, 0);
//        Intent toAllCategories = MoreCategoriesActivity.start(PlaceOrderActivity.this);
//        SharedPrefs.setString(SharedPrefs.Keys.MERCHANT_ID, "16");
//        toAllCategories.putExtra(Constants.MERCHANT_ID, "16");
//        startActivity(toAllCategories);
                Intent homeIntent = HomeMainActivity.start(this,false);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(homeIntent);
//                if (postResponse.getData()!=null){
//                    RazorPayHelper razorPayHelper = new RazorPayHelper(PlaceOrderActivity.this, totalPrice,postResponse.getData().getRazorpayOrderId(),"rzp_test_Wsk6F7p43i6gmz");
//                    razorPayHelper.startPayment();
//                }

//                CommonUtils.showToast(this, postResponse.getMessage());
//                SharedPrefs.setString(SharedPrefs.Keys.CART_COUNT, "");
//                Intent homeIntent = HomeMainActivity.start(this,false);
//                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(homeIntent);
//                Intent toAllCategories = MoreCategoriesActivity.start(PlaceOrderActivity.this);
//                SharedPrefs.setString(SharedPrefs.Keys.MERCHANT_ID, "16");
//                toAllCategories.putExtra(Constants.MERCHANT_ID, "16");
//                startActivity(toAllCategories);
            }
        }
    }

    @Override
    public void setCartData(List<Success> cartData) {
        rvOrderItems.setAdapter(new ReviewBookingAdapter(this, cartData));
        totalPrice = 0;
        for (int i=0;i<cartData.size();i++){
            totalPrice=totalPrice+((Double.parseDouble(cartData.get(i).getSellingPrice()))*Integer.parseInt(cartData.get(i).getQuantity()));
        }
        tvGrandTotal.setText(getString(R.string.pound_symbol) + totalPrice);
//        placeOrderPresenterInterface.getFinalBalance(userId, addressId);
    }

    @Override
    public void paymentSuccess(ProductResult makePaymentResponse) {
        CommonUtils.showToast(this, "Successfully place the order");
        SharedPrefs.setInt(SharedPrefs.Keys.CART_COUNT, 0);
//        Intent toAllCategories = MoreCategoriesActivity.start(PlaceOrderActivity.this);
//        SharedPrefs.setString(SharedPrefs.Keys.MERCHANT_ID, "16");
//        toAllCategories.putExtra(Constants.MERCHANT_ID, "16");
//        startActivity(toAllCategories);
        Intent homeIntent = HomeMainActivity.start(this,false);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(homeIntent);
    }

    @Override
    public void paymentError(ProductResult makePaymentResponse) {
        CommonUtils.showToast(this, "Some problem with payment please try again");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == STRIPE_PAY_ACTIVITY_CODE) {
            if (resultCode == RESULT_OK) {
//                String token = data.getStringExtra(Constants.WORLD_PAY_CARD_TOKEN);,
//                Card cardDetails = (Card)data.getParcelableExtra(Constants.STRIPE_PAY_CARD);
//                String card_number = cardDetails.getNumber();
//                String exp_month = String.valueOf(cardDetails.getExpMonth());
//                String exp_year = String.valueOf(cardDetails.getExpYear());
//                String cvc = cardDetails.getCvc();
//                Double total = Double.parseDouble(grandTotal);
//                int totalValue = (int) (total * 100);
//                Log.e(TAG, "onActivityResultToken: "+ token);
//                Log.e(TAG, "onActivityResultCustomerId: "+ userId);
//                Log.e(TAG, "onActivityResultAddressId: "+ addressId);
//                Log.e(TAG, "onActivityResultAmount: "+ grandTotal);
//                placeOrderPresenterInterface.makePayment(userId, addressId, String.valueOf(totalValue), card_number,exp_month,exp_year,cvc);
            }
        }
    }

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {

        if (paymentData!=null){
            Log.d("Order_id", paymentData.getOrderId());
            placeOrderPresenterInterface.makePayment("",paymentData.getOrderId(),"1");
        }

    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {

    }
}
