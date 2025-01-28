package com.dev.properti

import android.app.Application
import com.dev.properti.di.AppContainer
import com.dev.properti.di.PropertiContainer

class PropertiApplications:Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = PropertiContainer()
    }
}