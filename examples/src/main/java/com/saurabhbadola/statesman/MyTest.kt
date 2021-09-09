package com.saurabhbadola.statesman

import com.saurabhbadola.statesman.annotations.State
import com.saurabhbadola.statesman.annotations.StateField


@State
data class MyTest(
    @StateField val valueOne: String,
    @StateField val valueTwo: String
)
