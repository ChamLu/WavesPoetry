package com.cc.t820

import android.app.Application
import android.content.Context

class MyApp : Application() {

    companion object {
        private var instance: MyApp? = null

        fun getInstance(): MyApp? {
            return instance
        }
    }


    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        //   MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}