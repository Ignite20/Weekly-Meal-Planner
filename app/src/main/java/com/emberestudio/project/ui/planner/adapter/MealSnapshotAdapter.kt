package com.emberestudio.project.ui.planner.adapter

import android.content.ClipData
import android.os.Build
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emberestudio.project.ui.domain.model.MealSnapshot
import com.emberestudio.project.ui.planner.holder.MealSnapshotViewHolder


class MealSnapshotAdapter (var list : MutableList<MealSnapshot>, var listener : Listener?) :
    RecyclerView.Adapter<MealSnapshotViewHolder>(), MealSnapshotViewHolder.Action{

    interface Listener {
        fun onMealSelected(mealId: String)
        fun setEmptyList(visibility: Boolean, tag: Int)
        fun editMeal(mealPosition: Int)
        fun updatePlan()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealSnapshotViewHolder = MealSnapshotViewHolder.from(parent)

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: MealSnapshotViewHolder, position: Int) {
        holder.bind(list[position])
        holder.itemView.tag = position
        holder.itemView.setOnDragListener(DragListener(listener))
        holder.itemView.setOnLongClickListener {
            val data = ClipData.newPlainText(list[position].title, list[position].title)
            val shadowBuilder = View.DragShadowBuilder(it)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                it.startDragAndDrop(data, shadowBuilder, it, 0)
            }else{
                it.startDrag(data, shadowBuilder, it, 0)
            }
            it.visibility = View.INVISIBLE
            true
        }

        holder.callback = this@MealSnapshotAdapter
    }

    fun getMealSnapshotList(): MutableList<MealSnapshot>{
        return list
    }

    fun updateList(updatedList : MutableList<MealSnapshot>){
        list = updatedList
        notifyDataSetChanged()
    }



    fun getDragInstance(): DragListener? {
        return if (listener != null) {
            DragListener(listener)
        } else {
            Log.e("Route Adapter: ", "Initialize listener first!")
            null
        }
    }

    override fun onMealSelected(mealId: String) {
        listener?.onMealSelected(mealId)
    }

    override fun onEditMealClick(mealPosition: Int) {
        listener?.editMeal(mealPosition)
    }

    override fun onDeleteMealClick(mealPosition: Int) {
        list.removeAt(mealPosition)
        notifyItemRemoved(mealPosition)
        listener?.updatePlan()
    }


}