package com.example.android.conde.com.cinephile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.android.conde.com.cinephile.R;
import com.example.android.conde.com.cinephile.models.Movie;
import com.example.android.conde.com.cinephile.util.Constants;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class SliderPagerAdapter extends PagerAdapter {

    private Context mContext ;
    private List<Movie> mList ;

    public SliderPagerAdapter(Context mContext, List<Movie> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View slideLayout = inflater.inflate(R.layout.item_slide,null);

        ImageView slideImg = slideLayout.findViewById(R.id.slide_img);
        TextView slideText = slideLayout.findViewById(R.id.slide_title);

        Glide.with(mContext)
                .load(Constants.BASE_IMAGE_URL+ Constants.BACK_DROP_IMAGE_SIZE +mList.get(position).getBackDropPath())
                //.placeholder(R)
                //.error(R.drawable.ic_profile_placeholder)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(slideImg);

        slideText.setText(mList.get(position).getTitle());


        container.addView(slideLayout);
        return slideLayout;






    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
