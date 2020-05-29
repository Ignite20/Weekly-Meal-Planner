package com.emberestudio.project.ui.grocery.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emberestudio.project.ui.domain.model.GroceryItem
import com.emberestudio.project.ui.grocery.holder.GroceryItemViewHolder

class GroceryAdapter(var list: MutableList<GroceryItem>, var callback : GroceryAdapterActions? ) : RecyclerView.Adapter<GroceryItemViewHolder>() {

    interface GroceryAdapterActions{
        fun onAddGroceryItem(position: Int)
        fun onDeleteGroceryItem(position : Int)
        fun onCheckedGroceryItem(isChecked: Boolean, position: Int)
    }

    var focusPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroceryItemViewHolder {
        return GroceryItemViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: GroceryItemViewHolder, position: Int) {
        holder.bind(list[position], focusPosition)
        holder.callback = object : GroceryItemViewHolder.GroceryListActions{
            override fun onGroceryItemDelete(position : Int) {
                callback?.onDeleteGroceryItem(position)
            }

            override fun onGroceryItemChanged(text: CharSequence?, position: Int) {
                list[position].content = text.toString()
            }

            override fun onKeyboardEnterPressed(position: Int) {
                callback?.onAddGroceryItem(position + 1)
            }

            override fun setGroceryItemChecked(isChecked: Boolean, position: Int) {
//                if(isChecked){
//                    val checkedItem = list.removeAt(position)
//                    checkedItem.lastKnownPosition = position
//                    list.add(checkedItem)
//                }else{
//                    val uncheckedItem = list.removeAt(position)
//                    list.add(uncheckedItem.lastKnownPosition, uncheckedItem)
//                }

                list[position].lastKnownPosition = if(isChecked) position else -1
                callback?.onCheckedGroceryItem(isChecked, position)
            }
        }
    }
}