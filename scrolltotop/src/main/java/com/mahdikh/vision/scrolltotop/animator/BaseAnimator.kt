package com.mahdikh.vision.scrolltotop.animator

import android.animation.TimeInterpolator
import android.view.View

abstract class BaseAnimator {
    var duration:Long = 125
    var interpolator:TimeInterpolator?=null

    fun animateShow(view: View){
        preAnimateShow(view)
        animateShowImpl(view)
    }

    fun animateHide(view: View){
        animateHideImpl(view)
    }

    abstract fun prepare(view: View)

    protected abstract fun preAnimateShow(view: View)

    protected abstract fun animateShowImpl(view: View)

    protected abstract fun animateHideImpl(view: View)

    open fun onStartScroll(view: View){}
}