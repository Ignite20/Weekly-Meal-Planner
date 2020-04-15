package com.emberestudio.project.ui.meals.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.emberestudio.project.ui.base.BaseViewModel
import com.emberestudio.project.ui.domain.model.Meal
import com.emberestudio.project.ui.planner.usecase.MealUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class MealsViewModel @Inject constructor(
    private val mealUseCase: MealUseCase
) : BaseViewModel() {
    val uiData = MediatorLiveData<MutableList<Meal>>()
    val meals : LiveData<MutableList<Meal>> = uiData

    init {
        setupObservers()
    }

    fun saveMeal(meal: Meal){
        mealUseCase.saveMeal(meal)
    }

    fun getMeals(){
        mealUseCase.getMeals()
    }

    fun removeItem(position : Int){
        meals.value!![position].let {
            mealUseCase.removeMeal(it.id)
        }

    }

    private fun setupObservers(){
        uiData.apply {
            addSource(mealUseCase.mealsResponse.data){ response ->
                createAndPostUiModel(response)
            }

            addSource(mealUseCase.mealResponse.data){ response ->
                createAndPostUiModel(response)
            }
        }
    }

    private fun createAndPostUiModel(response: MutableList<Meal>) {
        viewModelScope.launch {
            uiData.postValue(response)
        }
    }

    private fun createAndPostUiModel(response: Meal) {
        viewModelScope.launch {
            meals.value?.add(response)
            uiData.postValue(meals.value)
        }
    }

}