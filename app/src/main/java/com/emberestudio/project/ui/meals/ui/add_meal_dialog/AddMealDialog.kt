package com.emberestudio.project.ui.meals.ui.add_meal_dialog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emberestudio.project.databinding.DialogAddMealToPlanBinding
import com.emberestudio.project.ui.base.BaseDialogFragment
import com.emberestudio.project.ui.domain.model.Ingredient
import com.emberestudio.project.ui.domain.model.Meal
import com.emberestudio.project.ui.domain.model.Step
import com.emberestudio.project.ui.meals.ui.add_meal_dialog.adapter.IngredientsAdapter
import com.emberestudio.project.ui.meals.ui.add_meal_dialog.adapter.StepsAdapter
import java.util.*

class AddMealDialog (var callback: Actions) : BaseDialogFragment(), IngredientsAdapter.OnItemAddedListener {

    interface Actions {
        fun onSaveMeal(item: Meal)
    }

    lateinit var binding: DialogAddMealToPlanBinding

    var ingredients : MutableList<Ingredient> = mutableListOf()
    var steps : MutableList<Step> = mutableListOf()

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
        prepareIngredients()
        prepareSaveMealAction()
        prepareCancel()
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
        }
    }

    private fun prepareSaveMealAction(){
        binding.btnSaveMeal.setOnClickListener {
            callback.onSaveMeal(
                Meal(id = UUID.randomUUID().toString() + Calendar.getInstance().timeInMillis,
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