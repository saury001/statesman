package com.saurabhbadola.statesman_annotation_processor.models

import com.saurabhbadola.statesman_annotation_processor.models.PersistentFieldData

data class StateData(
        val packageName: String,
        val modelName: String,
        val persistentFieldData: List<PersistentFieldData>?
)