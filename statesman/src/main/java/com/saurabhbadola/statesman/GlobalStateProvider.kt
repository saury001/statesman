package com.saurabhbadola.statesman

import android.app.Application
import androidx.lifecycle.LiveData

object GlobalStateProvider {

    fun <T : BaseState> get(application: Application): T? {
        return (application as BaseApplication<T>).globalState.value
    }

    fun <T : BaseState> set(application: Application, t: T) {
        val oldState = (application as BaseApplication<T>).globalState.value
        val new = oldState!!.changeToNewStateFrom(t) as T
        application.globalState.postValue(new)
    }

    fun <T : BaseState> observe(application: Application): LiveData<T> {
        return (application as BaseApplication<T>).globalState
    }

}