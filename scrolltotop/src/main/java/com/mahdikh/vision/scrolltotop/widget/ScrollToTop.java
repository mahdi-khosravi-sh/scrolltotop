package com.mahdikh.vision.scrolltotop.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.util.AttributeSet;

import androidx.annotation.CallSuper;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.mahdikh.vision.scrolltotop.R;
import com.mahdikh.vision.scrolltotop.animator.BaseAnimator;
import com.mahdikh.vision.scrolltotop.animator.FadeAnimator;

/**
 * ScrollToTop view for recyclerView
 */
public class ScrollToTop extends AppCompatImageView {
    private BaseAnimator baseAnimator = new FadeAnimator(0.8F);
    protected RecyclerView recyclerView;
    /**
     * اگر برابر با true باشد به این معناست که اسکرول کوتاه فعال است
     * اگر smoothScroll برابر با false باشد این بی اثر است
     * زمانی که لیست اسکرول شود و بخواهیم به بالا برگردیم در این هنگام اگر
     * مقدار smoothScroll و shortScrollEnabled برابر با true باشد
     * ابتدا به نزدیک ترین position که حالت smoothScroll را از بین نبرد اسکرول می شود و
     * سپس بعد از آن از آن position تا بالای لیست به صورت smooth اسکرول می شود
     */
    private boolean shortScrollEnabled;

    /**
     * حداقل مقدار اسکرول که چهت نمایش ScrollToTop نیاز است
     */
    private int minimumScroll;
    /**
     * اگر true باشد عمل اسکرول به به صورت Smooth انجام می شود
     */
    private boolean smoothScroll;
    /**
     * اگر برابر با true باشد به این معناست که هر بار با فراخوانی onScrolled تابع checkupScroll فراخوانی خواهد شد
     * بدین صورت با تغییر حتی یک پیکسل در اسرول این تابع فراخوانی خواهد شد
     * <p>
     * مقدار پیشفرض false است و به طور پیشفرض تایع checkupScroll دز تابع onScrollStateChanged فراخوانی میشود
     */
    private boolean heavyCheckup = false;

    public ScrollToTop(@NonNull Context context) {
        super(context);
        minimumScroll = 200;
        setClickable(true);
        setFocusable(true);
    }

    public ScrollToTop(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialization(context, attrs, R.style.ScrollToTop);
    }

    public ScrollToTop(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialization(context, attrs, defStyleAttr);
    }

    @CallSuper
    protected void initialization(Context context, AttributeSet attrs, int defStyleAttr) {
        setClickable(true);
        setFocusable(true);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ScrollToTop, 0, defStyleAttr);

        heavyCheckup = a.getBoolean(R.styleable.ScrollToTop_heavyCheckup, false);
        smoothScroll = a.getBoolean(R.styleable.ScrollToTop_smoothScroll, false);
        minimumScroll = a.getDimensionPixelOffset(R.styleable.ScrollToTop_minimumScroll, 250);

        if (!a.hasValue(R.styleable.ScrollToTop_android_src) && !a.hasValue(R.styleable.ScrollToTop_srcCompat)) {
            setImageResource(R.drawable.ic_scroll_top);
        }

        if (!a.hasValue(R.styleable.ScrollToTop_android_background)) {
            setBackgroundResource(R.drawable.oval_background);
            if (a.hasValue(R.styleable.ScrollToTop_rippleColor)) {
                final int rippleColor = a.getColor(R.styleable.ScrollToTop_rippleColor, Color.DKGRAY);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    setRippleColor(rippleColor);
                }
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setElevation(a.getDimension(R.styleable.ScrollToTop_android_elevation, 2));
        }

        final int padding = a.getDimensionPixelOffset(R.styleable.ScrollToTop_android_padding, 6);
        setPadding(padding, padding, padding, padding);

        a.recycle();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setRippleColor(@ColorInt int rippleColor) {
        Drawable drawable = getBackground();
        if (drawable instanceof RippleDrawable) {
            ((RippleDrawable) drawable)
                    .setColor(new ColorStateList(
                            new int[][]{{}},
                            new int[]{rippleColor}));
        }
    }

    public void setMinimumScroll(int minimumScroll) {
        this.minimumScroll = minimumScroll;
    }

    public void setSmoothScroll(boolean smoothScroll) {
        this.smoothScroll = smoothScroll;
    }

    public void setHeavyCheckup(boolean heavyCheckup) {
        this.heavyCheckup = heavyCheckup;
    }

    public boolean isSmoothScroll() {
        return smoothScroll;
    }

    public boolean isHeavyCheckup() {
        return heavyCheckup;
    }

    /**
     * Setter for baseAnimator
     *
     * @param animator BaseAnimator
     */
    public void setAnimator(@Nullable BaseAnimator animator) {
        baseAnimator = animator;
        if (recyclerView != null) {
            checkupScroll();
        }
    }

    /**
     * این متغییر جهت تشخیص وقفه و یا کامل شدن اسکرول با عمل کلیک ایجاد شده است
     * اگر روی نما کلیک شود مقدار true می گیرد و در صورتی که در حین اسکرول توسط عمل Dragging
     * اسکرول متقف شود یا اگر اسکرول کامل شد مقدار false خواهد گرفت
     */
    private boolean performedClick = false;

    /**
     * این تابع فقط یک بار فراخوانی میشود
     * جهت اعمال نیازمندی ها روی نما
     */
    protected void prepare() {
        if (baseAnimator != null) {
            baseAnimator.onPrepare(this);
        }
        if (recyclerView != null) {
            checkupScroll();
        }
    }

    /**
     * نمایش نما
     */
    protected void show() {
        if (baseAnimator != null) {
            baseAnimator.onShow(ScrollToTop.this);
        } else {
            setAlpha(1.0F);
            setVisibility(VISIBLE);
        }
    }

    /**
     * مخفی کردن نما
     */
    protected void hide() {
        if (baseAnimator != null) {
            baseAnimator.onHide(ScrollToTop.this);
        } else {
            setVisibility(GONE);
        }
    }

    @Override
    public boolean performClick() {
        startScroll();
        return super.performClick();
    }

    protected int getFirstVisiblePosition() {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager != null) {
            if (layoutManager instanceof LinearLayoutManager) {
                LinearLayoutManager manager = (LinearLayoutManager) layoutManager;
                return manager.findFirstVisibleItemPosition();
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) layoutManager;

                int[] positions = new int[manager.getSpanCount()];
                manager.findFirstVisibleItemPositions(positions);
                return positions[0];
            }
        }
        return 0;
    }

    protected int getLastVisiblePosition() {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager != null) {
            if (layoutManager instanceof LinearLayoutManager) {
                return ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) layoutManager;

                int[] positions = new int[manager.getSpanCount()];
                manager.findLastVisibleItemPositions(positions);

                if (positions.length > 0) {
                    return positions[positions.length - 1];
                }
            }
        }
        return 0;
    }

    protected int getShortPosition() {
        final int firstVisiblePosition = getFirstVisiblePosition();
        final int lastVisiblePosition = getLastVisiblePosition();
        final int itemInScreen = lastVisiblePosition - firstVisiblePosition;

        /*
         * تعداد آیتم موجود در صفحه نمایش را با 5 جمع کرده
         * اگر position از firstVisiblePosition بزرگتر بود به این معناست که
         * این position بعد از firstVisiblePosition است پس من آن را برابر با RecyclerView.NO_POSITION قرارمی دهم
         */

        int shortPosition = itemInScreen + 5;

        if (shortPosition > firstVisiblePosition) {
            shortPosition = RecyclerView.NO_POSITION;
        }

        return shortPosition;
    }

    protected void startScroll() {
        if (performedClick) {
            return;
        }

        performedClick = true;

        if (smoothScroll) {
            if (shortScrollEnabled) {
                int position = getShortPosition();
                if (position != RecyclerView.NO_POSITION) {
                    recyclerView.scrollToPosition(getShortPosition());
                    performedClick = true;
                }
            }
            recyclerView.smoothScrollToPosition(0);
        } else {
            /*
             * تابع scrollToPosition موجب فراخوانی تابع های onScrolled و onScrollStateChanged نمی شود
             * بنابراین بایستی ب صورت شخصی این مورد را فراخوانی کرد
             * */
            recyclerView.scrollToPosition(0);
            post(() -> scrollListener.onScrollStateChanged(recyclerView, RecyclerView.SCROLL_STATE_IDLE));
        }
        if (baseAnimator != null) {
            baseAnimator.onStartScroll(this);
        }
    }

    /**
     * بااستفاده از متغییر singleRunFlag روندی را ایجاد کرده ام که تابع checkupScroll به طور هوشمند
     * توابع  show یا hide را فراخوانی کند
     * وجود این متغییر از فراخوانی تکراری توابع نامبرده جلوگیری میکند
     */
    private boolean onceCall = true;

    protected void setOnceCall(boolean onceCall) {
        this.onceCall = onceCall;
    }

    protected boolean isOnceCall() {
        return onceCall;
    }

    protected int getCurrentScroll() {
        return recyclerView.computeVerticalScrollOffset();
    }

    protected void checkupScroll() {
        int newScroll = getCurrentScroll();
        if (newScroll >= minimumScroll && onceCall) {
            show();
            onceCall = false;
        } else if (newScroll < minimumScroll && !onceCall) {
            hide();
            onceCall = true;
        }
    }

    public boolean isShortScrollEnabled() {
        return shortScrollEnabled;
    }

    public void setShortScrollEnabled(boolean shortScrollEnabled) {
        this.shortScrollEnabled = shortScrollEnabled;
    }

    private final RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            if (isHeavyCheckup()) {
                checkupScroll();
            }
        }

        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            checkupScroll();
            if (performedClick) {
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    // onScrollInterrupted
                    onceCall = true;
                    performedClick = false;
                } else if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //onScrollCompleted
                    performedClick = false;
                }
            }
        }
    };

    public void setupWithRecyclerView(@NonNull RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        prepare();
        recyclerView.addOnScrollListener(scrollListener);
    }
}