package com.emberestudio.project.ui.meals.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.emberestudio.project.R
import com.emberestudio.project.databinding.DialogAddMealToPlanBinding
import com.emberestudio.project.ui.base.BaseDialogFragment
import com.emberestudio.project.ui.domain.model.Ingredient
import com.emberestudio.project.ui.domain.model.Meal
import com.emberestudio.project.ui.domain.model.MealDays
import com.emberestudio.project.ui.domain.model.Step
import java.util.*

class AddMealToPlanDialog (var callback: Actions) : BaseDialogFragment() {

    interface Actions {
        fun onSaveMeal(item: Meal, day : Int)
    }

    lateinit var binding: DialogAddMealToPlanBinding

    override fun onBind(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = DialogAddMealToPlanBinding.inflate(inflater).apply {
            lifecycleOwner = this@AddMealToPlanDialog
            executePendingBindings()
        }
        prepareUI()
        return binding.root
    }

    override fun prepareUI() {
        prepareMealTypesSpinner()
        prepareMealDaysSpinner()
        prepareSaveMealAction()
        prepareCancel()
    }

    private fun prepareMealTypesSpinner(){
        binding.spnrMealTypes.apply {
            adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.meal_types,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
            setSelection(0)
        }

    }

    private fun prepareMealDaysSpinner(){
        binding.spnrMealDays.apply {
            adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.meal_days,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
            setSelection(0)
        }
    }

    private fun prepareSaveMealAction(){
        binding.btnSaveMeal.setOnClickListener {
            callback.onSaveMeal(
                Meal( UUID.randomUUID().toString() + Calendar.getInstance().timeInMillis,
                    binding.tiMealName.text.toString(),
                    "" ,
                    listOf(Ingredient(),Ingredient()),
                    listOf(
                        Step(order = 1,description = "description 1"),
                        Step(order = 2, description = "description 2")
                )),
                MealDays.valueOf(binding.spnrMealDays.selectedItem.toString().toUpperCase()).ordinal)
            dismiss()
        }
    }

    private fun prepareCancel(){
        binding.btnCancel.setOnClickListener {
            dismissAllowingStateLoss()
        }
    }



}