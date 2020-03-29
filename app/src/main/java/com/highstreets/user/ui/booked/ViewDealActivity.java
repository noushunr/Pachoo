package com.highstreets.user.ui.booked;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.highstreets.user.R;
import com.highstreets.user.ui.BaseActivity;

public class ViewDealActivity extends BaseActivity {

    TextView tvOfferPercentage;
    TextView tvName;
    TextView tvTotal;
    TextView tvValidTill;
    TextView tvMerchantName;
    TextView tvToolbarText;
    TextView tvDescription;
    ImageView ivImage;

    public static Intent getActivityIntent(Context context) {
        return new Intent(context, ViewDealActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        tvDescription = findViewById(R.id.tvDescription);
        tvOfferPercentage = findViewById(R.id.tvOfferPercentage);
        tvName = findViewById(R.id.tvName);
        tvTotal = findViewById(R.id.tvTotal);
        tvValidTill = findViewById(R.id.tvValidTill);
        tvMerchantName = findViewById(R.id.tvMerchantName);
        tvToolbarText = findViewById(R.id.tvToolbarText);
        ivImage = findViewById(R.id.ivImage);

        tvToolbarText.setText("Details");

        if (getIntent().getStringExtra("image") != null) {
            Glide.with(this)
                    .setDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.place_holder_rectangle))
                    .load(getIntent().getStringExtra("image"))
                    .into(ivImage);
        }

        if (getIntent().getStringExtra("offerPercentage") != null) {
            tvOfferPercentage.setText(getIntent().getStringExtra("offerPercentage"));
        }
        if (getIntent().getStringExtra("name") != null) {
            tvName.setText(getIntent().getStringExtra("name"));
        }
        if (getIntent().getStringExtra("desc") != null) {
            tvDescription.setText(getIntent().getStringExtra("desc"));
        }
        if (getIntent().getStringExtra("validity_till") != null) {
            tvValidTill.setText(getIntent().getStringExtra("validity_till"));
        }
        if (getIntent().getStringExtra("total") != null) {
            tvTotal.setText("₹" + getIntent().getStringExtra("total"));
        }
        if (getIntent().getStringExtra("merchant") != null) {
            tvMerchantName.setText(getIntent().getStringExtra("merchant"));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.search_close) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected boolean setToolbar() {
        return true;
    }

    @Override
    public int setLayout() {
        return R.layout.activity_view_deal;
    }

    @Override
    public void reloadPage() {

    }
}
