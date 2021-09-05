package com.mahdikh.vision.scrolltotop.animator;

import android.view.View;

public class SlideAnimator extends ScaleAnimator {
    @Override
    public void onPrepare(View view) {
        super.onPrepare(view);
    }

    @Override
    public void onShow(View view) {
        view.measure(0, 0);
        view.setTranslationY(view.getMeasuredHeight());
        super.onShow(view);
        view.animate().translationY(0);
    }

    @Override
    public void onHide(View view) {
        super.onHide(view);
        view.animate().translationY(view.getHeight());
    }
}