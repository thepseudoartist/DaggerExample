package com.daggerexample

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.daggerexample.ui.auth.AuthActivity
import com.daggerexample.ui.auth.AuthResource
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

private const val TAG = "BaseActivity"

abstract class BaseActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeObservers()
    }

    private fun subscribeObservers() {
        sessionManager.getAuthUser().observe(this) {
            if (it != null) {
                when (it.status) {
                    AuthResource.AuthStatus.LOADING -> {

                    }
                    AuthResource.AuthStatus.NOT_AUTHENTICATED -> navLogInScreen()
                    AuthResource.AuthStatus.AUTHENTICATED -> {
                        Log.i(TAG, "onChanged: Login Success: " + it.data?.email)
                    }
                    AuthResource.AuthStatus.ERROR -> {
                        Log.e(TAG, "subscribeObservers: ${it.message}")
                    }
                }
            }
        }
    }

    private fun navLogInScreen() {
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        finish()
    }
}