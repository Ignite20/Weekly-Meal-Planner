package com.emberestudio.project.ui.planner.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import com.emberestudio.project.ui.domain.model.Meal
import com.emberestudio.project.ui.meals.holder.MealViewHolder
import com.emberestudio.project.ui.planner.holder.DayViewHolder


class MealPlannerAdapter(
    var listDays: List<String>,
    var listMeals: MutableMap<Int, MutableList<Meal>>)
    : BaseExpandableListAdapter() {

    var selectedGroup = 0
    private var selectedChild = 0

    override fun getGroup(groupPosition: Int): Any {
        return listDays[groupPosition]
    }

    private fun getGroupSize(groupPosition: Int) : Int{
        return listMeals[groupPosition]!!.size
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    override fun hasStableIds(): Boolean {
        return false
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
            .bind(meal)
            .itemView
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return listMeals[groupPosition]!![childPosition].hashCode().toLong()
    }

    override fun getGroupCount(): Int {
        return listMeals.size
    }

    fun onPick(position: IntArray) {
        selectedGroup = position[0]
        selectedChild = position[1]
    }

    fun onDrop(from: IntArray, to: IntArray) {
        if (to[0] > listDays.size || to[0] < 0 || to[1] < 0) return
        val tValue = getValue(from) as Meal
        listMeals[listMeals.keys.toTypedArray()[from[0]]]!!.remove(tValue)
        listMeals[listMeals.keys.toTypedArray()[to[0]]]!!.add(to[1], tValue)
        selectedGroup = -1
        selectedChild = -1
        notifyDataSetChanged()
    }

    private fun getValue(id: IntArray): Any {
        return listMeals[listMeals.keys.toTypedArray()[id[0]]]!![id[1]]
    }

}