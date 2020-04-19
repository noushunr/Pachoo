package com.highstreets.user.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.highstreets.user.R;
import com.highstreets.user.app_pref.SharedPrefs;
import com.highstreets.user.models.FilterItem;
import com.highstreets.user.ui.main.categories.sub_categories.SubCategoryActivity;

import java.util.List;

public class BrandFilterListAdapter extends RecyclerView.Adapter<BrandFilterListAdapter.MyViewHolderAdapter> implements SubCategoryActivity.ResetCheckboxInterface {

    private Context mContext;
    private List<FilterItem> filterItemModelList;

    public BrandFilterListAdapter(Context mContext, List<FilterItem> filterItemModelList) {
        this.mContext = mContext;
        this.filterItemModelList = filterItemModelList;
    }

    @NonNull
    @Override
    public MyViewHolderAdapter onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View ItemView = LayoutInflater.from(mContext).inflate(R.layout.filter_items, viewGroup, false);
        return new MyViewHolderAdapter(ItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolderAdapter myViewHolderAdapter, final int i) {

        final FilterItem selected = filterItemModelList.get(i);

        myViewHolderAdapter.tvFilterOptionName.setText(selected.getName());
        myViewHolderAdapter.rbFilterSelectedOption.setChecked(selected.isSelected());
        myViewHolderAdapter.rbFilterSelectedOption.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    clearSelection();
                    selected.setSelected(true);
                    notifyDataSetChanged();

                    String SELECTED = myViewHolderAdapter.tvFilterOptionName.getText().toString();
                    SharedPrefs.setString(SharedPrefs.Keys.SELECTED_BRAND, SELECTED);
                }
            }
        });

        myViewHolderAdapter.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myViewHolderAdapter.rbFilterSelectedOption.toggle();

            }
        });
    }

    private void clearSelection() {
        for (FilterItem item : filterItemModelList) {
            item.setSelected(false);
        }
    }


    @Override
    public int getItemCount() {
        return filterItemModelList.size();
    }

    @Override
    public void reset() {
        clearSelection();
        notifyDataSetChanged();
    }

    public class MyViewHolderAdapter extends RecyclerView.ViewHolder {
        private TextView tvFilterOptionName;
        private RadioButton rbFilterSelectedOption;

        public MyViewHolderAdapter(@NonNull View itemView) {
            super(itemView);
            tvFilterOptionName = itemView.findViewById(R.id.txt_filter_option);
            rbFilterSelectedOption = itemView.findViewById(R.id.checkbox_selected);
        }
    }
}
