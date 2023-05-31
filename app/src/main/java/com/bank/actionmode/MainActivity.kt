package com.bank.actionmode

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.animation.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bank.actionmode.databinding.ActivityMainBinding
import com.bank.actionmode.databinding.RecyclerItemBinding
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = BaseAdapter<String>()
        adapter.listOfItems = mutableListOf(
            "This is the sample text about nothing.",
            "This is the sample text about nothing.",
            "This is the sample text about nothing.",
            "This is the sample text about nothing.",
            "This is the sample text about nothing.",
            "This is the sample text about nothing.",
            "This is the sample text about nothing.",
            "This is the sample text about nothing.",
            "This is the sample text about nothing.",
            "This is the sample text about nothing.",
            "This is the sample text about nothing.",
            "This is the sample text about nothing.",
            "This is the sample text about nothing.",
            "This is the sample text about nothing.",
            "This is the sample text about nothing.",
            "This is the sample text about nothing.",
            "This is the sample text about nothing.",
            "This is the sample text about nothing.",
            "This is the sample text about nothing.",
            "This is the sample text about nothing.",
            "This is the sample text about nothing.",
            "This is the sample text about nothing.",
            "This is the sample text about nothing.",
            "This is the sample text about nothing.",
            "This is the sample text about nothing.",
            "This is the sample text about nothing.",
            "This is the sample text about nothing.",
            "This is the sample text about nothing.",
            "This is the sample text about nothing.",
            "This is the sample text about nothing.",
            "This is the sample text about nothing.",
            "This is the sample text about nothing.",
            "This is the sample text about nothing.",
            "This is the sample text about nothing.",
            "This is the sample text about nothing.",
            "This is the sample text about nothing.",
            "This is the sample text about nothing.",
            "This is the sample text about nothing.",
            "This is the sample text about nothing.",
            "This is the sample text about nothing.",
            "This is the sample text about nothing.",
            "This is the sample text about nothing.",
            "This is the sample text about nothing.",
            "This is the sample text about nothing.",
            "This is the sample text about nothing.",
            "This is the sample text about nothing.",
            "This is the sample text about nothing."
        )

        adapter.expressionViewHolderBinding = { eachItem, viewBinding ->
            val view = viewBinding as RecyclerItemBinding
            view.textview.text = eachItem
            view.root.setOnClickListener {}
            view.main.setOnClickListener {
                val snackbar = Snackbar.make(view.main, "Main card", Snackbar.LENGTH_SHORT)
                snackbar.show()
            }
            view.root.setOnTouchListener(
                DragExperimentTouchListener(binding.recycler, view.textview.pivotX, view.textview, view.main)
            )


        }
        adapter.expressionOnCreateViewHolder = { viewGroup ->
            RecyclerItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        }
        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.adapter = adapter

//
//        Handler().postDelayed({
//            animateButton()
//        }, 1000)
    }


    private fun animateButton() {
        val viewHolder = binding.recycler.findViewHolderForAdapterPosition(0)
        val main = viewHolder?.itemView?.findViewById<TextView>(R.id.main)
        val textView = viewHolder?.itemView?.findViewById<TextView>(R.id.textview)

        val x = (-80).dp.toFloat()
        val openAnimator = ObjectAnimator.ofFloat(textView, "translationX", x)
        openAnimator.duration = 800
        openAnimator.addUpdateListener { animation: ValueAnimator ->
            textView!!.translationX = (animation.animatedValue as Float)
            main!!.translationX = animation.animatedValue as Float / 2 + 40.dp
        }
        openAnimator.interpolator = BounceInterpolator()


        val closeAnimator = ObjectAnimator.ofFloat(textView, "translationX", 0f)
        closeAnimator.duration = 800
        closeAnimator.addUpdateListener { animation: ValueAnimator ->
            textView!!.translationX = (animation.animatedValue as Float)
            main!!.translationX = animation.animatedValue as Float / 2 + 40.dp
        }
        closeAnimator.interpolator = BounceInterpolator()

        openAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                closeAnimator.start()
            }
        })


        openAnimator.start()
    }


}


//
//
//import android.graphics.Canvas
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.os.Handler
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.view.animation.AccelerateInterpolator
//import android.view.animation.AlphaAnimation
//import android.view.animation.Animation
//import android.view.animation.AnimationSet
//import android.view.animation.TranslateAnimation
//import android.widget.Button
//import android.widget.TextView
//import androidx.recyclerview.widget.ItemTouchHelper
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//
//class MainActivity : AppCompatActivity() {
//
//    private lateinit var recyclerView: RecyclerView
//    private lateinit var itemTouchHelper: ItemTouchHelper
//    private lateinit var adapter: MyAdapter
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        recyclerView = findViewById(R.id.recycler)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//
//        adapter = MyAdapter(listOf("Item 1", "Item 2", "Item 3"))
//        recyclerView.adapter = adapter
//
//        itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
//            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
//                return false
//            }
//
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//              //  adapter.removeItem(viewHolder.adapterPosition)
//            }
//
//            override fun onChildDraw(
//                c: Canvas,
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder,
//                dX: Float,
//                dY: Float,
//                actionState: Int,
//                isCurrentlyActive: Boolean
//            ) {
//                val itemView = viewHolder.itemView
//                val deleteButton = viewHolder.itemView.findViewById<Button>(R.id.deleteButton)
//                deleteButton.visibility = if (dX < 0) View.VISIBLE else View.INVISIBLE
//                itemView.translationX = dX
//
//                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
//            }
//        })
//
//        itemTouchHelper.attachToRecyclerView(recyclerView)
//
//        // Animate the button after a delay of 2 seconds
//        Handler().postDelayed({
//            animateButton()
//        }, 2000)
//    }
//
//    private fun animateButton() {
//        val viewHolder = recyclerView.findViewHolderForAdapterPosition(0)
//        val deleteButton = viewHolder?.itemView?.findViewById<Button>(R.id.deleteButton)
//        deleteButton?.let {
//            val translationAnimation = TranslateAnimation(0f, -it.width.toFloat(), 0f, 0f)
//            translationAnimation.duration = 500
//            translationAnimation.interpolator = AccelerateInterpolator()
//
//            val alphaAnimation = AlphaAnimation(1f, 0f)
//            alphaAnimation.duration = 500
//
//            val animationSet = AnimationSet(true)
//            animationSet.addAnimation(translationAnimation)
//            animationSet.addAnimation(alphaAnimation)
//            animationSet.setAnimationListener(object : Animation.AnimationListener {
//                override fun onAnimationStart(animation: Animation?) {}
//
//                override fun onAnimationEnd(animation: Animation?) {
//                    // Hide the button after the animation is complete
//                    deleteButton.visibility = View.INVISIBLE
//                }
//
//                override fun onAnimationRepeat(animation: Animation?) {}
//            })
//
//            it.startAnimation(animationSet)
//        }
//    }
//}