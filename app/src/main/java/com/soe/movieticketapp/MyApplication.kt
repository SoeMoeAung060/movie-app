package com.soe.movieticketapp

import android.app.Application
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.soe.movieticketapp.util.PUBLISHABLE_KEY
import com.stripe.android.PaymentConfiguration
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        PaymentConfiguration.init(
            applicationContext,
            PUBLISHABLE_KEY
        )

        FirebaseApp.initializeApp(this)


        // Initialize Firebase Authentication
        FirebaseAuth.getInstance().signInAnonymously()
            .addOnSuccessListener { Log.d("Auth", "Signed in anonymously") }
            .addOnFailureListener { e -> Log.e("Auth", "Authentication failed: ${e.message}") }

    }

}