package com.emberestudio.project.ui.util

import android.app.Activity
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.toastLong(message : String){
    Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
}

fun Fragment.toastShort(message : String){
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun Activity.toastLong(message: String){
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Activity.toastShort(message: String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}