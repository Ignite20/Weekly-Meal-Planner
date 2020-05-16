package com.emberestudio.project.ui.meals.dialog.add_meal_dialog.viewholder

import android.text.Editable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.emberestudio.project.databinding.ItemStepBinding
import com.emberestudio.project.ui.domain.model.Step

class StepViewHolder(var binding : ItemStepBinding) : RecyclerView.ViewHolder(binding.root){
    var callbackOnDelete : OnDeleteItem? = null
    var callbackOnChange : OnChange? = null

    var content : String = ""

    interface OnChange{
        fun onChangeContent(position: Int, content : String)
    }

    companion object {
        fun from(viewGroup: ViewGroup?): StepViewHolder {
            val layoutInflater = LayoutInflater.from(viewGroup?.context)
            val binding = ItemStepBinding.inflate(layoutInflater, viewGroup, false)
            binding.executePendingBindings()
            return StepViewHolder(
                binding
            )
        }
    }

    fun bind(step : Step?){
        step?.let {
            content = it.description
        }

        binding.stepContent.text = Editable.Factory.getInstance().newEditable(content)

        prepareStepContentWatcher()
        prepareDeleteButton()
    }

    private fun prepareStepContentWatcher(){
        binding.stepContent.doAfterTextChanged {
            callbackOnChange?.onChangeContent(adapterPosition, it.toString())
        }
    }

    private fun prepareDeleteButton(){
        binding.itemDelete.setOnClickListener {
            callbackOnDelete?.onDeleteItem(adapterPosition)

        }
    }

    fun focus(focus: Boolean){
        if(focus) binding.stepContent.requestFocus()
    }
}