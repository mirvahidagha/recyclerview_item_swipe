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
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.bank.actionmode.databinding.FragmentCardBinding


class CardFragment : Fragment() {
    private lateinit var viewBinding: FragmentCardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        sharedElementEnterTransition = TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
       // sharedElementReturnTransition = TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.explode)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewBinding = FragmentCardBinding.inflate(inflater, container, false)
        viewBinding.recyclerSecond.adapter = MyAdapterSecond(listOf("a", "b")) {

        }
        arguments?.let {
          val position = CardFragmentArgs.fromBundle(it).position
            viewBinding.cardview.transitionName="card$position"
            viewBinding.textPan.transitionName="pan$position"
        }
        return viewBinding.root
    }

}