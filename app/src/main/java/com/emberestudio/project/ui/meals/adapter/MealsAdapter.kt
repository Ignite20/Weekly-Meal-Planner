package com.emberestudio.project.ui.meals.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emberestudio.project.ui.domain.model.Meal
import com.emberestudio.project.ui.meals.holder.MealViewHolder

class MealsAdapter constructor(var list: MutableList<Meal>, var callback : OnItemActions?, var canDeleteItems : Boolean = true): RecyclerView.Adapter<MealViewHolder>() {

    interface OnItemActions{
        fun onItemClick(item: Meal)
        fun onItemLongClick(item : Meal)
        fun onItemDelete(position: Int)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        return MealViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        holder.bind(list[position]).apply {
            showDeleteButton(canDeleteItems)
            itemView.apply {
                this.setOnClickListener {
                    callback?.onItemClick(list[position])
                }
                this.setOnLongClickListener {
                    callback?.onItemLongClick(list[position])
                    true
                }
            }

            setOnRemoveListener(callback)
        }
    }
}