package com.emberestudio.project.ui.meals.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.emberestudio.project.databinding.FragmentMealsListBinding
import com.emberestudio.project.ui.base.BaseFragment

class MealsFragment : BaseFragment<MealsViewModel>() {

    lateinit var binding : FragmentMealsListBinding

    override fun onBind(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentMealsListBinding.inflate(inflater).apply {
            lifecycleOwner = this@MealsFragment
            executePendingBindings()
        }

        return binding.root
    }
}