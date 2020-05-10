package com.emberestudio.project.ui.planner.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emberestudio.project.ui.domain.model.DayPlan
import com.emberestudio.project.ui.planner.holder.DayPlanViewHolder

class PlanAdapter2 (val list: MutableList<DayPlan>, var callback : OnPlanModified? = null) : RecyclerView.Adapter<DayPlanViewHolder>(){

    interface OnPlanModified{
        fun onAddNewMeal(dayPosition : Int)
        fun onEditMeal(dayPosition: Int, mealPosition: Int)
        fun onPlanChanged()
        fun onMealRemoved()
        fun openMeal(mealId: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayPlanViewHolder = DayPlanViewHolder.from(parent)

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: DayPlanViewHolder, position: Int) {
        holder.bind(list[position])
        holder.callback = object : DayPlanViewHolder.OnAddNewMealAction{
            override fun addMealToPlan() {
                callback?.onAddNewMeal(position)
            }

            override fun updatePlan() {
                callback?.onPlanChanged()
            }

            override fun onMealSelected(mealId: String) {
                callback?.openMeal(mealId)
            }

            override fun onEditMeal(dayPosition: Int, mealPosition: Int) {
                callback?.onEditMeal(dayPosition, mealPosition)
            }
        }
    }
}