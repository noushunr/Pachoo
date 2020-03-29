
package com.highstreets.user.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;


import com.highstreets.user.R;
import com.highstreets.user.adapters.AllCategoriesAdapter;
import com.highstreets.user.models.Category;
import com.highstreets.user.ui.base.BaseActivity;
import com.highstreets.user.utils.Constants;

import java.util.ArrayList;

public class MoreCategoriesActivity extends BaseActivity {

    private RecyclerView rvMoreCat;
    private TextView tvToolbarText;

    public static Intent start(Context context) {
        return new Intent(context, MoreCategoriesActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ArrayList<Category> categoryList = getIntent().getParcelableArrayListExtra(Constants.CATEGORIES);
        rvMoreCat = findViewById(R.id.rvMoreCat);
        tvToolbarText = findViewById(R.id.tvToolbarText);
        tvToolbarText.setText("Categories");
        rvMoreCat.setLayoutManager(new GridLayoutManager(this, 3));
        AllCategoriesAdapter allCategoriesAdapter = new AllCategoriesAdapter(this, categoryList);
        rvMoreCat.setAdapter(allCategoriesAdapter);
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
}

