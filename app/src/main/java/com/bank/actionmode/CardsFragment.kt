package com.bank.actionmode

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.bank.actionmode.databinding.ActivityMainBinding
import com.bank.actionmode.databinding.FragmentCardsBinding


class CardsFragment : Fragment() {
    private lateinit var viewBinding: FragmentCardsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        if (::viewBinding.isInitialized) return viewBinding.root
        else {
            viewBinding = FragmentCardsBinding.inflate(inflater, container, false)
            viewBinding.recyclerSecond.adapter = MyAdapterThree(listOf("a", "b")) {

            }
            viewBinding.recyclerView.apply {
                adapter = MyAdapter(listOf("a", "b", "c", "d", "e")) { position, holder ->

                    val extras = FragmentNavigatorExtras(
                        holder.cardView to holder.cardView.transitionName,
                        holder.textPan to holder.textPan.transitionName,
                    )
                      findNavController().navigate(CardsFragmentDirections.actionCardsFragmentToCardFragment(position), extras)
                }
            }

            return viewBinding.root
        }
    }

    override fun onCreateAnimator(transit: Int, enter: Boolean, nextAnim: Int): Animator? {
        if (enter) {
            val fadeOut = ObjectAnimator.ofFloat(viewBinding.recyclerSecond, "alpha", 1f, 0f)
            val moveDown = ObjectAnimator.ofFloat(viewBinding.recyclerSecond, "translationY", 0f, 50f)
            return AnimatorSet().apply {
                playTogether(fadeOut, moveDown)
                duration = 5000
            }
        }
        return super.onCreateAnimator(transit, enter, nextAnim)
    }


}