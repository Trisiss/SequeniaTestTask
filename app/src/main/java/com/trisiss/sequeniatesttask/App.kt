package com.trisiss.sequeniatesttask

import android.app.Application
import com.trisiss.sequeniatesttask.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * Created by trisiss on 8/8/2021.
 */
class App: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                appModule
            )
        }
    }
}