package com.emberestudio.project.ui.meals.ui.add_meal_dialog.viewholder

import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.emberestudio.project.databinding.ItemIngredientBinding
import com.emberestudio.project.ui.domain.model.Ingredient
import com.emberestudio.project.ui.domain.model.QuantityUnit

class IngredientViewHolder(var binding : ItemIngredientBinding) : RecyclerView.ViewHolder(binding.root) {

    var callbackOnDelete : OnDeleteItem? = null
    var callbackOnChange : OnChange? = null

    var name : String = ""
    var quantity : String = ""
    var quantityUnit : QuantityUnit? = QuantityUnit.NO_UNIT

    var quantityUnits = QuantityUnit.shortNames()

    interface OnChange{
        fun onChangeName(position: Int, name : String)
        fun onChangeQuantity(position: Int, quantity : String)
        fun onChangeUnit (position: Int, quantityUnit : QuantityUnit?)
    }

    companion object {
        fun from(viewGroup: ViewGroup?): IngredientViewHolder {
            val layoutInflater = LayoutInflater.from(viewGroup?.context)
            val binding = ItemIngredientBinding.inflate(layoutInflater, viewGroup, false)
            binding.executePendingBindings()
            return IngredientViewHolder(binding)
        }
    }

    fun bind(ingredient : Ingredient?){
        ingredient?.let {
            name = it.name
            quantity = it.quantity
            quantityUnit = QuantityUnit.findValue(it.unit)
        }

        binding.ingredientName.text = Editable.Factory.getInstance().newEditable(name)
        binding.ingredientQuantity.text = Editable.Factory.getInstance().newEditable(quantity)

        prepareIngredientNameWatcher()
        prepareIngredientQuantityWatcher()
        prepareSpinner()
        prepareDeleteButton()

    }
    private fun prepareIngredientNameWatcher(){
        binding.ingredientName.doAfterTextChanged {
            callbackOnChange?.onChangeName(adapterPosition, it.toString())
        }
    }

    private fun prepareIngredientQuantityWatcher(){
        binding.ingredientQuantity.doAfterTextChanged {
            callbackOnChange?.onChangeQuantity(adapterPosition, it.toString())
        }
    }

    private fun prepareDeleteButton(){
        binding.itemDelete.setOnClickListener {
            callbackOnDelete?.onDeleteItem(adapterPosition)

        }
    }

    private fun prepareSpinner(){
        binding.spnrUnit.apply {
            adapter = ArrayAdapter(
                this.context,
                android.R.layout.simple_spinner_item,
                quantityUnits
            ).also { adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    callbackOnChange?.onChangeUnit(adapterPosition, QuantityUnit.findValue(quantityUnits[position]))
                }
            }
            quantityUnit?.let { setSelection(it.ordinal) }
        }
    }

    fun focus(focus: Boolean){
        if(focus) binding.ingredientName.requestFocus()
    }
}