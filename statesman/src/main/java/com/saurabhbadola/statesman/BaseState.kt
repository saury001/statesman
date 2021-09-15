package com.saurabhbadola.statesman

import androidx.databinding.BaseObservable

abstract class BaseState(
) : BaseObservable() {
    /**
     * This method will called by the reference of the new state
     * @param newState contains the old state values
     * */
    abstract fun changeToNewStateFrom(newState: BaseState): BaseState
    abstract fun getCopy(): BaseState
    abstract fun compareTo(state:BaseState):Boolean
}