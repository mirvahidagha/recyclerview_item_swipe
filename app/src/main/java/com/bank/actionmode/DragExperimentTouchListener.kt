package com.bank.actionmode

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.util.Log
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewParent
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.roundToLong

class DragExperimentTouchListener(var parent: ViewParent, private var lastX: Float, view: View?, main: View?) :
    OnTouchListener {
    private var dragDistanceX = 0f
    private var dragDistanceY = 0f
    private var isOpen = false
    private var view: View? = null
    private var main: View? = null
    private var isDragging = false
    private var deltaX = 0f
    private var deltaY = 0f
    private var ratio = 0f

    private var dontPass = true

    init {
        this.view = view
        this.main = main
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(view: View, event: MotionEvent): Boolean {
        val action = event.action
        if (action == MotionEvent.ACTION_DOWN && !isDragging) {
            parent.requestDisallowInterceptTouchEvent(false)
            isDragging = true
            deltaX = if (isOpen) event.x - dpToPx(80f) else event.x
            deltaY = if (isOpen) event.y - dpToPx(80f) else event.y
            return false
        } else if (isDragging) {
            when (action) {
                MotionEvent.ACTION_MOVE -> {
                    this.dragDistanceX = deltaX - event.x
                    this.dragDistanceY = deltaY - event.y

                    //todo
                    if (abs(Math.toDegrees(atan2(dragDistanceY, dragDistanceX).toDouble())) !in 80f..100f) {
                        parent.requestDisallowInterceptTouchEvent(true)
                    }

                    if (!isOpen)
                        if (dragDistanceX <= dpToPx(80f) && dragDistanceX > 0) {
                            this.view!!.translationX = -dragDistanceX
                            main!!.translationX = -dragDistanceX / 2 - dpToPx(-40f)
                            //todo
                            ratio = abs(dragDistanceX / dpToPx(80f))
                        }

                    if (isOpen) {
                        dragDistanceX += dpToPx(80f)
                        if (dragDistanceX >= dpToPx(-80f) && dragDistanceX < 0) {
                            this.view!!.translationX = dpToPx(-80f) - dragDistanceX
                            main!!.translationX = (dpToPx(-80f) - dragDistanceX) / 2 - dpToPx(-40f)
                            //todo
                            ratio = abs((dpToPx(-80f) - dragDistanceX) / dpToPx(80f))
                        }
                    }

                    return false
                }
                MotionEvent.ACTION_UP -> {
                    dontPass = true
                    isDragging = false
                    lastX = event.x
                    dragDistanceX = deltaX - lastX
                    if (dragDistanceX > 0 && abs(dragDistanceX) > dpToPx(60f)) open() else if (!isOpen) close()
                    if (dragDistanceX <= 0 && abs(dragDistanceX) > dpToPx(60f)) close() else if (isOpen) open()
                    return false
                }
                MotionEvent.ACTION_CANCEL -> {
                    dontPass = true
                    dragDistanceX = deltaX - lastX
                    if (dragDistanceX > 0 && abs(dragDistanceX) > dpToPx(60f)) open() else if (!isOpen) close()
                    if (dragDistanceX <= 0 && abs(dragDistanceX) > dpToPx(60f)) close() else if (isOpen) open()
                    lastX = event.x
                    isDragging = false
                    return true
                }
            }
        }
        return false
    }

    @SuppressLint("ObjectAnimatorBinding")
    fun open() {
        Log.d("note ratio", "open: $ratio")
        val x = -dpToPx(80f)
        val animator = ObjectAnimator.ofFloat(view, "translationX", x)
        //todo
        animator.duration = (400*ratio).roundToLong()
        animator.addUpdateListener { animation: ValueAnimator ->
            view!!.translationX = (animation.animatedValue as Float)
            main!!.translationX = animation.animatedValue as Float / 2 + dpToPx(40f)
        }
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.start()
        isOpen = true
    }

    @SuppressLint("ObjectAnimatorBinding")
    fun close() {
        Log.d("note ratio", "close: $ratio")
        val x = 0f
        val animator = ObjectAnimator.ofFloat(view, "translationX", x)
        //todo
        animator.duration = (400*ratio).roundToLong()
        animator.addUpdateListener { animation: ValueAnimator ->
            view!!.translationX = (animation.animatedValue as Float)
            main!!.translationX = animation.animatedValue as Float / 2 + dpToPx(40f)
        }
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.start()
        isOpen = false
    }

    private fun dpToPx(d: Float): Float {
        val r = main!!.resources
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, d, r.displayMetrics)
    }

}