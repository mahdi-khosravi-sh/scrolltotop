package com.mahdikh.vision.scrolltotop.animator

import android.view.View

open class ScaleAnimator : FadeAnimator {
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

    override fun onPrepare(view: View) {
        super.onPrepare(view)
        view.scaleX = fromScale
        view.scaleY = fromScale
    }

    override fun onShow(view: View) {
        view.scaleY = fromScale
        view.scaleX = fromScale
        super.onShow(view)
        view.animate()
            .scaleX(1.0f)
            .scaleY(1.0f)
    }

    override fun onHide(view: View) {
        super.onHide(view)
        view.animate()
            .scaleX(fromScale)
            .scaleY(fromScale)
    }

    fun setFromScale(fromScale: Float): ScaleAnimator {
        this.fromScale = fromScale
        return this
    }
}