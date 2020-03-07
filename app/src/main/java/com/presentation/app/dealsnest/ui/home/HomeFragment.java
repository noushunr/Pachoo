package com.presentation.app.dealsnest.ui.home;

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
import com.presentation.app.dealsnest.R;
import com.presentation.app.dealsnest.adapters.BrandedShopAdapter;
import com.presentation.app.dealsnest.adapters.CategoryRecyclerAdapter;
import com.presentation.app.dealsnest.adapters.DealsRecyclerAdapter;
import com.presentation.app.dealsnest.adapters.MostVisitedShopAdapter;
import com.presentation.app.dealsnest.adapters.RecentlyBookedRecyclerAdapter;
import com.presentation.app.dealsnest.api.ApiClient;
import com.presentation.app.dealsnest.app_pref.GlobalPreferManager;
import com.presentation.app.dealsnest.common.CommonViewInterface;
import com.presentation.app.dealsnest.common.OnFragmentInteractionListener;
import com.presentation.app.dealsnest.models.BottomBanner;
import com.presentation.app.dealsnest.models.BrandedShop;
import com.presentation.app.dealsnest.models.Category;
import com.presentation.app.dealsnest.models.Deal;
import com.presentation.app.dealsnest.models.MiddleBanner;
import com.presentation.app.dealsnest.models.MostViewedShop;
import com.presentation.app.dealsnest.models.RecentlyBookedShop;
import com.presentation.app.dealsnest.models.Slider;
import com.presentation.app.dealsnest.models.TopBanner;
import com.presentation.app.dealsnest.ui.BaseFragment;
import com.presentation.app.dealsnest.ui.dialog_fragment.ProgressDialogFragment;
import com.presentation.app.dealsnest.ui.most_viewed_shop.ViewAllMostViewShopActivity;
import com.presentation.app.dealsnest.ui.select_location.SelectLocationActivity;
import com.presentation.app.dealsnest.ui.view_all_deals.ViewAllDealsActivity;
import com.presentation.app.dealsnest.ui.view_all_recently_booked.ViewAllRecentlyBookedActivity;
import com.presentation.app.dealsnest.utils.CommonUtils;
import com.presentation.app.dealsnest.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends BaseFragment implements
        HomeFragmentViewInterface,
        CommonViewInterface,
        View.OnClickListener,
        CategoryRecyclerAdapter.ToAllCategories,
        HomeMainActivity.LocationChange {

    private final static int LOCATION_REQUEST_CODE = 101;
    private View view;
    private ViewPager mViewPager;
    private CustomPagerAdapter mPagerAdapter;
    private ActionBar toolbar;
    private HomeFragmentPresenterInterface homeFragmentPresenter;
    private TextView mLocation;
    private ImageView mTopBanner, mMiddleBanner, mBottomBanner;
    private ProgressDialogFragment progressDialogFragment;
    private TextView view_more;
    private TextView view_more3;
    private TextView view_more4;
    private TextView tvNoData;
    private RecyclerView mCategoryRecycler, mDealsRecycler, mRecentlyBookedRecycler, mBrandedShopRecycler, mMostVisitedShopRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private CategoryRecyclerAdapter categoryRecyclerAdapter;
    private DealsRecyclerAdapter dealsRecyclerAdapter;
    private RecentlyBookedRecyclerAdapter recentlyBookedRecyclerAdapter;
    private BrandedShopAdapter brandedShopAdapter;
    private MostVisitedShopAdapter mostVisitedShopAdapter;
    private ArrayList<Category> mCategoryList = new ArrayList<>();
    private Category addRemoveItem;
    private Context context;
    private RelativeLayout rlDealOfTheDay, rlBrandedShops, rlOfferOfTheDay, rlMostlyViewShops;
    private String SELECTED_CITY, TOP_BANNER, MIDDLE_BANNER, BOTTOM_BANNER;
    private String CITY_ID, LATITUDE, LONGITUDE;
    private List<Slider> mSliderList = new ArrayList<>();
    private List<Deal> mDealsModelList = new ArrayList<>();
    private List<TopBanner> mTopBannerList = new ArrayList<>();
    private List<BrandedShop> mBrandedShopList = new ArrayList<>();
    private List<MiddleBanner> mMiddleBannerList = new ArrayList<>();
    private List<RecentlyBookedShop> mRecentlyBookedShopList = new ArrayList<>();
    private List<BottomBanner> mBottomBannersList = new ArrayList<>();
    private List<MostViewedShop> mMostViewedShopList = new ArrayList<>();
    private OnFragmentInteractionListener mListener;
    private CommonViewInterface mCommonListener;
    private boolean isCitySelected;
    private LinearLayout ll_most_viewed, ll_recent, ll_branded, ll_deals;

    private boolean mIsStateAlreadySaved = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeFragmentPresenter = new HomeFragmentPresenter(context, this);
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
        mListener.setTitle(getString(R.string.dealsnest));
        toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        view = inflater.inflate(R.layout.fragment_home, container, false);
        initView();

        SELECTED_CITY = GlobalPreferManager.getString(GlobalPreferManager.Keys.GET_CITY_NAME, "");
        if (TextUtils.isEmpty(SELECTED_CITY)) {
            isCitySelected = false;
            getCategories("-1");
            mLocation.setText("Choose area or city");
        } else {
            isCitySelected = true;
            getCategories(SELECTED_CITY);
            mLocation.setText(SELECTED_CITY);
        }
        return view;
    }


    private void initView() {
        mViewPager = view.findViewById(R.id.viewpager);
        view_more = view.findViewById(R.id.view_more);
        view_more3 = view.findViewById(R.id.view_more3);
        view_more4 = view.findViewById(R.id.view_more4);
        tvNoData = view.findViewById(R.id.tvNoData);
        ll_deals = view.findViewById(R.id.ll_deals);
        ll_most_viewed = view.findViewById(R.id.ll_most_viewed);
        ll_branded = view.findViewById(R.id.ll_branded);
        ll_recent = view.findViewById(R.id.ll_recent);
        mTopBanner = view.findViewById(R.id.top_banner);
        mMiddleBanner = view.findViewById(R.id.middle_banner);
        mBottomBanner = view.findViewById(R.id.last_banner);
        mCategoryRecycler = view.findViewById(R.id.category_grid);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mCategoryRecycler.setLayoutManager(layoutManager);
        mCategoryRecycler.setHasFixedSize(false);
        mCategoryRecycler.setNestedScrollingEnabled(false);
        rlDealOfTheDay = view.findViewById(R.id.rlDealsOfTheDay);
        mDealsRecycler = view.findViewById(R.id.deals_grid);
        layoutManager = new GridLayoutManager(getContext(), 2);
        mDealsRecycler.setLayoutManager(layoutManager);
        mDealsRecycler.setHasFixedSize(false);
        mDealsRecycler.setNestedScrollingEnabled(false);
        rlOfferOfTheDay = view.findViewById(R.id.rlOffersForYou);
        mRecentlyBookedRecycler = view.findViewById(R.id.recently_booked_recycler);
        layoutManager = new GridLayoutManager(getContext(), 2);
        mRecentlyBookedRecycler.setLayoutManager(layoutManager);
        mRecentlyBookedRecycler.setHasFixedSize(false);
        mRecentlyBookedRecycler.setNestedScrollingEnabled(false);
        rlBrandedShops = view.findViewById(R.id.rlBrandedShops);
        mBrandedShopRecycler = view.findViewById(R.id.branded_shops);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mBrandedShopRecycler.setLayoutManager(layoutManager);
        mBrandedShopRecycler.setHasFixedSize(false);
        mBrandedShopRecycler.setNestedScrollingEnabled(false);
        rlMostlyViewShops = view.findViewById(R.id.rlMostlyViewedShops);
        mMostVisitedShopRecyclerView = view.findViewById(R.id.most_visited_recycler);
        layoutManager = new GridLayoutManager(getContext(), 2);
        mMostVisitedShopRecyclerView.setLayoutManager(layoutManager);
        mMostVisitedShopRecyclerView.setHasFixedSize(false);
        mMostVisitedShopRecyclerView.setNestedScrollingEnabled(false);
        mLocation = view.findViewById(R.id.location);
        mLocation.setText(SELECTED_CITY);

        view_more.setOnClickListener(this);
        view_more3.setOnClickListener(this);
        view_more4.setOnClickListener(this);

        mLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SelectLocationActivity.class);
                startActivityForResult(intent, LOCATION_REQUEST_CODE);

            }
        });

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimer(), 2000, 4000);
    }

    public void getCategories(String city) {
        homeFragmentPresenter.getHomeDetails(city);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == LOCATION_REQUEST_CODE) {
                SELECTED_CITY = data.getStringExtra("city");
                LATITUDE = data.getStringExtra("lat");
                LONGITUDE = data.getStringExtra("lon");
                CITY_ID = data.getStringExtra("city_id");
                GlobalPreferManager.setString(GlobalPreferManager.Keys.GET_CITY_NAME, SELECTED_CITY);
                GlobalPreferManager.setString(GlobalPreferManager.Keys.GET_CITY_LATITUDE, LATITUDE);
                GlobalPreferManager.setString(GlobalPreferManager.Keys.GET_CITY_LONGITUDE, LONGITUDE);
                GlobalPreferManager.setString(GlobalPreferManager.Keys.GET_CITY_ID, CITY_ID);
                mLocation.setText(SELECTED_CITY);
                isCitySelected = true;
                getCategories(SELECTED_CITY);
            }
        }
    }


    @Override
    public void setCategoryList(final List<Category> categoryList) {
        mCategoryList = (ArrayList<Category>) categoryList;
        List<Category> listOf10Categories = mCategoryList.subList(0, 9);
        addRemoveItem = categoryList.get(categoryList.size() - 1);
        listOf10Categories.add(addRemoveItem);
        categoryRecyclerAdapter = new CategoryRecyclerAdapter(getActivity(), listOf10Categories, this);
        mCategoryRecycler.setAdapter(categoryRecyclerAdapter);
    }

    @Override
    public void setSliderList(List<Slider> sliderList) {
        mSliderList = sliderList;
        try {
            if (sliderList.size() > 0) {
                mPagerAdapter = new CustomPagerAdapter(getActivity(), sliderList);
                mViewPager.setAdapter(mPagerAdapter);
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
                dealsRecyclerAdapter = new DealsRecyclerAdapter(getActivity(), dealList);
                mDealsRecycler.setAdapter(dealsRecyclerAdapter);
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
                TOP_BANNER = topBannerList.get(0).getImage();
                if (getActivity() != null) {
                    Glide.with(getActivity())
                            .setDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.place_holder_rectangle))
                            .load(ApiClient.TOP_BANNER_BASE_URL + TOP_BANNER)
                            .into(mTopBanner);
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
                brandedShopAdapter = new BrandedShopAdapter(getActivity(), brandedShopList);
                mBrandedShopRecycler.setAdapter(brandedShopAdapter);
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
                MIDDLE_BANNER = middleBannerList.get(0).getImage();
                if (getActivity() != null) {
                    Glide.with(getActivity())
                            .setDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.place_holder_rectangle))
                            .load(ApiClient.MIDDLE_BANNER_BASE_URL + MIDDLE_BANNER).into(mMiddleBanner);
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
                recentlyBookedRecyclerAdapter = new RecentlyBookedRecyclerAdapter(getActivity(), recentlyBookedShops);
                mRecentlyBookedRecycler.setAdapter(recentlyBookedRecyclerAdapter);
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
                BOTTOM_BANNER = bottomBannersList.get(0).getImage();
                if (getActivity() != null) {
                    Glide.with(getActivity())
                            .setDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.place_holder_rectangle))
                            .load(ApiClient.MIDDLE_BANNER_BASE_URL + BOTTOM_BANNER)
                            .into(mBottomBanner);
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
                mostVisitedShopAdapter = new MostVisitedShopAdapter(getActivity(), mostViewedShops);
                mMostVisitedShopRecyclerView.setAdapter(mostVisitedShopAdapter);
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
            mViewPager.setVisibility(View.VISIBLE);
            rlDealOfTheDay.setVisibility(View.VISIBLE);
            mTopBanner.setVisibility(View.VISIBLE);
            rlBrandedShops.setVisibility(View.VISIBLE);
            mMiddleBanner.setVisibility(View.VISIBLE);
            rlOfferOfTheDay.setVisibility(View.VISIBLE);
            mBottomBanner.setVisibility(View.VISIBLE);
            rlMostlyViewShops.setVisibility(View.VISIBLE);
        } else if (!isDataExists()) {

            tvNoData.setVisibility(View.VISIBLE);
            mViewPager.setVisibility(View.GONE);
            rlDealOfTheDay.setVisibility(View.GONE);
            mTopBanner.setVisibility(View.GONE);
            rlBrandedShops.setVisibility(View.GONE);
            mMiddleBanner.setVisibility(View.GONE);
            rlOfferOfTheDay.setVisibility(View.GONE);
            mBottomBanner.setVisibility(View.GONE);
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

      /*  if (!mIsStateAlreadySaved) {
            if (getActivity() != null) {
                progressDialogFragment = ProgressDialogFragment.newInstance();
                progressDialogFragment.show(getActivity().getSupportFragmentManager(), "progress_dialog");
            }
        }*/
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

        SELECTED_CITY = GlobalPreferManager.getString(GlobalPreferManager.Keys.GET_CITY_NAME, "");
        if (TextUtils.isEmpty(SELECTED_CITY)) {
            getCategories("-1");
            mLocation.setText("Choose area or city");
        } else {
            getCategories(SELECTED_CITY);
            mLocation.setText(SELECTED_CITY);
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
                mViewPager.setVisibility(View.VISIBLE);
            else
                mViewPager.setVisibility(View.GONE);
        } else {
            ll_deals.setVisibility(View.GONE);
            rlDealOfTheDay.setVisibility(View.GONE);
            mViewPager.setVisibility(View.GONE);
        }

        if (mBrandedShopList.size() > 0) {
            ll_branded.setVisibility(View.VISIBLE);
            rlBrandedShops.setVisibility(View.VISIBLE);
            if (mTopBannerList.size() > 0)
                mTopBanner.setVisibility(View.VISIBLE);
            else
                mTopBanner.setVisibility(View.GONE);
        } else {
            ll_branded.setVisibility(View.GONE);
            mTopBanner.setVisibility(View.GONE);
            rlBrandedShops.setVisibility(View.GONE);
        }

        if (mRecentlyBookedShopList.size() > 0) {
            ll_recent.setVisibility(View.VISIBLE);
            rlOfferOfTheDay.setVisibility(View.VISIBLE);
            if (mMiddleBannerList.size() > 0)
                mMiddleBanner.setVisibility(View.VISIBLE);
            else
                mMiddleBanner.setVisibility(View.GONE);
        } else {
            ll_recent.setVisibility(View.GONE);
            rlOfferOfTheDay.setVisibility(View.GONE);
            mMiddleBanner.setVisibility(View.GONE);
        }

        if (mMostViewedShopList.size() > 0) {
            ll_most_viewed.setVisibility(View.VISIBLE);
            rlMostlyViewShops.setVisibility(View.VISIBLE);
            if (mBottomBannersList.size() > 0)
                mBottomBanner.setVisibility(View.VISIBLE);
            else
                mBottomBanner.setVisibility(View.GONE);
        } else {
            ll_most_viewed.setVisibility(View.GONE);
            mBottomBanner.setVisibility(View.GONE);
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
                        if (mViewPager.getAdapter() != null) {
                            int last = mViewPager.getAdapter().getCount();
                            int current = mViewPager.getCurrentItem() + 1;
                            if (current < last) {
                                mViewPager.setCurrentItem(current);
                            } else mViewPager.setCurrentItem(0);
                        }
                    }
                });
            }
        }
    }
}


