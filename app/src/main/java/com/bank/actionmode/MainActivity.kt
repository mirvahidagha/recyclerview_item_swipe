package com.bank.actionmode

import android.animation.ObjectAnimator
import android.content.res.Resources
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.animation.BounceInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bank.actionmode.databinding.ActivityMainBinding
import com.bank.actionmode.databinding.RecyclerItemBinding
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {
    var isExpanded = false
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = BaseAdapter<String>()

        adapter.listOfItems = mutableListOf("This is the sample text about nothing.", "This is the sample text about nothing.", "This is the sample text about nothing.")

        adapter.expressionViewHolderBinding = { eachItem, viewBinding ->
            val view = viewBinding as RecyclerItemBinding
            view.textview.text = eachItem
            view.root.setOnClickListener {
            }
            view.main.setOnClickListener {
                val snackbar = Snackbar
                    .make(view.main, "Main card", Snackbar.LENGTH_SHORT)
                snackbar.show()
            }
            1.dp
            view.root.setOnTouchListener(DragExperimentTouchListener(view.textview.pivotX, view.textview.pivotY, view.textview, view.main))

//            view.root.setOnTouchListener(object : OnSwipeTouchListener(this) {
//
//                override fun onSwipeLeft() {
//
//                    var x = ((-80).dp).toFloat()
//                    if (isExpanded) x = (0.dp).toFloat()
//                    ObjectAnimator.ofFloat(view.textview, "translationX", x).apply {
//                        duration = 700
//                        addUpdateListener {
//                            view.main.translationX = ((it.animatedValue as Float) / 2) + 40.dp
//                        }
//                        interpolator = BounceInterpolator()
//                        start()
//                    }
//
//                    isExpanded = !isExpanded
//
//                }
//
//                override fun onSwipeRight() {
//                    var x = ((-80).dp).toFloat()
//                    if (isExpanded) x = (0.dp).toFloat()
//                    ObjectAnimator.ofFloat(view.textview, "translationX", x).apply {
//                        duration = 700
//                        addUpdateListener {
//                            view.main.translationX = ((it.animatedValue as Float) / 2) + 40.dp
//                        }
//                        interpolator = BounceInterpolator()
//                        start()
//                    }
//
//                    isExpanded = !isExpanded
//
//                }
//
//            })
        }
        adapter.expressionOnCreateViewHolder = { viewGroup ->
            RecyclerItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        }

//        binding.recycler.addOnItemTouchListener(object : SimpleOnItemTouchListener() {
//            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
//                // true: consume touch event
//                // false: dispatch touch event
//                return false
//            }
//        })

        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.adapter = adapter
    }


}


val Number.dp: Int
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics).toInt()