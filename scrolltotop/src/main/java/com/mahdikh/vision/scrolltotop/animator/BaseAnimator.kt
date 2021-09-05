package com.mahdikh.vision.scrolltotop.animator

import android.view.View
import android.view.animation.Interpolator

abstract class BaseAnimator {
    var duration = 125
    var interpolator: Interpolator? = null

    abstract fun onPrepare(view: View)

    abstract fun onShow(view: View)

    abstract fun onHide(view: View)

    open fun onStartScroll(view: View) {}
}