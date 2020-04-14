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
import com.emberestudio.project.ui.components.customexpandablelist.DragNDropListeners
import com.emberestudio.project.ui.domain.model.Meal
import com.emberestudio.project.ui.domain.model.MealDays
import com.emberestudio.project.ui.meals.ui.AddMealToPlanDialog
import com.emberestudio.project.ui.planner.adapter.MealPlannerAdapter
import com.emberestudio.project.ui.util.toastShort


class MealPlannerFragment : BaseFragment<MealPlannerViewModel>(),
    ExpandableListView.OnChildClickListener,
    ExpandableListView.OnGroupCollapseListener,
    ExpandableListView.OnGroupExpandListener,
    DragNDropListeners,
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
    }

    private fun prepareRecyclerView(items: MutableMap<Int, MutableList<Meal>>){
        binding.elvMealsWeek.apply {
            setAdapter(MealPlannerAdapter(MealDays.values().map { it.name }, items))
            setOnChildClickListener(this@MealPlannerFragment)
            setOnGroupExpandListener(this@MealPlannerFragment)
            setOnGroupCollapseListener(this@MealPlannerFragment)
        }.also{
            it.setDragOnLongPress(true)
            it.setDragListener(this@MealPlannerFragment)
        }
    }



    private fun observeData(){
        viewModel.plan.observe(viewLifecycleOwner, Observer {
            prepareRecyclerView(it)
        })

        viewModel.change.observe(viewLifecycleOwner, Observer {
            toastShort("Change Successful: ".plus(it))
        })
    }

    override fun onChildClick(
        parent: ExpandableListView?,
        v: View?,
        groupPosition: Int,
        childPosition: Int,
        id: Long
    ): Boolean {
        findNavController().navigate(MealPlannerFragmentDirections.actionPlannerToMealDetail(""))
        return true
    }

    override fun onGroupCollapse(groupPosition: Int) {
        viewModel.removeExpansibleState(groupPosition)
    }

    override fun onGroupExpand(groupPosition: Int) {
        viewModel.setExpansibleState(groupPosition)
    }

    override fun onSaveMeal(item: Meal, day: Int) {
        viewModel.saveMeal(day, item)
        binding.elvMealsWeek.deferNotifyDataSetChanged()
        binding.elvMealsWeek.expandGroup(day)
    }

    override fun onDrag(x: Float, y: Float) {
        // Not needed
    }

    override fun onPick(position: IntArray?) {

        //TODO : Change to affect data source
        toastShort(position!![0].toString().plus(position[1]))
    }

    override fun onDrop(from: IntArray?, to: IntArray?) {
        //TODO : Changee to affect data source
        toastShort(from!![0].toString().plus(from[1]).plus(" ").plus(to!![0].toString().plus(to[1])))
//        viewModel.updateOrder(from, to)
        binding.elvMealsWeek.collapseGroup(from[0])
        binding.elvMealsWeek.expandGroup(to[0])
    }
}