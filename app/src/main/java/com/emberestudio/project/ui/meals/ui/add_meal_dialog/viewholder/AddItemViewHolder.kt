package com.emberestudio.project.ui.meals.ui.add_meal_dialog.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emberestudio.project.databinding.ItemAddNewBinding

class AddItemViewHolder(val binding : ItemAddNewBinding) : RecyclerView.ViewHolder(binding.root) {


    companion object {
        fun from(viewGroup: ViewGroup?): AddItemViewHolder {
            val layoutInflater = LayoutInflater.from(viewGroup?.context)
            val binding = ItemAddNewBinding.inflate(layoutInflater, viewGroup, false)
            return AddItemViewHolder(binding)
        }
    }


}