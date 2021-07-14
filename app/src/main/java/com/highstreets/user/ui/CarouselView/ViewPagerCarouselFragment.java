package com.highstreets.user.ui.CarouselView;

import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.highstreets.user.R;
import com.highstreets.user.api.ApiClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static com.highstreets.user.api.ApiClient.MERCHANTS_IMAGE_URL;
import static com.highstreets.user.api.ApiClient.OFFERS_IMAGE_URL;

/**
 * Created by Noushad on 14/06/2020.
 **/
public class ViewPagerCarouselFragment extends Fragment {

    public static final String IMAGE_RESOURCE_ID = "imageResourceId";
    private ImageView mCarouselImage;

    private int mPos = 0;

    private ArrayList<String> mArrayListBanners;

    private String mImageUrl;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.view_pager_carousel_fragment, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        checkBundle();
        initView(view);
    }

    private void checkBundle() {
        mArrayListBanners = new ArrayList<>();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mImageUrl = bundle.getString("imageUrl");

        }
    }

    private void initView(View view) {
        mCarouselImage = view.findViewById(R.id.iv_carousel_image);
        if (mImageUrl != null) {
            Glide.with(getContext())
                    .load(OFFERS_IMAGE_URL + mImageUrl)
                    .into(mCarouselImage);

        }


    }
}


