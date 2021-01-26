package com.daggerexample.ui.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.daggerexample.SessionManager
import com.daggerexample.models.User
import com.daggerexample.network.auth.AuthAPI
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

private const val TAG = "AuthViewModel"

class AuthViewModel @Inject constructor(
    private val authAPI: AuthAPI,
    private val sessionManager: SessionManager
) : ViewModel() {

    fun authenticateWithID(userId: Int) {
        Log.d(TAG, "authenticateWithID: Attempting to LogIn.")
        sessionManager.authenticateWithID(queryUserID(userId))
    }

    private fun queryUserID(userId: Int): LiveData<AuthResource<User>> {
        return LiveDataReactiveStreams.fromPublisher(
            authAPI.getUser(userId)
                .onErrorReturn {
                    User(-1, null, null, null)
                }
                .map {
                    if (it.id == -1)
                        return@map AuthResource.error(
                            "Couldn't authenticate the user.",
                            null as User?
                        )
                    return@map AuthResource.authenticated(it)
                }
                .subscribeOn(Schedulers.io())
        )
    }

    fun observeAuthState(): LiveData<AuthResource<User>> {
        return sessionManager.getAuthUser()
    }
}