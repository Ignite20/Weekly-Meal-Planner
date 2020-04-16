package com.emberestudio.project.ui.meals.ui.add_meal_dialog

import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emberestudio.project.R
import com.emberestudio.project.databinding.DialogAddMealToPlanBinding
import com.emberestudio.project.ui.base.BaseDialogFragment
import com.emberestudio.project.ui.domain.model.Ingredient
import com.emberestudio.project.ui.domain.model.Meal
import com.emberestudio.project.ui.domain.model.Step
import com.emberestudio.project.ui.meals.ui.add_meal_dialog.adapter.IngredientsAdapter
import com.emberestudio.project.ui.meals.ui.add_meal_dialog.adapter.StepsAdapter
import java.util.*

class AddMealDialog (var callback: Actions, var meal : Meal? = null) : BaseDialogFragment(), IngredientsAdapter.OnItemAddedListener {

    interface Actions {
        fun onSaveMeal(item: Meal)
    }

    lateinit var binding: DialogAddMealToPlanBinding

    lateinit var ingredients : MutableList<Ingredient>
    lateinit var steps : MutableList<Step>

    lateinit var ingredientsAdapter : IngredientsAdapter
    lateinit var stepsAdapter: StepsAdapter

    override fun onBind(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = DialogAddMealToPlanBinding.inflate(inflater).apply {
            lifecycleOwner = this@AddMealDialog
            executePendingBindings()
        }
        prepareUI()
        return binding.root
    }

    override fun prepareUI() {
        prepareGeneral()
        prepareIngredients()
        prepareSaveMealAction()
        prepareCancel()
    }

    private fun prepareGeneral(){
        if(meal == null){
            binding.mealAddEditTitle.text = getText(R.string.add_meal)
            ingredients = mutableListOf()
            steps = mutableListOf()
        } else {
            binding.mealAddEditTitle.text = getText(R.string.edit_meal)
            binding.tiMealNameEdit.text = Editable.Factory.getInstance().newEditable(meal?.name)
            binding.tiMealDescriptionEdit.text = Editable.Factory.getInstance().newEditable(meal?.description)
            ingredients = meal?.ingredients!!.toMutableList()
            steps = meal?.steps!!.toMutableList()

        }
    }

    private fun prepareIngredients(){
        ingredientsAdapter = IngredientsAdapter(ingredients, this@AddMealDialog)
        binding.rvIngredients.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
            adapter = ingredientsAdapter
        }

        prepareAddNewIngredientButton()
    }

    private fun prepareAddNewIngredientButton(){
        binding.btnAddIngredient.setOnClickListener {
            ingredients.add(Ingredient())
            binding.rvIngredients.adapter = IngredientsAdapter(ingredients, this)
            ingredientsAdapter.notifyDataSetChanged()
            binding.rvIngredients.scrollToPosition(ingredients.size - 1)
        }
    }

    private fun prepareSaveMealAction(){
        binding.btnSaveMeal.setOnClickListener {

            callback.onSaveMeal(
                Meal(id = if(meal != null) meal?.id!! else UUID.randomUUID().toString(),
                    name = binding.tiMealNameEdit.text.toString(),
                    description = binding.tiMealDescriptionEdit.text.toString(),
                    ingredients = ingredients,
                    steps = steps
                    ))
            dismiss()
        }
    }

    private fun prepareCancel(){
        binding.addPlanCloseButton.setOnClickListener {
            dismissAllowingStateLoss()
        }
    }

    override fun saveIngredient(position: Int,ingredient: Ingredient) {
        ingredientsAdapter.notifyItemInserted(position)
    }

    override fun deleteIngredient(position: Int) {
        ingredientsAdapter.notifyItemRemoved(position)
    }
}