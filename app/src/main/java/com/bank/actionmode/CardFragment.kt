package com.bank.actionmode

import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bank.actionmode.databinding.FragmentCardBinding


class CardFragment : Fragment() {
    private lateinit var viewBinding: FragmentCardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        sharedElementEnterTransition = TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
        sharedElementReturnTransition = TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewBinding = FragmentCardBinding.inflate(inflater, container, false)
        arguments?.let {
            viewBinding.cardview.transitionName = CardFragmentArgs.fromBundle(it).cardKey
        }
        return viewBinding.root
    }
}