package com.mahdikh.vision.scrolltotop.animator

import android.view.View
import android.view.animation.Interpolator
import androidx.interpolator.view.animation.FastOutSlowInInterpolator

class FlyAnimator : SlideAnimator() {
    private var flyFlag = false
    private var hasShowRequest = false
    var flyDuration = 1200
    var flyToScale = 0.8f
    var flyToAlpha = 0.0f
    var flyToY = 0.0f
    var flyInterpolator: Interpolator = FastOutSlowInInterpolator()

    override fun onShow(view: View) {
        if (!flyFlag) {
            view.rotation = 0.0f
            super.onShow(view)
        } else {
            hasShowRequest = true
        }
    }

    override fun onHide(view: View) {
        if (!flyFlag) {
            view.rotation = 180.0f
            super.onHide(view)
        }
    }

    override fun onStartScroll(view: View) {
        flyFlag = true
        view.animate()
            .setDuration(flyDuration.toLong())
            .setInterpolator(flyInterpolator)
            .y(flyToY)
            .alpha(flyToAlpha)
            .scaleX(flyToScale)
            .scaleY(flyToScale)
            .setStartDelay(0)
            .withEndAction {
                view.visibility = View.GONE
                view.translationY = view.height.toFloat()
                view.alpha = 0.0f
                flyFlag = false
                if (hasShowRequest) {
                    onShow(view)
                    hasShowRequest = false
                }
            }
    }
}