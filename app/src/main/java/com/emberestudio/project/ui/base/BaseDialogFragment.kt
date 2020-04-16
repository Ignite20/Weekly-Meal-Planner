package com.emberestudio.project.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.emberestudio.project.R

abstract class BaseDialogFragment : DialogFragment() {

    protected lateinit var rootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.ThemeOverlay_AppCompat)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = onBind(inflater, container)
        return rootView
    }

    protected abstract fun prepareUI()
    protected abstract fun onBind(inflater: LayoutInflater, container: ViewGroup?): View
}