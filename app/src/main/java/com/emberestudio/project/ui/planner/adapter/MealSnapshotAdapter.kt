package com.emberestudio.project.ui.planner.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emberestudio.project.ui.domain.model.MealSnapshot
import com.emberestudio.project.ui.planner.holder.MealSnapshotViewHolder

class MealSnapshotAdapter (var list : MutableList<MealSnapshot>) : RecyclerView.Adapter<MealSnapshotViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealSnapshotViewHolder = MealSnapshotViewHolder.from(parent)

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: MealSnapshotViewHolder, position: Int) {
        holder.bind(list[position])
    }

}