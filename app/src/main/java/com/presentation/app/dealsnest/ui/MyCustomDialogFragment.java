package com.presentation.app.dealsnest.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
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

import com.presentation.app.dealsnest.R;
import com.presentation.app.dealsnest.ui.reset_password_otp.ResetPasswordOTPActivity;


public class MyCustomDialogFragment extends DialogFragment implements View.OnClickListener {
    private Button mContinueButton;
    private TextView ResetMail;
    private String EMAIL_HOLDER, RESET_LINK_MESSAGE;
    private String mId;
    private Resources res;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.activity_my_custom_dialog_fragment, null, false);

        res = getResources();
        Bundle bundle = getArguments();
        if (bundle != null) {
            EMAIL_HOLDER = bundle.getString("email_id");
            mId = bundle.getString("id");
        }

        mContinueButton = view.findViewById(R.id.btn_continue);
        ResetMail = view.findViewById(R.id.dialog_message);
        mContinueButton.setOnClickListener(this);

        RESET_LINK_MESSAGE = String.format(res.getString(R.string.your_reset_email_message), EMAIL_HOLDER);
        ResetMail.setText(RESET_LINK_MESSAGE);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        if (alertDialog.getWindow() != null)
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return alertDialog;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_continue:
                Intent intent = new Intent(getContext(), ResetPasswordOTPActivity.class);
                intent.putExtra("email_id", EMAIL_HOLDER);
                intent.putExtra("id", mId);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                dismiss();
                break;
        }
    }
}
