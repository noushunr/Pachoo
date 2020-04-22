package com.highstreets.user.ui.address.add_address.select_district.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.highstreets.user.R;
import com.highstreets.user.ui.address.add_address.select_district.model.District;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DistrictAdapter extends RecyclerView.Adapter<DistrictAdapter.ViewHold> {

    private List<District> districtList;
    private SelectDistrict selectDistrict;

    public DistrictAdapter(List<District> districtList, SelectDistrict selectDistrict) {
        this.districtList = districtList;
        this.selectDistrict = selectDistrict;
    }

    @NonNull
    @Override
    public ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_state_item, parent, false);
        return new ViewHold(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHold holder, int position) {
        District district = districtList.get(position);
        holder.tvText.setText(district.getName());
        holder.tvText.setOnClickListener(view -> selectDistrict.setDistrict(district));
    }

    @Override
    public int getItemCount() {
        return districtList.size();
    }

    public class ViewHold extends RecyclerView.ViewHolder {
        @BindView(R.id.tvText)
        TextView tvText;

        public ViewHold(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface SelectDistrict{
        void setDistrict(District district);
    }
}
