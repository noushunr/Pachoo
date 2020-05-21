package com.highstreets.user.ui.base;


import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.highstreets.user.ui.OnInternetConnectionCallback;
import com.highstreets.user.ui.dialog_fragment.ProgressDialogFragment;

public abstract class BaseFragment
        extends Fragment
        implements OnInternetConnectionCallback {
    private DialogFragment progressDialogFragment;

    public void showProgress(){
        progressDialogFragment = new ProgressDialogFragment();
        progressDialogFragment.show(getFragmentManager(), null);
    }

    public void dismissProgress(){
        if (progressDialogFragment != null) {
            progressDialogFragment.dismiss();
        }
    }
}