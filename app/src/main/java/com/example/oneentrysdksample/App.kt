package com.example.oneentrysdksample

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {

    init {
        instance = this
    }

    companion object {

        private var instance: App? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }
}