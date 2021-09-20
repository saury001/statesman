package com.saurabhbadola.statesman.examples.sample

import android.app.Application
import com.saurabhbadola.statesman.BaseViewModel

class SampleViewModel(application: Application) :
    BaseViewModel<SampleState>(application = application) {

    /**
     * Array of some sample name strings
     */
    companion object {
        val sampleNames = arrayOf(
            "Mary", "John", "Ashley", "Alaska", "Chip", "Jake", "Narobi", "Lara", "Eagle", "Chris"
        )
    }


    /**
     * Override this method and return an instance of generated state class that will act as the starting state
     */
    override fun createInitialState(): SampleState {
        return SampleState(
            "Justin",
            0
        )
    }


    /**
     * Method to update field B value of the state
     */
    fun setSampleFieldB(fieldB: Int) {
        setState(
            SampleState(
                sampleFieldB = fieldB
            )
        )
    }

    /**
     * Method to update field A value of the state
     */
    fun setSampleFieldA(fieldA: String) {
        setState(
            SampleState(
                sampleFieldA = fieldA
            )
        )
    }


    /**
     * Method randomly generates an index and pulls the string of that index from sampleNames and set that as value of field A of the state
     */
    fun randomizeFieldA() {
        val randomIndex = (Math.random() * sampleNames.size).toInt()
        setState(
            SampleState(
                sampleFieldA = sampleNames[randomIndex]
            )
        )
    }


    /**
     * Method to clear input i.e. clear the value of sample field A
     */
    fun clearInput() = setState(
        SampleState(
            sampleFieldA = ""
        )
    )

}