package com.mahdikh.vision.scrolltotop.animator;

import android.view.View;

public class FadeAnimator extends BaseAnimator {
    private float maxAlpha;

    public FadeAnimator(float maxAlpha) {
        this.maxAlpha = maxAlpha;
    }

    public FadeAnimator() {
        maxAlpha = 0.8F;
    }

    @Override
    public void onPrepare(View view) {
        view.setAlpha(0.0F);
        view.setVisibility(View.GONE);
    }

    @Override
    public void onShow(View view) {
        view.setVisibility(View.VISIBLE);
        view.setAlpha(0.0F);
        view.animate()
                .alpha(maxAlpha)
                .setDuration(getDuration())
                .setInterpolator(getInterpolator());
    }

    @Override
    public void onHide(View view) {
        view.animate()
                .alpha(0F)
                .setDuration(getDuration())
                .setInterpolator(getInterpolator())
                .withEndAction(() -> view.setVisibility(View.GONE));
    }

    public FadeAnimator setMaxAlpha(float maxAlpha) {
        this.maxAlpha = maxAlpha;
        return this;
    }

    public float getMaxAlpha() {
        return maxAlpha;
    }
}
