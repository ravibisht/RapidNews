package com.stark.rnews;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

public class VerticalViewPager extends ViewPager {

    private static final String TAG = "VerticalViewPager";

    public VerticalViewPager(@NonNull Context context) {
        super(context);
        setPageTransformer(true, new VerticalPageTransformer());
        setOverScrollMode(OVER_SCROLL_NEVER);


    }

    public VerticalViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setPageTransformer(true, new VerticalPageTransformer());
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    private MotionEvent swapXY(MotionEvent ev) {

        float height = getWidth();
        float width = getWidth();

        Log.e(TAG, "swapXY: Height -> " + height + "width : " + width);

        float newX = (ev.getY() / height) * width;
        float newY = (ev.getX() / width) * height;

        Log.e(TAG, "swapXY: new Height -> " + height + "new width : " + width);

        ev.setLocation(newX, newY);

        return ev;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = super.onInterceptTouchEvent(swapXY(ev));
        swapXY(ev); // return touch coordinates to original reference frame for any child views
        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(swapXY(ev));
    }

    private class VerticalPageTransformer implements ViewPager.PageTransformer {

        @Override
        public void transformPage(@NonNull View page, float position) {

            /*Log.e(TAG, "transformPage: Position -> "+ position);
            if (position < -1) { //In this case we are checking for the location of the page -0.5 when page in half of the way                //            page.setAlpha(0);
                // this will set opacity of view to transparent
            } else if (position <= 1) { //this means page set
                page.setAlpha(1);
                page.setTranslationX(page.getWidth() * -position);
                page.setTranslationY(page.getHeight() * position);
                //this will set opacity of view to visible
            } else {

                page.setAlpha(0);
                //the page is slide towards right hand side
            }*/


            Log.i(TAG, "transformPage: " + (page instanceof CardView));

            //Log.i(TAG, "transformPage: id of the page"+page.getId());
            Log.e(TAG, "transformPage: Position -> " + position);
            if (position < -1) { //In this case we are checking for the location of the page -0.5 when page in half of the way                //            page.setAlpha(0);
                // this will set opacity of view to transparent
            } else if (position <= 0) { //this means page set
                page.setAlpha(1);
                page.setTranslationX(page.getWidth() * -position);
                page.setTranslationY(page.getHeight() * position);

                page.setScaleX(1);
                page.setScaleY(1);
                //this will set opacity of view to visible
            } else if (position <= 1) {

                page.setTranslationX(page.getWidth() * -position);

                float scale = 0.75f + (1 - 0.75f) * (1 - Math.abs(position));
                page.setScaleX(scale);
                page.setScaleY(scale);
                //  page.setScaleY(scale);

            } else {

                page.setAlpha(0);
                //the page is slide towards right hand side
            }


        }


    }

}
