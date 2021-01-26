package com.daggerexample.ui.main.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.daggerexample.SessionManager
import com.daggerexample.models.User
import com.daggerexample.ui.auth.AuthResource
import javax.inject.Inject

private const val TAG = "ProfileViewModel"

class ProfileViewModel @Inject constructor(private val sessionManager: SessionManager) : ViewModel() {

    init {
        Log.d(TAG, "ProfileViewModel: $TAG is ready.")
    }

    fun getAuthenticatedUser(): LiveData<AuthResource<User>> {
        return sessionManager.getAuthUser()
    }
}
