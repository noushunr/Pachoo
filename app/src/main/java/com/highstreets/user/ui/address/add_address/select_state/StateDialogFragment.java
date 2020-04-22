package com.highstreets.user.ui.address.add_address.select_state;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.highstreets.user.R;
import com.highstreets.user.ui.address.add_address.select_state.adapter.StatesAdapter;
import com.highstreets.user.ui.address.add_address.select_state.model.State;
import com.highstreets.user.ui.base.BaseDialogFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class StateDialogFragment extends BaseDialogFragment implements
        StateViewInterface,
        StatesAdapter.SelectState {

    private StatesAdapter.SelectState selectState;

    @BindView(R.id.rvStates)
    RecyclerView rvStates;

    public static StateDialogFragment newInstance() {
        Bundle args = new Bundle();
        StateDialogFragment fragment = new StateDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View setView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_state_dialog, container, false);
        ButterKnife.bind(this, view);
        selectState = (StatesAdapter.SelectState) context;
        rvStates.setLayoutManager(new LinearLayoutManager(context));
        StatePresenterInterface statePresenterInterface = new StatePresenter(this);
        statePresenterInterface.getStates();
        return view;
    }

    @Override
    public void setStateList(List<State> stateList) {
        rvStates.setAdapter(new StatesAdapter(stateList, this));
    }

    @Override
    public void setState(State state) {
        selectState.setState(state);
        dismiss();
    }
}
