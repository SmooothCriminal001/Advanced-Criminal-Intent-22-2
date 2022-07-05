package com.bignerdranch.android.advanced_criminal_intent_22_2

import android.app.Application

class CrimeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        CrimeRepository.initialize(this)
    }
}