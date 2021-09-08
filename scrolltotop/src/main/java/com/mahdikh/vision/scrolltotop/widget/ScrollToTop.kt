package com.mahdikh.vision.scrolltotop.widget

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.RippleDrawable
import android.os.Build
import android.util.AttributeSet
import androidx.annotation.CallSuper
import androidx.annotation.ColorInt
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mahdikh.vision.scrolltotop.R
import com.mahdikh.vision.scrolltotop.animator.BaseAnimator
import com.mahdikh.vision.scrolltotop.animator.FadeAnimator

open class ScrollToTop : AppCompatImageView {
    /**
     * اگر برابر با true باشد به این معناست که اسکرول کوتاه فعال است
     * اگر smoothScroll برابر با false باشد این بی اثر است
     * زمانی که لیست اسکرول شود و بخواهیم به بالا برگردیم در این هنگام اگر
     * مقدار smoothScroll و isShortScroll برابر با true باشد
     * ابتدا به نزدیک ترین position که حالت smoothScroll را از بین نبرد اسکرول می شود و
     * سپس بعد از آن از آن position تا بالای لیست به صورت smooth اسکرول می شود
     */
    var isShortScroll = false

    /**
     * اگر true باشد عمل اسکرول به به صورت Smooth انجام می شود
     */
    var isSmoothScroll = false

    /**
     * اگر برابر با true باشد به این معناست که هر بار با فراخوانی onScrolled تابع checkupScroll فراخوانی خواهد شد
     * بدین صورت با تغییر حتی یک پیکسل در اسرول این تابع فراخوانی خواهد شد
     * <p>
     * مقدار پیشفرض false است و به طور پیشفرض تایع checkupScroll دز تابع onScrollStateChanged فراخوانی میشود
     */
    var isHeavyCheckup = false

    /**
     * حداقل مقدار اسکرول که چهت نمایش ScrollToTop نیاز است
     */
    var minimumScroll = 0

    /**
     * اگر مقدار غیر از RecyclerView.NO_POSITION داشته باشد به این معناست
     * که نما در صورتی نمایان شود که عمل اسکرول این position را رد کرده باشد
     */
    var minimumPosition = RecyclerView.NO_POSITION

    var reverseLayout = false

    /**
     * این متغییر جهت تشخیص وقفه و یا کامل شدن اسکرول با عمل کلیک ایجاد شده است
     * اگر روی نما کلیک شود مقدار true می گیرد و در صورتی که در حین اسکرول توسط عمل Dragging
     * اسکرول متقف شود یا اگر اسکرول کامل شد مقدار false خواهد گرفت
     */
    private var performedClick = false

    /**
     * بااستفاده از متغییر singleRunFlag روندی را ایجاد کرده ام که تابع checkupScroll به طور هوشمند
     * توابع show یا hide را فراخوانی کند
     * وجود این متغییر از فراخوانی تکراری توابع نامبرده جلوگیری میکند
     */
    private var callFlag = true

    private var recyclerView: RecyclerView? = null

    var animator: BaseAnimator? = FadeAnimator(0.8f)
        set(value) {
            field = value
            if (!isVisible) {
                resetProperties()
                prepare()
            }
        }

    private val scrollListener: RecyclerView.OnScrollListener = object :
        RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            if (isHeavyCheckup) {
                checkupScroll()
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            checkupScroll()
            if (performedClick) {
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    // onScrollInterrupted
                    callFlag = true
                    performedClick = false
                } else if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //onScrollCompleted
                    performedClick = false
                }
            }
        }
    }

    init {
        isClickable = true
        isFocusable = true
    }

    constructor(context: Context) : super(context) {
        initialization(context, null, 0)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initialization(context, attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initialization(context, attrs, defStyleAttr)
    }

    private fun initialization(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.ScrollToTop, 0, defStyleAttr)
        val count = a.indexCount
        var index: Int
        for (i in 0 until count) {
            index = a.getIndex(i)
            when (index) {
                R.styleable.ScrollToTop_minimumPosition -> {
                    minimumPosition = a.getInt(index, RecyclerView.NO_POSITION)
                }
                R.styleable.ScrollToTop_heavyCheckup -> {
                    isHeavyCheckup = a.getBoolean(
                        R.styleable.ScrollToTop_heavyCheckup,
                        false
                    )
                }
                R.styleable.ScrollToTop_smoothScroll -> {
                    isSmoothScroll = a.getBoolean(
                        R.styleable.ScrollToTop_smoothScroll,
                        false
                    )
                }
                R.styleable.ScrollToTop_minimumScroll -> {
                    minimumScroll = a.getDimensionPixelOffset(
                        R.styleable.ScrollToTop_minimumScroll,
                        250
                    )
                }
            }
        }

        if (!a.hasValue(R.styleable.ScrollToTop_android_src) && !a.hasValue(R.styleable.ScrollToTop_srcCompat)) {
            setImageResource(R.drawable.ic_scroll_top)
        }
        if (!a.hasValue(R.styleable.ScrollToTop_android_background)) {
            setBackgroundResource(R.drawable.oval_background)
            if (a.hasValue(R.styleable.ScrollToTop_rippleColor)) {
                val rippleColor = a.getColor(R.styleable.ScrollToTop_rippleColor, Color.DKGRAY)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    setRippleColor(rippleColor)
                }
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            elevation = a.getDimension(R.styleable.ScrollToTop_android_elevation, 2f)
        }
        val padding = a.getDimensionPixelOffset(R.styleable.ScrollToTop_android_padding, 6)
        setPadding(padding, padding, padding, padding)
        a.recycle()
    }

    protected open fun getShortPosition(): Int {
        val firstVisiblePosition: Int = getFirstVisiblePosition()
        val lastVisiblePosition: Int = getLastVisiblePosition()
        val itemInScreen = lastVisiblePosition - firstVisiblePosition

        /*
         * تعداد آیتم موجود در صفحه نمایش را با 5 جمع کرده
         * اگر position از firstVisiblePosition بزرگتر بود به این معناست که
         * این position بعد از firstVisiblePosition است پس من آن را برابر با RecyclerView.NO_POSITION قرارمی دهم
         */
        var shortPosition = itemInScreen + (itemInScreen * 0.8F).toInt()
        if (shortPosition > firstVisiblePosition) {
            shortPosition = RecyclerView.NO_POSITION
        }
        return shortPosition
    }

    protected open fun getFirstVisiblePosition(): Int {
        val layoutManager = recyclerView?.layoutManager
        if (layoutManager is LinearLayoutManager) {
            return layoutManager.findFirstVisibleItemPosition()
        } else if (layoutManager is StaggeredGridLayoutManager) {
            val positions = IntArray(layoutManager.spanCount)
            layoutManager.findFirstVisibleItemPositions(positions)
            return positions[0]
        }
        return 0
    }

    protected open fun getLastVisiblePosition(): Int {
        val layoutManager = recyclerView?.layoutManager
        if (layoutManager is LinearLayoutManager) {
            return layoutManager.findLastVisibleItemPosition()
        } else if (layoutManager is StaggeredGridLayoutManager) {
            val positions = IntArray(layoutManager.spanCount)
            layoutManager.findLastVisibleItemPositions(positions)
            if (positions.isNotEmpty()) {
                return positions[positions.size - 1]
            }
        }
        return 0
    }

    protected open fun computeCurrentScroll(): Int {
        if (reverseLayout) {
            recyclerView?.let {
                return it.computeVerticalScrollRange() - (it.computeVerticalScrollExtent()
                        + recyclerView!!.computeVerticalScrollOffset())
            }
        }
        return recyclerView!!.computeVerticalScrollOffset()
    }

    protected open fun checkupScroll() {
        if (minimumPosition != RecyclerView.NO_POSITION) {
            val firstVisible = getFirstVisiblePosition()
            if (firstVisible > minimumPosition && callFlag) {
                show()
                callFlag = false
            } else if (firstVisible <= minimumPosition && !callFlag) {
                hide()
                callFlag = true
            }
        } else {
            val newScroll: Int = computeCurrentScroll()
            if (newScroll >= minimumScroll && callFlag) {
                show()
                callFlag = false
            } else if (newScroll < minimumScroll && !callFlag) {
                hide()
                callFlag = true
            }
        }
    }

    @CallSuper
    protected open fun startScroll() {
        if (performedClick) {
            return
        }
        performedClick = true
        if (isSmoothScroll) {
            if (isShortScroll) {
                val position: Int = getShortPosition()
                if (position != RecyclerView.NO_POSITION) {
                    recyclerView?.scrollToPosition(position)
                    performedClick = true
                }
            }
            recyclerView?.smoothScrollToPosition(0)
        } else {
            /*
             * تابع scrollToPosition موجب فراخوانی تابع های onScrolled و onScrollStateChanged نمی شود
             * بنابراین بایستی ب صورت شخصی این مورد را فراخوانی کرد
             * */
            recyclerView?.let {
                it.scrollToPosition(0)
                scrollListener.onScrollStateChanged(
                    it,
                    RecyclerView.SCROLL_STATE_IDLE
                )
            }
        }
        animator?.onStartScroll(this)
    }

    @CallSuper
    protected open fun prepare() {
        animator?.onPrepare(this)
        recyclerView?.let {
            checkupScroll()
        }
    }

    @CallSuper
    protected open fun show() {
        animator?.onShow(this) ?: kotlin.run {
            alpha = 1.0F
            visibility = VISIBLE
        }
    }

    @CallSuper
    protected open fun hide() {
        animator?.onHide(this) ?: kotlin.run {
            visibility = GONE
        }
    }

    @CallSuper
    open fun setupWithRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = recyclerView
        prepare()
        recyclerView.addOnScrollListener(scrollListener)
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    open fun setRippleColor(@ColorInt rippleColor: Int) {
        val drawable = background
        if (drawable is RippleDrawable) {
            drawable.setColor(
                ColorStateList(
                    arrayOf(intArrayOf()),
                    intArrayOf(rippleColor)
                )
            )
        }
    }

    private fun resetProperties() {
        scaleX = 1.0F
        scaleY = 1.0F
        translationX = 1.0F
        translationY = 1.0F
    }

    override fun performClick(): Boolean {
        startScroll()
        return super.performClick()
    }
}