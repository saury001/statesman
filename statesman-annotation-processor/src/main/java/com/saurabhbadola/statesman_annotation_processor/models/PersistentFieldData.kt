package com.saurabhbadola.statesman_annotation_processor.models

import com.squareup.kotlinpoet.TypeName

data class PersistentFieldData(
        val fieldName: String,
        val fieldType: TypeName
)