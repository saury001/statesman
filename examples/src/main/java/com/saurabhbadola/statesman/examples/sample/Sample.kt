package com.saurabhbadola.statesman.examples.sample

import com.saurabhbadola.statesman.annotations.State
import com.saurabhbadola.statesman.annotations.StateField


@State
data class Sample(
    @StateField var sampleFieldA: String,
    @StateField var sampleFieldB: Int
)