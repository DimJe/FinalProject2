package org.techtown.finalproject2.DI

import android.app.Application
import org.koin.core.context.startKoin
import org.techtown.finalproject2.module

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin{
            modules(module)
        }
    }
}