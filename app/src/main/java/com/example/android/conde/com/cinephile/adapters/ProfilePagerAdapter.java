package com.example.android.conde.com.cinephile.adapters;

import android.content.Context;

import com.example.android.conde.com.cinephile.ActorBioFragment;
import com.example.android.conde.com.cinephile.ActorFilmographyFragment;
import com.example.android.conde.com.cinephile.R;
import com.example.android.conde.com.cinephile.util.Constants;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ProfilePagerAdapter extends FragmentStatePagerAdapter{
    private Context mContext;
    public ProfilePagerAdapter(@NonNull FragmentManager fm, int behavior, Context context) {
        super(fm, behavior);
        mContext = context;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case Constants.FRAGMENT_BIO:
                return   new ActorBioFragment();
            case Constants.FRAGMENT_FILMOGRAPHY:
                return new ActorFilmographyFragment();
        }
        return null;
    }


    @Override
    public int getCount() {
        return Constants.NUMBER_OF_PROFILE_ELEMENT;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case Constants.FRAGMENT_BIO:
                return mContext.getString(R.string.fragment_bio_title);
            case Constants.FRAGMENT_FILMOGRAPHY:
                return mContext.getString(R.string.fragment_filmography_title);
        }
        return null;
    }

}
