package com.emberestudio.project.ui.mealdetail.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.emberestudio.project.ui.base.BaseViewModel
import com.emberestudio.project.ui.mealdetail.model.MealDetailUiModel
import com.emberestudio.project.ui.mealdetail.model.toUiModel
import com.emberestudio.project.ui.planner.usecase.MealUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MealDetailViewModel @Inject constructor(private val mealUseCase: MealUseCase) : BaseViewModel() {

    val uiData = MediatorLiveData<MealDetailUiModel>()
    var mealItem : LiveData<MealDetailUiModel> = uiData

    init {
        setupObservers()
    }


    fun getMeal(day: Int, meal: Int){
        viewModelScope.launch(Dispatchers.IO) {

            mealUseCase.getMeal(day, meal)
        }
    }

    fun getMeal(id : String){
        viewModelScope.launch(Dispatchers.IO) {
            mealUseCase.getMeal(id)
        }
    }

    private fun setupObservers(){
        uiData.apply {
            addSource(mealUseCase.mealResponse.data){ response ->
                createAndPostUiModel(response.toUiModel())
            }
        }
    }

    private fun createAndPostUiModel(response: MealDetailUiModel) {
        viewModelScope.launch {
            uiData.postValue(response)
        }
    }
}