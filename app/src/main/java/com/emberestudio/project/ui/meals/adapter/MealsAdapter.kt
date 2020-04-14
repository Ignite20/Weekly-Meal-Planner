package com.emberestudio.project.ui.meals.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emberestudio.project.ui.domain.model.Meal
import com.emberestudio.project.ui.planner.holder.MealViewHolder

class MealsAdapter constructor(var list: MutableList<Meal>, var callback : OnItemClick?): RecyclerView.Adapter<MealViewHolder>() {

    interface OnItemClick{
        fun onItemClick(item: Meal)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        return MealViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        holder.bind(list[position]).itemView.setOnClickListener {
            callback?.onItemClick(list[position])
        }
    }
}