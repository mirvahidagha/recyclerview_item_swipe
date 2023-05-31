package com.bank.actionmode
import android.animation.ObjectAnimator
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import androidx.recyclerview.widget.RecyclerView

class MyItemDecoration(private val underlayButtonWidth: Int) : RecyclerView.ItemDecoration(){

    private val underlayButtonMargin = 20 // Change as needed
    private var underlayButtonShown = false

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)

        // Draw the underlay buttons for the first item with animation
        if (parent.childCount > 0 && parent.getChildAt(0) != null) {
            val firstItemView = parent.getChildAt(0)
            if (parent.getChildAdapterPosition(firstItemView) == 0) {
                val buttonLeft = firstItemView.right - underlayButtonWidth.toFloat() - underlayButtonMargin
                val buttonRight = (firstItemView.right - underlayButtonMargin).toFloat()
                val buttonTop = firstItemView.top.toFloat()
                val buttonBottom = firstItemView.bottom.toFloat()
                val buttonRect = RectF(buttonLeft, buttonTop, buttonRight, buttonBottom)

                val paint = Paint().apply {
                    color = Color.BLUE
                    style = Paint.Style.FILL
                }

                c.drawRoundRect(buttonRect, 0f, 0f, paint)

                // Apply animation to show and hide the underlay button for the first item
                if (underlayButtonShown) {
                    ObjectAnimator.ofFloat(buttonRect, "left", buttonLeft, buttonRight).apply {
                        duration = 1000
                        start()
                    }
                } else {
                    ObjectAnimator.ofFloat(buttonRect, "left", buttonRight, buttonLeft).apply {
                        duration = 1000
                        start()
                    }
                }
            }
        }
    }

    fun toggleUnderlayButtonShown() {
        underlayButtonShown = !underlayButtonShown
        }
}