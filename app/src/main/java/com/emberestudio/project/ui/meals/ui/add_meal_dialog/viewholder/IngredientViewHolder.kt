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
import java.util.*

class IngredientViewHolder(var binding : ItemIngredientBinding) : RecyclerView.ViewHolder(binding.root) {

    var callback : OnFocusLost? = null
    var callbackOnChange : OnChange? = null
    var focusCallback : View.OnFocusChangeListener? = null

    var name : String = ""
    var quantity : String = ""
    var quantityUnit : QuantityUnit? = QuantityUnit.NO_UNIT

    interface OnFocusLost{
        fun onFocusLost(position : Int, ingredient : Ingredient)
        fun onDeleteItem(position: Int)
    }

    interface OnChange{
        fun onChangeName(position: Int, name : String)
        fun onChangeQuantity(position: Int, quantity : String)
        fun onChangeUnit (position: Int, quantityUnit : QuantityUnit)
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
//        binding.spnrUnit.setSelection(quantityUnit!!.ordinal)

        binding.ingredientName.doAfterTextChanged {
            callbackOnChange?.onChangeName(adapterPosition, it.toString())
        }

        binding.ingredientQuantity.doAfterTextChanged {
            callbackOnChange?.onChangeQuantity(adapterPosition, it.toString())
        }

        prepareSpinner()
        prepareDeleteButton()

    }
    private fun prepareDeleteButton(){
        binding.itemDelete.setOnClickListener {
            callback?.onDeleteItem(adapterPosition)

        }
    }
    private fun prepareSpinner(){
        binding.spnrUnit.apply {
            adapter = ArrayAdapter(
                this.context,
                android.R.layout.simple_spinner_item,
                QuantityUnit.shortNames()
            ).also { adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    callbackOnChange?.onChangeUnit(adapterPosition, QuantityUnit.findValue(QuantityUnit.longNames()[position].toUpperCase(Locale.getDefault()))!!)
                }
            }
            setSelection(0)
        }
    }
}