package com.emberestudio.project.ui.planner.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.emberestudio.project.databinding.FragmentMealPlannerBinding
import com.emberestudio.project.ui.base.BaseFragment
import com.emberestudio.project.ui.domain.model.Meal
import com.emberestudio.project.ui.domain.model.MealDays
import com.emberestudio.project.ui.mealdetail.ui.AddMealToPlanDialog
import com.emberestudio.project.ui.planner.adapter.MealPlannerAdapter

class MealPlannerFragment : BaseFragment<MealPlannerViewModel>(),
    ExpandableListView.OnChildClickListener,
    ExpandableListView.OnGroupCollapseListener,
    ExpandableListView.OnGroupExpandListener,
    AddMealToPlanDialog.Actions
{

    lateinit var binding: FragmentMealPlannerBinding

    override fun onBind(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentMealPlannerBinding.inflate(inflater).apply {
            lifecycleOwner = this@MealPlannerFragment
            executePendingBindings()
        }
        prepareUI()
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
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
            setOnGroupExpandListener(this@MealPlannerFragment)
            setOnGroupCollapseListener(this@MealPlannerFragment)

        }
    }

    private fun prepareEditButton(){
        binding.fabEditMealPlan.setOnClickListener {
            AddMealToPlanDialog(this@MealPlannerFragment).show(parentFragmentManager,"")
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
        findNavController().navigate(MealPlannerFragmentDirections.actionPlannerToMealDetail(groupPosition, childPosition))
        return true
    }

    override fun onGroupCollapse(groupPosition: Int) {
        viewModel.removeExpansibleState(groupPosition)
    }

    override fun onGroupExpand(groupPosition: Int) {
        viewModel.setExpansibleState(groupPosition)
    }

    override fun onSaveMeal(item: Meal, day: Int) {
        binding.elvMealsWeek.expandGroup(day)
        viewModel.saveMeal(day, item)
    }

}