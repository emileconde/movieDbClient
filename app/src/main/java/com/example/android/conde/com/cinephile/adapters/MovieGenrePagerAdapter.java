package com.example.android.conde.com.cinephile.adapters;

import android.content.Context;

import com.example.android.conde.com.cinephile.ActionGenreFragment;
import com.example.android.conde.com.cinephile.AdventureGenreFragment;
import com.example.android.conde.com.cinephile.AnimationGenreFragment;
import com.example.android.conde.com.cinephile.ComedyGenreFragment;
import com.example.android.conde.com.cinephile.DocumentaryGenreFragment;
import com.example.android.conde.com.cinephile.R;
import com.example.android.conde.com.cinephile.util.Constants;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MovieGenrePagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public MovieGenrePagerAdapter(@NonNull FragmentManager fm, int behavior, Context context) {
        super(fm, behavior);
        mContext = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case Constants.FRAGMENT_ACTION:
                return new ActionGenreFragment();
            case Constants.FRAGMENT_ADVENTURE:
                return new AdventureGenreFragment();
            case Constants.FRAGMENT_ANIMATION:
                return new AnimationGenreFragment();
            case Constants.FRAGMENT_COMEDY:
                return new ComedyGenreFragment();
            case Constants.FRAGMENT_DOCUMENTARY:
                return new DocumentaryGenreFragment();

        }
        return null;
    }

    @Override
    public int getCount() {
        return Constants.NUMBER_OF_GENRE_ELEMENT;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case Constants.FRAGMENT_ACTION:
               return mContext.getString(R.string.fragment_action_title);
            case Constants.FRAGMENT_ADVENTURE:
                return mContext.getString(R.string.fragment_adventure_title);
            case Constants.FRAGMENT_ANIMATION:
                return mContext.getString(R.string.fragment_animation_title);
            case Constants.FRAGMENT_COMEDY:
                return mContext.getString(R.string.fragment_comedy_title);
            case Constants.FRAGMENT_DOCUMENTARY:
                return mContext.getString(R.string.fragment_documentary_title);

        }
        return null;
    }
}
