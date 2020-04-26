package com.emberestudio.project.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.bumptech.glide.Glide
import com.emberestudio.project.R
import com.emberestudio.project.ui.base.BaseActivity
import com.emberestudio.project.ui.managers.AuthenticationManager
import com.google.firebase.auth.FirebaseUser
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.main_activity.*
import javax.inject.Inject


class MainActivity : BaseActivity(), AuthenticationManager.AuthCallback{


    @Inject
    lateinit var authManager : AuthenticationManager

    companion object{
        fun start(activity: Activity){
            activity.startActivity(Intent(activity, MainActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        AndroidInjection.inject(this)
        authManager.callback = this
        setupActionBar()
        setUpNavigation()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        return when (item?.itemId) {
            R.id.sing_out -> {
                authManager.logout(this)
                return true
            }
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun setUpNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment?
        NavigationUI.setupWithNavController(bottom_navigation, navHostFragment!!.navController)
    }

    private fun setupActionBar() {
        Glide.with(iv_profile_icon.context).load(authManager.getCurrentUser()?.photoUrl).optionalCircleCrop().into(iv_profile_icon)
        iv_profile_icon.setOnClickListener {
            authManager.logout(this)
        }
//        setupActionBarWithNavController(findNavController(R.id.nav_host_fragment_container))
    }

    override fun onAuthSuccessful(user: FirebaseUser?) {
        TODO("Not yet implemented")
    }

    override fun onLogout() {
        finish()
    }

    override fun onAuthFailure() {
        TODO("Not yet implemented")
    }

}