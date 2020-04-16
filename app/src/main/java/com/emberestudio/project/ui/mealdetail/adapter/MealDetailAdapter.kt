package com.emberestudio.project.ui.mealdetail.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emberestudio.project.ui.domain.model.Ingredient
import com.emberestudio.project.ui.domain.model.Item
import com.emberestudio.project.ui.domain.model.Step
import com.emberestudio.project.ui.mealdetail.holder.ItemDetailViewHolder

class MealDetailAdapter(val items : List<Item>) : RecyclerView.Adapter<ItemDetailViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemDetailViewHolder {
        return ItemDetailViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ItemDetailViewHolder, position: Int) {
        when(val item = items[position]){
            is Ingredient -> {
                holder.bind(item.name, item.quantity.plus(" ").plus(item.unit))
            }
            is Step -> {
                holder.bind(item.order.plus(1).toString().plus(". "), item.description.plus("\n"))
            }
        }
    }
}