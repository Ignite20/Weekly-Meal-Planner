package com.emberestudio.project.ui.planifications.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emberestudio.project.databinding.FragmentPlanificationsListBinding
import com.emberestudio.project.ui.base.BaseFragment
import com.emberestudio.project.ui.domain.model.Plan
import com.emberestudio.project.ui.planifications.adapter.PlanificationsAdapter

class PlanificationsFragment : BaseFragment<PlanificationsViewModel>(), PlanificationsAdapter.OnItemActions {

    lateinit var binding : FragmentPlanificationsListBinding

    override fun onBind(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentPlanificationsListBinding.inflate(inflater).apply {
            lifecycleOwner = this@PlanificationsFragment
            executePendingBindings()
        }
        prepareUi()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getPlans()
    }

    private fun prepareUi(){
        observeData()
        prepareAddButton()
        preparePlanificationsList()

    }

    private fun observeData() {
        viewModel.plans.observe(this, Observer {
            binding.rvPlanifications.adapter = PlanificationsAdapter(it, this)
            binding.rvPlanifications.adapter?.notifyDataSetChanged()
        })

        viewModel.planId.observe(this, Observer { planId ->
            findNavController().navigate(PlanificationsFragmentDirections.actionPlanificationsToPlan(planId))
        })
    }

    private fun prepareAddButton(){
        binding.btnCreatePlan.setOnClickListener {
            viewModel.addNewPlan()
        }
    }

    private fun preparePlanificationsList(){
        binding.rvPlanifications.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
    }

    override fun onPlanClick(plan: Plan) {
        findNavController().navigate(PlanificationsFragmentDirections.actionPlanificationsToPlan(plan.id))
    }
}