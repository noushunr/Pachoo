package com.highstreets.user.ui.main.categories.sub_categories;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.highstreets.user.R;
import com.highstreets.user.adapters.BrandFilterListAdapter;
import com.highstreets.user.adapters.PriceFilterListAdapter;
import com.highstreets.user.adapters.ShopListAdapter;
import com.highstreets.user.adapters.SubCatAdapter;
import com.highstreets.user.app_pref.SharedPrefs;
import com.highstreets.user.models.FilterItem;
import com.highstreets.user.models.FilterItemModel;
import com.highstreets.user.models.FilterPriceModel;
import com.highstreets.user.models.SubCategory;
import com.highstreets.user.models.Success;
import com.highstreets.user.models.shop.ShopBanner;
import com.highstreets.user.ui.base.BaseActivity;
import com.highstreets.user.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import static com.highstreets.user.app_pref.SharedPrefs.Keys.MERCHANT_ID;

public class SubCategoryActivity extends BaseActivity implements SubCategoryViewInterface, SubCatAdapter.SubCategoryAdapterCallback {
    private Button mBrandButton, mPriceButton, mDistanceButton;
    private ImageView mCloseIcon;
    private TextView mResetFilter, mSort, tvSortReset, mApplyButton;
    private RadioGroup mSortGroup;
    private SubCategoryPresenter subCategoryPresenter;
    private View bottom_sheet_view;
    private LinearLayout llSort, llFilter;
    private BottomSheetDialog dialog;
    private BottomSheetDialog FilterDialog;
    private RecyclerView mSubCatGrid, rvShopList;
    private SubCatAdapter subCatAdapter;
    private ShopListAdapter shopListAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private GridLayoutManager gridLayoutManager;
    private TextView tvToolbarText;
    private TextView tvNoData;
    private BrandFilterListAdapter brandFilterListAdapter;
    private PriceFilterListAdapter priceFilterListAdapter;
    private RecyclerView rvBrandFilter, rvPriceFilter;
    private List<FilterItem> priceHeaderList;
    private List<FilterItem> brandHeaderList;
    private String CITY_NAME, LATITUDE, LONGITUDE, SUB_CATEGORY_ID, SELECTED_BRAND, SELECTED_PRICE;
    private String categoryId, SortedOption;
    private String merchantId;

    public static Intent getActivityIntent(Context context) {
        return new Intent(context, SubCategoryActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tvToolbarText = findViewById(R.id.tvToolbarText);
        tvToolbarText.setText(getIntent().getStringExtra(Constants.CATEGORY_NAME));
        categoryId = getIntent().getStringExtra(Constants.CATEGORY_ID);
        merchantId = getIntent().getStringExtra(Constants.MERCHANT_ID);

        CITY_NAME = SharedPrefs.getString(SharedPrefs.Keys.GET_CITY_NAME, "");
        LATITUDE = SharedPrefs.getString(SharedPrefs.Keys.GET_CITY_LATITUDE, "");
        LONGITUDE = SharedPrefs.getString(SharedPrefs.Keys.GET_CITY_LONGITUDE, "");

        subCategoryPresenter = new SubCategoryPresenter(this, this);

        llSort = findViewById(R.id.sort_layout);
        llFilter = findViewById(R.id.filter_layout);
        mSort = findViewById(R.id.button_sort);
        mSubCatGrid = findViewById(R.id.sub_cat_recyclerView);
        tvNoData = findViewById(R.id.tvNoData);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        gridLayoutManager = new GridLayoutManager(this,3);
        mSubCatGrid.setLayoutManager(gridLayoutManager);
        mSubCatGrid.setHasFixedSize(false);
        rvShopList = findViewById(R.id.rvShop);
        layoutManager = new LinearLayoutManager(this);
        rvShopList.setLayoutManager(layoutManager);
        rvShopList.setHasFixedSize(false);
        rvShopList.setNestedScrollingEnabled(false);
        bottom_sheet_view = getLayoutInflater().inflate(R.layout.filter_bottom_sheet, null);
        mCloseIcon = bottom_sheet_view.findViewById(R.id.close_icon);
        mApplyButton = bottom_sheet_view.findViewById(R.id.apply_button);
        mResetFilter = bottom_sheet_view.findViewById(R.id.filter_reset);
        mBrandButton = bottom_sheet_view.findViewById(R.id.filter_button_brands);
        mPriceButton = bottom_sheet_view.findViewById(R.id.filter_button_options);
        mDistanceButton = bottom_sheet_view.findViewById(R.id.filter_button_distance);
        rvBrandFilter = bottom_sheet_view.findViewById(R.id.filter_brand_option);
        rvBrandFilter.setLayoutManager(new LinearLayoutManager(SubCategoryActivity.this));

        rvPriceFilter = bottom_sheet_view.findViewById(R.id.filter_price_option);
        rvPriceFilter.setLayoutManager(new LinearLayoutManager(SubCategoryActivity.this));

        mCloseIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilterDialog.dismiss();
            }
        });
        mResetFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (brandFilterListAdapter != null)
                    brandFilterListAdapter.reset();
                if (priceFilterListAdapter != null)
                    priceFilterListAdapter.reset();
                FilterDialog.dismiss();
                subCategoryPresenter.getSubCategories(categoryId, SharedPrefs.getString(MERCHANT_ID,""));
            }
        });

        mApplyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SELECTED_BRAND = SharedPrefs.getString(SharedPrefs.Keys.SELECTED_BRAND, "");
                SELECTED_PRICE = SharedPrefs.getString(SharedPrefs.Keys.SELECTED_PRICE, "");
                if (!isBrandSelected() && !isPriceSelected()) {
                    getShopList(CITY_NAME, categoryId, "-1", LATITUDE, LONGITUDE);
                } else {
                    if (!CITY_NAME.equals("") && !LATITUDE.equals("") && !LONGITUDE.equals("")) {
                        //getShopList(CITY_NAME, categoryId, "-1", LATITUDE, LONGITUDE);
                        if (SUB_CATEGORY_ID != null) {
                            subCategoryPresenter.getFilterResult(categoryId, SUB_CATEGORY_ID, LATITUDE, LONGITUDE, SELECTED_BRAND, SELECTED_PRICE, CITY_NAME);
                        } else {
                            subCategoryPresenter.getFilterResult(categoryId, "-1", LATITUDE, LONGITUDE, SELECTED_BRAND, SELECTED_PRICE, CITY_NAME);
                        }
                    }else {
                       /// getShopList("-1", categoryId, "-1", "-1", "-1");
                        if (SUB_CATEGORY_ID != null) {
                            subCategoryPresenter.getFilterResult(categoryId, SUB_CATEGORY_ID, "-1", "-1", SELECTED_BRAND, SELECTED_PRICE, "-1");
                        } else {
                            subCategoryPresenter.getFilterResult(categoryId, "-1", "-1", "-1", SELECTED_BRAND, SELECTED_PRICE, "-1");
                        }
                    }
                }
                FilterDialog.dismiss();
            }
        });

        FilterDialog = new BottomSheetDialog(SubCategoryActivity.this);
        FilterDialog.setContentView(bottom_sheet_view);

        llFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mBrandButton.setSelected(true);
                mBrandButton.setPressed(false);

                mPriceButton.setSelected(false);
                mPriceButton.setPressed(false);

                mDistanceButton.setSelected(false);
                mDistanceButton.setPressed(false);

                rvBrandFilter.setVisibility(View.VISIBLE);
                rvPriceFilter.setVisibility(View.GONE);

                mBrandButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mBrandButton.setSelected(true);
                        mBrandButton.setPressed(false);

                        mPriceButton.setSelected(false);
                        mPriceButton.setPressed(false);

                        mDistanceButton.setSelected(false);
                        mDistanceButton.setPressed(false);

                        rvBrandFilter.setVisibility(View.VISIBLE);
                        rvPriceFilter.setVisibility(View.GONE);
                    }
                });
                mPriceButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mBrandButton.setSelected(false);
                        mBrandButton.setPressed(false);

                        mPriceButton.setSelected(true);
                        mPriceButton.setPressed(false);

                        mDistanceButton.setSelected(false);
                        mDistanceButton.setPressed(false);
                        rvPriceFilter.setVisibility(View.VISIBLE);
                        rvBrandFilter.setVisibility(View.GONE);

                    }
                });
                FilterDialog.show();
            }
        });
        llSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final View sort_bottom_sheet_view = getLayoutInflater().inflate(R.layout.sort_bottom_sheet, null);
                mCloseIcon = sort_bottom_sheet_view.findViewById(R.id.close_icon);
                mSort = sort_bottom_sheet_view.findViewById(R.id.sort_button);
                tvSortReset = sort_bottom_sheet_view.findViewById(R.id.txt_sort_reset);
                mSortGroup = sort_bottom_sheet_view.findViewById(R.id.sorting_group);
                mApplyButton = sort_bottom_sheet_view.findViewById(R.id.apply_button);
                tvSortReset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mSortGroup.clearCheck();
                        subCategoryPresenter.getSubCategories(categoryId,SharedPrefs.getString(MERCHANT_ID,""));
                        SharedPrefs.remove(SharedPrefs.Keys.CHECKED_ITEM_ID);
                        dialog.dismiss();
                    }
                });
                mApplyButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mSortGroup.findViewById(SharedPrefs.getInt(SharedPrefs.Keys.CHECKED_ITEM_ID, 0)) == null) {
                            getShopList(CITY_NAME, categoryId, "-1", LATITUDE, LONGITUDE);
                        } else {
                            if (!CITY_NAME.equals("") && !LATITUDE.equals("") && !LONGITUDE.equals("")) {
                              //  getShopList(CITY_NAME, categoryId, "-1", LATITUDE, LONGITUDE);
                                if (SUB_CATEGORY_ID != null) {
                                    subCategoryPresenter.getSortedShopList(SortedOption,CITY_NAME,categoryId, SUB_CATEGORY_ID, LATITUDE, LONGITUDE);
                                } else {
                                    subCategoryPresenter.getSortedShopList(SortedOption,CITY_NAME,categoryId, "-1", LATITUDE, LONGITUDE);
                                }
                            }else {
                               // getShopList("-1", categoryId, "-1", "-1", "-1");
                                if (SUB_CATEGORY_ID != null) {
                                    subCategoryPresenter.getSortedShopList(SortedOption,"-1",categoryId, SUB_CATEGORY_ID, "-1", "-1");
                                } else {
                                    subCategoryPresenter.getSortedShopList(SortedOption,"-1",categoryId, "-1", "-1", "-1");
                                }
                            }
                        }
                        dialog.dismiss();
                    }
                });
                mCloseIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                mSortGroup.setVisibility(View.VISIBLE);
                mSort.setSelected(true);
                mSort.setPressed(false);
                RadioButton radioButton = mSortGroup.findViewById(SharedPrefs.getInt(SharedPrefs.Keys.CHECKED_ITEM_ID, 0));
                if (radioButton != null) {
                    radioButton.setChecked(true);
                }
                dialog = new BottomSheetDialog(SubCategoryActivity.this);
                dialog.setContentView(sort_bottom_sheet_view);
                dialog.show();

                mSortGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @SuppressLint("ResourceType")
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton rb = group.findViewById(checkedId);
                        if (null != rb && checkedId > -1) {

                            int idx = mSortGroup.indexOfChild(rb);
                            SortedOption = String.valueOf(idx);
                            SharedPrefs.setInt(SharedPrefs.Keys.CHECKED_ITEM_ID, checkedId);
                        }
                    }
                });

            }
        });
        subCategoryPresenter.getSubCategories(categoryId,SharedPrefs.getString(MERCHANT_ID,""));
    }

    private boolean isPriceSelected() {
        for (FilterItem filterItem : priceHeaderList) {
            if (filterItem.isSelected()) {
                return true;
            }
        }
        return false;
    }

    private boolean isBrandSelected() {
        for (FilterItem filterItem : brandHeaderList) {
            if (filterItem.isSelected()) {
                return true;
            }
        }
        return false;
    }


    @Override
    public int setLayout() {
        return R.layout.activity_sub_category;
    }

    @Override
    public void reloadPage() {

    }

    @Override
    public boolean setToolbar() {
        return true;
    }

    private void getShopList(String city_name, String category_id_holder, String sub_category_id, String latitude, String longitude) {
        subCategoryPresenter.getShopLists(city_name, category_id_holder, sub_category_id, latitude, longitude);
    }

    @Override
    public void onFailedToLoadSubCategories(String message) {

    }

    @Override
    public void onLoadingSubCategoriesSuccess(final List<Success> subCategoryModelList) {
        dismissProgress();
        subCatAdapter = new SubCatAdapter(this, subCategoryModelList,merchantId);
        mSubCatGrid.setAdapter(subCatAdapter);
//        if (!CITY_NAME.equals("") && !LATITUDE.equals("") && !LONGITUDE.equals("")) {
//            getShopList(CITY_NAME, categoryId, "-1", LATITUDE, LONGITUDE);
//            if (SUB_CATEGORY_ID != null) {
//                subCategoryPresenter.getFilterList(categoryId, SUB_CATEGORY_ID, LATITUDE, LONGITUDE);
//            } else {
//                subCategoryPresenter.getFilterList(categoryId, "-1", LATITUDE, LONGITUDE);
//            }
//        } else {
//            getShopList("-1", categoryId, "-1", "-1", "-1");
//            if (SUB_CATEGORY_ID != null) {
//                subCategoryPresenter.getFilterList(categoryId, SUB_CATEGORY_ID, "-1", "-1");
//            } else {
//                subCategoryPresenter.getFilterList(categoryId, "-1", "-1", "-1");
//            }
//        }

        if (subCategoryModelList.size() <= 0) {
            tvNoData.setVisibility(View.VISIBLE);
        } else {
            tvNoData.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoadingShopListSuccess(List<ShopBanner> shopList) {
        dismissProgress();
        shopListAdapter = new ShopListAdapter(this, shopList);
        rvShopList.setAdapter(shopListAdapter);
        if (shopList.size() <= 0) {
            tvNoData.setVisibility(View.GONE);
        } else {
            tvNoData.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoadingFilteredShopListSuccess(List<ShopBanner> shopList) {
        shopListAdapter = new ShopListAdapter(this, shopList);
        rvShopList.setAdapter(shopListAdapter);
        if (shopList.size() <= 0) {
            tvNoData.setVisibility(View.VISIBLE);
        } else {
            tvNoData.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoadingSortedShopListSuccess(List<ShopBanner> shopList) {
        shopListAdapter = new ShopListAdapter(this, shopList);
        rvShopList.setAdapter(shopListAdapter);
        if (shopList.size() <= 0) {
            tvNoData.setVisibility(View.VISIBLE);
        } else {
            tvNoData.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoadingPriceItemsSuccess(FilterPriceModel priceList) {
        priceHeaderList = new ArrayList<>();
        priceHeaderList.add(new FilterItem(priceList.get_1()));
        priceHeaderList.add(new FilterItem(priceList.get_2()));
        priceHeaderList.add(new FilterItem(priceList.get_3()));
        priceHeaderList.add(new FilterItem(priceList.get_4()));

        priceFilterListAdapter = new PriceFilterListAdapter(this, priceHeaderList);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvBrandFilter.getContext(),
                DividerItemDecoration.VERTICAL);
        rvPriceFilter.addItemDecoration(dividerItemDecoration);
        rvPriceFilter.setAdapter(priceFilterListAdapter);
    }

    @Override
    public void onLoadingBrandItemsSuccess(FilterItemModel brandItems) {

        brandHeaderList = new ArrayList<>();

        brandHeaderList.add(new FilterItem(brandItems.get1()));
        brandHeaderList.add(new FilterItem(brandItems.get2()));
        brandHeaderList.add(new FilterItem(brandItems.get3()));

        brandFilterListAdapter = new BrandFilterListAdapter(this, brandHeaderList);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rvBrandFilter.addItemDecoration(dividerItemDecoration);
        rvBrandFilter.setAdapter(brandFilterListAdapter);
    }

    @Override
    public void onResponseFailed(String message) {

    }

    @Override
    public void onServerError(String message) {

    }

    @Override
    public void dismissProgressIndicator() {
        dismissProgress();
    }

    @Override
    public void noInternet() {

    }

    @Override
    public void showProgressIndicator() {
        showProgress();
    }

    @Override
    public void getShops(String id, String categoryId) {
        SUB_CATEGORY_ID = id;

        if (!CITY_NAME.equals("") && !LATITUDE.equals("") && !LONGITUDE.equals("")){
            if (SUB_CATEGORY_ID!=null) {
                getShopList(CITY_NAME, categoryId, id, LATITUDE, LONGITUDE);
            }else {
                getShopList(CITY_NAME, categoryId, "-1", LATITUDE, LONGITUDE);
            }
        }else {
            if (SUB_CATEGORY_ID!=null) {
                getShopList("-1", categoryId, id, "-1", "-1");
            }else {
                getShopList("-1", categoryId, "-1", "-1", "-1");
            }
        }



    }

    public interface ResetCheckboxInterface {
        void reset();
    }
}
