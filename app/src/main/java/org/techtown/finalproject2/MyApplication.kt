package org.techtown.finalproject2

import android.app.Application
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin{
            modules(module)
        }
    }
}