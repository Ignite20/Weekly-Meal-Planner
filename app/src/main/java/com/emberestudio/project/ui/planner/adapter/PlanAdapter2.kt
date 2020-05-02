package com.emberestudio.project.ui.planner.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emberestudio.project.ui.domain.model.DayPlan
import com.emberestudio.project.ui.domain.model.MealSnapshot
import com.emberestudio.project.ui.planner.holder.DayPlanViewHolder

class PlanAdapter2 (val list: MutableList<DayPlan>, var callback : OnPlanModified? = null) : RecyclerView.Adapter<DayPlanViewHolder>(){

    interface OnPlanModified{
        fun onAddNewMeal()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayPlanViewHolder = DayPlanViewHolder.from(parent)

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: DayPlanViewHolder, position: Int) {
        holder.bind(list[position])
        holder.callback = object : DayPlanViewHolder.OnAddNewMealAction{
            override fun addMealToPlan() {
                list[position].meals.add(MealSnapshot("test ".plus(position), "meal id".plus(position)))
                notifyItemChanged(position)
                //TODO: Add logic and link to meals list
                callback?.onAddNewMeal()

            }
        }
    }
}