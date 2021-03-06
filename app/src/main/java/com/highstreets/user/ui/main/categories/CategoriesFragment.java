package com.highstreets.user.ui.main.categories;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.highstreets.user.R;
import com.highstreets.user.adapters.AllCategoriesAdapter;
import com.highstreets.user.app_pref.SharedPrefs;
import com.highstreets.user.common.CommonViewInterface;
import com.highstreets.user.common.OnFragmentInteractionListener;
import com.highstreets.user.models.Category;
import com.highstreets.user.models.Success;
import com.highstreets.user.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import static com.highstreets.user.app_pref.SharedPrefs.Keys.MERCHANT_ID;


public class CategoriesFragment extends Fragment implements CategoriesFragmentViewInterface {
    private View view;
    private Context context;
    private RecyclerView rvCategoryList;
    private RecyclerView.LayoutManager layoutManager;
    private AllCategoriesAdapter allCategoriesAdapter;
    private CategoriesFragmentPresenterInterface categoriesFragmentPresenterInterface;
    private List<Success> alCategories = new ArrayList<>();
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
//        mListener.setTitle("Categories");
        rvCategoryList = view.findViewById(R.id.rvCategoryList);
        layoutManager = new GridLayoutManager(getContext(), 3);
        rvCategoryList.setLayoutManager(layoutManager);
        rvCategoryList.setHasFixedSize(false);
        rvCategoryList.setNestedScrollingEnabled(false);
        categoriesFragmentPresenterInterface.getCategories(SharedPrefs.getString(MERCHANT_ID,""));

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

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
    public void setCategoryList(List<Success> categoryList) {
        alCategories = (ArrayList<Success>) categoryList;
        allCategoriesAdapter = new AllCategoriesAdapter(getContext(), alCategories, "");
        rvCategoryList.setAdapter(allCategoriesAdapter);
    }

    @Override
    public void onLoadingCategoriesFailed(String message) {

        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponseFailed(String message) {
        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
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
