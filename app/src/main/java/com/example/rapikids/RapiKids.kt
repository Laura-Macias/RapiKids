package com.example.rapikids

import android.app.Application
import com.google.firebase.FirebaseApp

class RapiKidsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}
