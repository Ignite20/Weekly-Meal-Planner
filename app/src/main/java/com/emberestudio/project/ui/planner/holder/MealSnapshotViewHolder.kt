package com.emberestudio.project.ui.planner.holder

import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.emberestudio.project.R
import com.emberestudio.project.databinding.ItemMealSnapshotViewBinding
import com.emberestudio.project.ui.domain.model.MealSnapshot

class MealSnapshotViewHolder(val binding: ItemMealSnapshotViewBinding) : RecyclerView.ViewHolder(binding.root) {

    interface Action{
        fun onEditMealClick(mealPosition : Int)
        fun onDeleteMealClick(mealPosition : Int)
    }

    var callback : Action? = null

    companion object{
        fun from(viewGroup: ViewGroup?): MealSnapshotViewHolder{
            val layoutInflater = LayoutInflater.from(viewGroup?.context)
            val binding = ItemMealSnapshotViewBinding.inflate(layoutInflater, viewGroup, false)
            return MealSnapshotViewHolder(binding)
        }
    }

    fun bind(item : MealSnapshot){
        binding.tvMealSnapshotTitle.text = item.title
        binding.ivChangeMeal.setOnClickListener {
            showContextMenu(it, item)
        }
    }

    private fun showContextMenu(view : View, item : MealSnapshot){
        val popup = PopupMenu(view.context, view)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.meal_options_menu, popup.menu)
        popup.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.meal_menu_modify -> {
                    callback?.onEditMealClick(adapterPosition)
                }

                R.id.meal_menu_delete -> {
                    callback?.onDeleteMealClick(adapterPosition)
                }
            }
            true
        }
        popup.show()
    }
}