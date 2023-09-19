package com.bank.actionmode

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

            viewBinding.recyclerView.apply {
                adapter = MyAdapter(listOf("a", "b", "c", "d", "e")) { item ->

                    val extras = FragmentNavigatorExtras(item to item.transitionName)
                    findNavController().navigate(CardsFragmentDirections.actionCardsFragmentToCardFragment(item.transitionName), extras)
                }
            }

            return viewBinding.root
        }
    }

}