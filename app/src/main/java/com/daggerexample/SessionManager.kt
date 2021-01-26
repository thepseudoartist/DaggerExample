package com.daggerexample

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.daggerexample.models.User
import com.daggerexample.ui.auth.AuthResource
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "SessionManager"

@Singleton
class SessionManager @Inject constructor() {
    private val cachedUser: MediatorLiveData<AuthResource<User>> = MediatorLiveData()

    fun authenticateWithID(source: LiveData<AuthResource<User>>) {
        cachedUser.value = AuthResource.loading(null)
        cachedUser.addSource(source) {
            cachedUser.value = it
            cachedUser.removeSource(source)
        }
    }

    fun logout() {
        Log.d(TAG, "logout: Logging Out.")
        cachedUser.value = AuthResource.logout()
    }

    fun getAuthUser(): LiveData<AuthResource<User>> {
        return cachedUser
    }
}
