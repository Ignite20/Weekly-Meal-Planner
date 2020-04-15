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

        holder.callbackOnChange = object :IngredientViewHolder.OnChange{
            override fun onChangeName(position: Int, name: String) {
                ingredients[position].name = name
//                notifyItemChanged(position)
            }

            override fun onChangeQuantity(position: Int, quantity: String) {
                ingredients[position].quantity = quantity
//                notifyItemChanged(position)
            }

            override fun onChangeUnit(position: Int, quantityUnit: QuantityUnit) {
                ingredients[position].unit = quantityUnit.name
//                notifyItemChanged(position)
            }
        }

        holder.callback = object : IngredientViewHolder.OnFocusLost{
            override fun onFocusLost(position: Int, ingredient: Ingredient) {
                callback?.saveIngredient(position, ingredient)
            }

            override fun onDeleteItem(position: Int) {
                ingredients.removeAt(position)
                notifyItemRemoved(position)
                callback?.deleteIngredient(position)
            }
        }
    }
}