package com.saurabhbadola.statesman


import android.app.Application
import androidx.databinding.Observable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

abstract class BaseViewModel<T : BaseState>(application: Application) :
    AndroidViewModel(application),
    Observable {

    private var _state = CustomMutableLiveData(createInitialState())
    var state: LiveData<T> = _state

    private var currentState = createInitialState()
    var oldState = createInitialState()

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

    public open fun getCurrentState(): T {
        return currentState
    }

    /**
     * @param newStateVal contains the value that would change in the new state
     * */
    fun setState(newStateVal: T) {
        val oldState = getCurrentState()
        val modifiedState = oldState.changeToNewStateFrom(newStateVal) as T

        if (!modifiedState.compareTo(oldState)) {
            this.oldState = getCurrentState()
            this.currentState = modifiedState
            this._state.postValue(modifiedState)
        }
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}
}