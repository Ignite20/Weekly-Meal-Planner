package com.emberestudio.project.ui.planner.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.emberestudio.project.R
import com.emberestudio.project.databinding.ItemDayBinding
import kotlinx.android.synthetic.main.item_day.view.*

class DayViewHolder(binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun from(viewGroup: ViewGroup?): DayViewHolder {
            val layoutInflater = LayoutInflater.from(viewGroup?.context)
            val binding = ItemDayBinding.inflate(layoutInflater, viewGroup, false)
            return DayViewHolder(binding)
        }
    }

    fun bind(day : String) : DayViewHolder{
        itemView.tv_item_day.text = day
        return this
    }

    fun setArrow(expanded : Boolean) : DayViewHolder{
        if (expanded) {
            itemView.iv_expand_icon.setImageResource(R.drawable.ic_expand_less_black_24dp)
        } else {
            itemView.iv_expand_icon.setImageResource(R.drawable.ic_expand_more_black_24dp)
        }
        return this
    }
}