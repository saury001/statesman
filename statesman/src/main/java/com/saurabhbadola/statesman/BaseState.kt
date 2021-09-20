package com.saurabhbadola.statesman

import androidx.databinding.BaseObservable

abstract class BaseState(
) : BaseObservable() {
    /**
     * This method will called by the reference of the new state
     * @param newState contains the old state values
     * */
    abstract fun changeToNewStateFrom(newState: BaseState): BaseState // Convert current state to new and return the copy
    abstract fun getCopy(): BaseState //Return a new copy of the current instance
    abstract fun compareTo(state: BaseState): Boolean //Return true if the instance is same, false otherwise
}