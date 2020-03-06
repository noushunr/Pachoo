
package com.presentation.app.dealsnest.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;


import com.presentation.app.dealsnest.R;
import com.presentation.app.dealsnest.adapters.AllCategoriesAdapter;
import com.presentation.app.dealsnest.models.Category;
import com.presentation.app.dealsnest.ui.BaseActivity;
import com.presentation.app.dealsnest.utils.Constants;

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

