package com.sserdiuk.covid_19

import android.app.Application
import android.content.Context
import android.widget.Toast
import timber.log.Timber
import timber.log.Timber.DebugTree


class App : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext

        Timber.plant(DebugTree())
    }

    companion object {
        lateinit var appContext: Context
            private set

//        val appDataBase: AppDataBase by lazy {
//            Room.databaseBuilder(appContext, AppDataBase::class.java, "AppDataBase")
////                .addMigrations()
//                .build()
//        }

        private var toast: Toast? = null
        fun showToast(
            context: Context = appContext,
            message: String,
            duration: Int = Toast.LENGTH_LONG
        ) {
            toast?.cancel()
            toast = Toast.makeText(context, message, duration)
            toast?.show()
        }
    }
}