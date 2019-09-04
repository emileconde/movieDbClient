package com.example.android.conde.com.cinephile.util;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;

import androidx.recyclerview.widget.RecyclerView;

public class AnimationUtil {

    public static void animateY(RecyclerView.ViewHolder holder, boolean goesDown) {
        AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator animatorTranslateY = ObjectAnimator.ofFloat(holder.itemView,
                "translationY", goesDown ? 200 : -200, 0);
        animatorTranslateY.setDuration(1000);

        animatorSet.playTogether(animatorTranslateY);
        animatorSet.start();

    }


    public static void animateX(RecyclerView.ViewHolder holder, boolean goesRight){

        AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator animatorTranslateX = ObjectAnimator.ofFloat(holder.itemView,
                "translationX", goesRight? 200:-200, 0);
        animatorTranslateX.setDuration(1000);

        animatorSet.playTogether(animatorTranslateX);

        animatorSet.start();

    }


}
