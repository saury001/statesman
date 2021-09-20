package com.saurabhbadola.statesman_annotation_processor.codegen

import com.saurabhbadola.statesman_annotation_processor.models.StateData
import com.squareup.kotlinpoet.*

class StateCodeBuilder(
    private val stateName: String,
    private val data: StateData
) {

    private val viewHolderName = "ViewHolder" // 1
    private val classTypeBoolean = ClassName("kotlin", "Boolean")
    private val className: TypeName = ClassName(data.packageName, stateName)
    private val classNameBR = ClassName("androidx.databinding.library.baseAdapters", "BR")
    private var initializerBlock = ""

    fun build(): TypeSpec {
        val constructorSpecBuilder = FunSpec.constructorBuilder()
        val propertySpecs = mutableListOf<PropertySpec>()
        val memberFields = mutableListOf<String>()
        val changeAnalyserFields = mutableListOf<String>()
        var compareToFields = mutableListOf<String>()

        data.persistentFieldData?.forEach {
            var stringClassName = it.fieldType
            if (it.fieldType == ClassName("java.lang", "String")) {
                stringClassName = ClassName("kotlin", "String")
            } else if (it.fieldType == ClassName("java.util", "ArrayList")) {
                stringClassName = ClassName("kotlin.collections", "ArrayList")
            } else if (it.fieldType == ClassName("java.lang", "Double")) {
                stringClassName = ClassName("kotlin", "Double")
            } else if (it.fieldType == ClassName("java.lang", "Integer")) {
                stringClassName = ClassName("kotlin", "Int")
            } else if (it.fieldType == ClassName("java.lang", "Boolean")) {
                stringClassName = ClassName("kotlin", "Boolean")
            }

//
            constructorSpecBuilder.addParameter(
                ParameterSpec.builder(
                    it.fieldName,
                    stringClassName.copy(nullable = true)
                ).defaultValue("null").build()
            )
            val memberFieldName = "m" + it.fieldName[0].uppercaseChar() + it.fieldName.subSequence(
                1,
                it.fieldName.length
            )
            initializerBlock =
                initializerBlock.plus("if( ${it.fieldName} !=null ) $memberFieldName = ${it.fieldName} \n")
            propertySpecs.add(
                PropertySpec.builder(
                    memberFieldName,
                    stringClassName.copy(nullable = true),
                    KModifier.PUBLIC
                )
                    .mutable(true)
                    .initializer(it.fieldName)
                    .addAnnotation(ClassName("androidx.databinding", "Bindable"))
                    .setter(
                        FunSpec.setterBuilder()
                            .addParameter("value", stringClassName)
                            .addStatement("%N = %L", memberFieldName + "Changed", "true")
                            .addStatement("%N = %N", "field", "value")
                            .addStatement("notifyPropertyChanged(%T.%L)", classNameBR, it.fieldName)
                            .build()
                    )
                    .build()
            )
            propertySpecs.add(
                PropertySpec.builder(
                    memberFieldName + "Changed",
                    classTypeBoolean,
                    KModifier.PRIVATE
                ).initializer("false").mutable(true).build()
            )
            changeAnalyserFields.add("if(newState.${memberFieldName}Changed) oldState.$memberFieldName = newState.$memberFieldName")
            compareToFields.add("this.${memberFieldName} == state.${memberFieldName} &&")
            memberFields.add(memberFieldName)
        }
        var returnStatement = ""
        memberFields.forEach {
            returnStatement += "this.$it,"
        }

        return TypeSpec.classBuilder(stateName) // You’re building a type whose name is adapterName.
            .primaryConstructor(constructorSpecBuilder.build())
            .addProperties(propertySpecs)
            .addModifiers(KModifier.PUBLIC)
            .superclass(ClassName("com.saurabhbadola.statesman", "BaseState"))
            .addFunction(
                FunSpec.builder("getCopy")
                    .returns(className)
                    .addModifiers(KModifier.OVERRIDE)
                    .addStatement("return %L(%L)", stateName, returnStatement)
                    .build()
            )
            .addInitializerBlock(
                CodeBlock.of(
                    initializerBlock
                )
            )
            .addFunction(
                FunSpec.builder("changeToNewStateFrom")
                    .returns(className)
                    .addModifiers(KModifier.OVERRIDE)
                    .addParameter(
                        "newState",
                        ClassName("com.saurabhbadola.statesman", "BaseState")
                    )
                    .addStatement("val oldState = getCopy()")
                    .addStatement("newState as %N", stateName)
                    .addStatement(changeAnalyserFields.joinToString(separator = "\n"))
                    .addStatement("if(newState.mIsLoadingChanged) oldState.mIsLoading = newState.mIsLoading")
                    .addStatement("if(newState.mAlertMessageChanged) oldState.mAlertMessage = newState.mAlertMessage")
                    .addStatement("if(newState.mErrorMessageChanged) oldState.mErrorMessage = newState.mErrorMessage")
                    .addStatement("if(newState.mLoadingMessageChanged) oldState.mLoadingMessage = newState.mLoadingMessage")
                    .addStatement("return oldState")
                    .build()
            ).addFunction(
                FunSpec.builder("compareTo")
                    .returns(classTypeBoolean)
                    .addModifiers(KModifier.OVERRIDE)
                    .addParameter(
                        "state",
                        ClassName("com.saurabhbadola.statesman", "BaseState")
                    )
                    .addStatement("state as %N", stateName)
                    .addStatement("if(")
                    .addStatement(compareToFields.joinToString(separator = "\n"))
                    .addStatement("this.mIsLoading == state.mIsLoading &&")
                    .addStatement("this.mAlertMessage == state.mAlertMessage &&")
                    .addStatement("this.mErrorMessage == state.mErrorMessage &&")
                    .addStatement("this.mLoadingMessage == state.mLoadingMessage")
                    .addStatement(")")
                    .addStatement("return true")
                    .addStatement("return false")
                    .build()
            )
            .build()
    }
}