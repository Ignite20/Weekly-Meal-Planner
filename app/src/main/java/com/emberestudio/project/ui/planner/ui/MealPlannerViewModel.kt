package com.emberestudio.project.ui.planner.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.emberestudio.project.ui.base.BaseViewModel
import com.emberestudio.project.ui.planner.model.Meal
import com.emberestudio.project.ui.planner.usecase.MealUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MealPlannerViewModel @Inject constructor(private val mealUseCase: MealUseCase) : BaseViewModel() {

    val uiData = MediatorLiveData<MutableMap<Int, MutableList<Meal>>>()
    var plan : LiveData<MutableMap<Int, MutableList<Meal>>> = uiData

    init {
        setupObservers()
    }

    fun getMeals() {
        viewModelScope.launch(Dispatchers.IO) {
            mealUseCase.getPlan()
        }

    }

    private fun setupObservers(){
        uiData.apply {
            addSource(mealUseCase.planResponse.data){ response ->
                createAndPostUiModel(response)
            }
        }
    }

    private fun createAndPostUiModel(response: MutableMap<Int, MutableList<Meal>>) {
        viewModelScope.launch {
            uiData.postValue(response)
        }
    }
}