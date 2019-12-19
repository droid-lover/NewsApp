package com.assignment.rxjavakotlinway.app

import androidx.multidex.MultiDexApplication

/**
 * Created by Sachin
 */
class ExampleApp : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        private val TAG = ExampleApp::class.java.name
        @get:Synchronized
        var instance: ExampleApp? = null
        private val MAX_HEAP_SIZE = Runtime.getRuntime().maxMemory().toInt()

    }
}
