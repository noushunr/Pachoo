package com.highstreets.user.ui.cart;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.highstreets.user.R;
import com.highstreets.user.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartActivity extends BaseActivity {

    @BindView(R.id.tvToolbarText)
    TextView tvToolbarText;
    @BindView(R.id.rvCartList)
    RecyclerView rvCartList;

    public static Intent start(Context context){
        return new Intent(context, CartActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tvToolbarText.setText(getString(R.string.cart));
    }

    @Override
    protected boolean setToolbar() {
        return true;
    }

    @Override
    public int setLayout() {
        return R.layout.activity_cart;
    }

    @Override
    public void reloadPage() {

    }
}
