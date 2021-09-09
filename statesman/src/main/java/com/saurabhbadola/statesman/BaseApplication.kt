package com.saurabhbadola.statesman

import android.app.Application
import androidx.lifecycle.MutableLiveData

abstract class BaseApplication<T : BaseState> : Application() {

    lateinit var globalState: MutableLiveData<T>

    abstract fun createInitialState(): T

    override fun onCreate() {
        super.onCreate()
        globalState = MutableLiveData(createInitialState())
    }
}