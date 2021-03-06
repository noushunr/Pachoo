package com.highstreets.user.ui.cart.check_postcode;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.highstreets.user.R;
import com.highstreets.user.api.ApiClient;
import com.highstreets.user.ui.address.AddressActivity;
import com.highstreets.user.ui.address.add_address.model.PostResponse;
import com.highstreets.user.ui.base.BaseDialogFragment;
import com.highstreets.user.utils.CommonUtils;
import com.highstreets.user.utils.Constants;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CheckPostcodeDialogFragment extends BaseDialogFragment {

    @BindView(R.id.etPostcode)
    EditText etPostcode;
    @BindView(R.id.btnCheck)
    Button btnCheck;
    @BindView(R.id.tvError)
    TextView tvError;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    String regex = "^([A-Za-z][A-Ha-hJ-Yj-y]?[0-9][A-Za-z0-9]? ?[0-9][A-Za-z]{2}|[Gg][Ii][Rr] ?0[Aa]{2})$";

    Pattern pattern ;
    public static CheckPostcodeDialogFragment newInstance() {
        Bundle args = new Bundle();
        CheckPostcodeDialogFragment fragment = new CheckPostcodeDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View setView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_check_postcode_dialog, container, false);
        ButterKnife.bind(this, view);
        pattern = Pattern.compile(regex);
        btnCheck.setOnClickListener(view1 -> {
            if (TextUtils.isEmpty(etPostcode.getText())){
                CommonUtils.showToast(context, "Enter Postcode");
            }else if (!pattern.matcher(etPostcode.getText().toString()).matches()) {
                etPostcode.setError(getString(R.string.invalid_pincode));
                etPostcode.requestFocus();
            }
            else {
                checkPostcode(etPostcode.getText().toString());
            }
        });
        return view;
    }

    private void checkPostcode(String postcode) {
        progressBar.setVisibility(View.VISIBLE);
        ApiClient.getApiInterface().checkPostcode(postcode).enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                progressBar.setVisibility(View.INVISIBLE);
                if (response.isSuccessful()){
                    handleResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void handleResponse(PostResponse postResponse) {
        if (postResponse.getStatus().equals(Constants.SUCCESS)){
            tvError.setVisibility(View.INVISIBLE);
            CommonUtils.showToast(context, postResponse.getMessage());
            startActivity(AddressActivity.start(context));
        } else {
            tvError.setVisibility(View.VISIBLE);
            tvError.setText(postResponse.getMessage());
        }
    }
}
