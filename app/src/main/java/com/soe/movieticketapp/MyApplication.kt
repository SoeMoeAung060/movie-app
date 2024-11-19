package com.soe.movieticketapp

import android.app.Application
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
    }

}