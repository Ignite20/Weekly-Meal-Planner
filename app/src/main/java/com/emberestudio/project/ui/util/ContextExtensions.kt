package com.emberestudio.project.ui.util

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.toastLong(message : String){
    Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
}

fun Fragment.toastShort(message : String){
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}
