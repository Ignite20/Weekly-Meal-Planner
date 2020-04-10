package com.emberestudio.project.ui.domain.usecase.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.emberestudio.project.ui.domain.api.ApiCallback
import com.emberestudio.project.ui.domain.usecase.error.Error

interface SingleUseCase<T> {

    val data: LiveData<T>
    val error: LiveData<Error>
}

class SingleUseCaseImplementation<T> : ApiCallback<T, Error>, SingleUseCase<T> {

    private val _error: MutableLiveData<Error> = MutableLiveData()

    private val _data: MutableLiveData<T> = MutableLiveData()

    override val data: LiveData<T> = _data

    override val error: LiveData<Error> = _error

    override fun onResponse(code: String, response: T?) {
        if (response != null) {
            _data.postValue(response)
        } else {
            // ToDo we know what kind of errorMessage it is
            _error.postValue(Error())
        }
    }

    override fun onErrorResponse(code: String, response: Error?) {
        _error.postValue(response ?: Error())
    }


}