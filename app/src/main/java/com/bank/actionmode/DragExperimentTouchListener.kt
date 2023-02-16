package com.bank.actionmode

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.util.Log
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.animation.BounceInterpolator
import kotlin.math.abs

class DragExperimentTouchListener(private var lastX: Float, view: View?, main: View?) :
    OnTouchListener {
    private var dragDistance = 0f
    private var isOpen = false
    private var view: View? = null
    private var main: View? = null
    private var isDragging = false
    private var deltaX = 0f

    init {
        this.view = view
        this.main = main
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(view: View, event: MotionEvent): Boolean {
        val action = event.action
        if (action == MotionEvent.ACTION_DOWN && !isDragging) {
            isDragging = true
            deltaX = if (isOpen) event.x - dpToPx(80f) else event.x
            return false
        } else if (isDragging) {
            when (action) {
                MotionEvent.ACTION_MOVE -> {
                    this.dragDistance = deltaX - event.x

                    if(!isOpen)
                    if (dragDistance <= dpToPx(80f) && dragDistance > 0) {
                        this.view!!.translationX = -dragDistance
                        main!!.translationX = -dragDistance / 2 - dpToPx(-40f)

                    }

                    if (isOpen) {
                        dragDistance += dpToPx(80f)
                        Log.d("distance here", "$dragDistance")
                        if (dragDistance >= dpToPx(-80f) && dragDistance < 0) {
                            main!!.resources.displayMetrics.widthPixels - dpToPx(-80f)
                            this.view!!.translationX = dpToPx(-80f) - dragDistance
                            main!!.translationX = (dpToPx(-80f) - dragDistance) / 2 - dpToPx(-40f)
                        }
                    }

                    return false
                }
                MotionEvent.ACTION_UP -> {
                    isDragging = false
                    lastX = event.x
                    dragDistance = deltaX - lastX
                    if (dragDistance > 0 && abs(dragDistance) > dpToPx(40f)) open() else if (!isOpen) close()
                    if (dragDistance <= 0 && abs(dragDistance) > dpToPx(40f)) close() else if (isOpen) open()
                    return false
                }
                MotionEvent.ACTION_CANCEL -> {
                    dragDistance = deltaX - lastX
                    if (dragDistance > 0 && abs(dragDistance) > dpToPx(40f)) open() else if (!isOpen) close()
                    if (dragDistance <= 0 && abs(dragDistance) > dpToPx(40f)) close() else if (isOpen) open()
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
        val x = -dpToPx(80f)
        val animator = ObjectAnimator.ofFloat(view, "translationX", x)
        animator.duration = 700
        animator.addUpdateListener { animation: ValueAnimator ->
            view!!.translationX = (animation.animatedValue as Float)
            main!!.translationX = animation.animatedValue as Float / 2 + dpToPx(40f)
        }
        animator.interpolator = BounceInterpolator()
        animator.start()
        isOpen = true
    }

    @SuppressLint("ObjectAnimatorBinding")
    fun close() {
        val x = 0f
        val animator = ObjectAnimator.ofFloat(view, "translationX", x)
        animator.duration = 700
        animator.addUpdateListener { animation: ValueAnimator ->
            view!!.translationX = (animation.animatedValue as Float)
            main!!.translationX = animation.animatedValue as Float / 2 + dpToPx(40f)
        }
        animator.interpolator = BounceInterpolator()
        animator.start()
        isOpen = false
    }

    private fun dpToPx(d: Float): Float {
        val r = main!!.resources
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, d, r.displayMetrics)
    }

}