package com.emberestudio.project.ui.planner.ui

import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.LayoutAnimationController
import com.emberestudio.project.databinding.FragmentMealPlannerBinding
import com.emberestudio.project.ui.base.BaseFragment
import com.emberestudio.project.ui.planner.adapter.MealPlannerAdapter
import com.emberestudio.project.ui.planner.model.Meal
import com.emberestudio.project.ui.planner.model.MealDays

class MealPlannerFragment : BaseFragment<MealPlannerViewModel>() {

    lateinit var binding: FragmentMealPlannerBinding

    override fun onBind(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentMealPlannerBinding.inflate(inflater).apply {
            lifecycleOwner = this@MealPlannerFragment
            executePendingBindings()
        }
        prepareUI()
        return binding.root
    }

    private fun prepareUI(){
        prepareRecyclerView()
    }

    private fun prepareRecyclerView(){
        binding.elvMealsWeek.apply {
            setAdapter(MealPlannerAdapter(MealDays.values().map { it.name }, viewModel.getMeals() as HashMap<Int, List<Meal>>))
        }
    }

}