package com.example.android.conde.com.cinephile;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.android.conde.com.cinephile.adapters.ProfilePagerAdapter;
import com.example.android.conde.com.cinephile.models.Cast;
import com.example.android.conde.com.cinephile.util.Constants;
import com.example.android.conde.com.cinephile.util.Global;
import com.example.android.conde.com.cinephile.viewmodels.ProfileViewModel;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

public class ProfileDetailActivity extends AppCompatActivity {
    private ImageView mProfile;
    private ProfileViewModel mProfileViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        initViews();
    }

    private void initViews() {
        Cast cast = getIntent().getParcelableExtra(Constants.CAST_KEY);
        mProfileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        mProfileViewModel.getCastID().setValue(cast.getId());
        TabLayout profileTabLayout = findViewById(R.id.profile_tab_layout);
        ViewPager profileViewPager = findViewById(R.id.profile_viewPager);
        mProfile = findViewById(R.id.iv_profile);
        ProfilePagerAdapter profilePagerAdapter =
                new ProfilePagerAdapter(getSupportFragmentManager(),
                        FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, this);
        profileViewPager.setAdapter(profilePagerAdapter);
        profileTabLayout.setupWithViewPager(profileViewPager);
        Glide.with(this)
                .load(Constants.BASE_IMAGE_URL + Constants.PROFILE_IMAGE_SIZE
                        + cast.getProfile_path())
                .placeholder(R.drawable.ic_image_black_32dp)
                .error(R.drawable.ic_broken_image_black_32dp)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(mProfile);
        mProfile.setAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_animation));
        Global.castID = cast.getId();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


}

