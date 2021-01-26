package com.daggerexample.di.auth

import com.daggerexample.network.auth.AuthAPI
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class AuthModule {
    @AuthScope
    @Provides
    fun provideAuthAPI(retrofit: Retrofit) : AuthAPI {
        return retrofit.create(AuthAPI::class.java)
    }
}