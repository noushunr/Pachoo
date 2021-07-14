package com.highstreets.user.ui.CarouselView;

import android.os.Bundle;

import com.highstreets.user.models.Image;
import com.highstreets.user.models.Offer;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

/**
 * Created by Noushad on 14/06/2020.
 **/

public class ViewPagerCarouselAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Offer> mImageArrayList;
    private ArrayList<Offer> mArrayListBanners;
    private String mImageUrl;


    public ViewPagerCarouselAdapter(FragmentManager fm, ArrayList<Offer> mArrayListBanners) {
        super(fm);
        this.mArrayListBanners = mArrayListBanners;

    }

    @Override
    public Fragment getItem(int position) {
        mImageArrayList = new ArrayList();
        for (int i = 0; i < mArrayListBanners.size(); i++) {
            mImageArrayList.add(mArrayListBanners.get(i));
        }
        mImageUrl = mArrayListBanners.get(position).getFeaturedImage();
        //  mContentUrl = mArrayListBanners.get(position).getContentURL();

        Bundle bundle = new Bundle();
        //bundle.putString(ViewPagerCarouselFragment.IMAGE_RESOURCE_ID, mImageArrayList.get(position).toString());

        bundle.putString("imageUrl", mImageUrl);


        bundle.putInt("position", position);
        ViewPagerCarouselFragment frag = new ViewPagerCarouselFragment();
        frag.setArguments(bundle);
        return frag;
    }

    @Override
    public int getCount() {
        return (mArrayListBanners == null) ? 0 : mArrayListBanners.size();
    }

}