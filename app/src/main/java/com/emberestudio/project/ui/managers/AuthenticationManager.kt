package com.emberestudio.project.ui.managers

import android.app.Activity
import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import dagger.Module
import javax.inject.Inject


@Module
class AuthenticationManager @Inject constructor() {

    interface AuthCallback{
        fun onGoogleAuthCompleted(account: GoogleSignInAccount?)
    }
    companion object{
         const val RC_SIGN_IN : Int = 1
    }

    lateinit var options : GoogleSignInOptions
    private lateinit var client : GoogleSignInClient
    var account : GoogleSignInAccount? = null
    var callback : AuthCallback? = null

    fun setListener(callback: AuthCallback?){
        this.callback = callback
    }

    fun loginGoogle(context: Activity){
        if(account == null) {
            options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
            client = GoogleSignIn.getClient(context, options)

            val signInIntent = client.signInIntent
            context.startActivityForResult(signInIntent, RC_SIGN_IN)
        }else{
            client.signOut()
            account = null
        }
    }

    fun getGoogleAccount(context: Context) : GoogleSignInAccount?{
        return GoogleSignIn.getLastSignedInAccount(context)
    }

    fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            account = completedTask.getResult(ApiException::class.java)
            callback?.onGoogleAuthCompleted(account)
            // Signed in successfully, show authenticated UI.
//            updateUI(account)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
//            updateUI(null)
        }
    }
}