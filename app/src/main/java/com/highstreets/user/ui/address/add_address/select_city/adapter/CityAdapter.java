package com.highstreets.user.ui.address.add_address.select_city.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.highstreets.user.R;
import com.highstreets.user.ui.address.add_address.select_city.model.City;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHold> {

    private List<City> cityList;
    private SelectCity selectCity;

    public CityAdapter(List<City> cityList, SelectCity selectCity) {
        this.cityList = cityList;
        this.selectCity = selectCity;
    }

    @NonNull
    @Override
    public ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_state_item, parent, false);
        return new ViewHold(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHold holder, int position) {
        City city = cityList.get(position);
        holder.tvText.setText(city.getCity());
        holder.tvText.setOnClickListener(view -> selectCity.setCity(city));
    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }

    public class ViewHold extends RecyclerView.ViewHolder {
        @BindView(R.id.tvText)
        TextView tvText;

        public ViewHold(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface SelectCity{
        void setCity(City city);
    }
}
