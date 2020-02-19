package com.presentation.app.dealsnest.ui.offer_details;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.presentation.app.dealsnest.R;
import com.presentation.app.dealsnest.ui.BaseActivity;

public class DetailActivity extends BaseActivity {

    private ImageView ivImage;
    private TextView tvName;
    private TextView tvOfferPercentage;
    private TextView tvValidTill;
    private TextView tvTotal;
    private TextView tvValidFor;
    private TextView tvDescription;
    private TextView tvToolbarText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ivImage = findViewById(R.id.ivImage);
        tvOfferPercentage = findViewById(R.id.tvOfferPercentage);
        tvName = findViewById(R.id.tvName);
        tvValidTill = findViewById(R.id.tvValidTill);
        tvTotal = findViewById(R.id.tvTotal);
        tvValidFor = findViewById(R.id.tvValidFor);
        tvDescription = findViewById(R.id.tvDescription);
        tvToolbarText = findViewById(R.id.tvToolbarText);

        tvToolbarText.setText("Details");
        if (getIntent().getStringExtra("image") != null) {
            Glide.with(this)
                    .load(getIntent().getStringExtra("image"))
                    .into(ivImage);
        }

        if (getIntent().getStringExtra("desc") != null) {
            tvDescription.setText(getIntent().getStringExtra("desc"));
        }
        if (getIntent().getStringExtra("name") != null) {
            tvName.setText(getIntent().getStringExtra("name"));
        }
        if (getIntent().getStringExtra("offer_percentage") != null) {
            tvOfferPercentage.setText(getIntent().getStringExtra("offer_percentage"));
        }
        if (getIntent().getStringExtra("mrp") != null) {
            String mrpStr = "₹" + getIntent().getStringExtra("mrp");
            tvTotal.setText(mrpStr);
        }
        if (getIntent().getStringExtra("valid_till") != null) {
            tvValidTill.setText(getIntent().getStringExtra("valid_till"));
        }
        if (getIntent().getStringExtra("validity_for") != null) {
            tvValidFor.setText(getIntent().getStringExtra("validity_for"));
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
        return R.layout.activity_detail;
    }

    @Override
    public void reloadPage() {

    }
}
