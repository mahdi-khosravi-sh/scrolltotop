package com.mahdikh.vision.scrolltotop.widget

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.util.AttributeSet
import com.mahdikh.vision.scrolltotop.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * پشتیبانی از لایه برعکس شده یا reverseLayout
 * پشتیبانی از تنظیم minimumPosition
 */
class ScrollToTop2 : ScrollToTop {
    var reverseLayout = false

    /**
     * اگر مقدار غیر از RecyclerView.NO_POSITION داشته باشد به این معناست
     * که نما در صورتی نمایان شود که عمل اسکرول این position را رد کرده باشد
     */
    var minimumPosition = RecyclerView.NO_POSITION

    constructor(context: Context) : super(context) {
        initialization(context, null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initialization(context, attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initialization(context, attrs, defStyleAttr)
    }

    private fun initialization(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.ScrollToTop2, 0, defStyleAttr)
        val count = a.indexCount
        var index: Int
        for (i in 0 until count) {
            index = a.getIndex(i)
            when (index) {
                R.styleable.ScrollToTop2_minimumPosition -> {
                    minimumPosition = a.getInt(index, RecyclerView.NO_POSITION)
                }
                R.styleable.ScrollToTop2_reverseLayout -> {
                    reverseLayout = a.getBoolean(index, false)
                }
            }
        }
        a.recycle()
    }

    override fun setupWithRecyclerView(recyclerView: RecyclerView) {
        super.setupWithRecyclerView(recyclerView)
        reverseLayout = isRecyclerViewReverseLayout()
    }

    private fun isRecyclerViewReverseLayout(): Boolean {
        val manager = recyclerView?.layoutManager
        if (manager != null) {
            if (manager is LinearLayoutManager) {
                return manager.reverseLayout
            } else if (manager is StaggeredGridLayoutManager) {
                return manager.reverseLayout
            }
        }
        return false
    }

    override fun computeCurrentScroll(): Int {
        if (reverseLayout) {
            /*
             * محاسبه مقدار اسکرول در لایه برعکس شده reverseLayout
             */
            recyclerView?.let {
                return it.computeVerticalScrollRange() - (it.computeVerticalScrollExtent() + super.computeCurrentScroll())
            }
        }
        return super.computeCurrentScroll()
    }

    override fun checkupScroll() {
        if (minimumPosition != RecyclerView.NO_POSITION) {
            val firstVisible = getFirstVisiblePosition()
            if (firstVisible > minimumPosition && getCallFlag()) {
                show()
                setCallFlag(false)
            } else if (firstVisible <= minimumPosition && !getCallFlag()) {
                hide()
                setCallFlag(true)
            }
        } else {
            super.checkupScroll()
        }
    }
}