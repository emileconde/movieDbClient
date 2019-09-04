package com.example.android.conde.com.cinephile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.conde.com.cinephile.models.Profile;
import com.example.android.conde.com.cinephile.util.Constants;
import com.example.android.conde.com.cinephile.util.Global;
import com.example.android.conde.com.cinephile.viewmodels.ProfileViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class ActorBioFragment extends Fragment {
    private static final String TAG = "Movie";
    private ProfileViewModel mProfileViewModel;
    private TextView mTvName, mTvOccupation, mTvBirthday, mTvBirthPlace, mTvBio;
    public ActorBioFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_actor_bio, container, false);
        mProfileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        mTvName = view.findViewById(R.id.tv_actor_name);
        mTvOccupation = view.findViewById(R.id.tv_actor_occupation);
        mTvBirthday = view.findViewById(R.id.tv_actor_birthday);
        mTvBirthPlace = view.findViewById(R.id.tv_actor_birth_place);
        mTvBio = view.findViewById(R.id.tv_actor_bio);
        makeQueries(Global.castID);
        subscribeObservers();
        return view;
    }



    private void makeQueries(int actorId){
        mProfileViewModel.queryProfile(actorId, Constants.API_KEY);
    }

    private void setViews(Profile profile){
        mTvName.setText(String.format("%s%s", getContext().getString(R.string.actor_name), profile.getName()));
        mTvOccupation.setText(String.format("%s%s", getContext().getString(R.string.actor_occupation),profile.getOccupation()));
        mTvBirthday.setText(String.format("%s%s", getContext().getString(R.string.actor_birthday),profile.getBirthDay()));
        mTvBirthPlace.setText(String.format("%s%s", getContext().getString(R.string.actor_birth_place),profile.getBirthPlace()));
        mTvBio.setText(String.format("%s%s", getContext().getString(R.string.actor_biography),profile.getBio()));
    }


    private void subscribeObservers(){
        mProfileViewModel.getProfile().observe(this, new Observer<Profile>() {
            @Override
            public void onChanged(Profile profile) {
                if (profile!= null)
                setViews(profile);
            }
        });

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Global.castID = 0;
    }

}
