package com.highstreets.user.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.highstreets.user.R;
import com.highstreets.user.models.Help;

import java.util.ArrayList;

public class HelpAdapter extends RecyclerView.Adapter<HelpAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<Help> helpArrayList;

    public HelpAdapter(Context mContext, ArrayList<Help> helpArrayList) {
        this.mContext = mContext;
        this.helpArrayList = helpArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.help_item, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        Help items = helpArrayList.get(i);

        myViewHolder.tvTitle.setText(items.getTitle());
        myViewHolder.tvContent.setText(items.getContent());
    }

    @Override
    public int getItemCount() {
        return helpArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private TextView tvContent;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvContent = itemView.findViewById(R.id.tvContent);
        }
    }
}
