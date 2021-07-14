
package com.highstreets.user.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;


import com.highstreets.user.R;
import com.highstreets.user.adapters.AllCategoriesAdapter;
import com.highstreets.user.common.CommonViewInterface;
import com.highstreets.user.models.Category;
import com.highstreets.user.ui.base.BaseActivity;
import com.highstreets.user.ui.dialog_fragment.ProgressDialogFragment;
import com.highstreets.user.ui.main.categories.CategoriesFragment;
import com.highstreets.user.ui.main.home.HomeFragment;
import com.highstreets.user.utils.Constants;

import java.util.ArrayList;

public class MoreCategoriesActivity extends BaseActivity implements CommonViewInterface {

    private TextView tvToolbarText;
    private ProgressDialogFragment progressDialogFragment;
    public static Intent start(Context context) {
        return new Intent(context, MoreCategoriesActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tvToolbarText = findViewById(R.id.tvToolbarText);
        tvToolbarText.setText("Categories");
        loadFragment(new CategoriesFragment());
//        rvMoreCat.setLayoutManager(new GridLayoutManager(this, 3));
//        AllCategoriesAdapter allCategoriesAdapter = new AllCategoriesAdapter(this, categoryList,getIntent().getStringExtra(Constants.MERCHANT_ID));
//        rvMoreCat.setAdapter(allCategoriesAdapter);
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.flContainer, fragment);
        try {
            transaction.commit();
        } catch (IllegalStateException e) {
            transaction.commitAllowingStateLoss();
        }
    }
    @Override
    protected boolean setToolbar() {
        return true;
    }

    @Override
    public int setLayout() {
        return R.layout.activity_more_categories;
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
        if (progressDialogFragment != null) {
            progressDialogFragment.dismiss();
        }
    }

    @Override
    public void noInternet() {

    }

    @Override
    public void showProgressIndicator() {
        if (getApplicationContext() != null) {

            findViewById(android.R.id.content).post(new Runnable() {
                @Override
                public void run() {
                    progressDialogFragment = ProgressDialogFragment.newInstance();
                    progressDialogFragment.show(getSupportFragmentManager(), "progress_dialog");
                }
            });
        }
    }
}

