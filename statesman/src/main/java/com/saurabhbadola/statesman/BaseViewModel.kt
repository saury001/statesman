package com.saurabhbadola.statesman

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

abstract class BaseViewModel<T : BaseState>(application: Application) :
    AndroidViewModel(application) {

    companion object {
        val DEFAULT_ROUTE = NavigationRoute("No Change", 0)
    }

    private val isInitialStateChanged = true

    internal var currentState = createInitialState()
    internal var oldState = currentState

    private var _observableState = CustomMutableLiveData(currentState)
    var observableState: LiveData<T> = _observableState

    private var _observableRoute = MutableLiveData(DEFAULT_ROUTE)
    var observableRoute: LiveData<NavigationRoute> = _observableRoute

    protected var currentRoute = DEFAULT_ROUTE
    internal var oldRoute = DEFAULT_ROUTE

    abstract fun createInitialState(): T

    fun navigateTo(navigationRoute: NavigationRoute) {
        _observableRoute.postValue(navigationRoute)
    }

    /**
     * @param newStateVal contains the value that would change in the new state
     * */
    fun setState(newStateVal: T) {
        val state = currentState
        val modifiedState = state.changeToNewStateFrom(newStateVal) as T
        this._observableState.postValue(modifiedState)
    }


}