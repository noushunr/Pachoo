package com.highstreets.user.ui.main.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.highstreets.user.R;
import com.highstreets.user.adapters.BrandedShopAdapter;
import com.highstreets.user.adapters.CategoryRecyclerAdapter;
import com.highstreets.user.ui.main.home.adapter.DealsRecyclerAdapter;
import com.highstreets.user.adapters.MostVisitedShopAdapter;
import com.highstreets.user.ui.main.home.adapter.RecentlyBookedRecyclerAdapter;
import com.highstreets.user.api.ApiClient;
import com.highstreets.user.app_pref.SharedPrefs;
import com.highstreets.user.common.CommonViewInterface;
import com.highstreets.user.common.OnFragmentInteractionListener;
import com.highstreets.user.models.BottomBanner;
import com.highstreets.user.models.BrandedShop;
import com.highstreets.user.models.Category;
import com.highstreets.user.models.Deal;
import com.highstreets.user.models.MiddleBanner;
import com.highstreets.user.models.MostViewedShop;
import com.highstreets.user.models.RecentlyBookedShop;
import com.highstreets.user.models.Slider;
import com.highstreets.user.models.TopBanner;
import com.highstreets.user.ui.base.BaseFragment;
import com.highstreets.user.ui.dialog_fragment.ProgressDialogFragment;
import com.highstreets.user.ui.main.HomeMainActivity;
import com.highstreets.user.ui.main.MoreCategoriesActivity;
import com.highstreets.user.ui.most_viewed_shop.ViewAllMostViewShopActivity;
import com.highstreets.user.ui.select_location.SelectLocationActivity;
import com.highstreets.user.ui.view_all_deals.ViewAllDealsActivity;
import com.highstreets.user.ui.view_all_recently_booked.ViewAllRecentlyBookedActivity;
import com.highstreets.user.utils.CommonUtils;
import com.highstreets.user.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends BaseFragment implements
        HomeViewInterface,
        CommonViewInterface,
        View.OnClickListener,
        CategoryRecyclerAdapter.ToAllCategories,
        HomeMainActivity.LocationChange {

    private final static int LOCATION_REQUEST_CODE = 101;
    private View view;
    private CustomPagerAdapter mPagerAdapter;
    private ActionBar toolbar;
    private HomePresenterInterface homeFragmentPresenter;
    private List<Slider> mSliderList = new ArrayList<>();
    private List<Deal> mDealsModelList = new ArrayList<>();
    private List<TopBanner> mTopBannerList = new ArrayList<>();
    private List<BrandedShop> mBrandedShopList = new ArrayList<>();
    private List<MiddleBanner> mMiddleBannerList = new ArrayList<>();
    private List<RecentlyBookedShop> mRecentlyBookedShopList = new ArrayList<>();
    private List<BottomBanner> mBottomBannersList = new ArrayList<>();
    private List<MostViewedShop> mMostViewedShopList = new ArrayList<>();
    private ArrayList<Category> mCategoryList = new ArrayList<>();
    private Category addRemoveItem;
    private Context context;
    private ProgressDialogFragment progressDialogFragment;
    private String SELECTED_CITY;
    private OnFragmentInteractionListener mListener;
    private CommonViewInterface mCommonListener;
    private boolean isCitySelected;
    private boolean mIsStateAlreadySaved = false;

    @BindView(R.id.tvLocation)
    TextView tvLocation;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.rvCategories)
    RecyclerView rvCategories;
    @BindView(R.id.rvDealsGrid)
    RecyclerView rvDealsGrid;
    @BindView(R.id.rvBrandedShops)
    RecyclerView rvBrandedShops;
    @BindView(R.id.rvRecentlyBooked)
    RecyclerView rvRecentlyBooked;
    @BindView(R.id.rvMostVisited)
    RecyclerView rvMostVisited;
    @BindView(R.id.ivTopBanner)
    ImageView ivTopBanner;
    @BindView(R.id.ivMiddleBanner)
    ImageView ivMiddleBanner;
    @BindView(R.id.ivLastBanner)
    ImageView ivLastBanner;
    private TextView view_more;
    private TextView view_more3;
    private TextView view_more4;
    private TextView tvNoData;
    private RelativeLayout rlDealOfTheDay;
    private RelativeLayout rlBrandedShops;
    private RelativeLayout rlOfferOfTheDay;
    private RelativeLayout rlMostlyViewShops;
    private LinearLayout ll_most_viewed;
    private LinearLayout ll_recent;
    private LinearLayout ll_branded;
    private LinearLayout ll_deals;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeFragmentPresenter = new HomePresenter(context, this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }

        if (context instanceof CommonViewInterface){
            mCommonListener = (CommonViewInterface) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mListener.setTitle(getString(R.string.high_streets));
        toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        initView();

        SELECTED_CITY = SharedPrefs.getString(SharedPrefs.Keys.GET_CITY_NAME, "");
        if (TextUtils.isEmpty(SELECTED_CITY)) {
            isCitySelected = false;
            getCategories("-1");
            tvLocation.setText("Choose area or city");
        } else {
            isCitySelected = true;
            getCategories(SELECTED_CITY);
            tvLocation.setText(SELECTED_CITY);
        }
        return view;
    }


    private void initView() {
        view_more = view.findViewById(R.id.view_more);
        view_more3 = view.findViewById(R.id.view_more3);
        view_more4 = view.findViewById(R.id.view_more4);
        tvNoData = view.findViewById(R.id.tvNoData);
        ll_deals = view.findViewById(R.id.ll_deals);
        ll_most_viewed = view.findViewById(R.id.ll_most_viewed);
        ll_branded = view.findViewById(R.id.ll_branded);
        ll_recent = view.findViewById(R.id.ll_recent);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvCategories.setLayoutManager(layoutManager);
        rvCategories.setHasFixedSize(false);
        rvCategories.setNestedScrollingEnabled(false);
        rlDealOfTheDay = view.findViewById(R.id.rlDealsOfTheDay);
        layoutManager = new GridLayoutManager(getContext(), 2);
        rvDealsGrid.setLayoutManager(layoutManager);
        rvDealsGrid.setHasFixedSize(false);
        rvDealsGrid.setNestedScrollingEnabled(false);
        rlOfferOfTheDay = view.findViewById(R.id.rlOffersForYou);
        layoutManager = new GridLayoutManager(getContext(), 2);
        rvRecentlyBooked.setLayoutManager(layoutManager);
        rvRecentlyBooked.setHasFixedSize(false);
        rvRecentlyBooked.setNestedScrollingEnabled(false);
        rlBrandedShops = view.findViewById(R.id.rlBrandedShops);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvBrandedShops.setLayoutManager(layoutManager);
        rvBrandedShops.setHasFixedSize(false);
        rvBrandedShops.setNestedScrollingEnabled(false);
        rlMostlyViewShops = view.findViewById(R.id.rlMostlyViewedShops);
        layoutManager = new GridLayoutManager(getContext(), 2);
        rvMostVisited.setLayoutManager(layoutManager);
        rvMostVisited.setHasFixedSize(false);
        rvMostVisited.setNestedScrollingEnabled(false);
        tvLocation.setText(SELECTED_CITY);

        view_more.setOnClickListener(this);
        view_more3.setOnClickListener(this);
        view_more4.setOnClickListener(this);

        tvLocation.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), SelectLocationActivity.class);
            startActivityForResult(intent, LOCATION_REQUEST_CODE);
        });

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimer(), 2000, 4000);
    }

    private void getCategories(String city) {
        homeFragmentPresenter.getHomeDetails(city);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == LOCATION_REQUEST_CODE) {
                SELECTED_CITY = data.getStringExtra("city");
                String LATITUDE = data.getStringExtra("lat");
                String LONGITUDE = data.getStringExtra("lon");
                String CITY_ID = data.getStringExtra("city_id");
                SharedPrefs.setString(SharedPrefs.Keys.GET_CITY_NAME, SELECTED_CITY);
                SharedPrefs.setString(SharedPrefs.Keys.GET_CITY_LATITUDE, LATITUDE);
                SharedPrefs.setString(SharedPrefs.Keys.GET_CITY_LONGITUDE, LONGITUDE);
                SharedPrefs.setString(SharedPrefs.Keys.GET_CITY_ID, CITY_ID);
                tvLocation.setText(SELECTED_CITY);
                isCitySelected = true;
                getCategories(SELECTED_CITY);
            }
        }
    }

    @Override
    public void setCategoryList(final List<Category> categoryList) {
        mCategoryList = (ArrayList<Category>) categoryList;
        CategoryRecyclerAdapter categoryRecyclerAdapter = new CategoryRecyclerAdapter(getActivity(), categoryList, this);
        rvCategories.setAdapter(categoryRecyclerAdapter);
    }

    @Override
    public void setSliderList(List<Slider> sliderList) {
        mSliderList = sliderList;
        try {
            if (sliderList.size() > 0) {
                mPagerAdapter = new CustomPagerAdapter(getActivity(), sliderList);
                viewpager.setAdapter(mPagerAdapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setDealList(List<Deal> dealsModelList) {
        mDealsModelList = dealsModelList;
        try {
            if (dealsModelList.size() > 0) {
                String viewMoreStr = String.format("View All(%d)", dealsModelList.get(0).getTotalCount());
                view_more.setText(viewMoreStr);
                List<Deal> dealList;
                if (dealsModelList.size() > 4) {
                    dealList = dealsModelList.subList(0, 4);
                } else {
                    dealList = dealsModelList;
                }
                DealsRecyclerAdapter dealsRecyclerAdapter = new DealsRecyclerAdapter(getActivity(), dealList);
                rvDealsGrid.setAdapter(dealsRecyclerAdapter);
                int count = dealsModelList.get(0).getTotalCount();
                if (count <= 4) {
                    view_more.setVisibility(View.GONE);
                } else {
                    view_more.setVisibility(View.VISIBLE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setTopBannerList(List<TopBanner> topBannerList) {
        mTopBannerList = topBannerList;
        try {
            if (topBannerList.size() > 0) {
                String TOP_BANNER = topBannerList.get(0).getImage();
                if (getActivity() != null) {
                    Glide.with(getActivity())
                            .setDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.place_holder_rectangle))
                            .load(ApiClient.TOP_BANNER_BASE_URL + TOP_BANNER)
                            .into(ivTopBanner);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void setBrandedShopList(List<BrandedShop> brandedShopList) {
        mBrandedShopList = brandedShopList;
        try {
            if (brandedShopList.size() > 0) {
                BrandedShopAdapter brandedShopAdapter = new BrandedShopAdapter(getActivity(), brandedShopList);
                rvBrandedShops.setAdapter(brandedShopAdapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void setMiddleBannerList(List<MiddleBanner> middleBannerList) {
        mMiddleBannerList = middleBannerList;
        try {
            if (middleBannerList.size() > 0) {
                String MIDDLE_BANNER = middleBannerList.get(0).getImage();
                if (getActivity() != null) {
                    Glide.with(getActivity())
                            .setDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.place_holder_rectangle))
                            .load(ApiClient.MIDDLE_BANNER_BASE_URL + MIDDLE_BANNER)
                            .into(ivMiddleBanner);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setRecentlyBookedList(List<RecentlyBookedShop> recentlyBookedShopList) {
        mRecentlyBookedShopList = recentlyBookedShopList;
        try {
            if (recentlyBookedShopList.size() > 0) {
                List<RecentlyBookedShop> recentlyBookedShops;
                if (recentlyBookedShopList.size() > 4) {
                    recentlyBookedShops = recentlyBookedShopList.subList(0, 4);
                } else {
                    recentlyBookedShops = recentlyBookedShopList;
                }
                String viewMoreStr = String.format("View All(%d)", recentlyBookedShopList.get(0).getTotalCount());
                view_more3.setText(viewMoreStr);
                RecentlyBookedRecyclerAdapter recentlyBookedRecyclerAdapter = new RecentlyBookedRecyclerAdapter(getActivity(), recentlyBookedShops);
                rvRecentlyBooked.setAdapter(recentlyBookedRecyclerAdapter);
                int count = recentlyBookedShopList.get(0).getTotalCount();
                if (count <= 4) {
                    view_more3.setVisibility(View.GONE);
                } else {
                    view_more3.setVisibility(View.VISIBLE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setBottomBannersList(List<BottomBanner> bottomBannersList) {
        mBottomBannersList = bottomBannersList;
        try {
            if (bottomBannersList.size() > 0) {
                String BOTTOM_BANNER = bottomBannersList.get(0).getImage();
                if (getActivity() != null) {
                    Glide.with(getActivity())
                            .setDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.place_holder_rectangle))
                            .load(ApiClient.MIDDLE_BANNER_BASE_URL + BOTTOM_BANNER)
                            .into(ivLastBanner);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void setMostViewedShops(List<MostViewedShop> mostViewedShopList) {
        mMostViewedShopList = mostViewedShopList;
        try {
            if (mostViewedShopList.size() > 0) {
                String viewMoreStr = String.format("View All(%d)", mostViewedShopList.get(0).getTotalCount());
                view_more4.setText(viewMoreStr);
                List<MostViewedShop> mostViewedShops;
                if (mostViewedShopList.size() > 4) {
                    mostViewedShops = mostViewedShopList.subList(0, 4);
                } else {
                    mostViewedShops = mostViewedShopList;
                }
                MostVisitedShopAdapter mostVisitedShopAdapter = new MostVisitedShopAdapter(getActivity(), mostViewedShops);
                rvMostVisited.setAdapter(mostVisitedShopAdapter);
                int count = mostViewedShopList.get(0).getTotalCount();
                if (count <= 4) {
                    view_more4.setVisibility(View.GONE);
                } else {
                    view_more4.setVisibility(View.VISIBLE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!isCitySelected) {
            tvNoData.setVisibility(View.GONE);
            viewpager.setVisibility(View.VISIBLE);
            rlDealOfTheDay.setVisibility(View.VISIBLE);
            ivTopBanner.setVisibility(View.VISIBLE);
            rlBrandedShops.setVisibility(View.VISIBLE);
            ivMiddleBanner.setVisibility(View.VISIBLE);
            rlOfferOfTheDay.setVisibility(View.VISIBLE);
            ivLastBanner.setVisibility(View.VISIBLE);
            rlMostlyViewShops.setVisibility(View.VISIBLE);
        } else if (!isDataExists()) {
            tvNoData.setVisibility(View.VISIBLE);
            viewpager.setVisibility(View.GONE);
            rlDealOfTheDay.setVisibility(View.GONE);
            ivTopBanner.setVisibility(View.GONE);
            rlBrandedShops.setVisibility(View.GONE);
            ivMiddleBanner.setVisibility(View.GONE);
            rlOfferOfTheDay.setVisibility(View.GONE);
            ivLastBanner.setVisibility(View.GONE);
            rlMostlyViewShops.setVisibility(View.GONE);
        } else {
            valdate();
        }
    }

    @Override
    public void onResponseFailed(String message) {
        CommonUtils.showToast(getActivity(), message);
    }

    @Override
    public void onServerError(String message) {
        CommonUtils.showToast(getActivity(), message);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_more:
                Intent intent = new Intent(getContext(), ViewAllDealsActivity.class);
                intent.putExtra("city", SELECTED_CITY);
                startActivity(intent);
                break;
            case R.id.view_more3:
                Intent i = new Intent(getContext(), ViewAllRecentlyBookedActivity.class);
                i.putExtra("city", SELECTED_CITY);
                startActivity(i);
                break;

            case R.id.view_more4:
                Intent mostView = new Intent(getContext(), ViewAllMostViewShopActivity.class);
                mostView.putExtra("city", SELECTED_CITY);
                startActivity(mostView);
                break;
        }
    }

    @Override
    public void toAll() {
        Intent toAllCategories = MoreCategoriesActivity.start(getActivity());
        ArrayList<Category> categoryArrayList = new ArrayList<>();
        categoryArrayList.addAll(mCategoryList);
        categoryArrayList.remove(addRemoveItem);
        categoryArrayList.remove(categoryArrayList.get(categoryArrayList.size() - 1));
        toAllCategories.putParcelableArrayListExtra(Constants.CATEGORIES, categoryArrayList);
        getActivity().startActivity(toAllCategories);
    }

    @Override
    public void onLocationChanged(String place) {

    }

    @Override
    public void onRetry() {

        SELECTED_CITY = SharedPrefs.getString(SharedPrefs.Keys.GET_CITY_NAME, "-1");
        if (TextUtils.isEmpty(SELECTED_CITY)) {
            getCategories("-1");
            tvLocation.setText("Choose area or city");
        } else {
            getCategories(SELECTED_CITY);
            tvLocation.setText(SELECTED_CITY);
        }
    }

    private boolean isDataExists() {
        return (mDealsModelList.size() > 0
                || mBrandedShopList.size() > 0
                || mMostViewedShopList.size() > 0
                || mRecentlyBookedShopList.size() > 0);
    }

    private void valdate() {
        tvNoData.setVisibility(View.GONE);
        if (mDealsModelList.size() > 0) {
            ll_deals.setVisibility(View.VISIBLE);
            rlDealOfTheDay.setVisibility(View.VISIBLE);
            if (mSliderList.size() > 0)
                viewpager.setVisibility(View.VISIBLE);
            else
                viewpager.setVisibility(View.GONE);
        } else {
            ll_deals.setVisibility(View.GONE);
            rlDealOfTheDay.setVisibility(View.GONE);
            viewpager.setVisibility(View.GONE);
        }

        if (mBrandedShopList.size() > 0) {
            ll_branded.setVisibility(View.VISIBLE);
            rlBrandedShops.setVisibility(View.VISIBLE);
            if (mTopBannerList.size() > 0)
                ivTopBanner.setVisibility(View.VISIBLE);
            else
                ivTopBanner.setVisibility(View.GONE);
        } else {
            ll_branded.setVisibility(View.GONE);
            ivTopBanner.setVisibility(View.GONE);
            rlBrandedShops.setVisibility(View.GONE);
        }

        if (mRecentlyBookedShopList.size() > 0) {
            ll_recent.setVisibility(View.VISIBLE);
            rlOfferOfTheDay.setVisibility(View.VISIBLE);
            if (mMiddleBannerList.size() > 0)
                ivMiddleBanner.setVisibility(View.VISIBLE);
            else
                ivMiddleBanner.setVisibility(View.GONE);
        } else {
            ll_recent.setVisibility(View.GONE);
            rlOfferOfTheDay.setVisibility(View.GONE);
            ivMiddleBanner.setVisibility(View.GONE);
        }

        if (mMostViewedShopList.size() > 0) {
            ll_most_viewed.setVisibility(View.VISIBLE);
            rlMostlyViewShops.setVisibility(View.VISIBLE);
            if (mBottomBannersList.size() > 0)
                ivLastBanner.setVisibility(View.VISIBLE);
            else
                ivLastBanner.setVisibility(View.GONE);
        } else {
            ll_most_viewed.setVisibility(View.GONE);
            ivLastBanner.setVisibility(View.GONE);
            rlMostlyViewShops.setVisibility(View.GONE);
        }
    }

    class CustomPagerAdapter extends PagerAdapter {
        private Context mContext;
        private List<Slider> sliderList;

        public CustomPagerAdapter(Context mContext, List<Slider> bannerModels) {
            this.mContext = mContext;
            this.sliderList = bannerModels;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View Item = LayoutInflater.from(mContext).inflate(R.layout.banners_card, container, false);

            Slider slider = sliderList.get(position);

            ImageView imageView = Item.findViewById(R.id.banner_image);
            Glide.with(mContext)
                    .setDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.place_holder_rectangle))
                    .load(ApiClient.SLIDERS_BASE_URL + slider.getImage())
                    .into(imageView);
            container.addView(Item);

            return Item;
        }

        @Override
        public int getCount() {
            return sliderList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {

            return view == o;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }

    private class MyTimer extends TimerTask {
        @Override
        public void run() {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (viewpager.getAdapter() != null) {
                            int last = viewpager.getAdapter().getCount();
                            int current = viewpager.getCurrentItem() + 1;
                            if (current < last) {
                                viewpager.setCurrentItem(current);
                            } else viewpager.setCurrentItem(0);
                        }
                    }
                });
            }
        }
    }
}


