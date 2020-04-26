package com.emberestudio.project.ui.planifications.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emberestudio.project.databinding.ItemPlanBinding
import com.emberestudio.project.ui.domain.model.Plan

class PlanificationViewHolder (var binding: ItemPlanBinding) : RecyclerView.ViewHolder(binding.root){

    companion object {
        fun from(viewGroup: ViewGroup?): PlanificationViewHolder {
            val layoutInflater = LayoutInflater.from(viewGroup?.context)
            val binding = ItemPlanBinding.inflate(layoutInflater, viewGroup, false)
            return PlanificationViewHolder(
                binding
            )
        }
    }
    fun bind(plan: Plan){
        binding.planName.text = plan.title
    }
}