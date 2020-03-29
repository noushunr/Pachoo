package com.highstreets.user.ui.help;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;

import com.highstreets.user.R;
import com.highstreets.user.adapters.HelpAdapter;
import com.highstreets.user.models.Help;
import com.highstreets.user.ui.base.BaseActivity;
import com.highstreets.user.ui.dialog_fragment.ProgressDialogFragment;
import com.highstreets.user.utils.CommonUtils;

import java.util.ArrayList;


public class HelpActivity extends BaseActivity implements HelpActivityViewInterface {

    private TextView tvToolbarTitle;
    private RecyclerView rvHelp;
    private RecyclerView.LayoutManager layoutManager;
    private HelpAdapter helpAdapter;
    private ProgressDialogFragment progressDialogFragment;
    private HelpActivityPresenter helpActivityPresenter;
    private ArrayList<Help> helpArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        helpActivityPresenter = new HelpActivityPresenter(this);
        initView();
    }

    private void initView() {
        tvToolbarTitle = findViewById(R.id.tvToolbarText);
        tvToolbarTitle.setText("Help");
        rvHelp = findViewById(R.id.rvHelp);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvHelp.setLayoutManager(layoutManager);
        rvHelp.setHasFixedSize(false);
        rvHelp.setNestedScrollingEnabled(false);

        helpActivityPresenter.getHelp();
    }

    @Override
    protected boolean setToolbar() {
        return true;
    }

    @Override
    public int setLayout() {
        return R.layout.activity_help;
    }

    @Override
    public void reloadPage() {

    }

    @Override
    public void onLoadingHelpSuccess(ArrayList<Help> help) {
        helpArrayList = help;
        helpAdapter = new HelpAdapter(this, helpArrayList);
        rvHelp.setAdapter(helpAdapter);
    }

    @Override
    public void onLoadingHelpFailed(String message) {
        CommonUtils.showToast(this, message);
    }

    @Override
    public void onResponseFailed(String message) {
        CommonUtils.showToast(this, message);
    }

    @Override
    public void onServerError(String message) {
        CommonUtils.showToast(this, message);
    }

    @Override
    public void dismissProgressIndicator() {
        try {
            if (progressDialogFragment != null && !isFinishing())
                progressDialogFragment.dismiss();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void noInternet() {

    }


    @Override
    public void showProgressIndicator() {
        progressDialogFragment = ProgressDialogFragment.newInstance();
        progressDialogFragment.show(getSupportFragmentManager(), "progress_dialog");
    }
}
