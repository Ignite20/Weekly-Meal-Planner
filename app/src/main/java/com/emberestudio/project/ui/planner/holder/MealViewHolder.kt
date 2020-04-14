package com.emberestudio.project.ui.planner.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.emberestudio.project.databinding.ItemMealBinding
import com.emberestudio.project.ui.domain.model.Meal
import kotlinx.android.synthetic.main.item_meal.view.*

class MealViewHolder (binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

    interface OnItemClick{
        fun onItemClick(id: String)
    }

    companion object {
        fun from(viewGroup: ViewGroup?): MealViewHolder {
            val layoutInflater = LayoutInflater.from(viewGroup?.context)
            val binding = ItemMealBinding.inflate(layoutInflater, viewGroup, false)
            return MealViewHolder(binding)
        }
    }

    fun bind(meal : Meal) : MealViewHolder{
        itemView.tv_item_meal_name.text = meal.name
        itemView.tv_item_meal_type.text = meal.description
        return this
    }
}