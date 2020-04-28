package com.highstreets.user.ui.payment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.highstreets.user.R;
import com.highstreets.user.ui.base.BaseActivity;
import com.highstreets.user.ui.payment.adapter.PaymentTypeAdapter;
import com.highstreets.user.ui.payment.model.PaymentType;
import com.highstreets.user.utils.CommonUtils;
import com.highstreets.user.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PaymentOptionsActivity extends BaseActivity {

    private List<PaymentType> paymentTypeList;
    private PaymentType paymentTypeSelected;

    @BindView(R.id.tvToolbarText)
    TextView tvToolbarText;
    @BindView(R.id.rvPaymentOptions)
    RecyclerView rvPaymentOptions;
    @BindView(R.id.btnContinue)
    Button btnContinue;

    public static Intent start(Context context){
        return new Intent(context, PaymentOptionsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        tvToolbarText.setText(R.string.select_option);
        rvPaymentOptions.setLayoutManager(new LinearLayoutManager(this));
        paymentTypeList = getPaymentTypes();
        rvPaymentOptions.setAdapter(new PaymentTypeAdapter(paymentTypeList));

        btnContinue.setOnClickListener(view -> {
            if (checkSelection()) {
//                CommonUtils.showToast(this, paymentTypeSelected.getType());
                Intent selectedOptionIntent = new Intent();
                selectedOptionIntent.putExtra(Constants.PAYMENT_TYPE, paymentTypeSelected.getId());
                setResult(Activity.RESULT_OK, selectedOptionIntent);
                finish();
            } else {
                CommonUtils.showToast(this, getString(R.string.select_an_option));
            }
        });
    }

    private boolean checkSelection() {
        for (PaymentType paymentType : paymentTypeList){
            if (paymentType.isSelected()){
                paymentTypeSelected = paymentType;
                return true;
            }
        }
        return false;
    }

    private List<PaymentType> getPaymentTypes(){
        List<PaymentType> paymentTypeList = new ArrayList<>();
//        paymentTypeList.add(new PaymentType(Constants.PAYMENT_TYPE_CASH_ON_DELIVERY, getString(R.string.cash_on_delivery)));
        paymentTypeList.add(new PaymentType(Constants.PAYMENT_TYPE_PAY_NOW, getString(R.string.pay_now)));
        paymentTypeList.add(new PaymentType(Constants.PAYMENT_TYPE_COLLECT_FROM_SHOP, getString(R.string.collect_from_shop)));
        return paymentTypeList;
    }

    @Override
    protected boolean setToolbar() {
        return true;
    }

    @Override
    public int setLayout() {
        return R.layout.activity_payment_options;
    }

    @Override
    public void reloadPage() {

    }
}
