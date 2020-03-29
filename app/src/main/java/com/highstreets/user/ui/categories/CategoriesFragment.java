package com.highstreets.user.ui.categories;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.highstreets.user.R;
import com.highstreets.user.adapters.AllCategoriesAdapter;
import com.highstreets.user.app_pref.GlobalPreferManager;
import com.highstreets.user.common.CommonViewInterface;
import com.highstreets.user.common.OnFragmentInteractionListener;
import com.highstreets.user.models.Category;

import java.util.ArrayList;
import java.util.List;


public class CategoriesFragment extends Fragment implements CategoriesFragmentViewInterface {
    private View view;
    private Context context;
    private RecyclerView rvCategoryList;
    private RecyclerView.LayoutManager layoutManager;
    private AllCategoriesAdapter allCategoriesAdapter;
    private CategoriesFragmentPresenterInterface categoriesFragmentPresenterInterface;
    private ArrayList<Category> brands_modelsArrayList = new ArrayList<>();
    private String SELECTED_CITY;
    private OnFragmentInteractionListener mListener;
    private CommonViewInterface mCommonListener;

    public static CategoriesFragment newInstance() {
        CategoriesFragment fragment = new CategoriesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        categoriesFragmentPresenterInterface = new CategoriesFragmentPresenter(this);

        view = inflater.inflate(R.layout.fragment_categories, container, false);
        mListener.setTitle("Categories");
        rvCategoryList = view.findViewById(R.id.rvCategoryList);
        layoutManager = new GridLayoutManager(getContext(), 3);
        rvCategoryList.setLayoutManager(layoutManager);
        rvCategoryList.setHasFixedSize(false);
        rvCategoryList.setNestedScrollingEnabled(false);

        SELECTED_CITY = GlobalPreferManager.getString(GlobalPreferManager.Keys.GET_CITY_NAME, "");
        if (TextUtils.isEmpty(SELECTED_CITY)) {
            categoriesFragmentPresenterInterface.getCategories("-1");
        } else {
            categoriesFragmentPresenterInterface.getCategories(SELECTED_CITY);
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }

        if (context instanceof CommonViewInterface){
            mCommonListener = (CommonViewInterface) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void setCategoryList(List<Category> categoryList) {
        brands_modelsArrayList = (ArrayList<Category>) categoryList;
        allCategoriesAdapter = new AllCategoriesAdapter(getContext(), brands_modelsArrayList);
        rvCategoryList.setAdapter(allCategoriesAdapter);
    }

    @Override
    public void onLoadingCategoriesFailed(String message) {

    }

    @Override
    public void onResponseFailed(String message) {

    }

    @Override
    public void onServerError(String message) {

    }

    @Override
    public void dismissProgressIndicator() {
        if (mCommonListener!=null) {
            mCommonListener.dismissProgressIndicator();
        }
    }

    @Override
    public void noInternet() {

    }

    @Override
    public void showProgressIndicator() {
        if (mCommonListener!=null){
            mCommonListener.showProgressIndicator();
        }
    }
}
