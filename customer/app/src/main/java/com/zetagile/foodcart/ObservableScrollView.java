package com.zetagile.foodcart;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;


public class ObservableScrollView extends ScrollView {


    private ScrollViewListener scrollViewListener = null;

    public ObservableScrollView(Context context) {
        super(context);
    }

    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ObservableScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void onScrollChanged(int x, int y, int oldx, int oldy) {

        View view = getChildAt(getChildCount() - 1);
        int diff = (view.getBottom() - (getHeight() + getScrollY()));
        if (diff == 0 && scrollViewListener != null) {
            scrollViewListener.onBottomReached();
        }
        super.onScrollChanged(x, y, oldx, oldy);
    }

    public ScrollViewListener getScrollViewListener() {

        return scrollViewListener;
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

}



