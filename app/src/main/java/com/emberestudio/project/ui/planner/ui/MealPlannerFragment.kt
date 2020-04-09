package com.emberestudio.project.ui.planner.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import androidx.lifecycle.Observer
import com.emberestudio.project.databinding.FragmentMealPlannerBinding
import com.emberestudio.project.ui.base.BaseFragment
import com.emberestudio.project.ui.planner.adapter.MealPlannerAdapter
import com.emberestudio.project.ui.planner.model.Meal
import com.emberestudio.project.ui.planner.model.MealDays
import com.emberestudio.project.ui.util.DebugTags
import com.emberestudio.project.ui.util.toastShort

class MealPlannerFragment : BaseFragment<MealPlannerViewModel>(), ExpandableListView.OnChildClickListener {

    lateinit var binding: FragmentMealPlannerBinding

    override fun onBind(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentMealPlannerBinding.inflate(inflater).apply {
            lifecycleOwner = this@MealPlannerFragment
            executePendingBindings()
        }
        prepareUI()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.getMeals()
    }
    private fun prepareUI(){
        observeData()
        prepareEditButton()
    }

    private fun prepareRecyclerView(items: MutableMap<Int, MutableList<Meal>>){
        binding.elvMealsWeek.apply {
            setAdapter(MealPlannerAdapter(MealDays.values().map { it.name }, items))
            setOnChildClickListener(this@MealPlannerFragment)
        }
    }

    private fun prepareEditButton(){
        binding.fabEditMealPlan.setOnClickListener {
            toastShort("Action")
        }
    }

    private fun observeData(){
        viewModel.plan.observe(viewLifecycleOwner, Observer {
            prepareRecyclerView(it)
        })
    }

    override fun onChildClick(
        parent: ExpandableListView?,
        v: View?,
        groupPosition: Int,
        childPosition: Int,
        id: Long
    ): Boolean {
        Log.d(DebugTags.MEAL_PLANS.name, "CHILD GROUP".plus(groupPosition))
        Log.d(DebugTags.MEAL_PLANS.name, "CHILD CHILD".plus(childPosition))
        toastShort(groupPosition.toString().plus(" ").plus(childPosition))
        return true
    }


}