package com.highstreets.user.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.highstreets.user.R;
import com.highstreets.user.utils.CommonUtils;

public class NoInternetActivity extends AppCompatActivity {
    private Button btnContinueShopping;

    public static Intent getActivityIntent(Context context) {
        return new Intent(context, NoInternetActivity.class);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);
        btnContinueShopping = findViewById(R.id.btnContinueShopping);

        btnContinueShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonUtils.isNetworkAvailable(NoInternetActivity.this)) {
                    Intent data = new Intent();
                    setResult(RESULT_OK, data);
                    finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (CommonUtils.isNetworkAvailable(NoInternetActivity.this))
            finish();
        else
            finishAffinity();
    }
}
