package com.emberestudio.project.ui.planner.holder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emberestudio.project.databinding.ItemDayPlanItemViewBinding
import com.emberestudio.project.ui.domain.model.DayPlan
import com.emberestudio.project.ui.planner.adapter.MealSnapshotAdapter

class DayPlanViewHolder(val binding: ItemDayPlanItemViewBinding) : RecyclerView.ViewHolder(binding.root), MealSnapshotAdapter.Listener {

    interface OnAddNewMealAction{
        fun addMealToPlan()
        fun updatePlan()
    }

    var callback : OnAddNewMealAction? = null

    companion object{
        fun from(viewGroup: ViewGroup?): DayPlanViewHolder{
            val layoutInflater = LayoutInflater.from(viewGroup?.context)
            val binding = ItemDayPlanItemViewBinding.inflate(layoutInflater, viewGroup, false)
            return DayPlanViewHolder(binding)
        }
    }

    fun bind(dayPlan: DayPlan){
        binding.tvDayTitle.text = dayPlan.day
        val mAdapter = MealSnapshotAdapter(dayPlan.meals, this@DayPlanViewHolder)
        binding.textEmptyList.setOnDragListener(mAdapter.getDragInstance())
        binding.rvMealsSnapshots.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            tag = adapterPosition
        }
        binding.cardAddNewMeal.setOnClickListener {
            callback?.addMealToPlan()
        }
        checkListVisibility(dayPlan.meals.size)
    }

    override fun setEmptyList(visibility: Boolean, tag: Int) {
        // Not used for now
    }

    override fun updatePlan() {
        binding.rvMealsSnapshots.adapter?.let {
            checkListVisibility(it.itemCount)
        }
        callback?.updatePlan()
    }

    private fun checkListVisibility(listSize : Int){
        binding.textEmptyList.visibility = if (listSize == 0) View.VISIBLE else View.GONE
        binding.rvMealsSnapshots.visibility = if (listSize == 0) View.GONE else View.VISIBLE
    }
}