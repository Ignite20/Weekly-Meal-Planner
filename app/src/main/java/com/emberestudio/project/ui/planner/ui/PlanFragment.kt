package com.emberestudio.project.ui.planner.ui

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emberestudio.project.databinding.FragmentMealPlannerBinding
import com.emberestudio.project.ui.base.BaseFragment
import com.emberestudio.project.ui.domain.model.DayPlan
import com.emberestudio.project.ui.domain.model.MealSnapshot
import com.emberestudio.project.ui.domain.model.Plan
import com.emberestudio.project.ui.planner.adapter.PlanAdapter2


class PlanFragment : BaseFragment<PlanViewModel>(), PlanAdapter2.OnPlanModified {

    lateinit var binding: FragmentMealPlannerBinding

    private val args: PlanFragmentArgs by navArgs()

    override fun onBind(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentMealPlannerBinding.inflate(inflater).apply {
            lifecycleOwner = this@PlanFragment
            executePendingBindings()
        }
        prepareUI()
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    private fun prepareUI(){
        retrieveArgs()
        observeData()
    }

    private fun preparePlan(plan: Plan){
        binding.tvPlanTitle.text = Editable.Factory.getInstance().newEditable(plan.title)
        binding.tvPlanTitle.setSelection(plan.title.length)
        binding.tvPlanTitle.doOnTextChanged { text, start, count, after ->
            viewModel.updateTitle(text.toString())
        }
        prepareRecyclerView(plan.planification)
    }

    private fun prepareRecyclerView(items: MutableList<DayPlan>){
        binding.rvDayMeals.apply {
            adapter = PlanAdapter2(items, this@PlanFragment)
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
    }

    private fun retrieveArgs(){
        args.let {
            viewModel.getPlan(it.planId)
        }
    }

    private fun observeData(){
        viewModel.plan.observe(viewLifecycleOwner, Observer {
            preparePlan(it)
        })
    }

    override fun onAddNewMeal(dayPosition: Int) {
        viewModel.updatePlanfication(dayPosition, MealSnapshot("test Summer", "meal ID"))
        binding.rvDayMeals.adapter?.notifyDataSetChanged()
    }

    override fun onMealChanged() {
        viewModel.updatePlanfication()
    }

    override fun onMealRemoved() {
        //TODO: Implementation
    }
}