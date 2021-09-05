package com.mahdikh.vision.scrolltotop.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.mahdikh.vision.scrolltotop.R;

/**
 * پشتیبانی از لایه برعکس شده یا reverseLayout
 * پشتیبانی از تنظیم minimumPosition
 */
public class ScrollToTop2 extends ScrollToTop {
    private boolean reverseLayout;
    /**
     * اگر مقدار غیر از RecyclerView.NO_POSITION داشته باشد به این معناست
     * که نما در صورتی نمایان شود که عمل اسکرول این position را رد کرده باشد
     */
    private int     minimumPosition;

    public ScrollToTop2(@NonNull Context context) {
        super(context);
        minimumPosition = RecyclerView.NO_POSITION;
    }

    public ScrollToTop2(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollToTop2(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initialization(Context context, AttributeSet attrs, int defStyleAttr) {
        super.initialization(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ScrollToTop2, 0, defStyleAttr);
        minimumPosition = a.getInt(R.styleable.ScrollToTop2_minimumPosition, RecyclerView.NO_POSITION);
        reverseLayout = a.getBoolean(R.styleable.ScrollToTop2_reverseLayout, false);
        a.recycle();
    }

    @Override
    public void setupWithRecyclerView(@NonNull RecyclerView recyclerView) {
        super.setupWithRecyclerView(recyclerView);
        reverseLayout = getReverseLayout();
    }

    public void setReverseLayout(boolean reverseLayout) {
        this.reverseLayout = reverseLayout;
    }

    protected boolean getReverseLayout() {
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager != null) {
            if (manager instanceof LinearLayoutManager) {
                return ((LinearLayoutManager) manager).getReverseLayout();
            } else if (manager instanceof StaggeredGridLayoutManager) {
                return ((StaggeredGridLayoutManager) manager).getReverseLayout();
            }
        }
        return false;
    }

    @Override
    protected int getCurrentScroll() {
        if (reverseLayout) {
            /*
             * محاسبه مقدار اسکرول در لایه برعکس شده reverseLayout
             */
            return recyclerView.computeVerticalScrollRange() -
                    (recyclerView.computeVerticalScrollExtent() + super.getCurrentScroll());
        }
        return super.getCurrentScroll();
    }

    @Override
    protected void checkupScroll() {
        if (minimumPosition != RecyclerView.NO_POSITION) {
            int firstVisible = getFirstVisiblePosition();
            if (firstVisible > minimumPosition && isOnceCall()) {
                show();
                setOnceCall(false);
            } else if (firstVisible <= minimumPosition && !isOnceCall()) {
                hide();
                setOnceCall(true);
            }
        } else {
            super.checkupScroll();
        }
    }

    public void setMinimumPosition(int minimumPosition) {
        this.minimumPosition = minimumPosition;
    }

    public int getMinimumPosition() {
        return minimumPosition;
    }
}