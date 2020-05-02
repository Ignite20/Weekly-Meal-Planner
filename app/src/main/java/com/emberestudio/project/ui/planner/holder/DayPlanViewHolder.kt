package com.emberestudio.project.ui.planner.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emberestudio.project.databinding.ItemDayPlanItemViewBinding
import com.emberestudio.project.ui.domain.model.DayPlan
import com.emberestudio.project.ui.planner.adapter.MealSnapshotAdapter

class DayPlanViewHolder(val binding: ItemDayPlanItemViewBinding) : RecyclerView.ViewHolder(binding.root) {

    interface OnAddNewMealAction{
        fun addMealToPlan()
    }

    var callback : OnAddNewMealAction? = null

    companion object{
        fun from(viewGroup: ViewGroup?): DayPlanViewHolder{
            val layoutInflater = LayoutInflater.from(viewGroup?.context)
            val binding = ItemDayPlanItemViewBinding.inflate(layoutInflater, viewGroup, false)
            return DayPlanViewHolder(binding)
        }
    }

    init {

    }

    fun bind(dayPlan: DayPlan){
        binding.tvDayTitle.text = dayPlan.day
        binding.rvDayMeals.apply {
            adapter = MealSnapshotAdapter(dayPlan.meals)
        }
        binding.cardAddNewMeal.setOnClickListener {
            callback?.addMealToPlan()
        }

    }
}