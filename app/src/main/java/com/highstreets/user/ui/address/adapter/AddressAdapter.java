package com.highstreets.user.ui.address.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.highstreets.user.R;
import com.highstreets.user.models.Success;
import com.highstreets.user.ui.address.add_address.AddAddressActivity;
import com.highstreets.user.ui.address.model.Address;
import com.highstreets.user.utils.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHold> {

    private List<Success> addressList;
    private Context context;

    public AddressAdapter(List<Success> addressList) {
        this.addressList = addressList;
    }

    @NonNull
    @Override
    public ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_address_item, parent, false);
        return new ViewHold(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHold holder, int position) {
        Success address = addressList.get(position);
        String name = address.getName() ;
        holder.tvName.setText(name);
        holder.tvNumber.setText(address.getPhone());
        holder.tvAddress.setText(address.getAddress());

        if (address.isSelected()){
            holder.checkAddress.setChecked(true);
        } else {
            holder.checkAddress.setChecked(false);
        }

        holder.clAddress.setOnClickListener(view -> {
            if (address.isSelected()){
                address.setSelected(false);
            } else {
                clearSelection();
                address.setSelected(true);
            }
            notifyDataSetChanged();
        });

        holder.checkAddress.setOnClickListener(view -> {
            if (address.isSelected()){
                address.setSelected(false);
            } else {
                clearSelection();
                address.setSelected(true);
            }
            notifyDataSetChanged();
        });


        holder.ivEditAddress.setOnClickListener(view -> {
            Intent editAddressIntent = AddAddressActivity.start(context);
            editAddressIntent.putExtra(Constants.EDIT_ADDRESS_ID, address.getDeliveryAddressId());
            context.startActivity(editAddressIntent);
        });

    }

    private void clearSelection(){
        for (Success address : addressList){
            address.setSelected(false);
        }
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

    public class ViewHold extends RecyclerView.ViewHolder {
        @BindView(R.id.clAddress)
        ConstraintLayout clAddress;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvNumber)
        TextView tvNumber;
        @BindView(R.id.tvAddress)
        TextView tvAddress;
        @BindView(R.id.ivEditAddress)
        ImageView ivEditAddress;
        @BindView(R.id.checkAddress)
        CheckBox checkAddress;

        public ViewHold(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
