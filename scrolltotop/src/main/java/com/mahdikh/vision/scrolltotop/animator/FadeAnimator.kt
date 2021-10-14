package com.mahdikh.vision.scrolltotop.animator

import android.view.View

open class FadeAnimator : BaseAnimator {
    var maxAlpha: Float

    constructor() : super() {
        maxAlpha = 0.8F
    }

    constructor(maxAlpha: Float) : super() {
        this.maxAlpha = maxAlpha
    }

    override fun prepare(view: View) {
        view.visibility = View.GONE
        view.alpha = 0.0F
    }

    override fun preAnimateShow(view: View) {
        view.visibility = View.VISIBLE
        view.alpha = 0.0F
    }

    override fun animateShowImpl(view: View) {
        view.animate().apply {
            alpha(maxAlpha)
            duration = this@FadeAnimator.duration
            interpolator = null
        }.start()
    }

    override fun animateHideImpl(view: View) {
        view.animate().apply {
            alpha(0.0F)
            duration = this@FadeAnimator.duration
            interpolator = null
            withEndAction { view.visibility = View.GONE }
        }.start()
    }
}