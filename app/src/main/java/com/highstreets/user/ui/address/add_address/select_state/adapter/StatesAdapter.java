package com.highstreets.user.ui.address.add_address.select_state.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.highstreets.user.R;
import com.highstreets.user.ui.address.add_address.select_state.model.State;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StatesAdapter extends RecyclerView.Adapter<StatesAdapter.ViewHold> {

    private List<State> stateList;
    private SelectState selectState;

    public StatesAdapter(List<State> stateList, SelectState SelectState) {
        this.stateList = stateList;
        this.selectState = SelectState;
    }

    @NonNull
    @Override
    public ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_state_item, parent, false);
        return new ViewHold(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHold holder, int position) {
        State state = stateList.get(position);
        holder.tvText.setText(state.getName());
        holder.tvText.setOnClickListener(view -> selectState.setState(state));
    }

    @Override
    public int getItemCount() {
        return stateList.size();
    }

    public class ViewHold extends RecyclerView.ViewHolder {
        @BindView(R.id.tvText)
        TextView tvText;

        public ViewHold(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface SelectState{
        void setState(State state);
    }
}
