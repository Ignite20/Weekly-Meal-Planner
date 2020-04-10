package com.emberestudio.project.ui.planner.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import com.emberestudio.project.ui.domain.model.Meal
import com.emberestudio.project.ui.planner.holder.DayViewHolder
import com.emberestudio.project.ui.planner.holder.MealViewHolder


class MealPlannerAdapter(
    var listDays: List<String>,
    var listMeals: MutableMap<Int, MutableList<Meal>>)
    : BaseExpandableListAdapter() {


    override fun getGroup(groupPosition: Int): Any {
        return listDays[groupPosition]
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val listTitle = getGroup(groupPosition) as String
        return DayViewHolder.from(parent)
            .bind(listTitle)
            .setArrow(isExpanded)
            .itemView

    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return listMeals[groupPosition]!!.size
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return listMeals[groupPosition]?.get(childPosition) as Meal
    }

    override fun getGroupId(groupPosition: Int): Long {
        return listDays[groupPosition].hashCode().toLong()
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val meal = getChild(groupPosition, childPosition) as Meal
        return MealViewHolder.from(parent)
            .bind(meal.name, meal.type.toString())
            .itemView
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return listMeals[groupPosition]!![childPosition].hashCode().toLong()
    }

    override fun getGroupCount(): Int {
        return listMeals.size
    }


}