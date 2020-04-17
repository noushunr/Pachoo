package com.highstreets.user.ui.address;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.highstreets.user.R;
import com.highstreets.user.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddressActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected boolean setToolbar() {
        return true;
    }

    @Override
    public int setLayout() {
        return R.layout.activity_address;
    }

    @Override
    public void reloadPage() {

    }
}
