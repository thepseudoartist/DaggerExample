package com.daggerexample.di

import android.app.Application
import com.daggerexample.BaseApplication
import com.daggerexample.SessionManager
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ViewModelFactoryModule::class,
        ActivityBuilderModule::class,
        AppModule::class,
    ]
)
interface AppComponent : AndroidInjector<BaseApplication> {

    fun sessionManager() : SessionManager

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}