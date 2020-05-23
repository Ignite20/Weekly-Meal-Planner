package com.emberestudio.project.ui.grocery.holder

import android.text.Editable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.RecyclerView
import com.emberestudio.project.databinding.ItemGroceryBinding
import com.emberestudio.project.ui.domain.model.GroceryItem

class GroceryItemViewHolder(val binding: ItemGroceryBinding) : RecyclerView.ViewHolder(binding.root) {

    interface GroceryListActions{
        fun onGroceryItemDelete(position : Int)
        fun onGroceryItemChanged()
        fun onKeyboardEnterPressed()
    }

    var callback: GroceryListActions? = null

    companion object{
        fun from(viewGroup: ViewGroup?): GroceryItemViewHolder{
            val layoutInflater = LayoutInflater.from(viewGroup?.context)
            val binding = ItemGroceryBinding.inflate(layoutInflater, viewGroup, false)
            return GroceryItemViewHolder(binding)
        }
    }

    fun bind(item: GroceryItem, focus: Boolean){
        binding.etGroceryItemContent.text = Editable.Factory.getInstance().newEditable(item.content)
        if(focus) binding.etGroceryItemContent.requestFocus()
        binding.etGroceryItemContent.setOnEditorActionListener { view, actionId, event ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    callback?.onKeyboardEnterPressed()
                    true
                }
                else -> false
            }
        }
        binding.ivDeleteGroceryItem.setOnClickListener {
            callback?.onGroceryItemDelete(adapterPosition)
        }
    }
}