package com.presentation.app.dealsnest.ui.dialog_fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.presentation.app.dealsnest.R;

public class PlayStoreUpdateDialogFragment  extends DialogFragment implements View.OnClickListener {

    private Button btnUpdate;
    private Button btnExit;


    public static PlayStoreUpdateDialogFragment newInstance() {
        PlayStoreUpdateDialogFragment fragment = new PlayStoreUpdateDialogFragment();
//        Bundle args = new Bundle();
//        args.putString("page_name", pageName);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        super.onStart();
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.fragment_play_store_update, null, false);
        initViews(v);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(v);
        builder.setCancelable(false);
        AlertDialog alertDialog = builder.create();
        if (alertDialog.getWindow() != null)
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return alertDialog;
    }


    private void initViews(View view) {
        btnUpdate = view.findViewById(R.id.btnUpdate);
        btnExit = view.findViewById(R.id.btnExit);

        btnUpdate.setOnClickListener(this);
        btnExit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnUpdate: {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(
                        "https://play.google.com/store/apps/details?id=com.shoffz.shoffzuser"));
                intent.setPackage("com.android.vending");
                startActivity(intent);
                break;
            }
            case R.id.btnExit: {
                dismiss();
                break;
            }
        }
    }
}
