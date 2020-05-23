package com.emberestudio.project.ui.grocery.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emberestudio.project.ui.domain.model.GroceryItem
import com.emberestudio.project.ui.grocery.holder.GroceryItemViewHolder

class GroceryAdapter(var list: MutableList<GroceryItem>, var callback : GroceryAdapterActions? ) : RecyclerView.Adapter<GroceryItemViewHolder>() {

    interface GroceryAdapterActions{
        fun onAddGroceryItem()
        fun onDeleteGroceryItem(position : Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroceryItemViewHolder {
        return GroceryItemViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: GroceryItemViewHolder, position: Int) {
        holder.bind(list[position], position == list.size - 1)
        holder.callback = object : GroceryItemViewHolder.GroceryListActions{
            override fun onGroceryItemDelete(position : Int) {
                list.removeAt(position)
                notifyItemRemoved(position)
            }

            override fun onGroceryItemChanged() {

            }

            override fun onKeyboardEnterPressed() {
                callback?.onAddGroceryItem()
            }
        }
    }
}