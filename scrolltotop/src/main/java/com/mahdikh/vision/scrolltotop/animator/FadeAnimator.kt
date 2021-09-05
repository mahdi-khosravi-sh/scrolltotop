package com.mahdikh.vision.scrolltotop.animator

import android.view.View

open class FadeAnimator : BaseAnimator {
    var maxAlpha: Float

    constructor(maxAlpha: Float) {
        this.maxAlpha = maxAlpha
    }

    constructor() {
        maxAlpha = 0.8f
    }

    override fun onPrepare(view: View) {
        view.alpha = 0.0f
        view.visibility = View.GONE
    }

    override fun onShow(view: View) {
        view.visibility = View.VISIBLE
        view.alpha = 0.0f
        view.animate()
            .alpha(maxAlpha)
            .setDuration(duration.toLong()).interpolator = interpolator
    }

    override fun onHide(view: View) {
        view.animate()
            .alpha(0f)
            .setDuration(duration.toLong())
            .setInterpolator(interpolator)
            .withEndAction { view.visibility = View.GONE }
    }

    fun setMaxAlpha(maxAlpha: Float): FadeAnimator {
        this.maxAlpha = maxAlpha
        return this
    }
}