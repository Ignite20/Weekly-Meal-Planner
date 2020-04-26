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
import com.emberestudio.project.ui.managers.AuthenticationManager
import com.emberestudio.project.ui.meals.ui.add_meal_dialog.adapter.IngredientsAdapter
import com.emberestudio.project.ui.meals.ui.add_meal_dialog.adapter.StepsAdapter

class AddMealDialog (var callback: Actions, var meal : Meal? = null, var authManager : AuthenticationManager ) : BaseDialogFragment(),
    IngredientsAdapter.OnItemAddedListener ,
    StepsAdapter.OnItemAddedListener
{

    interface Actions {
        fun onSaveMeal(item: Meal)
    }

    lateinit var binding: DialogAddMealToPlanBinding

    lateinit var ingredients : MutableList<Ingredient>
    lateinit var steps : MutableList<Step>

    lateinit var ingredientsAdapter : IngredientsAdapter
    lateinit var stepsAdapter: StepsAdapter

//    @Inject lateinit var authManager : AuthenticationManager

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
        prepareSteps()
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

            ingredients = if(meal?.ingredients == null){
                mutableListOf()
            }else{
                meal?.ingredients!!.toMutableList()
            }

            steps = if(meal?.steps == null){
                mutableListOf()
            }else{
                meal?.steps!!.toMutableList()
            }

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
        prepareAddNewStepButton()
    }

    private fun prepareSteps(){
        stepsAdapter = StepsAdapter(steps, this@AddMealDialog)
        binding.rvSteps.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
            adapter = stepsAdapter
        }
    }

    private fun prepareAddNewIngredientButton(){
        binding.btnAddIngredient.setOnClickListener {
            ingredients.add(Ingredient())
            binding.rvIngredients.adapter = IngredientsAdapter(ingredients, this)
            ingredientsAdapter.notifyDataSetChanged()
            binding.rvIngredients.scrollToPosition(ingredients.size - 1)
            binding.scrollContent.smoothScrollTo(binding.btnAddIngredient.x.toInt(), binding.btnAddIngredient.y.toInt())
        }
    }

    private fun prepareAddNewStepButton(){
        binding.btnAddStep.setOnClickListener {
            steps.add(Step())
            binding.rvSteps.adapter = StepsAdapter(steps, this)
            stepsAdapter.notifyDataSetChanged()
            binding.rvSteps.scrollToPosition(steps.size - 1)
            binding.scrollContent.smoothScrollTo(binding.btnAddStep.x.toInt(), binding.btnAddStep.y.toInt())
        }
    }

    private fun prepareSaveMealAction(){
        binding.btnSaveMeal.setOnClickListener {
            //TODO : Rework update meal
            authManager.getCurrentUser()?.uid?.let {
                callback.onSaveMeal(
                    Meal(
                        id = if(meal != null) meal?.id!! else "",
                        author = it,
                        name = binding.tiMealNameEdit.text.toString().trim(),
                        description = binding.tiMealDescriptionEdit.text.toString().trim(),
                        ingredients = ingredients,
                        steps = steps,
                        global = true
                ))
            }

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

    override fun saveStep(position: Int, step: Step) {
        stepsAdapter.notifyItemInserted(position)
    }

    override fun deleteStep(position: Int) {
        stepsAdapter.notifyItemRemoved(position)
    }
}