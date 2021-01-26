package com.daggerexample.di.main

import com.daggerexample.network.main.MainAPI
import com.daggerexample.ui.main.posts.PostsRecyclerViewAdapter
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class MainModule {
    @MainScope
    @Provides
    fun provideMainAPI(retrofit: Retrofit) : MainAPI = retrofit.create(MainAPI::class.java)

    @MainScope
    @Provides
    fun provideAdapter(): PostsRecyclerViewAdapter = PostsRecyclerViewAdapter()
}