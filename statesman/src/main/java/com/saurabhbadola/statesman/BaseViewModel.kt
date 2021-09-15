package com.saurabhbadola.statesman


import android.app.Application
import androidx.databinding.Observable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

abstract class BaseViewModel<T : BaseState>(application: Application) :
    AndroidViewModel(application),
    Observable {

    companion object {
        val DEFAULT_ROUTE = NavigationRoute("No Change", 0)
    }

    private var _observableState = CustomMutableLiveData(createInitialState())
    var observableState: LiveData<T> = _observableState

    protected var currentState = createInitialState()
    var oldState = createInitialState()


    private var _observableRoute = MutableLiveData(DEFAULT_ROUTE)
    var observableRoute: LiveData<NavigationRoute> = _observableRoute
    var oldRoute = DEFAULT_ROUTE
    var currentRoute = DEFAULT_ROUTE


    abstract fun createInitialState(): T

    fun navigateTo(navigationRoute: NavigationRoute) {
        oldRoute = currentRoute
        if (navigationRoute.routeName != oldRoute.routeName || navigationRoute.routeValue != oldRoute.routeValue)
            _observableRoute.postValue(navigationRoute)
    }


    /**
     * @param newStateVal contains the value that would change in the new state
     * */
    fun setState(newStateVal: T) {
        val oldState = currentState
        val modifiedState = oldState.changeToNewStateFrom(newStateVal) as T

        if (!modifiedState.compareTo(oldState)) {
            this.oldState = currentState
            this.currentState = modifiedState
            this._observableState.postValue(modifiedState)
        }
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}
}