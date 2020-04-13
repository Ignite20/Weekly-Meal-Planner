package com.emberestudio.project.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.emberestudio.project.R
import com.emberestudio.project.ui.managers.AuthenticationManager
import com.emberestudio.project.ui.managers.AuthenticationManager.Companion.RC_SIGN_IN
import com.emberestudio.project.ui.util.toastLong
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.main_activity.*
import javax.inject.Inject


class MainActivity : AppCompatActivity(), HasSupportFragmentInjector, AuthenticationManager.AuthCallback {

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var authManager : AuthenticationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        AndroidInjection.inject(this)
        setupActionBar()
        setUpNavigation()
        setupAuthenticator()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        return when (item?.itemId) {
            R.id.sing_in -> {
                signIn(item)

                return false
            }
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun signIn(item: MenuItem?){
        authManager.loginGoogle(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            authManager.handleSignInResult(task)
        }
    }
    private fun setUpNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment?
        NavigationUI.setupWithNavController(bottom_navigation, navHostFragment!!.navController)
    }

    private fun setupActionBar() {
        setupActionBarWithNavController(findNavController(R.id.nav_host_fragment_container))
    }

    private fun setupAuthenticator(){
        authManager.setListener(this)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentInjector

    override fun onGoogleAuthCompleted(account: GoogleSignInAccount?) {
        toastLong(account?.displayName.toString())

    }
}