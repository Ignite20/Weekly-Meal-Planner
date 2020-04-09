package com.emberestudio.project.ui.domain.api

interface ApiCallback<S, E> {
    /**
     * Invoked for a received HTTP response.
     */
    fun onResponse(code: String, response: S?)


    /**
     * Invoked for a received HTTP error response.
     */
    fun onErrorResponse(code: String, response: E?)

}