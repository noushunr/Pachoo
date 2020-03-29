package com.highstreets.user.ui.notification;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.widget.TextView;

import com.highstreets.user.R;
import com.highstreets.user.adapters.NotificationAdapter;
import com.highstreets.user.app_pref.GlobalPreferManager;
import com.highstreets.user.models.notification_model;
import com.highstreets.user.ui.BaseActivity;
import com.highstreets.user.ui.dialog_fragment.ProgressDialogFragment;
import com.highstreets.user.utils.CommonUtils;

import java.util.List;

public class NotificationActivity extends BaseActivity implements NotificationViewInterface {

    private Toolbar toolbar;
    private RecyclerView mNotificationRecyclerView;
    private NotificationAdapter mNotificationAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private TextView tvToolbarText;
    private NotificationPresenter notificationPresenter;
    private ProgressDialogFragment progressDialogFragment;
    private String NOTIFICATION_TITLE, NOTIFICATION_DESCRIPTION, NOTIFICATION_LOGO;

    public static Intent getActivityIntent(Context context) {
        return new Intent(context, NotificationActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        GlobalPreferManager.initializePreferenceManager(getApplicationContext());
        notificationPresenter = new NotificationPresenter(this);
        NOTIFICATION_TITLE = GlobalPreferManager.getString(GlobalPreferManager.Keys.NOTIFICATION_TITLE, "");
        NOTIFICATION_DESCRIPTION = GlobalPreferManager.getString(GlobalPreferManager.Keys.NOTIFICATION_DESCRIPTION, "");
        NOTIFICATION_LOGO = GlobalPreferManager.getString(GlobalPreferManager.Keys.OFFER_IMAGE, "");
        initView();


    }

    private void initView() {

        tvToolbarText = findViewById(R.id.tvToolbarText);
        tvToolbarText.setText("Notifications");
        mNotificationRecyclerView = findViewById(R.id.notification_recycler);
        layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mNotificationRecyclerView.setLayoutManager(layoutManager);
        mNotificationRecyclerView.setHasFixedSize(false);

        GetNotification();
    }

    private void GetNotification() {
        notificationPresenter.notification();
    }

    @Override
    protected boolean setToolbar() {
        return true;
    }

    @Override
    public int setLayout() {
        return R.layout.activity_notification;
    }

    @Override
    public void reloadPage() {
        startActivity(NotificationActivity.getActivityIntent(this));
        finish();
    }

    @Override
    public void failedToNotify(String message) {
        CommonUtils.showToast(this, message);
    }

    @Override
    public void onNotificationSuccess(List<notification_model> notificationModelArrayList) {
        mNotificationAdapter = new NotificationAdapter(this, notificationModelArrayList);
        mNotificationRecyclerView.setAdapter(mNotificationAdapter);
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
