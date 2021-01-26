package com.daggerexample.ui.auth

class AuthResource<T>(val status: AuthStatus, val data: T?, val message: String?) {
    enum class AuthStatus {
        AUTHENTICATED, ERROR, LOADING, NOT_AUTHENTICATED,
    }

    companion object {
        @JvmStatic
        fun <T> authenticated(data: T?) : AuthResource<T> {
            return AuthResource(AuthStatus.AUTHENTICATED, data, null)
        }

        @JvmStatic
        fun <T> error(message: String, data: T?) : AuthResource<T> {
            return AuthResource(AuthStatus.ERROR, data, message)
        }

        @JvmStatic
        fun <T> loading(data: T?) : AuthResource<T> {
            return AuthResource(AuthStatus.LOADING, data, null)
        }

        @JvmStatic
        fun <T> logout() : AuthResource<T> {
            return AuthResource(AuthStatus.NOT_AUTHENTICATED, null, null)
        }
    }
}
