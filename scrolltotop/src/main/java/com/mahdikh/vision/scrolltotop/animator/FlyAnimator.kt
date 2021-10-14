package com.mahdikh.vision.scrolltotop.animator

import android.view.View
import android.view.animation.Interpolator
import androidx.core.view.marginBottom
import androidx.interpolator.view.animation.FastOutSlowInInterpolator

class FlyAnimator : SlideAnimator() {
    private var flyFlag = false
    private var hasShowRequest = false
    var flyDuration: Long = 1200
    var flyToScale = 0.8f
    var flyToAlpha = 0.0f
    var flyToY = 0.0f
    var flyInterpolator: Interpolator = FastOutSlowInInterpolator()

    override fun preAnimateShow(view: View) {
        if (!flyFlag) {
            view.rotation = 0.0F
            super.preAnimateShow(view)
        } else {
            hasShowRequest = true
        }
    }

    override fun animateShowImpl(view: View) {
        if (!flyFlag) {
            super.animateShowImpl(view)
        }
    }

    override fun animateHideImpl(view: View) {
        if (!flyFlag) {
            view.rotation = 180.0F
            super.animateHideImpl(view)
        }
    }

    override fun onStartScroll(view: View) {
        flyFlag = true
        view.animate().apply {
            alpha(flyToAlpha)
                .scaleX(flyToScale)
                .scaleY(flyToScale)
                .y(flyToY)
                .withEndAction {
                    view.apply {
                        visibility = View.GONE
                        alpha = 0.0F
                        translationY = height.toFloat() + view.marginBottom
                    }
                    flyFlag = false
                    if (hasShowRequest) {
                        animateShow(view)
                        hasShowRequest = false
                    }
                }
            startDelay = 0
            duration = flyDuration
            interpolator = flyInterpolator
        }.start()
    }
}