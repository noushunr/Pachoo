package com.highstreets.user.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.highstreets.user.R;
import com.highstreets.user.app_pref.SharedPrefs;
import com.highstreets.user.ui.main.HomeMainActivity;
import com.highstreets.user.ui.main.MoreCategoriesActivity;
import com.highstreets.user.utils.Constants;

public class SuccessDialogFragment extends DialogFragment {

    private Button CloseSuccessDialog;
    private TextView tvEmailID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        String email = bundle.getString("message");

        View v = LayoutInflater.from(getContext()).inflate(R.layout.fragment_success_dialog, null, false);
        tvEmailID = v.findViewById(R.id.tv_email_id);
        tvEmailID.setText(email);

        CloseSuccessDialog = v.findViewById(R.id.close_success_dialog);
        CloseSuccessDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
//                Intent toAllCategories = MoreCategoriesActivity.start(getContext());
//                SharedPrefs.setString(SharedPrefs.Keys.MERCHANT_ID, "16");
//                toAllCategories.putExtra(Constants.MERCHANT_ID, "16");
//                startActivity(toAllCategories);
                Intent homeIntent = HomeMainActivity.start(getActivity(),false);
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().startActivity(homeIntent);
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(v);
        AlertDialog alertDialog = builder.create();
        if (alertDialog.getWindow() != null)
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return alertDialog;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
