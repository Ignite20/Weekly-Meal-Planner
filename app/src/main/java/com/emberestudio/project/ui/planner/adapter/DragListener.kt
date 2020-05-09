package com.emberestudio.project.ui.planner.adapter

import android.view.DragEvent
import android.view.View
import android.view.View.OnDragListener
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.emberestudio.project.R
import com.emberestudio.project.ui.domain.model.MealSnapshot

class DragListener(var mListener: MealSnapshotAdapter.Listener?) : OnDragListener {

    private var isDropped = false
    override fun onDrag(v: View, event: DragEvent): Boolean {
        when (event.action) {
            DragEvent.ACTION_DRAG_LOCATION -> {

            }
            DragEvent.ACTION_DRAG_STARTED -> {
            }
            DragEvent.ACTION_DRAG_ENTERED -> {
            }
            DragEvent.ACTION_DRAG_EXITED -> {
            }
            DragEvent.ACTION_DROP -> {
                isDropped = true
                val positionSource: Int
                val positionTarget: Int
                val viewSource = event.localState as View
                if (v.id == R.id.meal_cl || v.id == R.id.textEmptyList || v.id == R.id.ll_meals_container) {
                    val target: RecyclerView
                    if (v.id == R.id.textEmptyList || v.id == R.id.card_add_new_meal) {
                        val parentTarget = v.parent as LinearLayout
                        target = parentTarget.children.find { it is RecyclerView } as RecyclerView
                        positionTarget = target.tag as Int
                    } else {
                        target = v.parent as RecyclerView
                        positionTarget = v.tag as Int
                    }
                    val source = viewSource.parent as RecyclerView

                    val adapterSource: MealSnapshotAdapter = source.adapter as MealSnapshotAdapter
                    positionSource = viewSource.tag as Int

                    val mealSnapshot : MealSnapshot = adapterSource.getMealSnapshotList()[positionSource]

                    val sourceList: MutableList<MealSnapshot> = adapterSource.getMealSnapshotList()

                    sourceList.removeAt(positionSource)

                    adapterSource.updateList(sourceList)
                    adapterSource.notifyDataSetChanged()

                    val adapterTarget: MealSnapshotAdapter = target.adapter as MealSnapshotAdapter
                    val targetList: MutableList<MealSnapshot> = adapterTarget.getMealSnapshotList()

                    if (positionTarget >= 0 && adapterTarget.list.size > 0) {
                        targetList.add(positionTarget, mealSnapshot)
                    } else {
                        targetList.add(mealSnapshot)
                    }

                    adapterTarget.updateList(targetList)
                    adapterTarget.notifyDataSetChanged()

                    v.visibility = View.VISIBLE
                    if (adapterSource.itemCount == 0) {
                        source.visibility = View.GONE
                        getEmptyText(source.parent as LinearLayout).visibility = View.VISIBLE
                    }else{
                        source.visibility = View.VISIBLE
                        getEmptyText(source.parent as LinearLayout).visibility = View.GONE
                    }

                    if (adapterTarget.itemCount == 0) {
                        target.visibility = View.GONE
                        getEmptyText(target.parent as LinearLayout).visibility = View.VISIBLE
                    }else{
                        target.visibility = View.VISIBLE
                        getEmptyText(target.parent as LinearLayout).visibility = View.GONE
                    }


                }
            }
            DragEvent.ACTION_DRAG_ENDED -> {
            }
            else -> {
            }
        }
        if (!isDropped) {
            val vw = event.localState as View
            vw.visibility = View.VISIBLE
        }
        return true
    }

    private fun getEmptyText(group : ViewGroup): TextView{
        return group.children.find { it is TextView } as TextView
    }

}