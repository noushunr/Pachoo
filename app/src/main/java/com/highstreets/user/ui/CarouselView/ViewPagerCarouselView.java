package com.highstreets.user.ui.CarouselView;

import android.content.Context;
import android.os.Handler;

import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.highstreets.user.R;
import com.highstreets.user.models.Image;
import com.highstreets.user.models.Offer;

import java.util.ArrayList;

import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;


/**
 * Created by Noushad on 14/06/2020.
 */
public class ViewPagerCarouselView extends RelativeLayout {
    private FragmentManager mFragmentManager;                // FragmentManager for managing the fragments withing the ViewPager
    private ViewPager mViewPagerCarousel;                           // ViewPager for the Carousel view
    private LinearLayout mLinearLayoutPageIndicatorContainer;          // Carousel view item indicator, the little bullets at the bottom of the carousel
    private ArrayList<ImageView> mCarouselPageIndicators;    // Carousel view item, the little bullet at the bottom of the carousel
    //private int [] mImageResourceIds;                        // Carousel view background image
    private long mCarouselSlideInterval;                     // Carousel view item sliding interval
    private Handler mCarouselHandler;                        // Carousel view item sliding interval automation handler
    private ArrayList mArrayListImages;

    public ViewPagerCarouselView(Context context) {
        super(context);
        initView(context);
    }

    public ViewPagerCarouselView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ViewPagerCarouselView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    public void initView(Context context) {
        mArrayListImages = new ArrayList();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_pager_carousel_view, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mViewPagerCarousel = (ViewPager) this.findViewById(R.id.vp_carousel);
        mLinearLayoutPageIndicatorContainer = (LinearLayout) this.findViewById(R.id.ll_page_indicator_container);
    }

    /**
     * Set the data and initialize the carousel view
     * @param fragmentManager
     * @param mArrayListImages
     * @param carouselSlideInterval
     */
    public void setData(FragmentManager fragmentManager, ArrayList<Offer> mArrayListImages, long carouselSlideInterval) {
        this.mFragmentManager = fragmentManager;
        this.mArrayListImages = mArrayListImages;
        this.mCarouselSlideInterval = carouselSlideInterval;
        initData();
        initCarousel();
        initCarouselSlide();
    }

    /**
     * Initialize the data for the carousel
     */
    private void initData() {
        mCarouselPageIndicators = new ArrayList<>();
        mCarouselPageIndicators.clear();
        mLinearLayoutPageIndicatorContainer.removeAllViews();
        if (mArrayListImages != null) {
            if (mArrayListImages.size()>1){
                mLinearLayoutPageIndicatorContainer.setVisibility(VISIBLE);
                for (int i = 0; i < mArrayListImages.size(); i++) {
                    ImageView obj = new ImageView(getContext());
                    obj.setImageResource(R.drawable.selector_carousel_page_indicator);
                    obj.setPadding(0, 0, 5, 0); // left, top, right, bottom
                    mLinearLayoutPageIndicatorContainer.addView(obj);
                    mCarouselPageIndicators.add(obj);
                }
            }else {
                mLinearLayoutPageIndicatorContainer.setVisibility(GONE);
            }

        }
    }

    /**
     * Initialize carousel views, each item in the carousel view is a fragment
     */
    private void initCarousel() {
        if (mCarouselPageIndicators!=null && mCarouselPageIndicators.size()>0){
            mCarouselPageIndicators.get(0).setSelected(true);

            // Update the carousel page indicator on change
            mViewPagerCarousel.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    for (int i = 0; i < mCarouselPageIndicators.size(); i++) {
                        if (i == position)
                            mCarouselPageIndicators.get(position).setSelected(true);
                        else
                            mCarouselPageIndicators.get(i).setSelected(false);
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
        }


        ViewPagerCarouselAdapter viewPagerCarouselAdapter
                = new ViewPagerCarouselAdapter(mFragmentManager, mArrayListImages);
        mViewPagerCarousel.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                page.setRotationY(position * -30);
            }
        });
        mViewPagerCarousel.setAdapter(viewPagerCarouselAdapter);

    }

    /**
     * Handler to make the view pager to slide automatically
     */
    private void initCarouselSlide() {
        final int nCount = mArrayListImages.size();
        try {
            mCarouselHandler = new Handler();
            mCarouselHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    int curPos = mViewPagerCarousel.getCurrentItem();
                    curPos++;
                    if (curPos == nCount) curPos = 0;
                    mViewPagerCarousel.setCurrentItem(curPos, true);
                    mCarouselHandler.postDelayed(this, mCarouselSlideInterval);
                }
            }, mCarouselSlideInterval);

        } catch (Exception e) {
            Log.d("MainActivity", e.getMessage());
        }
    }

    public void onDestroy() {
        if (mCarouselHandler != null)
            mCarouselHandler.removeCallbacksAndMessages(null); // remove call backs to prevent memory leaks
    }
}