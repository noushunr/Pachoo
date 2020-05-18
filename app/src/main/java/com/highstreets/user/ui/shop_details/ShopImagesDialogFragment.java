package com.highstreets.user.ui.shop_details;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.highstreets.user.R;
import com.highstreets.user.api.ApiClient;
import com.highstreets.user.models.Image;

import java.util.ArrayList;
import java.util.List;

public class ShopImagesDialogFragment extends DialogFragment implements View.OnClickListener {

    List<Image> shopImagesArrayList = new ArrayList<>();
    CustomImageAdapter customImageAdapter;
    private ImageView ivClose;
    private RelativeLayout rlParent;

    public static ShopImagesDialogFragment newInstance(List<Image> imageList) {
        ShopImagesDialogFragment fragment = new ShopImagesDialogFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("image_list", (ArrayList<? extends Parcelable>) imageList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.fragment_shop_imnages, null, false);
        initViews(v);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(v);
        AlertDialog alertDialog = builder.create();
        if (alertDialog.getWindow() != null)
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return alertDialog;
    }

    private void initViews(View v) {
        Bundle bundle = getArguments();
        shopImagesArrayList = bundle.getParcelableArrayList("image_list");
        ViewPager viewPager = v.findViewById(R.id.image_view_pager);
        ivClose = v.findViewById(R.id.ivClose);
        rlParent = v.findViewById(R.id.rlParent);
        rlParent.setOnClickListener(this);
        ivClose.setOnClickListener(this);
        customImageAdapter = new CustomImageAdapter(getActivity(), shopImagesArrayList);
        viewPager.setAdapter(customImageAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlParent:
            case R.id.ivClose: {
                dismiss();
                break;
            }
        }
    }

    public class CustomImageAdapter extends PagerAdapter {

        Context mContext;
        List<Image> shopImagesList;

        public CustomImageAdapter(Context mContext, List<Image> shopImagesList) {
            this.mContext = mContext;
            this.shopImagesList = shopImagesList;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.shop_image_item, container, false);

            Image items = shopImagesList.get(position);
            ImageView imageView = view.findViewById(R.id.shop_images);
            Glide.with(mContext)
                    .setDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.place_holder_rectangle))
                    .load(ApiClient.MERCHANTS_IMAGE_URL + items.getImages())
                    .into(imageView);
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return shopImagesList.size();
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
}
