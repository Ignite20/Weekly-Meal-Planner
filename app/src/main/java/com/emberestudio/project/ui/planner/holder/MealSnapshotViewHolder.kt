package com.emberestudio.project.ui.planner.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emberestudio.project.databinding.ItemMealSnapshotViewBinding
import com.emberestudio.project.ui.domain.model.MealSnapshot

class MealSnapshotViewHolder(val binding: ItemMealSnapshotViewBinding) : RecyclerView.ViewHolder(binding.root) {

    companion object{
        fun from(viewGroup: ViewGroup?): MealSnapshotViewHolder{
            val layoutInflater = LayoutInflater.from(viewGroup?.context)
            val binding = ItemMealSnapshotViewBinding.inflate(layoutInflater, viewGroup, false)
            return MealSnapshotViewHolder(binding)
        }
    }

    fun bind(item : MealSnapshot){
        binding.tvMealSnapshotTitle.text = item.title
    }
}