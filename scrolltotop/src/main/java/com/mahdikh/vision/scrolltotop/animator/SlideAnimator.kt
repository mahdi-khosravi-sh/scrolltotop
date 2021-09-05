package com.mahdikh.vision.scrolltotop.animator

import android.view.View

open class SlideAnimator : ScaleAnimator() {
    override fun onShow(view: View) {
        view.measure(0, 0)
        view.translationY = view.measuredHeight.toFloat()
        super.onShow(view)
        view.animate().translationY(0f)
    }

    override fun onHide(view: View) {
        super.onHide(view)
        view.animate().translationY(view.height.toFloat())
    }
}