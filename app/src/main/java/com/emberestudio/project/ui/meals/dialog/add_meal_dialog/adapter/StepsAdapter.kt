package com.emberestudio.project.ui.meals.dialog.add_meal_dialog.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emberestudio.project.ui.domain.model.Step
import com.emberestudio.project.ui.meals.dialog.add_meal_dialog.viewholder.OnDeleteItem
import com.emberestudio.project.ui.meals.dialog.add_meal_dialog.viewholder.StepViewHolder

class StepsAdapter (
    var steps : MutableList<Step>,
    var callback: IngredientsAdapter.OnItemAddedListener
)
    :RecyclerView.Adapter<StepViewHolder>(){

    interface OnItemAddedListener{
        fun saveStep(position: Int, step: Step)
        fun deleteStep(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepViewHolder {
        return StepViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return steps.size
    }

    override fun onBindViewHolder(holder: StepViewHolder, position: Int) {
        steps[position].order = position
        holder.bind(steps[position])

        holder.callbackOnChange = object : StepViewHolder.OnChange{
            override fun onChangeContent(position: Int, content: String) {
                steps[position].description = content.trim()
            }
        }

        holder.callbackOnDelete = object :
            OnDeleteItem {
            override fun onDeleteItem(position: Int) {
                steps.removeAt(position)
                notifyItemRemoved(position)
            }
        }
    }
}