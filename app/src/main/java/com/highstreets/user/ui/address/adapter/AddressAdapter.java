package com.highstreets.user.ui.address.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.highstreets.user.R;
import com.highstreets.user.ui.address.add_address.AddAddressActivity;
import com.highstreets.user.ui.address.model.Address;
import com.highstreets.user.utils.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHold> {

    private List<Address> addressList;
    private Context context;

    public AddressAdapter(List<Address> addressList) {
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
        Address address = addressList.get(position);
        String name = address.getFirstname() + " " + address.getLastname();
        holder.tvName.setText(name);
        holder.tvNumber.setText(address.getMobile());
        holder.tvAddress.setText(address.getAddress2());

        if (address.isSelected()){
            holder.clAddress.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        } else {
            holder.clAddress.setBackgroundColor(context.getResources().getColor(R.color.transparent));
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

        holder.tvEditAddress.setOnClickListener(view -> {
            Intent editAddressIntent = AddAddressActivity.start(context);
            editAddressIntent.putExtra(Constants.EDIT_ADDRESS_ID, address.getAddressId());
            context.startActivity(editAddressIntent);
        });

    }

    private void clearSelection(){
        for (Address address : addressList){
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
        @BindView(R.id.tvEditAddress)
        TextView tvEditAddress;

        public ViewHold(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
