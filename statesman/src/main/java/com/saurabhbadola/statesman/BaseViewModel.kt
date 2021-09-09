package com.saurabhbadola.statesman


import android.app.Application
import androidx.databinding.Observable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

abstract class BaseViewModel<T : BaseState>(application: Application) :
    AndroidViewModel(application),
    Observable {


    var state = CustomMutableLiveData(createInitialState())
    var old = createInitialState()
    var route: MutableLiveData<NavigationRoute> =
        MutableLiveData(
            NavigationRoute("No Change", 0)
        )
    var oldRoute = route.value


    abstract fun createInitialState(): T

    fun navigateTo(navigationRoute: NavigationRoute) {
        oldRoute = route.value
        route.postValue(navigationRoute)
    }

    public open fun getCurrentState(): T? {
        return state.value
    }

    /**
     * @param newStateVal contains the value that would change in the new state
     * */
    fun setState(newStateVal: T) {
        val oldState = getCurrentState()!!
        old = getCurrentState()!!
        this.state.postValue(oldState.changeToNewStateFrom(newStateVal) as T)
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}
}