package com.emberestudio.project.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel() {

    /**
     * This CoroutineContext is cleared when the ViewModel is cleared.
     */
    private val coroutineContext: CoroutineContext get() = Job() + Dispatchers.Main

    /**
     * This scope lives within the ViewModel's lifecycle.
     * Dispatchers.Main is the default dispatcher for this scope.
     */
    protected val viewModelScope = CoroutineScope(coroutineContext)

    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancel()
    }

}
