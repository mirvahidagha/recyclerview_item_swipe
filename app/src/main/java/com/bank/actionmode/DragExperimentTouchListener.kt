package com.bank.actionmode;

import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.BounceInterpolator;

public class DragExperimentTouchListener implements View.OnTouchListener {

    private float dragDistance;
    boolean isOpen = false;

    public DragExperimentTouchListener(float initalX, float initialY, View view, View main) {
        lastX = initalX;
        lastY = initialY;
        this.view = view;
        this.main = main;
    }

    View view = null;
    View main = null;
    boolean isDragging = false;
    float lastX;
    float lastY;
    float deltaX;
    float deltaY;

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN && !isDragging) {
            isDragging = true;
            if (isOpen) deltaX = event.getX()-dpToPx(80);
             else deltaX = event.getX();
            return false;
        } else if (isDragging) {
            if (action == MotionEvent.ACTION_MOVE) {
                dragDistance = deltaX- event.getX();
                Log.d("distance", dragDistance+"");
                if (dragDistance <= dpToPx(80f) && dragDistance > 0) {
                    this.view.setTranslationX(-dragDistance);
                    main.setTranslationX((-dragDistance / 2) - (dpToPx(-40)));
                }

                if (dragDistance >= dpToPx(-80f) && dragDistance < 0) {

                    int width = main.getResources().getDisplayMetrics().widthPixels;

                    this.view.setTranslationX(-(width-dragDistance));
                  //  main.setTranslationX((-(width-dragDistance) / 2) - (dpToPx(-40)));
                }


                return false;
            } else if (action == MotionEvent.ACTION_UP) {
                isDragging = false;
                lastX = event.getX();
                dragDistance = deltaX-lastX;

                if (dragDistance>0 && Math.abs(dragDistance) > dpToPx(60))
                    open();
                else
                   if (!isOpen) close();

                if (dragDistance<=0 && Math.abs(dragDistance) > dpToPx(30))
                    close();
                else
                   if (isOpen) open();

                return false;
            } else if (action == MotionEvent.ACTION_CANCEL) {



                dragDistance = deltaX-lastX;
                if (dragDistance>0 && Math.abs(dragDistance) > dpToPx(60))
                    open();
                else
                if (!isOpen)  close();

                if (dragDistance<=0 && Math.abs(dragDistance) > dpToPx(30))
                    close();
                else
                if (isOpen)  open();

                lastX = event.getX();
                isDragging = false;
                return true;
            }
        }

        return false;
    }

    void open() {
        float x = -dpToPx(80);
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", x);
        animator.setDuration(700);
        animator.addUpdateListener(animation -> {
                    view.setTranslationX(((Float) (animation.getAnimatedValue())));
                    main.setTranslationX(((Float) (animation.getAnimatedValue()) / 2) + dpToPx(40f));
                }
        );
        animator.setInterpolator(new BounceInterpolator());
        animator.start();
        isOpen = true;
    }

    void close() {
        float x = 0f;
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", x);
        animator.setDuration(700);
        animator.addUpdateListener(animation -> {
                    view.setTranslationX(((Float) (animation.getAnimatedValue())));
                    main.setTranslationX(((Float) (animation.getAnimatedValue()) / 2) + dpToPx(40f));
                }
        );
        animator.setInterpolator(new BounceInterpolator());
        animator.start();

        isOpen = false;
    }

    float dpToPx(float d) {
        Resources r = main.getResources();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, d, r.getDisplayMetrics());
    }

    float pxToDp(float p) {

        return p / ((float) main.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

}