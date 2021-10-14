package com.mahdikh.vision.scrolltotop.animator

import android.view.View
import androidx.core.view.marginBottom

open class SlideAnimator : ScaleAnimator() {
    override fun prepare(view: View) {
        super.prepare(view)
        view.apply {
            measure(0, 0)
            view.translationY = view.measuredHeight.toFloat() + view.marginBottom
        }
    }

    override fun animateShowImpl(view: View) {
        super.animateShowImpl(view)
        view.animate().apply {
            translationY(0.0F)
            duration = this@SlideAnimator.duration
            interpolator = this@SlideAnimator.interpolator
        }.start()
    }

    override fun animateHideImpl(view: View) {
        super.animateHideImpl(view)
        view.animate().apply {
            translationY(view.measuredHeight.toFloat() + view.marginBottom)
            duration = this@SlideAnimator.duration
            interpolator = this@SlideAnimator.interpolator
        }.start()
    }
}