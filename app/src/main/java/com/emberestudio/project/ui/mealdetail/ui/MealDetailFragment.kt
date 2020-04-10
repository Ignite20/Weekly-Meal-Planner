package com.emberestudio.project.ui.mealdetail.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.emberestudio.project.databinding.FragmentMealDetailBinding
import com.emberestudio.project.ui.base.BaseFragment

class MealDetailFragment : BaseFragment<MealDetailViewModel>() {

    private val args : MealDetailFragmentArgs by navArgs()
    lateinit var binding : FragmentMealDetailBinding

    override fun onBind(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentMealDetailBinding.inflate(inflater).apply {
            lifecycleOwner
            executePendingBindings()
        }

        return binding.root
    }


}