package com.emberestudio.project.ui.planifications.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emberestudio.project.ui.domain.model.Plan
import com.emberestudio.project.ui.planifications.holder.PlanificationViewHolder

class PlanificationsAdapter (var list: MutableList<Plan>) : RecyclerView.Adapter<PlanificationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanificationViewHolder {
        return PlanificationViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: PlanificationViewHolder, position: Int) {
        holder.bind(list[position])
    }
}