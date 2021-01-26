package com.daggerexample.ui.main.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.daggerexample.R
import com.daggerexample.models.User
import com.daggerexample.ui.auth.AuthResource
import com.daggerexample.viewmodels.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

private const val TAG = "ProfileFragment"

class ProfileFragment : DaggerFragment() {

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    private lateinit var viewModel: ProfileViewModel

    private lateinit var email: TextView
    private lateinit var website: TextView
    private lateinit var username: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Toast.makeText(activity, TAG, Toast.LENGTH_SHORT).show()
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated: ProfileFragment was created.")
        viewModel = ViewModelProvider(this, providerFactory).get(ProfileViewModel::class.java)

        email = view.findViewById(R.id.email)
        website = view.findViewById(R.id.website)
        username = view.findViewById(R.id.username)

        subscribeObservers()
    }

    private fun subscribeObservers() {
        viewModel.getAuthenticatedUser().removeObservers(viewLifecycleOwner)
        viewModel.getAuthenticatedUser().observe(viewLifecycleOwner) {
            if (it != null) {
                when (it.status) {
                    AuthResource.AuthStatus.AUTHENTICATED -> {
                        setUserDetails(it.data)
                    }
                    AuthResource.AuthStatus.ERROR -> {
                        setErrorDetails(it.message)
                    }
                    else -> {
                    }
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setErrorDetails(message: String?) {
        email.text = message
        website.text = "error"
        username.text = "error"
    }

    private fun setUserDetails(data: User?) {
        if (data != null) {
            email.text = data.email
            website.text = data.website
            username.text = data.username
        }
    }
}
