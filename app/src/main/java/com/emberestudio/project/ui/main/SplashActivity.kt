package com.emberestudio.project.ui.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.emberestudio.project.R
import com.emberestudio.project.ui.base.BaseActivity
import com.emberestudio.project.ui.managers.AuthenticationManager
import com.google.firebase.auth.FirebaseUser
import dagger.android.AndroidInjection
import javax.inject.Inject

class SplashActivity : BaseActivity(), AuthenticationManager.AuthCallback {

    @Inject
    lateinit var authManager : AuthenticationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_Launcher)
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        authManager.callback = this
        checkLogin()
    }

    private fun checkLogin(){
        Handler().post {
            authManager.recoverUser()
        }

    }


    private fun startMainActivity(){
        MainActivity.start(this)
        finish()
    }

    override fun onAuthSuccessful(user: FirebaseUser?) {
        user?.let {
            if (it.isEmailVerified){
                startMainActivity()
            }else{
                user.sendEmailVerification().addOnSuccessListener {
                    startMainActivity()
                }.addOnFailureListener {
                    finish()
                }
            }
        }


    }

    override fun onLogout() {

    }

    override fun onAuthFailure() {
        authManager.login(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == AuthenticationManager.RC_SIGN_IN) {
            if(data != null) authManager.handleSignInResult(resultCode, data) else finish()
        }
    }

}
