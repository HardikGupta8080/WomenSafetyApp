package com.example.womensafetyapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WomenSafetyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
