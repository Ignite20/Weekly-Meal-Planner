package com.emberestudio.project.ui.planifications.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emberestudio.project.R
import com.emberestudio.project.databinding.FragmentPlanificationsListBinding
import com.emberestudio.project.ui.base.BaseFragment
import com.emberestudio.project.ui.domain.model.Plan
import com.emberestudio.project.ui.planifications.adapter.PlanificationsAdapter
import com.emberestudio.project.ui.planner.dialog.PLAN_OPTIONS_SHEET_TAG
import com.emberestudio.project.ui.planner.dialog.PlanOptionsSheetDialog
import com.emberestudio.project.ui.util.toastShort


class PlanificationsFragment : BaseFragment<PlanificationsViewModel>(), PlanificationsAdapter.OnItemActions, PlanOptionsSheetDialog.PlanOptionsCallback {

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
            if(planId.isNullOrEmpty().not()) onPlanClick(planId)
        })
    }

    override fun onPause() {
        super.onPause()
        viewModel.clearObservers()
        viewModel.plans.removeObservers(this)
        viewModel.planId.removeObservers(this)
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

    override fun onPlanClick(planId: String) {
        findNavController().navigate(PlanificationsFragmentDirections.actionPlanificationsToPlan(planId))
    }

    override fun onPlanLongClick(plan: Plan) {
        viewModel.selectedPlan = plan
        showPlanOptions()
    }

    private fun showPlanOptions(){
        PlanOptionsSheetDialog(this).show(parentFragmentManager, PLAN_OPTIONS_SHEET_TAG)
    }

    override fun onSharePlan() {
        toastShort("share")
    }

    override fun onDeletePlan() {
        showDeleteDialog()
    }

    private fun showDeleteDialog(){
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.delete_plan_title, viewModel.selectedPlan?.title))
            .setMessage(R.string.delete_plan_message)
            .setPositiveButton(android.R.string.ok) { dialog, _ ->
                viewModel.deletePlan()
                dialog.dismiss()
            }
            .setNegativeButton(android.R.string.cancel) { dialog, _ ->
                viewModel.selectedPlan = null
                dialog.dismiss()
            }.setOnDismissListener {
                viewModel.selectedPlan = null
            }
            .create()
            .show()
    }
}