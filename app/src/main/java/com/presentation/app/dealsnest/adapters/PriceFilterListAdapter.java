package com.presentation.app.dealsnest.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.presentation.app.dealsnest.R;
import com.presentation.app.dealsnest.app_pref.GlobalPreferManager;
import com.presentation.app.dealsnest.models.FilterItem;

import java.util.List;

public class PriceFilterListAdapter extends RecyclerView.Adapter<PriceFilterListAdapter.MyViewHolder> {

    private Context mContext;
    private List<FilterItem> filterPriceList;

    public PriceFilterListAdapter(Context mContext, List<FilterItem> filterPriceList) {
        this.mContext = mContext;
        this.filterPriceList = filterPriceList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View ItemView = LayoutInflater.from(mContext).inflate(R.layout.filter_items, viewGroup, false);

        return new MyViewHolder(ItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {

        final FilterItem item = filterPriceList.get(i);

        myViewHolder.tvPrice.setText(item.getName());
        myViewHolder.cbSelected.setChecked(item.isSelected());
        myViewHolder.cbSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    clearSelection();
                    item.setSelected(true);
                    notifyDataSetChanged();

                    String SELECTED = myViewHolder.tvPrice.getText().toString();
                    GlobalPreferManager.setString(GlobalPreferManager.Keys.SELECTED_PRICE, SELECTED);

                }
            }
        });

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myViewHolder.cbSelected.toggle();
            }
        });

    }

    private void clearSelection() {
        for (FilterItem item : filterPriceList) {
            item.setSelected(false);
        }
    }

    @Override
    public int getItemCount() {
        return filterPriceList.size();
    }

    public void reset() {
        clearSelection();
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvPrice;
        private RadioButton cbSelected;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPrice = itemView.findViewById(R.id.txt_filter_option);
            cbSelected = itemView.findViewById(R.id.checkbox_selected);
        }
    }
}
