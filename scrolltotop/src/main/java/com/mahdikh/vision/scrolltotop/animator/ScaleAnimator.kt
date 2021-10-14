package com.mahdikh.vision.scrolltotop.animator

import android.view.View

open class ScaleAnimator : FadeAnimator{
    var fromScale: Float

    constructor(maxAlpha: Float, fromScale: Float) : super(maxAlpha) {
        this.fromScale = fromScale
    }

    constructor(fromScale: Float) {
        this.fromScale = fromScale
    }

    constructor() {
        fromScale = 0.85f
    }

    override fun prepare(view: View) {
        super.prepare(view)
        view.apply {
            scaleX = fromScale
            scaleY = fromScale
        }
    }

    override fun preAnimateShow(view: View) {
        super.preAnimateShow(view)
        view.apply {
            scaleX = fromScale
            scaleY = fromScale
        }
    }

    override fun animateShowImpl(view: View) {
        super.animateShowImpl(view)
        view.animate().apply {
            scaleY(1.0F)
            scaleX(1.0F)
            duration = this@ScaleAnimator.duration
            interpolator  = this@ScaleAnimator.interpolator
        }.start()
    }

    override fun animateHideImpl(view: View) {
        super.animateHideImpl(view)
        view.animate().apply {
            scaleX(fromScale)
            scaleY(fromScale)
            duration = this@ScaleAnimator.duration
            interpolator = this@ScaleAnimator.interpolator
        }.start()
    }
}