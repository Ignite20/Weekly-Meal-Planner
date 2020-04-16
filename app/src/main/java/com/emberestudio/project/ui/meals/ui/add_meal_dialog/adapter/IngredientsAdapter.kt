package com.emberestudio.project.ui.meals.ui.add_meal_dialog.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emberestudio.project.ui.domain.model.Ingredient
import com.emberestudio.project.ui.domain.model.QuantityUnit
import com.emberestudio.project.ui.meals.ui.add_meal_dialog.viewholder.IngredientViewHolder

class IngredientsAdapter (
    var ingredients : MutableList<Ingredient>,
    var callback: OnItemAddedListener?): RecyclerView.Adapter<IngredientViewHolder>() {


    interface OnItemAddedListener{
        fun saveIngredient(position: Int, ingredient: Ingredient)
        fun deleteIngredient(position: Int)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        return IngredientViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return ingredients.size
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        holder.bind(ingredients[position])
        holder.focus(position == ingredients.size - 1)
        
        holder.callbackOnChange = object :IngredientViewHolder.OnChange{
            override fun onChangeName(position: Int, name: String) {
                ingredients[position].name = name
            }

            override fun onChangeQuantity(position: Int, quantity: String) {
                ingredients[position].quantity = quantity
            }

            override fun onChangeUnit(position: Int, quantityUnit: QuantityUnit?) {
                quantityUnit?.let {
                    ingredients[position].unit = it.shortName
                }
            }
        }

        holder.callbackOnDelete = object : IngredientViewHolder.OnDeleteItem{
            override fun onDeleteItem(position: Int) {
                ingredients.removeAt(position)
                notifyItemRemoved(position)
                callback?.deleteIngredient(position)
            }
        }
    }
}