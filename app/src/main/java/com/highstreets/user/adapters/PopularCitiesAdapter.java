package com.highstreets.user.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.highstreets.user.R;
import com.highstreets.user.models.location_model;

import java.util.ArrayList;
import java.util.List;

public class PopularCitiesAdapter extends RecyclerView.Adapter<PopularCitiesAdapter.MyViewHolder> implements Filterable {

    private Context mContext;
    private List<location_model> citiesList;
    private List<location_model> citilistFiltered;
    private OnItemClickListener listener;

    public PopularCitiesAdapter(Context mContext, List<location_model> citiesList) {
        this.mContext = mContext;
        this.citiesList = citiesList;
        this.citilistFiltered = citiesList;
        if (mContext instanceof OnItemClickListener) {
            this.listener = (OnItemClickListener) mContext;
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String query = charSequence.toString();
                if (query.isEmpty()) {
                    citilistFiltered = citiesList;
                } else {
                    List<location_model> filteredList = new ArrayList<>();
                    for (location_model row : citiesList) {
                        if (row.getCityName() != null) {
                            if (row.getCityName().toLowerCase().contains(query.toLowerCase())) {
                                filteredList.add(row);
                            }
                        }
                    }

                    citilistFiltered = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = citilistFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                citilistFiltered = (List<location_model>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View ItemView = LayoutInflater.from(mContext).inflate(R.layout.location_selection_card, viewGroup, false);
        return new MyViewHolder(ItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

        final location_model item = citilistFiltered.get(i);

        final String lat = item.getLatitude();
        final String lon = item.getLongitude();
        final String city_id = item.getCity();
        final String city_name = item.getCityName();

        myViewHolder.mCitiesName.setText(item.getCityName());

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(city_id, city_name, lat, lon);
            }
        });
    }

    @Override
    public int getItemCount() {
        return citilistFiltered.size();
    }

    public interface OnItemClickListener {
        void onItemClick(String city_id, String city_name, String lat, String lon);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mCitiesName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mCitiesName = itemView.findViewById(R.id.txt_cities);
        }
    }
}
