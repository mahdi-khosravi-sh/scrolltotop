package com.mahdikh.vision.scrolltotop.animator;

import android.view.View;
import android.view.animation.Interpolator;

import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

public class FlyAnimator extends SlideAnimator {
    private boolean      flyFlag         = false;
    private boolean      hasShowRequest  = false;
    private int          flyDuration     = 1200;
    private float        flyToScale      = 0.8F;
    private float        flyToAlpha      = 0.0F;
    private float        flyToY          = 0.0F;
    private Interpolator flyInterpolator = new FastOutSlowInInterpolator();

    @Override
    public void onShow(View view) {
        if (!flyFlag) {
            view.setRotation(0.0F);
            super.onShow(view);
        } else {
            hasShowRequest = true;
        }
    }

    @Override
    public void onHide(View view) {
        if (!flyFlag) {
            view.setRotation(180.0F);
            super.onHide(view);
        }
    }

    @Override
    public void onStartScroll(View view) {
        flyFlag = true;
        view.animate()
                .setDuration(flyDuration)
                .setInterpolator(flyInterpolator)
                .y(flyToY)
                .alpha(flyToAlpha)
                .scaleX(flyToScale)
                .scaleY(flyToScale)
                .setStartDelay(0)
                .withEndAction(() -> {
                    view.setVisibility(View.GONE);
                    view.setTranslationY(view.getHeight());
                    view.setAlpha(0.0F);
                    flyFlag = false;
                    if (hasShowRequest) {
                        onShow(view);
                        hasShowRequest = false;
                    }
                });
    }

    public FlyAnimator setFlyToScale(float flyToScale) {
        this.flyToScale = flyToScale;
        return this;
    }

    public FlyAnimator setFlyDuration(int flyDuration) {
        this.flyDuration = flyDuration;
        return this;
    }

    public FlyAnimator setFlyInterpolator(Interpolator flyInterpolator) {
        this.flyInterpolator = flyInterpolator;
        return this;
    }

    public FlyAnimator setFlyToAlpha(float flyToAlpha) {
        this.flyToAlpha = flyToAlpha;
        return this;
    }

    public void setFlyToY(float flyToY) {
        this.flyToY = flyToY;
    }
}