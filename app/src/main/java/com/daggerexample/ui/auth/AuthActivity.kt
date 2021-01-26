package com.daggerexample.ui.auth

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.RequestManager
import com.daggerexample.R
import com.daggerexample.ui.main.MainActivity
import com.daggerexample.viewmodels.ViewModelProviderFactory
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

private const val TAG = "AuthActivity"

class AuthActivity : DaggerAppCompatActivity() {
//    Solution to lateinit not supported by primitives
//    @set:[Inject Named("isAppNull")]
//    var isAppNull : Boolean = false

    // Dependency Injections
    @Inject
    lateinit var logo: Drawable

    @Inject
    lateinit var requestManager: RequestManager

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    // ViewModel
    private lateinit var authViewModel: AuthViewModel

    // Views
    private lateinit var userId: EditText
    private lateinit var progressBar: ProgressBar

    private fun setLogo() {
        requestManager.load(logo).into(findViewById(R.id.login_logo))
    }

    private fun attemptLogin() {
        if (TextUtils.isEmpty(userId.text.toString())) return
        authViewModel.authenticateWithID(Integer.parseInt(userId.text.toString()))
    }

    private fun subscribeObservers() {
        authViewModel.observeAuthState().observe(this, {
            if (it != null) {
                when (it.status) {
                    AuthResource.AuthStatus.LOADING -> showProgressBar(true)
                    AuthResource.AuthStatus.NOT_AUTHENTICATED -> showProgressBar(false)
                    AuthResource.AuthStatus.AUTHENTICATED -> {
                        showProgressBar(false)
                        Log.i(TAG, "onChanged: Login Success: " + it.data?.email)
                        onLoginSuccess()
                    }
                    AuthResource.AuthStatus.ERROR -> {
                        showProgressBar(false)
                        Toast.makeText(this, it.message
                                + "\nDid you enter number between 1-10?", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }

    private fun onLoginSuccess() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showProgressBar(isVisible: Boolean) {
        if (isVisible) progressBar.visibility = View.VISIBLE
        else progressBar.visibility = View.GONE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        authViewModel = ViewModelProvider(this, providerFactory).get(AuthViewModel::class.java)

        userId = findViewById(R.id.user_id_input)
        progressBar = findViewById(R.id.progress_bar)

        findViewById<Button>(R.id.login_button).setOnClickListener {
            attemptLogin()
        }

        setLogo()
        subscribeObservers()
    }
}
