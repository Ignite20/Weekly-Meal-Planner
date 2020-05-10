package com.emberestudio.project.ui.planner.dialog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emberestudio.project.R
import com.emberestudio.project.databinding.DialogAddMealToPlanBinding
import com.emberestudio.project.ui.base.BaseDialogFragment
import com.emberestudio.project.ui.domain.model.Meal
import com.emberestudio.project.ui.meals.adapter.MealsAdapter

class AddMealToPlanDialog (var day: String, var meals: MutableList<Meal>, var callback: Actions?) : BaseDialogFragment(), MealsAdapter.OnItemActions{

    interface Actions {
        fun onMealSelected(selectedMeal : Meal)
    }

    lateinit var binding : DialogAddMealToPlanBinding

    private lateinit var mealsAdapter: MealsAdapter

    override fun onBind(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = DialogAddMealToPlanBinding.inflate(inflater).apply {
            lifecycleOwner = this@AddMealToPlanDialog
            executePendingBindings()
        }

        prepareUI()
        return binding.root
    }

    override fun prepareUI() {
        prepareGeneral()
        prepareTitle()
        prepareCancel()
    }

    private fun prepareTitle(){
        binding.addMealToPlanTitle.text = getString(R.string.add_meal_to_plan_title, day)
    }

    private fun prepareGeneral(){
        mealsAdapter = MealsAdapter(meals, this, canDeleteItems = false)

        binding.rvMealsForPlan.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
            adapter = mealsAdapter
        }
    }

    private fun prepareCancel(){
        binding.addPlanCloseButton.setOnClickListener {
            dismissAllowingStateLoss()
        }
    }

    override fun onItemClick(item: Meal) {
        callback?.onMealSelected(item)
        dismissAllowingStateLoss()
    }

    override fun onItemLongClick(item: Meal) {
        // Not needed
    }

    override fun onItemDelete(position: Int) {
        // Not needed
    }
}