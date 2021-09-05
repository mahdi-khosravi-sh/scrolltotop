package com.mahdikh.vision.scrolltotop.animator;

import android.view.View;

public class ScaleAnimator extends FadeAnimator {
    private float fromScale;

    public ScaleAnimator(float maxAlpha, float fromScale) {
        super(maxAlpha);
        this.fromScale = fromScale;
    }

    public ScaleAnimator(float fromScale) {
        this.fromScale = fromScale;
    }

    public ScaleAnimator() {
        fromScale = 0.85F;
    }

    @Override
    public void onPrepare(View view) {
        super.onPrepare(view);
        view.setScaleX(fromScale);
        view.setScaleY(fromScale);
    }

    @Override
    public void onShow(View view) {
        view.setScaleY(fromScale);
        view.setScaleX(fromScale);

        super.onShow(view);

        view.animate()
                .scaleX(1.0F)
                .scaleY(1.0F);
    }

    @Override
    public void onHide(View view) {
        super.onHide(view);
        view.animate()
                .scaleX(fromScale)
                .scaleY(fromScale);
    }

    public ScaleAnimator setFromScale(float fromScale) {
        this.fromScale = fromScale;
        return this;
    }
}
