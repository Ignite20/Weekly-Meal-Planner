package com.emberestudio.project.ui.managers

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.Module
import javax.inject.Inject

@Module
class AuthenticationManager @Inject constructor() {

    interface AuthCallback{
        fun onAuthSuccessful(user: FirebaseUser?)
        fun onLogout()
        fun onAuthFailure()
    }
    companion object{
         const val RC_SIGN_IN : Int = 1
    }

    var user : FirebaseUser? = null
    var callback : AuthCallback? = null

    fun login(context: Fragment){
        if(user == null) {
            // Choose authentication providers
            val providers = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder().build(),
                AuthUI.IdpConfig.PhoneBuilder().build(),
                AuthUI.IdpConfig.GoogleBuilder().build()
            )

            // Create and launch sign-in intent
            context.startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build(),
                RC_SIGN_IN)
        }else{
            logout(context)
        }
    }

    fun silentLogin(context: Fragment){
        AuthUI.getInstance().silentSignIn(context.requireContext(), mutableListOf())
    }

    fun logout(context: Fragment){
        AuthUI.getInstance()
            .signOut(context.requireContext())
            .addOnCompleteListener {
                user = null
                callback?.onLogout()
            }
    }


    fun handleSignInResult(resultCode: Int, data: Intent) {

        val response = IdpResponse.fromResultIntent(data)

        if (resultCode == Activity.RESULT_OK) {
            // Successfully signed in
            user = FirebaseAuth.getInstance().currentUser
            callback?.onAuthSuccessful(user)
            // ...
        } else {
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
        }

    }
}