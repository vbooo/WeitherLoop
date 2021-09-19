package com.weither.weitherloop

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Our custom Application class to inject our dependences
 */
@HiltAndroidApp
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}
