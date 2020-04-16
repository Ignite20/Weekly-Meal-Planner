package com.emberestudio.project.ui.meals.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.emberestudio.project.databinding.ItemMealBinding
import com.emberestudio.project.ui.domain.model.Meal
import com.emberestudio.project.ui.meals.adapter.MealsAdapter
import kotlinx.android.synthetic.main.item_meal.view.*

class MealViewHolder (binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun from(viewGroup: ViewGroup?): MealViewHolder {
            val layoutInflater = LayoutInflater.from(viewGroup?.context)
            val binding = ItemMealBinding.inflate(layoutInflater, viewGroup, false)
            return MealViewHolder(
                binding
            )
        }
    }

    fun bind(meal : Meal) : MealViewHolder {
        itemView.tv_item_meal_name.text = meal.name
        itemView.tv_item_meal_type.text = meal.description
        return this
    }

    fun setOnRemoveListener(listener: MealsAdapter.OnItemActions?){
        itemView.delete_item.setOnClickListener {
            listener?.onItemDelete(adapterPosition)
        }
    }
}