package com.saurabhbadola.statesman


import androidx.databinding.BaseObservable
import androidx.databinding.Observable
import androidx.databinding.Observable.OnPropertyChangedCallback
import androidx.lifecycle.MutableLiveData

class CustomMutableLiveData<T : BaseObservable?>(t: T) : MutableLiveData<T>() {
    override fun setValue(value: T) {
        super.setValue(value)
        value!!.addOnPropertyChangedCallback(callback)
    }

    var callback: OnPropertyChangedCallback = object : OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable, propertyId: Int) {
            setValue(value!!)
        }
    }

    init {
        setValue(t)
    }
}