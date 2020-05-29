package com.emberestudio.project.ui.grocery.holder

import android.graphics.Paint
import android.text.Editable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.emberestudio.project.databinding.ItemGroceryBinding
import com.emberestudio.project.ui.domain.model.GroceryItem
import com.emberestudio.project.ui.util.gone
import com.emberestudio.project.ui.util.visible

class GroceryItemViewHolder(val binding: ItemGroceryBinding) : RecyclerView.ViewHolder(binding.root) {

    interface GroceryListActions{
        fun onGroceryItemDelete(position : Int)
        fun onGroceryItemChanged(text: CharSequence?, position: Int)
        fun onKeyboardEnterPressed(position: Int)
        fun setGroceryItemChecked(isChecked: Boolean, position: Int)
    }

    var callback: GroceryListActions? = null

    companion object{
        fun from(viewGroup: ViewGroup?): GroceryItemViewHolder{
            val layoutInflater = LayoutInflater.from(viewGroup?.context)
            val binding = ItemGroceryBinding.inflate(layoutInflater, viewGroup, false)
            return GroceryItemViewHolder(binding)
        }
    }

    fun bind(item: GroceryItem, focusPosition: Int){

        binding.etGroceryItemContent.text = Editable.Factory.getInstance().newEditable(item.content)
        binding.cbItemCheck.isChecked = item.checked


        binding.etGroceryItemContent.doAfterTextChanged { editable ->
            callback?.onGroceryItemChanged(editable.toString(), adapterPosition)
        }
        binding.etGroceryItemContent.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus) binding.ivDeleteGroceryItem.visible() else binding.ivDeleteGroceryItem.gone()
        }

        binding.etGroceryItemContent.post {
            if(focusPosition == adapterPosition) {
                binding.etGroceryItemContent.requestFocus()
            }
        }

        binding.etGroceryItemContent.setOnEditorActionListener { view, actionId, event ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    callback?.onKeyboardEnterPressed(adapterPosition)
                    true
                }
                else -> false
            }
        }

        binding.cbItemCheck.setOnCheckedChangeListener { buttonView, isChecked ->
            strikeThrough(isChecked)
            callback?.setGroceryItemChecked(isChecked, adapterPosition)
        }

        binding.ivDeleteGroceryItem.setOnClickListener {
            if(it.isVisible) callback?.onGroceryItemDelete(adapterPosition)
        }
    }

    private fun strikeThrough(isChecked: Boolean){
        if(isChecked) {
            binding.etGroceryItemContent.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            binding.etGroceryItemContent.isEnabled = false
        }
        else {
            binding.etGroceryItemContent.paintFlags = Paint.LINEAR_TEXT_FLAG
            binding.etGroceryItemContent.isEnabled = true
        }
    }

}