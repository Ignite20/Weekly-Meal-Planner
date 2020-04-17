package com.emberestudio.project.ui.main

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import com.emberestudio.project.R
import com.emberestudio.project.ui.managers.AuthenticationManager
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class SplashActivity : Activity(), HasSupportFragmentInjector {

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    lateinit var authManager : AuthenticationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_Launcher)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_view)
        authManager = AuthenticationManager()
        checkLogin()

//        Handler().postDelayed({
//            startMainActivity()
//            finish()
//        }, 5000)
    }

    private fun checkLogin(){
        Handler().post {
            val user = authManager.firebaseAuth?.currentUser
            checkUser(user != null)
        }

    }

    private fun checkUser(logged : Boolean){
        //TODO: Cleanup user handling
        if(logged) startMainActivity()
    }

    private fun startMainActivity(){
        MainActivity.start(this)
        finish()
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentInjector

}
