package com.mahdikh.vision.scrolltotop.animator;

import android.view.View;
import android.view.animation.Interpolator;

public abstract class BaseAnimator {
    private int          duration = 125;
    private Interpolator interpolator;

    public BaseAnimator setInterpolator(Interpolator interpolator) {
        this.interpolator = interpolator;
        return this;
    }

    public Interpolator getInterpolator() {
        return interpolator;
    }

    public BaseAnimator setDuration(int duration) {
        this.duration = duration;
        return this;
    }

    public int getDuration() {
        return duration;
    }

    /**
     * آماده سازی و مقدار دهی اولیه نما
     * این تابع فقط یکبار فراخوانی خواهد شد
     */
    public abstract void onPrepare(View view);

    /**
     * زمانی که اسکرول مرز حداقل را رد کند نما باید نمایان شود
     */
    public abstract void onShow(View view);

    /**
     * زمانی که اسکرول به حداقل نزدیک شود و وارد حداقل شود نما باید مخفی شود
     */
    public abstract void onHide(View view);

    /**
     * زمانی که روی نما کلیک شد و عمل اسکرول به بالا انجام شد
     */
    public void onStartScroll(View view) {

    }
}